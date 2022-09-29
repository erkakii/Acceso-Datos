import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /**
         * <summary>
         *     El programa leerá el archivo y mediante el método split iremos consiguiendo las palabras separadas por el
         *     espacio. A su ves las iremos escribiendo en un archivo nuevo en el que estarán escritas como se os pide.
         * </summary>
         */

        File palabras = new File("palabras.txt");
        BufferedReader lector;
        BufferedWriter escritor, escritor2;
        String cadena;
        try {
            lector = new BufferedReader(new FileReader(palabras));
            escritor = new BufferedWriter(new FileWriter("palabras_separadas.txt"));

            //Escribe las palabras
            cadena = lector.readLine();
            String[] palabrasGuardadas = cadena.split("(?=\\p{Upper})");
            for (String s : palabrasGuardadas) {
                escritor.write(s);
                escritor.newLine();
            }
            escritor.close();

            escritor2 = new BufferedWriter(new FileWriter("palabrasOrdenadas.txt"));

            Arrays.sort(palabrasGuardadas);


            for (String s : palabrasGuardadas) {
                escritor2.write(s);
                escritor2.newLine();
            }

            escritor2.close();


        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Ha ocurrido un error: " + fileNotFoundException);
            ;
        } catch (IOException ioException) {
            System.err.println("Ha ocurrido un error: " + ioException);
            ;
        }
    }
}