package Ejer3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LecturaAleatoria2 {
    public static void main(String[] args) {
        RandomAccessFile lector = null;
        RandomAccessFile escritor = null;
        String letra, desplazarLetra;
        int posicion = 0;


        try {
            lector = new RandomAccessFile(new File("src/Ejer4/LetrasYNumeros.txt"), "r");
            escritor = new RandomAccessFile(new File("src/Ejer4/Final.txt"), "rw");

            lector.seek(0);
            letra = lector.readLine();
            while (letra != null) {
                escritor.seek(posicion);
                if (escritor.readLine() != null) {
                    for (int i = (int) escritor.length(); i >= 2; i -= 2) {
                        escritor.seek(i - 2);
                        desplazarLetra = escritor.readLine();
                        escritor.seek(i);
                        escritor.write(desplazarLetra.getBytes());
                        escritor.write("\n".getBytes());
                    }
                    escritor.seek(posicion);
                }
                escritor.write(letra.getBytes());
                escritor.write("\n".getBytes());
                letra = lector.readLine();
            }

        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        } finally {
            try {
                assert lector != null;
                lector.close();
                assert escritor != null;
                escritor.close();
            } catch (IOException ioException) {
                System.err.println(ioException.getMessage());
            }
        }
    }
}

