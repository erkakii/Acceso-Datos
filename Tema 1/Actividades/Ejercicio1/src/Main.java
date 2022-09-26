import java.io.*;

public class Main {
    public static void main(String[] args){
        try {
            BufferedReader input = new BufferedReader(new FileReader("Fichero1.txt"));
            String linea = input.readLine();
            while (linea != null){
                File fichero = new File("C:\\Users\\acastro", linea);
                if (!fichero.exists()){
                    fichero.mkdir();
                }
                String nombre = fichero.getName();
                System.out.println(nombre);
                linea = input.readLine();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Error: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.err.println("Error: " + ioException);;
        }

    }
}