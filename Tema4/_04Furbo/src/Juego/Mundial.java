package Juego;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

public class Mundial {

    private static  final String SERVIDOR = "jdbc:mysql://dns11036.phdns11.es";

    private static String user = "acastro";
    //Contraseña
    private static String password = "1234";
    //Nombre de la base de datos del emisor
    private static String baseDatos = "ad2223_acastro";

    private static boolean octavosJugados = false;
    
    private static boolean cuartosJugados = false;
    
    private static boolean semifinalesJugadas = false;
    
    private static boolean finalJugada = false;

    private static Connection connection;
    private static Statement st;
    public static void main(String[] args) {
        try{
            connection = conectar();
            if (connection != null){
                st = connection.createStatement();

                int opc;
                do {
                    Mostrarmenu();
                    opc = Utilidades.validarOpcion(0,5);
                    switch (opc){
                        case 1 -> {
                            jugarOctavos();
                            if (octavosJugados){
                                mostrarResultadosOctavos();
                            }
                        }
                        case 2 -> {
                            jugarCuartos();
                            if (cuartosJugados){
                                mostrarResultadosCuartos();
                            }
                        }
                        case 3 -> {
                            jugarSemifinales();
                            if (semifinalesJugadas){
                                mostrarResultadosSemifinales();
                            }
                        }
                        case 4 -> {
                            jugarFinal();
                            if (finalJugada){
                                mostrarResultadoFinal();
                            }
                        }
                        case 5 -> {
                            reiniciar();
                        }
                        case 0 -> {
                            System.out.println("Adiós :)");
                        }
                    }
                }while (opc != 0);

                st.close();
            }

            if (st != null){
                connection.close();
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }

    }

    private static void mostrarResultadoFinal() {
    }

    private static void jugarFinal() {
    }

    private static void mostrarResultadosSemifinales() {
    }

    private static void jugarSemifinales() {
    }

    private static void mostrarResultadosCuartos() {
    }

    private static void jugarCuartos() {
    }

    private static void mostrarResultadosOctavos() {
    }

    private static void jugarOctavos() {
        Scanner sc = new Scanner(System.in);
        String equipoA = "España", equipoB = "Marruecos", enter;
        boolean finDePartidos = false;
        int contadorPartidos = 0;
        //conseguirEquipos();
        do {
            partido(equipoA, equipoB);
            contadorPartidos++;
            if (contadorPartidos == 8){
                finDePartidos = true;
            }
            System.out.println("Pulse enter para iniciar el siguiente partido");
            enter = sc.nextLine();
        }while(!finDePartidos);


    }

    private static void partido(String equipoA, String equipoB){
        int golesA = 0, golesB = 0, aleatorioA, aleatorioB, diferencia ,posesionA = 50, posesionB = 50, contador = 0;

        System.out.println("Va a dar comienzo el " + equipoA + " vs " + equipoB);
        do {
            aleatorioA = new Random().nextInt(1,15);
            aleatorioB = new Random().nextInt(1,15);
            diferencia = aleatorioA - aleatorioB;
            posesionA += diferencia;
            posesionB -= diferencia;
            System.out.println(equipoA + " posesion " + posesionA + "%   " + posesionB + "% " + equipoB + "   minuto: " + contador);
            if (posesionA >= 95){
                System.out.println("GOOOOOOLLLL de " + equipoA);
                golesA++;
                posesionA = 50;
                posesionB = 50;
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    System.err.println(interruptedException.getMessage());
                }
            }
            if (posesionB >= 95){
                System.out.println("GOOOOOOLLLL de " + equipoB);
                golesB++;
                posesionA = 50;
                posesionB = 50;
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    System.err.println(interruptedException.getMessage());
                }

            }
            contador++;
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        }while(contador <= 90);

        if (golesA == golesB){
            do {
                aleatorioA = new Random().nextInt(1,15);
                aleatorioB = new Random().nextInt(1, 15);
                diferencia = aleatorioA - aleatorioB;
                posesionA += diferencia;
                posesionB -= diferencia;
                System.out.println(equipoA + " posesion " + posesionA + "%   " + posesionB + "% " + equipoB + "  Prorróga");
                if (posesionA >= 95){
                    System.out.println("GOOOOOOLLLL de " + equipoA);
                    golesA++;
                }
                if (posesionB >= 95){
                    System.out.println("GOOOOOOLLLL de " + equipoB);
                    golesB++;

                }
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }while (golesA == golesB);
        }

        System.out.println(equipoA + " " + golesA +  "-" + golesB + " " + equipoB);

    }

    private static void reiniciar() {
        //eliminarTablas();
        //volverAcrearlas();
        //introducirDatos();
       
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