import java.io.*;
import java.sql.*;

public class Conexion {


    public static Conexion conexion = null;

    public static void main(String[] args) throws SQLException, IOException {
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String servidor = "jdbc:mysql://dns11036.phdns11.es";
            Connection conection = DriverManager.getConnection(servidor, "ad2223", "nervion");
            if (conection != null) {
                st = conection.createStatement();
                ejer10(st);
                //ejer9(st);
                //ejer8(st);
                //ejer7(st);
                //ejer6(st);
                //ejer5(st);
                //empiezePorJYOrdenadoPorApellido(st);
                //mostrarMayoresde30(st);
                //ordenarPorEdad(st);
                //hacerInserccion(st);
                //crearTabla(st, "acastro", new String[]{"Id int PRIMARY KEY AUTO_INCREMENT", "nombre varchar(255)", "apellidos varchar(255)", "edad int"});
                //System.out.println(st.toString());
                st.close();
            } else {
                System.out.println("Error ");
            }
            if (st != null) {
                conection.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    private static void ejer10(Statement st) {
    }

    private static void ejer9(Statement st) throws SQLException {
        String sql = "SELECT * FROM ad2223.acastro where edad > 65";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getString("nombre") + " " + rs.getString("apellidos") + " " + rs.getString("edad"));
    }

    private static void ejer8(Statement st) throws SQLException {
        String sql = "SELECT * FROM ad2223.acastro where edad between 24 and 32";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getString("nombre") + " " + rs.getString("apellidos") + " " + rs.getString("edad"));
    }

    private static void ejer7(Statement st) throws SQLException {
        String sql = "SELECT * FROM ad2223.acastro where apellidos like '%oh%' or apellidos like '%ma%'";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getString("nombre") + " " + rs.getString("apellidos") + " " + rs.getString("edad"));
    }

    private static void ejer6(Statement st) throws SQLException {
        String sql = "SELECT AVG(edad) AS 'media edad' FROM ad2223.acastro";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getInt("media edad"));
    }

    private static void ejer5(Statement st) throws SQLException {
        String sql = "SELECT * FROM ad2223.acastro where nombre like 'J%' and apellidos like 'K%' order by edad desc ";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getString("nombre") + " " + rs.getString("apellidos") + " " + rs.getString("edad"));
    }

    private static void empiezePorJYOrdenadoPorApellido(Statement st) throws SQLException {
        String sql = "SELECT * FROM ad2223.acastro where nombre like 'J%' order by apellidos";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getString("nombre") + " " + rs.getString("apellidos") + " " + rs.getString("edad"));
    }

    private static void mostrarMayoresde30(Statement st) throws SQLException {
        String sql = "SELECT * FROM ad2223.acastro where edad > 30";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getString("nombre") + " " + rs.getString("apellidos") + " " + rs.getString("edad"));
    }

    private static void ordenarPorEdad(Statement st) throws SQLException {
        String sql = "SELECT * FROM ad2223.acastro order by edad";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next())
            System.out.println(rs.getString("nombre") + " " + rs.getString("apellidos") + " " + rs.getString("edad"));
    }


    public static void crearTabla(Statement st, String tabla, String[] campos) {
        boolean salidaCorrecta;
        String sql = "CREATE TABLE ad2223." + tabla + "(";
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

    public static void borrarTabla(Statement st, String tabla) {
        String sql = "DROP TABLE ad2223." + tabla + ";";
        try {
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void hacerInserccion(Statement st) throws SQLException, IOException {

        BufferedReader br = new BufferedReader(new FileReader("datos.txt"));
        String cadena;
        cadena = br.readLine();
        while (cadena != null) {
            st.executeUpdate(cadena);
            cadena = br.readLine();
        }


    }


   /* try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        conection=DriverManager.getConnection(servidor, "ad2223", "nervion");
        if(conection!=null) {
            st=conection.createStatement();
            crearTabla(st, "acastro",new String[]{"Id int PRIMARY KEY AUTO_INCREMENT","nombre varchar(255)","apellidos varchar(255)","edad int"});
            //borrarTabla(st, "dcarvajal3");
            System.out.println(st.toString());
        }else {
            System.out.println("Error ");
        }
        if(st!=null){

        }
    }catch (ClassNotFoundException e){
        e.printStackTrace();
    }catch (SQLException e){
        e.printStackTrace();
    }*/

}
