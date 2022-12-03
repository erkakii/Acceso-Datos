package Juego;

import java.io.*;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Mundial {

    private static final String SERVIDOR = "jdbc:mysql://dns11036.phdns11.es";

    private static String user = "acastro";
    //Contraseña
    private static String password = "1234";
    //Nombre de la base de datos del emisor
    private static String baseDatos = "ad2223_acastro";

    private static boolean octavosJugados = false;

    private static boolean cuartosJugados = false;

    private static boolean semifinalesJugadas = false;

    private static boolean finalJugada = false;

    private static int velocidadPartido = 0;

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
                    Mostrarmenu();
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
                                menuVelocidades();
                                int opcion = Utilidades.validarOpcion(1, 4);
                                switch (opcion) {
                                    case 1 -> velocidadPartido = 1000;
                                    case 2 -> velocidadPartido = 750;
                                    case 3 -> velocidadPartido = 350;
                                    case 4 -> velocidadPartido = 100;
                                }
                                jugarCuartos();
                            }

                        }
                        case 3 -> {
                            if (semifinalesJugadas) {
                                System.out.println("La semifinal ya se ha jugado");
                                System.out.println("====Estos son los resultados====");
                                mostrarResultadosSemifinales();
                            } else {
                                menuVelocidades();
                                int opcion = Utilidades.validarOpcion(1, 4);
                                switch (opcion) {
                                    case 1 -> velocidadPartido = 1000;
                                    case 2 -> velocidadPartido = 750;
                                    case 3 -> velocidadPartido = 350;
                                    case 4 -> velocidadPartido = 100;
                                }
                                jugarSemifinales();
                            }
                        }
                        case 4 -> {

                            if (finalJugada) {
                                System.out.println("La final ya se ha jugado");
                                System.out.println("====Este ha sido el resultado====");
                                mostrarResultadoFinal();
                            } else {
                                menuVelocidades();
                                int opcion = Utilidades.validarOpcion(1,4);
                                switch (opcion){
                                    case 1 -> velocidadPartido = 1000;
                                    case 2 -> velocidadPartido = 750;
                                    case 3 -> velocidadPartido = 350;
                                    case 4 -> velocidadPartido = 100;
                                }
                                jugarFinal();
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

    private static void menuVelocidades() {
        System.out.println("""
                ====Diga a que velocidad desea que pasen los partidos====
                1.- Lento
                2.- Medio
                3.- Rápido
                4.- Muy rápido
                """);
    }


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
            System.err.println(fileNotFoundException.getMessage());
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage() + " :(");
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage() + " :'(:");
        }

    }


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

    private static void jugarFinal() {
        Scanner sc = new Scanner(System.in);
        String equipoA, equipoB, enter;
        String[] equipos = conseguirEquiposFinal();
            equipoA = equipos[0];
            equipoB = equipos[1];
            partido(equipoA, equipoB);
        System.out.println("Y aquí concluye nuestro mundial espero que lo hayan disfrutado :D");
        finalJugada = true;
    }

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
        return equipos;
    }

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
        return equipos;

    }

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
        return equipos;
    }

    private static void partido(String equipoA, String equipoB) {
        int golesA = 0, golesB = 0, aleatorioA, aleatorioB, diferencia, posesionA = 50, posesionB = 50, contador = 0;

        System.out.println("Va a dar comienzo el " + equipoA + " vs " + equipoB);
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
            System.out.println(equipoA + " posesion " + posesionA + "%   " + posesionB + "% posesion " + equipoB + "   minuto: " + contador);
            if (posesionA >= 95) {
                System.out.println("GOOOOOOLLLL de " + equipoA);
                golesA++;
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

    private static void insertarDatosFinal(String equipoA, String equipoB, int golesA, int golesB) {
        String sql = "INSERT INTO " + baseDatos + ".Final VALUES ('" + equipoA + "', '" + equipoB + "' , " + golesA + ", " + golesB + ")";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al introducir los datos del partido. Por favor reinicie el campeonato :(");
        }
    }

    private static void insertarDatosSemifinal(String equipoA, String equipoB, int golesA, int golesB) {
        String sql = "INSERT INTO " + baseDatos + ".Semifinales VALUES ('" + equipoA + "', '" + equipoB + "' , " + golesA + ", " + golesB + ")";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al introducir los datos del partido. Por favor reinicie el campeonato :(");
        }
    }

    private static void insertarDatosCuartos(String equipoA, String equipoB, int golesA, int golesB) {
        String sql = "INSERT INTO " + baseDatos + ".Cuartos VALUES ('" + equipoA + "', '" + equipoB + "' , " + golesA + ", " + golesB + ")";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al introducir los datos del partido. Por favor reinicie el campeonato :(");
        }
    }

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

    private static void insertarDatosOctavos(String equipoA, String equipoB, int golesA, int golesB) {
        String sql = "INSERT INTO " + baseDatos + ".Octavos VALUES ('" + equipoA + "', '" + equipoB + "' , " + golesA + ", " + golesB + ")";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al introducir los datos del partido. Por favor reinicie el campeonato :(");
        }
    }

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

    private static void eliminarTablas() {
        String sql = "DROP TABLE ad2223_acastro.EquiposFutbol, ad2223_acastro.Octavos, ad2223_acastro.Cuartos, ad2223_acastro.Semifinales, ad2223_acastro.Final ";
        try {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error reiniciando el programa");
        }
    }


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