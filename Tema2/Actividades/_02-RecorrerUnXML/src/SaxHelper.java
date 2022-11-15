import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHelper extends DefaultHandler {
    //Declaramos las variables
    int contProductos, idCompra = 1;
    float unidades = 1, precioUnitario, descuento, precioUnitarioTemp, descuentoTemp, productosTotal = 0, descuentoTotal = 0, precioTotal = 0;
    boolean esCompra = false;
    boolean esProducto = false;
    boolean esFecha = false;
    boolean esprecio_unitario = false;
    boolean esUnidades = false;
    boolean esDescuento = false;

    /**
     * Método que se ejecutara cuando se empiece a leer un elemento
     *
     * @param elementos elementos del xml
     * @param atributos atributos de la clase
     */
    public void startElement(String uri, String localName, String elementos, Attributes atributos) {
        //Buscamos que tipo de elemento estamos leyendo
        switch (elementos) {
            case "compra":
                esCompra = true;
                break;
            case "producto":
                esProducto = true;
                break;
            case "fecha":
                esFecha = true;
                break;
            case "precio_unidad":
                esprecio_unitario = true;
                break;
            case "unidades":
                esUnidades = true;
                break;
            case "descuento":
                esDescuento = true;
                break;
        }
    }

    /**
     * Este metoro realizará un funcion u otro dependiendo del elemento que se este leyendo
     *
     * @param ch     The characters.
     * @param inicio The start position in the character array.
     * @param length The number of characters to use from the
     *               character array.
     */
    public void characters(char[] ch, int inicio, int length) {
        //Si el elemento es compra, imprimiremos un mensaje con el id de compra
        if (esCompra) {
            contProductos = 0;
            System.out.printf("Compra %d:\n", idCompra);
            ++idCompra;//Aumentamos el id en 1 para cuando se lea la siguiente compra
            esCompra = false;
            return;
        }
        //Si es producto aumentamos el contador de productos
        if (esProducto) {
            contProductos++;
            esProducto = false;
            return;
        }
        //Si es fecha escribimos la fecha por pantalla
        if (esFecha) {
            System.out.println("Fecha: " + new String(ch, inicio, length));
            esFecha = false;
            return;
        }
        //Si es precio unitoria acumulamos el precio
        if (esprecio_unitario) {
            precioUnitarioTemp += Float.parseFloat(new String(ch, inicio, length));
            esprecio_unitario = false;
            return;
        }
        //Si es unidades guardamos la unidad
        if (esUnidades) {
            unidades = Float.parseFloat(new String(ch, inicio, length));
            esUnidades = false;
            return;
        }
        //Si es descuento acumulamos el descuento
        if (esDescuento) {
            descuentoTemp += Float.parseFloat(new String(ch, inicio, length));
            esDescuento = false;
            return;
        }
    }

    /**
     * Este método se ejecutará cuando se cierre un elemento
     * Dependiendo del elemento que se cierre se hará una función u otra (acumular los resultados de cada producto,
     * escribir los resultados de cada compra y escribir los resultados finales)
     *
     * @param uri       El URI es el nombre, o la cadena vacía si el
     *                  elemento no tiene URI de espacio de nombres o si el espacio de nombres
     *                  no se está realizando el procesamiento.
     * @param localName El nombre local (sin prefijo), o la
     *                  cadena vacía si no se está procesando el espacio de nombres
     *                  realizado.
     * @param elementos elementos del xml
     */
    public void endElement(String uri, String localName, String elementos) {
        //Si el elemento que se ha cerrado es un producto, se acumularan sun variables en las variables de la compra
        if (elementos.compareTo("producto") == 0) {
            descuento += unidades * descuentoTemp;
            precioUnitario += unidades * precioUnitarioTemp;
            //Se restablecen las variables para el siguiente producto que se lea
            unidades = 1;
            descuentoTemp = 0;
            precioUnitarioTemp = 0;
        }
        //Si el elemento es compra, se escribirá el resumen de la compra
        if (elementos.compareTo("compra") == 0) {
            System.out.println("Productos " + contProductos);
            precioUnitario -= descuento;//Le restamos el descuento al precio unitario
            System.out.println("Descuentos " + descuento);
            System.out.println("Pago " + precioUnitario + System.lineSeparator());
            //Se acumulan las variables para el resumen total
            productosTotal += contProductos;
            descuentoTotal += descuento;
            precioTotal += precioUnitario;
            //Se restablecen las variables para la siguiente compra que se lea
            contProductos = 0;
            descuento = 0;
            precioUnitario = 0;
        }
        //Si se cierra el elemento comercio, es decir que se termina de leer el xml, se escribirán los resultados totales
        if (elementos.compareTo("Comercio") == 0) {
            System.out.println("Productos totales: " + productosTotal);
            System.out.println("Descuento total " + descuentoTotal);
            System.out.println("Pago total " + precioTotal);
        }
    }
}
