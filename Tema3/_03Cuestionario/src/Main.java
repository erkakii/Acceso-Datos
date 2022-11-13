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
                //crearTabla(st, "Player", new String[]{"idPlayer int PRIMARY KEY", "Nick varchar(45)", "password varchar(128)", "email varchar(100)"});
                //crearTabla(st, "Games", new String[]{"idGames int PRIMARY KEY", "Nombre varchar(45)", "tiempoJugado TIME" });
                /*crearTabla(st, "Compras", new String[]{
                        "idCompra int PRIMARY KEY",
                        "idPlayer int",
                        "idGames int",
                        "Cosa varchar(25)",
                        "Precio DECIMAL(6,2)",
                        "FechaCompra date",
                        "CONSTRAINT FK_id_Player FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer)",
                        "CONSTRAINT FK_id_Games FOREIGN KEY (idGames) REFERENCES Games(idGames)"});*/
                //File datos = new File("datos.txt");
                //insertarDatos(st, datos);
                //crearTabla(st, "Maceta", new String[]{"idMaceta int Primary Key", "Nombre varchar(90)"});
                //listarJugadores(st);
                //listarGames(st);
                //listarCompras(st);
                //modificarTabla(st);
                //borrarTabla(st, "Maceta");

            } catch (SQLException sqlException) {
                System.err.println(sqlException.getMessage());
            }
        }


    }

    /**
     * Modifica la compra que tenga el id que nos diga el usuario con los datos que el mismo nos itroduzca
     * @param st statement
     * @throws SQLException lanza la excepcion de sql
     */
    public static void modificarCompras(Statement st) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int idCompra;
        float precio;
        String sql, cosa, fechaCompra;
        listarCompras(st);
        System.out.print("Escribe la ID de la compra a modificar");
        idCompra = sc.nextInt();
        System.out.print("Escribe la nueva cosa");
        cosa = sc.nextLine();
        System.out.print("Escribe el nuevo precio");
        precio = sc.nextFloat();
        System.out.print("Escribe la nueva fecha de compra");
        fechaCompra = sc.nextLine();
        try {
            sql = "UPDATE ad2223_acastro.Player SET idCompra= " + idCompra + ", Cosa=" + cosa + ", Precio=" + precio + ", FechaCompra=" + fechaCompra + " WHERE idCompra =" + idCompra + ";";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error." + e);
        }
        listarCompras(st);
    }

    /**
     * Modifica el juego que tenga el id que nos diga el usuario con los datos que el mismo nos itroduzca
     * @param st statement
     * @throws SQLException lanza la excepcion de sql
     */
    public static void modificarGames(Statement st) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int idGames;
        float timepoJugado;
        String sql, nombre;
        listarGames(st);
        System.out.print("Escribe la ID del juego a modificar");
        idGames = sc.nextInt();
        System.out.print("Escribe el nuevo nombre");
        nombre = sc.nextLine();
        System.out.print("Escribe el nuevo tiempo jugado");
        timepoJugado = sc.nextFloat();
        try {
            sql = "UPDATE ad2223_acastro.Games SET idGames= " + idGames + ", Nombre=" + nombre + ", tiempoJugado=" + timepoJugado + " WHERE idGames =" + idGames + ";";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error." + e);
        }
        listarGames(st);
    }

    /**
     * Modifica el jugador que tenga el id que nos diga el usuario con los datos que el mismo nos itroduzca
     * @param st statement
     * @throws SQLException lanza la excepcion de sql
     */
    public static void modificarPlayer(Statement st) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int idPlayer;
        String sql, Nick, password, email;
        listarJugadores(st);
        System.out.print("Escribe la ID del jugador a modificar");
        idPlayer = sc.nextInt();
        System.out.print("Escribe el nuevo Nick");
        Nick = sc.nextLine();
        System.out.print("Escribe la nueva contraseña");
        password = sc.nextLine();
        System.out.print("Escribe el nuevo email");
        email = sc.nextLine();
        try {
            sql = "UPDATE ad2223_acastro.Player SET idPlayer= " + idPlayer + ", Nick=" + Nick + ", password=" + password + ", email=" + email + " WHERE idPlayer =" + idPlayer + ";";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error." + e);
        }
        listarJugadores(st);
    }

    /**
     * Borra la tabla que tenga el mismo nombre que le pasamos por parametros
     *
     * @param st    statement
     * @param tabla nombre de la tabla qeu queremos borrar
     */
    public static void borrarTabla(Statement st, String tabla) {
        String sql = "DROP TABLE ad2223_acastro." + tabla + ";";
        try {
            st.executeUpdate(sql);
            System.out.println("Se ha borrado con exito :D");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Modifica la tabla que le digamos y le anade un campo a la misma
     *
     * @param st statement
     * @throws SQLException lanza la excepcion de sql
     */
    private static void modificarTabla(Statement st) throws SQLException {
        String sql = "ALTER TABLE Player ADD apellido varchar(28)";
        st.execute(sql);
    }

    /**
     * Lista la tabla de games
     *
     * @param st statement
     * @throws SQLException lanza la excepcion de sql
     */
    private static void listarGames(Statement st) throws SQLException {
        String sql = "SELECT Nombre, tiempoJugado FROM ad2223_acastro.Games";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            System.out.println("Nombre: " + rs.getString("Nombre") + " Tiempo Jugado: " + rs.getString("tiempoJugado"));
        }
    }

    /**
     * Lista la tabla de compras
     *
     * @param st statement
     * @throws SQLException lanza la excepcion de sql
     */
    private static void listarCompras(Statement st) throws SQLException {
        String sql = "SELECT idPlayer, Cosa, Precio FROM ad2223_acastro.Compras";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            System.out.println("idPlayer: " + rs.getString("idPlayer") + " Producto: " + rs.getString("Cosa") +
                    " Precio: " + rs.getString("Precio") + "€");
        }
    }

    /**
     * Lista los jugadores
     *
     * @param st statement
     * @throws SQLException lanza la excepcion de sql
     */
    private static void listarJugadores(Statement st) throws SQLException {
        String sql = "SELECT Nick, email FROM ad2223_acastro.Player";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            System.out.println("Nick: " + rs.getString("Nick") + " email: " + rs.getString("email"));
        }
    }

    /**
     * Inserta los datos a una tabla mediante una lectura de un fichero
     * @param st statement
     * @param datos fichero donde se encuentran los datos que vamos a introducir en la tabla
     */
    private static void insertarDatos(Statement st, File datos) {
        String cadena;
        try {
            BufferedReader br = new BufferedReader(new FileReader(datos));
            cadena = br.readLine();
            while (cadena != null) {
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

    /**
     * Crea una tabla con el nombre y los campos que le introduzcamos
     * @param st statement
     * @param nombreTabla nombre que tendra la tabla
     * @param campos array de los campos y el tipo de datos que seran esos campos
     */
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

    /**
     * Conecta a la base de datos mediante el usuario y la contraseña
     */
    private static void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String servidor = "jdbc:mysql://dns11036.phdns11.es:3306";
            conexion = DriverManager.getConnection(servidor, "ad2223_acastro", "1234");
            System.out.println("Se ha conectado con exito :D");
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println(exception.getMessage());
        }


    }
}
