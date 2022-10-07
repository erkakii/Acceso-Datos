package Ejer3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LecturaAleatoria3 {
    public static void main(String[] args) {
        RandomAccessFile lector = null;
        RandomAccessFile escritor = null;
        String letra;
        int j = 0;
        try {
            lector  = new RandomAccessFile(new File("src/Ejer3/letras.txt"), "r");
            escritor = new RandomAccessFile(new File("src/Ejer3/letrasVice.txt"), "rw");



            for (int i = 5; i > 0 || i == 0; i--) {
                escritor.seek(0);
                letra = lector.readLine();

                escritor.write("\n".getBytes());
                escritor.write(letra.getBytes());
            }

        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }finally {
            try {
                assert lector != null;
                lector.close();
                assert escritor != null;
                escritor.close();
            } catch (IOException ioException) {
                System.err.println(ioException.getMessage());
            }

            //Uno menos del final lo leo lo escribo me vuelvo dos hacia arriba y la machaco
        }
    }
}
