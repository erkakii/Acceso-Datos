import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Escribir {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Elemento raíz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);
            //Primer elemento
            Element elemento1 = doc.createElement("elemento1");
            rootElement.appendChild(elemento1);
            //Se agrega un atributo al nodo elemento y su valor
            Attr attr = doc.createAttribute("id");
            attr.setValue("valor del atributo");
            elemento1.setAttributeNode(attr);
            Element elemento2 = doc.createElement("elemento2");
            elemento2.setTextContent("Contenido del elemento 2");
            rootElement.appendChild(elemento2);
            //Se escribe el contenido del XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/index.txt"));
            transformer.transform(source, result);
            File xml = new File("src/index.xml");
            convertirEnXml(xml, leerFicheroHtlml(xml));
        } catch (ParserConfigurationException pce) {
            System.err.println(pce.getMessage());
        } catch (TransformerException tfe) {
            System.err.println(tfe.getMessage() + " error de transformación");
        }
    }

    public static ArrayList<Columna> leerFicheroHtlml(File fichero) {
        //Inicializamos las variables
        ArrayList<Columna> columnas = new ArrayList<>();
        ArrayList<String> temp;
        String linea, extraido, tipo;
        int inicio, fin, columnaActual = 0;
        Scanner sc;

        //Creamos el lector de documentos
        try (BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
            linea = reader.readLine();
            while (linea != null) {//Leeremos línea a línea hasta que se acabe el documento
                sc = new Scanner(linea);

                while (sc.hasNext()) {//Leemos palabra a palabra
                    extraido = sc.next();
                    //Extraemos los símbolos <>
                    inicio = extraido.indexOf("<");
                    fin = extraido.indexOf(">");
                    tipo = extraido.substring(inicio + 1, fin);//Aquí sacamos el tipo
                    //Si la palabra leida es TR, empezara una fila de la primera columna
                    if (tipo.compareTo("TR") == 0) {
                        columnaActual = 0;//Nos posicionamos en la primera columna
                    } else if (tipo.compareTo("TABLE") != 0 && tipo.compareTo("/TABLE") != 0 && tipo.compareTo("/TR") != 0) {//Verificamos que no sea una etiqueta de cierra o table
                        //Extraemos el valor que está dentro
                        extraido = extraido.substring(1);
                        inicio = extraido.indexOf(">");
                        fin = extraido.indexOf("<");
                        extraido = extraido.substring(inicio + 1, fin);
                        //Y vemos si el tipos es fila o columna
                        if (tipo.compareTo("TH") == 0) {
                            //Si es columna añadimos una columna y le ponemos el contenido
                            columnas.add(new Columna());
                            columnas.get(columnaActual).setCabecera(extraido);
                            ++columnaActual;
                        } else if (tipo.compareTo("TD") == 0) {
                            //Si es columna añadimos la fila a la columna
                            temp = columnas.get(columnaActual).getFila();
                            temp.add(extraido);
                            columnas.get(columnaActual).setFila(temp);
                            ++columnaActual;
                        }
                    }

                }
                linea = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el fichero");
        } catch (IOException e) {
            System.out.println("Error de entrada o salida");
        }
        return columnas;
    }

    public static void convertirEnXml(File xml, ArrayList<Columna> columnas) {
        try {
            //Creamos el documento
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            DOMImplementation impl = db.getDOMImplementation();
            Document doc = impl.createDocument(null, null, null);
            //Creamos el elemento raiz
            Element rootElement = doc.createElement("Tabla");
            doc.appendChild(rootElement);
            //Creamos las columnas como elemento hijos
            for (Columna columna : columnas) {
                Element elemento1 = doc.createElement(columna.getCabecera());
                rootElement.appendChild(elemento1);
                //Creamos las filas como hijos
                for (String fila : columna.getFila()) {
                    Element elemento2 = doc.createElement("Fila");
                    //Le ponemos el contenido de la fila
                    elemento2.setTextContent(fila);
                    elemento1.appendChild(elemento2);

                }
            }
            //Configuramos el documento xml
            DOMSource domSource = new DOMSource(doc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //Creamos el documento
            StreamResult sr = new StreamResult(xml);
            transformer.transform(domSource, sr);

        } catch (Exception e) {
            System.out.println("Hubo algun error transformando el html en xml");
            System.out.println("-------------------Detalles-------------------");
            e.printStackTrace();
        }
    }
}