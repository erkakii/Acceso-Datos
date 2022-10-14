package lecturaXml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Lectura {
    private static final File XML_FILE = new File("src/lecturaXml/ArchivoXml.xml");

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        var domDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(XML_FILE);

        var compras = domDocument.getElementsByTagName("compra");
        NodeList productos = domDocument.getElementsByTagName("producto");
        for (int i = 0; i < compras.getLength(); i++) {
            Node compra = compras.item(i);

            System.out.println(compra.getFirstChild().getNextSibling().getTextContent());
            Node ticket = compra.getLastChild().getPreviousSibling();
            Node producto = productos.item(i);
            System.out.println(producto.getFirstChild().getNextSibling().getTextContent());
            System.out.println(producto.getLastChild().getPreviousSibling().getTextContent());
        }

    }
}
