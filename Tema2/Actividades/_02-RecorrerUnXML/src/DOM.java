import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DOM {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        int contadorProductos, unidades;
        float valorDescuento, totalAPagar, precio_unidad;

        File xml = new File("src/compras.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xml);

        //Creamos una lista de nodos con los elementos del xml
        NodeList nodeList = doc.getElementsByTagName("compra");


        for (int i = 0; i < nodeList.getLength(); i++) {
            //Recorremos la lista de nodos
            System.out.println("compra " + (i + 1));
            Node compra = nodeList.item(i);
            if (compra.getNodeType() == Node.ELEMENT_NODE){
                Element element = ((Element) compra);
                //Esto es para en caso de no saber si es nulo o no element.getElementsByTagName("fecha").item(0) != null
                System.out.println(element.getElementsByTagName("fecha").item(0).getTextContent());
                NodeList productos = element.getElementsByTagName("producto");
                //Instanciamos las variables donde guardaremos los datos qeu tendremos que mostrar más adelante
                precio_unidad = 0;
                unidades = 0;
                contadorProductos = 0;
                valorDescuento = 0;
                for (int j = 0; j < productos.getLength(); j++) { //Recorremos la lista de productos
                    Node producto = productos.item(j);
                    if (producto.getNodeType() == Node.ELEMENT_NODE){
                        Element elementoProducto = ((Element) producto);
                        if (elementoProducto.getElementsByTagName("unidades").item(j) != null){ //Recorremos la lista de unidades
                            unidades += Integer.parseInt(elementoProducto.getElementsByTagName("unidades").item(0).getTextContent());
                        }
                        if (elementoProducto.getElementsByTagName("precio_unidad").item(j) != null){ //Recorremos la lista de precio_unidad
                            precio_unidad += Float.parseFloat(elementoProducto.getElementsByTagName("precio_unidad").item(0).getTextContent());
                        }
                        if (elementoProducto.getElementsByTagName("descuento").item(j) != null){ //Recorremos la lista descuento
                             valorDescuento += Float.parseFloat(elementoProducto.getElementsByTagName("descuento").item(0).getTextContent());
                        }
                    }
                    contadorProductos++;
                }
                //Mostramos los campos que hemos calculado por pantalla
                System.out.println("Cantidad de producto: " + contadorProductos);
                System.out.println("Había: " + valorDescuento);
                System.out.println("Unidades: " + unidades);
                System.out.println("Precio por unidad: " + precio_unidad);
                System.out.println();
            }


        }

    }
}