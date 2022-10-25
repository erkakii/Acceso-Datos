import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static Connection conexion = null;

    public static void main(String[] args) {
        Statement st = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String servidor = "jdbc:mysql://dns11036.phdns11.es";
            Connection conection = DriverManager.getConnection(servidor, "acastro", "acastro");
            if (conection != null){
                st = conection.createStatement();
                crearTabla(st, "acastro", new String[]{"Id int PRIMARY KEY AUTO_INCREMENT", "nombre varchar(255)", "apellidos varchar(255)", "edad int"});
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }



    }

    private static void crearTabla(Statement st, String tabla, String[] campos) {
        boolean salidaCorrecta;
        String sql = "CREATE TABLE ad2223_acastro." + tabla + "(";
        sql += campos[0];
        for (int i = 1; i < campos.length; i++) {
            sql += "," + campos[i];
        }
        sql = sql + " )";
        System.out.println(sql);
        try {
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}