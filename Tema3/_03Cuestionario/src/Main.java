import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static Connection conexion;


    public static void main(String[] args) {
        java.sql.Statement st = null;
        conectar();
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                //crearTabla(st, "Player", new String[]{"idPlayer int PRIMARY KEY", "Nick varchar(45)", "password varchar(128)", "email varchar(100)"});
                //crearTabla(st, "Games", new String[]{"idGames int PRIMARY KEY", "Nombre varchar(45)", "tiempoJugado TIME" });
                crearTabla(st, "Compras", new String[]{"idCompra int PRIMARY KEY", "Cosa varchar(25)",
                        "Precio DECIMAL(6,2)", "FechaCompra DATE", "FOREIGN KEY (idPlayer) REFERENCES Player(idPlayer)",
                        "FOREIGN KEY (idGames) REFERENCE Games(idGames)"});
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
            System.out.println("La tabla ha sido creada correctamente");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    private static void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String servidor = "jdbc:mysql://dns11036.phdns11.es:3306";
            conexion = DriverManager.getConnection(servidor, "acastro", "acastro");
            System.out.println("Se ha conectado con exito");
        } catch (SQLException | ClassNotFoundException exception) {
            System.err.println(exception.getMessage());
        }


    }
}
