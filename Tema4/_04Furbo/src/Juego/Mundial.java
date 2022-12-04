package Juego;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Mundial {

    //URL de la dirección del servidor
    private static final String SERVIDOR = "jdbc:mysql://dns11036.phdns11.es";

    //Nombre de usuario de la base de datos
    private static String user = "acastro";
    //Contraseña
    private static String password = "1234";
    //Nombre de la base de datos
    private static String baseDatos = "ad2223_acastro";

    //Booleano que comprueba si los octavos de final se han jugado
    private static boolean octavosJugados = false;

    //Booleano que comprueba si los cuartos de fiinal se han jugado
    private static boolean cuartosJugados = false;

    //Booleano que comprueba si las semifinales se han jugado
    private static boolean semifinalesJugadas = false;

    //Booleano que comprueba si la final se ha jugado
    private static boolean finalJugada = false;

    //Entero que nos dirá la velocidad a la que se reproducirán los partidos de cada fase
    private static int velocidadPartido = 0;

    //Conexión con la base de datos
    private static Connection connection;
    private static Statement st;

    public static void main(String[] args) {
        try {
            connection = conectar();
            if (connection != null) {
                st = connection.createStatement();
                reiniciar();
                int opc;
                do {
                    Mostrarmenu(); //Muestra el menú
                    opc = Utilidades.validarOpcion(0, 5);
                    switch (opc) {
                        case 1 -> {
                            if (octavosJugados) {
                                System.out.println("Los octavos de final ya se han jugado");
                                System.out.println("====Estos son los resultados====");
                                mostrarResultadosOctavos();
                            } else {
                                menuVelocidades();
                                int opcion = Utilidades.validarOpcion(1, 4);
                                switch (opcion) {
                                    case 1 -> velocidadPartido = 1000;
                                    case 2 -> velocidadPartido = 750;
                                    case 3 -> velocidadPartido = 350;
                                    case 4 -> velocidadPartido = 100;
                                }
                                jugarOctavos();
                            }

                        }
                        case 2 -> {
                            if (cuartosJugados) {
                                System.out.println("Los cuartos de final ya se han jugado");
                                System.out.println("====Estos son los resultados====");
                                mostrarResultadosCuartos();
                            } else {
                                if (octavosJugados){
                                    menuVelocidades();
                                    int opcion = Utilidades.validarOpcion(1, 4);
                                    switch (opcion) {
                                        case 1 -> velocidadPartido = 1000;
                                        case 2 -> velocidadPartido = 750;
                                        case 3 -> velocidadPartido = 350;
                                        case 4 -> velocidadPartido = 100;
                                    }
                                    jugarCuartos();
                                }else{
                                    System.out.println("Antes se deben de jugar los octavos de final");
                                }

                            }

                        }
                        case 3 -> {
                            if (semifinalesJugadas) {
                                System.out.println("La semifinal ya se ha jugado");
                                System.out.println("====Estos son los resultados====");
                                mostrarResultadosSemifinales();
                            } else {
                                if(cuartosJugados){
                                    menuVelocidades();
                                    int opcion = Utilidades.validarOpcion(1, 4);
                                    switch (opcion) {
                                        case 1 -> velocidadPartido = 1000;
                                        case 2 -> velocidadPartido = 750;
                                        case 3 -> velocidadPartido = 350;
                                        case 4 -> velocidadPartido = 100;
                                    }
                                    jugarSemifinales();
                                }else {
                                    System.out.println("Antes se deben de jugar los cuartos de final");
                                }

                            }
                        }
                        case 4 -> {

                            if (finalJugada) {
                                System.out.println("La final ya se ha jugado");
                                System.out.println("====Este ha sido el resultado====");
                                mostrarResultadoFinal();
                            } else {
                                if (semifinalesJugadas){
                                    menuVelocidades();
                                    int opcion = Utilidades.validarOpcion(1,4);
                                    switch (opcion){
                                        case 1 -> velocidadPartido = 1000;
                                        case 2 -> velocidadPartido = 750;
                                        case 3 -> velocidadPartido = 350;
                                        case 4 -> velocidadPartido = 100;
                                    }
                                    jugarFinal();
                                }else {
                                    System.out.println("Antes se deben de jugar las semifinales");
                                }

                            }
                        }
                        case 5 -> {
                            reiniciar();
                            System.out.println("Reinicio completado :D");
                        }
                        case 0 -> {
                            System.out.println("Adiós :)");

                        }
                    }
                } while (opc != 0);

                st.close();
            }

            if (st != null) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }

    }

    //Muestra las opciones que tiene el usuario para reproducir los partidos de la fase del torneo
    private static void menuVelocidades() {
        System.out.println("""
                ====Diga a que velocidad desea que pasen los partidos====
                1.- Lento
                2.- Medio
                3.- Rápido
                4.- Muy rápido
                """);
    }


    /**
     * Inserta los equipos en la base de datos mediante la lectura de un fichero en el que tenemos los 16 equipos que van
     * a participar. En este caso hemos introducido los 16 equipos que se encuentran en octavos de final del mundial de catar
     * Precondición: Que el fichero exista
     * Postcondición: Ninguna
     * @param datos archivo de texto en el que se encuentran el nombre de nuestros equipos
     */
    private static void insertarEquipos(File datos) {
        String cadena;
        try {
            BufferedReader br = new BufferedReader(new FileReader(datos));
            cadena = br.readLine();
            while (cadena != null) {
                st.execute(cadena);
                cadena = br.readLine();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException.getMessage() + " :/");
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage() + " :(");
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage() + " :'(:");
        }

    }


    /**
     * Crea las tablas que necesitaremos en nuestra base de datos para que el programa funcione
     * Precondición: Ninguna
     * Postcondición: Ninguna
     * @param nombreTabla el nombre que recibirá la tabla
     * @param campos string con el nombre de los campos y el tipo de datos que van a recoger
     */
    private static void crearTabla(String nombreTabla, String[] campos) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ad2223_acastro." + nombreTabla + "(");
        sql.append(campos[0]);
        for (int i = 1; i < campos.length; i++) {
            sql.append(",").append(campos[i]);
        }
        sql.append(" )");
        try {
            st.executeUpdate(sql.toString());
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Muestra el resultado que se ha obtenido en la final una vez ya jugada
     * Precondición: Que la final ya se haya jugado
     * PostCondición: Ninguna
     */
    private static void mostrarResultadoFinal() {
        String sql = "SELECT * FROM " + baseDatos + ".Final";
        try {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString("equipoA") + " " + rs.getInt("golesA") + "-" + rs.getInt("golesB") + " " + rs.getString("equipoB"));
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    /**
     * Este método simula el juego de la final con los dos quipos que hayan llegado a la misma
     * Precondición: Haber jugado antes las fases anteriores
     * Postcondición: Ninguna
     */
    private static void jugarFinal() {
        Scanner sc = new Scanner(System.in);
        String equipoA, equipoB;
        String[] equipos = conseguirEquiposFinal();
            equipoA = equipos[0];
            equipoB = equipos[1];
            partido(equipoA, equipoB);
        System.out.println("Y aquí concluye nuestro mundial espero que lo hayan disfrutado :D");
        finalJugada = true;
    }

    /**
     * Consigue los equipos que se han clasificado para la final. Estos son los que han ganado tres partidos. Los
     * almacena en un array que usaremos para jugar la final
     * Precondición: Ninguna
     * postCondición: Ninguna
     * @return devuelve el array con los equipos que han llegado a la final
     */
    private static String[] conseguirEquiposFinal() {
        String[] equipos = new String[2];
        String sql = "SELECT idEquipo FROM " + baseDatos + ".EquiposFutbol WHERE ganados = 3";
        try {
            ResultSet rs = st.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                equipos[i] = rs.getString("idEquipo");
                i++;
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
        return equipos;
    }

    /**
     * Muestra el resultado que se ha obtenido en las semifinales una vez ya jugada
     * Precondición: Que la semifinal ya se haya jugado
     * PostCondición: Ninguna
     */
    private static void mostrarResultadosSemifinales() {
        String sql = "SELECT * FROM " + baseDatos + ".Semifinales";
        try {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString("equipoA") + " " + rs.getInt("golesA") + "-" + rs.getInt("golesB") + " " + rs.getString("equipoB"));
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    /**
     * Este método simula el juego de las semifinales con los equipos que hayan llegado a las mismas.
     * Precondición: Haber jugado antes las fases anteriores
     * Postcondición: Ninguna
     */
    private static void jugarSemifinales() {
        Scanner sc = new Scanner(System.in);
        String equipoA, equipoB, enter;
        int j = -1, partidos = 2;
        String[] equipos = conseguirEquiposSemis();
        for (int i = 0; i < partidos; i++) {
            j++;
            equipoA = equipos[j];
            j++;
            equipoB = equipos[j];
            partido(equipoA, equipoB);
            if (i != 1) {
                System.out.println("Pulse enter para iniciar el siguiente partido");
                enter = sc.nextLine();
            }
        }
        System.out.println("Han finalizado las semifinales");
        semifinalesJugadas = true;
    }

    /**
     * Consigue los equipos que se han clasificado para las semifinales. Estos son los que han ganado dos partidos. Los
     * almacena en un array que usaremos para jugar la semifinal
     * Precondición: Ninguna
     * postCondición: Ninguna
     * @return devuelve el array con los equipos que han llegado a las semifinales
     */
    private static String[] conseguirEquiposSemis() {
        String[] equipos = new String[4];
        String sql = "SELECT idEquipo FROM " + baseDatos + ".EquiposFutbol WHERE ganados = 2";
        try {
            ResultSet rs = st.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                equipos[i] = rs.getString("idEquipo");
                i++;
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
        ordenarAleatoriamente(equipos);
        return equipos;
    }

    /**
     * Muestra el resultado que se ha obtenido en los cuartos de final una vez ya jugados.
     * Precondición: Que los cuartos de final ya se hayan jugado
     * PostCondición: Ninguna
     */
    private static void mostrarResultadosCuartos() {
        String sql = "SELECT * FROM " + baseDatos + ".Cuartos";
        try {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString("equipoA") + " " + rs.getInt("golesA") + "-" + rs.getInt("golesB") + " " + rs.getString("equipoB"));
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    /**
     * Este método simula el juego de los cuartos de final con los 8 quipos que hayan llegado a los mismos.
     * Precondición: Haber jugado antes las fases anteriores
     * Postcondición: Ninguna
     */
    private static void jugarCuartos() {
        Scanner sc = new Scanner(System.in);
        String equipoA, equipoB, enter;
        int j = -1, partidos = 4;
        String[] equipos = conseguirEquiposCuartos();
        for (int i = 0; i < partidos; i++) {
            j++;
            equipoA = equipos[j];
            j++;
            equipoB = equipos[j];
            partido(equipoA, equipoB);
            if (i != 3) {
                System.out.println("Pulse enter para iniciar el siguiente partido");
                enter = sc.nextLine();
            }
        }
        System.out.println("Han finalizado los cuartos de final");
        cuartosJugados = true;
    }

    /**
     * Consigue los equipos que se han clasificado para los cuartos de final. Estos son los que han ganado un partido. Los
     * almacena en un array que usaremos para jugar los cuartos de final
     * Precondición: Ninguna
     * postCondición: Ninguna
     * @return devuelve el array con los equipos que han llegado a los cuartos de final
     */
    private static String[] conseguirEquiposCuartos() {
        String[] equipos = new String[8];
        String sql = "SELECT idEquipo FROM " + baseDatos + ".EquiposFutbol WHERE ganados = 1";
        try {
            ResultSet rs = st.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                equipos[i] = rs.getString("idEquipo");
                i++;
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
        ordenarAleatoriamente(equipos);
        return equipos;

    }

    /**
     * Muestra el resultado que se ha obtenido en los octavos de final una vez ya jugados.
     * Precondición: Que los octavos de final ya se hayan jugado
     * PostCondición: Ninguna
     */
    private static void mostrarResultadosOctavos() {
        String sql = "SELECT * FROM " + baseDatos + ".Octavos";
        try {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getString("equipoA") + " " + rs.getInt("golesA") + "-" + rs.getInt("golesB") + " " + rs.getString("equipoB"));
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    /**
     * Este método simula el juego de los octavos de final con los 16 de nuestra base de datos
     * Precondición: Haber introducido antes los equipos en la base de datos
     * Postcondición: Ninguna
     */
    private static void jugarOctavos() {
        Scanner sc = new Scanner(System.in);
        String equipoA, equipoB, enter;
        int j = -1, partidos = 8;
        String[] equipos = conseguirEquiposOctavos();
        for (int i = 0; i < partidos; i++) {
            j++;
            equipoA = equipos[j];
            j++;
            equipoB = equipos[j];
            partido(equipoA, equipoB);
            if (i != 7) {
                System.out.println("Pulse enter para iniciar el siguiente partido");
                enter = sc.nextLine();
            }
        }
        System.out.println("Han finalizado los octavos de final");
        octavosJugados = true;


    }

    /**
     * Consigue los equipos que se van a disputar el mundial. Los almacena en un array que usaremos para jugar los octavos
     * de final
     * Precondición: Ninguna
     * postCondición: Ninguna
     * @return devuelve el array con los equipos van a jugar el mundial
     */
    private static String[] conseguirEquiposOctavos() {
        String[] equipos = new String[16];
        String sql = "SELECT idEquipo FROM " + baseDatos + ".EquiposFutbol";

        try {
            ResultSet rs = st.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                equipos[i] = rs.getString("idEquipo");
                i++;
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }

        ordenarAleatoriamente(equipos);

        return equipos;
    }

    /**
     * Ordena aleatoriamente nuestro array en el que se encuentran nuestros equipos qeu van a disputar el mundial
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipos array de strings donde se encuentran los nombres de nuestros equipos
     */
    private static void ordenarAleatoriamente(String[] equipos) {
        List<String> listaEquipos = new ArrayList<>(Arrays.asList(equipos));

        Collections.shuffle(listaEquipos);

        for (int i = 0; i < listaEquipos.size(); i++) {
            equipos[i] = listaEquipos.get(i);
        }

    }

    /**
     * Este método se encarga de toda la lógica del partido, calcula las posesiones suma los goles,
     * juega la prórroga en caso de haberla e introduce los datos en donde se debe.
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipoA un equipo de nuestro array con los equipos
     * @param equipoB El equipo que se enfrentará al equipo A. El equipo B también se encuentra en nuestro array
     */
    private static void partido(String equipoA, String equipoB) {
        int golesA = 0, golesB = 0, aleatorioA, aleatorioB, diferencia, posesionA = 50, posesionB = 50, contador = 0;

        System.out.println("Va a dar comienzo el " + equipoA + " vs " + equipoB);
        do {
            aleatorioA = new Random().nextInt(1, 20);
            aleatorioB = new Random().nextInt(1, 20);
            diferencia = aleatorioA - aleatorioB;
            posesionA += diferencia;
            posesionB -= diferencia;
            //Comprobamos posesiones
            if (posesionA > 100) {
                posesionA = 100;
            }
            if (posesionA < 0) {
                posesionA = 0;
            }
            if (posesionB > 100) {
                posesionB = 100;
            }
            if (posesionB < 0) {
                posesionB = 0;
            }
            //narramos el partido
            System.out.println(equipoA + " posesion " + posesionA + "%   " + posesionB + "% posesion " + equipoB + "   minuto: " + contador);
            if (posesionA >= 95) {
                System.out.println("GOOOOOOLLLL de " + equipoA);
                golesA++; //Sumamos los goles
                System.out.println(equipoA + " " + golesA + "-" + golesB + " " + equipoB);
                posesionA = 50;
                posesionB = 50;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    System.err.println(interruptedException.getMessage());
                }
            }
            if (posesionB >= 95) {
                System.out.println("GOOOOOOLLLL de " + equipoB);
                golesB++;
                System.out.println(equipoA + " " + golesA + "-" + golesB + " " + equipoB);
                posesionA = 50;
                posesionB = 50;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    System.err.println(interruptedException.getMessage());
                }

            }
            contador++;
            try {
                Thread.sleep(velocidadPartido);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        } while (contador <= 90);

        //Juego de la prórroga en caso de haberla
        if (golesA == golesB) {
            do {
                aleatorioA = new Random().nextInt(1, 20);
                aleatorioB = new Random().nextInt(1, 20);
                diferencia = aleatorioA - aleatorioB;
                posesionA += diferencia;
                posesionB -= diferencia;
                if (posesionA > 100) {
                    posesionA = 100;
                }
                if (posesionA < 0) {
                    posesionA = 0;
                }
                if (posesionB > 100) {
                    posesionB = 100;
                }
                if (posesionB < 0) {
                    posesionB = 0;
                }
                System.out.println(equipoA + " posesion " + posesionA + "%   " + posesionB + "% " + equipoB + "  Prorróga");
                if (posesionA >= 95) {
                    System.out.println("GOOOOOOLLLL de " + equipoA);
                    golesA++;
                }
                if (posesionB >= 95) {
                    System.out.println("GOOOOOOLLLL de " + equipoB);
                    golesB++;

                }
                try {
                    Thread.sleep(velocidadPartido);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } while (golesA == golesB);
        }

        System.out.println("Fin del partido " + equipoA + " " + golesA + "-" + golesB + " " + equipoB);

        //introducción de los datos del partido según la fase a la que pertenezca el partido
        if (!octavosJugados){
            insertarDatosOctavos(equipoA, equipoB, golesA, golesB);
        }
        if (!cuartosJugados && octavosJugados){
            insertarDatosCuartos(equipoA, equipoB, golesA, golesB);
        }
        if (!semifinalesJugadas && cuartosJugados && octavosJugados){
            insertarDatosSemifinal(equipoA, equipoB, golesA, golesB);
        }
        if (!finalJugada && semifinalesJugadas && cuartosJugados && octavosJugados ){
            insertarDatosFinal(equipoA, equipoB, golesA, golesB);
        }

        insertarDatosEquipoA(equipoA, golesA, golesB);
        insertarDatosEquiposB(equipoB, golesA, golesB);

    }

    /**
     * Inserta los datos de nuestra final en la base de datos
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipoA equipo local
     * @param equipoB equipo visitante
     * @param golesA goles anotados por el equipo A
     * @param golesB goles anotados por el equipo B
     */
    private static void insertarDatosFinal(String equipoA, String equipoB, int golesA, int golesB) {
        String sql = "INSERT INTO " + baseDatos + ".Final VALUES ('" + equipoA + "', '" + equipoB + "' , " + golesA + ", " + golesB + ")";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al introducir los datos del partido. Por favor reinicie el campeonato :(");
        }
    }

    /**
     * Inserta los datos de las semifinales en la base de datos
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipoA equipo local
     * @param equipoB equipo visitante
     * @param golesA goles anotados por el equipo A
     * @param golesB goles anotados por el equipo B
     */
    private static void insertarDatosSemifinal(String equipoA, String equipoB, int golesA, int golesB) {
        String sql = "INSERT INTO " + baseDatos + ".Semifinales VALUES ('" + equipoA + "', '" + equipoB + "' , " + golesA + ", " + golesB + ")";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al introducir los datos del partido. Por favor reinicie el campeonato :(");
        }
    }

    /**
     * Inserta los datos de los cuartos de final en la base de datos
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipoA equipo local
     * @param equipoB equipo visitante
     * @param golesA goles anotados por el equipo A
     * @param golesB goles anotados por el equipo B
     */
    private static void insertarDatosCuartos(String equipoA, String equipoB, int golesA, int golesB) {
        String sql = "INSERT INTO " + baseDatos + ".Cuartos VALUES ('" + equipoA + "', '" + equipoB + "' , " + golesA + ", " + golesB + ")";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al introducir los datos del partido. Por favor reinicie el campeonato :(");
        }
    }

    /**
     * Inserta los datos del equipo visitante en la tabla general de nuestro campeonato
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipoB equipo que ha jugado de visitante y del que vamos a agregar los datos
     * @param golesA goles que ha recibido el equipo
     * @param golesB goles que ha metido el equipo
     */
    private static void insertarDatosEquiposB(String equipoB, int golesA, int golesB) {
        String sql;
        if (golesA < golesB) {
            int ganados = conseguirPartidosGanados(equipoB);
            sql = "UPDATE " + baseDatos + ".EquiposFutbol SET ganados = " + ganados + ", golesMarcados = " + golesB + ", golesRecibidos = " + golesA + " WHERE idEquipo= '" + equipoB + "';";
            try {
                st.executeUpdate(sql);
            } catch (SQLException e) {
                System.err.println("Ha sucedido un error con la base de datos");
            }
        } else {
            int perdidos = conseguirPartidosPerdidos(equipoB);
            sql = "UPDATE " + baseDatos + ".EquiposFutbol SET perdidos = " + perdidos + ", golesMarcados = " + golesB + ", golesRecibidos = " + golesA + " WHERE idEquipo= '" + equipoB + "';";
            try {
                st.executeUpdate(sql);
            } catch (SQLException e) {
                System.err.println("Ha sucedido un erro con la base de datos");
            }
        }
    }


    /**
     * Inserta los datos del equipo local en la tabla general de nuestro campeonato
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipoA equipo que ha jugado de local y del que vamos a agregar los datos
     * @param golesA goles que ha metido el equipo
     * @param golesB goles que ha recibido el equipo
     */
    private static void insertarDatosEquipoA(String equipoA, int golesA, int golesB) {
        String sql;
        if (golesA > golesB) {
            int ganados = conseguirPartidosGanados(equipoA);
            sql = "UPDATE " + baseDatos + ".EquiposFutbol SET ganados = " + ganados + ", golesMarcados = " + golesA + ", golesRecibidos = " + golesB + " WHERE idEquipo= '" + equipoA + "';";
            try {
                st.executeUpdate(sql);
            } catch (SQLException e) {
                System.err.println("Ha sucedido un error con la base de datos");
            }
        } else {
            int perdidos = conseguirPartidosPerdidos(equipoA);
            sql = "UPDATE " + baseDatos + ".EquiposFutbol SET perdidos = " + perdidos + ", golesMarcados = " + golesA + ", golesRecibidos = " + golesB + " WHERE idEquipo= '" + equipoA + "';";
            try {
                st.executeUpdate(sql);
            } catch (SQLException e) {
                System.err.println("Ha sucedido un erro con la base de datos");
            }
        }
    }

    /**
     * Nos recoge de la base de datos los partidos que ha perdido un equipo para que en caso de que pierda sumarle uno
     * al contador
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipo equipo del que vamos a conseguir los partidos perdidos
     * @return el número de partidos perdidos del equipo
     */
    private static int conseguirPartidosPerdidos(String equipo) {
        int perdidos = 0;
        String sql = "SELECT perdidos FROM " + baseDatos + ".EquiposFutbol WHERE idEquipo = '" + equipo + "'";
        try {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                perdidos = rs.getInt("perdidos");
                perdidos++;
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return perdidos;
    }

    /**
     * Nos recoge de la base de datos los partidos que ha ganado un equipo para que en caso de que gane sumarle uno
     * al contador
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipo equipo del que vamos a conseguir los partidos ganados
     * @return el número de partidos ganados del equipo
     */
    private static int conseguirPartidosGanados(String equipo) {
        int ganados = 0;
        String sql = "SELECT ganados FROM " + baseDatos + ".EquiposFutbol WHERE idEquipo = '" + equipo + "'";
        try {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ganados = rs.getInt("ganados");
                ganados++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ganados;
    }

    /**
     * Inserta los datos de los octavos de final en la base de datos
     * Precondición: Ninguna
     * PostCondición: Ninguna
     * @param equipoA equipo local
     * @param equipoB equipo visitante
     * @param golesA goles anotados por el equipo A
     * @param golesB goles anotados por el equipo B
     */
    private static void insertarDatosOctavos(String equipoA, String equipoB, int golesA, int golesB) {
        String sql = "INSERT INTO " + baseDatos + ".Octavos VALUES ('" + equipoA + "', '" + equipoB + "' , " + golesA + ", " + golesB + ")";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al introducir los datos del partido. Por favor reinicie el campeonato :(");
        }
    }

    /**
     * Reinicia el programa borrando las tablas del torneo en curso y volviéndolas a crear vacías.
     * Precondición: Ninguna
     * PostCondición: Ninguna
     */
    private static void reiniciar() {
        eliminarTablas();
        crearTabla("EquiposFutbol", new String[]{"idEquipo varchar(25)", "ganados int", "empartados int", "perdidos int", "golesMarcados int", "golesRecibidos int"});
        crearTabla("Octavos", new String[]{"equipoA VARCHAR(25)", "equipoB VARCHAR(25)", "golesA INT", "golesB INT"});
        crearTabla("Cuartos", new String[]{"equipoA VARCHAR(25)", "equipoB VARCHAR(25)", "golesA INT", "golesB INT"});
        crearTabla("Semifinales", new String[]{"equipoA VARCHAR(25)", "equipoB VARCHAR(25)", "golesA INT", "golesB INT"});
        crearTabla("Final", new String[]{"equipoA VARCHAR(25)", "equipoB VARCHAR(25)", "golesA INT", "golesB INT"});
        File datosEquipos = new File("datosEquipos.txt");
        insertarEquipos(datosEquipos);
        octavosJugados = false;
        cuartosJugados = false;
        semifinalesJugadas = false;
        finalJugada = false;

    }

    /**
     * Borra las tablas de nuestra base de datos
     * Precondición: Que las tablas ya existas
     * PostCondición: Ninguna
     */
    private static void eliminarTablas() {
        String sql = "DROP TABLE ad2223_acastro.EquiposFutbol, ad2223_acastro.Octavos, ad2223_acastro.Cuartos, ad2223_acastro.Semifinales, ad2223_acastro.Final ";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error reiniciando el programa");
        }
    }


    /**
     * Método qeu conecta con la base de datos
     * Precondición: conexión a internet y que la base de datos, el usuario y la contraseña sean correctos
     * PostCondición: Ninguna
     * @return conexión xon la base de datos
     */
    public static Connection conectar() {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(SERVIDOR, "ad2223_" + user, password);
        } catch (SQLException e) {
            System.out.println("No se ha podido conectar a la base de datos");
            connection = null;
        } catch (ClassNotFoundException e) {
            System.out.println("Error con el jdbc driver");
            connection = null;
        }
        return connection;
    }

    /**
     * Muestra el menú al usuario con las opciones que tiene para jugar el mundial
     * Precondición: Ninguna
     * PostCondición: Ninguna
     */
    private static void Mostrarmenu() {

        System.out.println("- - - - - - Mundial Españita 2022 - - - - - -");
        System.out.println("""
                1.- Iniciar octavos de final
                2.- Iniciar cuartos de final
                3.- Iniciar las semifinales
                4.- Iniciar la gran final
                5.- Reiniciar
                0.- Salir
                """);
    }
}