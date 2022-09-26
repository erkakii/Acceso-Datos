import java.io.*;

public class Main2 {
    public static void main(String[] args) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("Fichero1.txt"));
            String linea = input.readLine();
            while (linea != null) {
                File index = new File("G:\\Mi unidad\\2ºDAM\\Asignaturas\\Acceso a datos\\Tema 1\\Actividades\\" +
                        "Ejercicio1\\" + linea, "index.html");
                BufferedWriter escritor = new BufferedWriter(new FileWriter(index));
                File fichero = new File(linea);
                escritor.write("""
                        <html>
                        \t<head>
                        \t\t<title>""" + fichero.getName() + """
                        </title>
                        \t</head>
                        \t<body>
                        \t\t<h1>""" + fichero.getPath() + """
                        </h1>
                        \t\t<h3>Autor: Álvaro Castro Sánchez</h3>
                        \t</body>
                        </html>"""
                );
                escritor.close();
                linea = input.readLine();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Error: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.err.println("Error: " + ioException);
            ;
        }

    }
}