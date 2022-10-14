import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Apuntes {
    private static final File XML_FILE = new File("src/lecturaXml/ArchivoXml.xml");

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        var domDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(XML_FILE);

        //Uri XML
        System.out.println(domDocument.getDocumentURI());

        //Obtener cabecera, comprobar antes si es un nodo documento
        var header = domDocument.getNodeType();
        System.out.println("Nodo documento: " + (header == Node.DOCUMENT_NODE));

        //Obtener la version

        System.out.println("version: " + domDocument.getXmlVersion());

        //Obtener encoding
        System.out.println("encoding: " + domDocument.getXmlEncoding());

        //Obtener nodos hijos
        System.out.println("Tamaño lista de nodos: " + domDocument.getChildNodes().getLength());

        //Obtener elementos por etiqueta

        var productos = domDocument.getElementsByTagName("producto");
        for (int i = 0; i < productos.getLength(); i++) {

            var producto = productos.item(i);
            System.out.println(producto.getTextContent());

            System.out.println("Parent: " + producto.getParentNode().getNodeName());

            System.out.println("primer nodo válido: " + producto.getFirstChild().getNextSibling().getTextContent());

            System.out.println("último nodo válido: " + producto.getLastChild().getPreviousSibling().getTextContent());

            /*var childNodes = producto.getChildNodes();
            System.out.println(childNodes.item(0).getParentNode().getAttributes().getNamedItem("descripcion").getTextContent());*/


        }


    }
}