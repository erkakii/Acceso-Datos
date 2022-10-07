package Ejer2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LecturaAleatoria {
    public static void main(String[] args) {
        RandomAccessFile randomAccessFile = null;
        RandomAccessFile randomAccessFile1 = null;
        try {
           randomAccessFile  = new RandomAccessFile(new File("src/Ejer2/letraA.txt"), "r");
           randomAccessFile1 = new RandomAccessFile(new File("src/Ejer2/letrasA.txt"), "rw");

           String letra = randomAccessFile.readLine();


           randomAccessFile1.seek(0);
            for (int i = 0; i < 5; i++) {
                randomAccessFile1.write(letra.getBytes());
            }




        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }finally {
            try {
                assert randomAccessFile != null;
                randomAccessFile.close();
                assert randomAccessFile1 != null;
                randomAccessFile1.close();
            } catch (IOException ioException) {
                System.err.println(ioException.getMessage());
            }
        }

    }
}
