import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static Connection conexion;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombreTabla;
        Statement st = null;
        conectar();
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                /*crearTabla(st, "Player", new String[]{"idPlayer int PRIMARY KEY", "Nick varchar(45)", "password varchar(128)", "email varchar(100)"});
                crearTabla(st, "Games", new String[]{"idGames int PRIMARY KEY", "Nombre varchar(45)", "tiempoJugado TIME" });
                crearTabla(st, "Compras", new String[]{
                        "idCompra int PRIMARY KEY",
                        "idPlayer int",
                        "idGames int",
                        "Cosa varchar(25)",
                        "Precio DECIMAL(6,2)",
                        "FechaCompra date",
                        "CONSTRAINT FK_id_Player FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer)",
                        "CONSTRAINT FK_id_Games FOREIGN KEY (idGames) REFERENCES Games(idGames)"});
                File datos = new File("datos.txt");
                insertarDatos(st, datos);*/
                //crearTabla(st, "Maceta", new String[]{"idMaceta int Primary Key", "Nombre varchar(90)"});
                //listarJugadores(st);
                //listarGames(st);
                //listarCompras(st);
                //modificarTabla(st);
                borrarTabla(st, "Maceta");

            } catch (SQLException sqlException) {
                System.err.println(sqlException.getMessage());
            }
        }


    }

    public static void borrarTabla(Statement st, String tabla) {
        String sql = "DROP TABLE ad2223_acastro." + tabla + ";";
        try {
            st.executeUpdate(sql);
            System.out.println("Se ha borrado con exito :D");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void modificarTabla(Statement st) throws SQLException {
        String sql = "ALTER TABLE ad2223_acastro.Player MODIFY Nick ";
        st.execute(sql);
    }


    private static void listarGames(Statement st) throws SQLException {
        String sql = "SELECT Nombre, tiempoJugado FROM ad2223_acastro.Games";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            System.out.println("Nombre: " + rs.getString("Nombre") + " Tiempo Jugado: " + rs.getString("tiempoJugado"));
        }
    }

    private static void listarCompras(Statement st) throws SQLException {
        String sql = "SELECT idPlayer, Cosa, Precio FROM ad2223_acastro.Compras";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            System.out.println("idPlayer: " + rs.getString("idPlayer") + " Producto: " + rs.getString("Cosa") +
                    " Precio: " + rs.getString("Precio") + "€");
        }
    }

    private static void listarJugadores(Statement st) throws SQLException {
        String sql = "SELECT Nick, email FROM ad2223_acastro.Player";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            System.out.println("Nick: " + rs.getString("Nick") + " email: " + rs.getString("email"));
        }
    }

    private static void insertarDatos(Statement st, File datos){
        String cadena;
        try {
            BufferedReader br = new BufferedReader(new FileReader(datos));
            cadena = br.readLine();
            while (cadena != null){
                st.execute(cadena);
                cadena = br.readLine();
            }
            System.out.println("La insercción se ha completado correctamente :D");
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException.getMessage());
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage() + " :(");
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage() + " :'(:");
        }

    }

    private static void crearTabla(Statement st, String nombreTabla, String[] campos) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ad2223_acastro." + nombreTabla + "(");
        sql.append(campos[0]);
        for (int i = 1; i < campos.length; i++) {
            sql.append(",").append(campos[i]);
        }
        sql.append(" )");
        System.out.println(sql);
        try {
            st.executeUpdate(sql.toString());
            System.out.println("La tabla ha sido creada correctamente :D");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    private static void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String servidor = "jdbc:mysql://dns11036.phdns11.es:3306";
            conexion = DriverManager.getConnection(servidor, "acastro", "acastro");
            System.out.println("Se ha conectado con exito :D");
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println(exception.getMessage());
        }


    }
}
