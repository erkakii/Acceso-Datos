import java.sql.*;

public class Conexion {

    public static Statement st = null;
    public static Conexion conexion = null;

    public static void main(String[] args) {
        String url = "jdbc:mysql://dns11036.phdns11.es";
        String user = "ad2223";
        String password = "nervion";
        String[] campos = {"id, nombre, edad"};

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println(con.toString());
            st = con.createStatement();
            crearTabla("acastro", campos);


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void crearTabla(String tabla, String[] nombresCampos) throws SQLException {
        String sql = "CREATE TABLE ad2223.pepitogrillo (ID int, nombre varchar(255), edad int);";
        for (int i = 0; i < nombresCampos.length; i++) {
            sql += nombresCampos[i];
            if (i < nombresCampos.length - 1) {
                sql += ",";
            }
        }
        sql += ");";
        st.executeQuery(sql);


    }

}