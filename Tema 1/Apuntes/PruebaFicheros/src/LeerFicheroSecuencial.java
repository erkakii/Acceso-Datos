import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LeerFicheroSecuencial {
    public static void main(String[] args) throws IOException {
        File fichero=new File("fichero1.txt");
        FileReader fic=new FileReader(fichero);

        char[] letras=new char[2];
        while (fic.read(letras,0,2) != -1) { //se va leyendo un carácter hasta que lea -1
            System.out.println(letras); //cast a char
        }

        fic.close(); //cerrar fichero
    }
}