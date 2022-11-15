import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class xml_json {
    public static String XML_PRUEBA = """
            <Comercio>
                <lugar cp="41920">San Juan de Aznalfarache</lugar>
                <Compras>
                    <compra>
                        <fecha>16-08-2022</fecha>
                        <ticket>
                            <producto>
                                <descripcion>Tomates</descripcion>
                                <precio_unidad>1,99</precio_unidad>
                            </producto>
                            <producto>
                                <descripcion>Taboulé</descripcion>
                                <precio_unidad>1,95</precio_unidad>
                            </producto>
                            <producto>
                                <descripcion>Tortilla</descripcion>
                                <precio_unidad>2,69</precio_unidad>
                            </producto>
                        </ticket>
                    </compra>
                    <compra>
                        <fecha>29-08-2022</fecha>
                        <ticket>
                            <producto>
                                <descripcion>Ensalada</descripcion>
                                <precio_unidad>1,09</precio_unidad>
                            </producto>
                            <producto>
                                <descripcion>Pasta</descripcion>
                                <precio_unidad>1,65</precio_unidad>
                                <unidades>2</unidades>
                                <descuento>0,35</descuento>
                            </producto>
                            <producto>
                                <descripcion>Taboulé</descripcion>
                                <precio_unidad>1,95</precio_unidad>
                            </producto>
                            <producto>
                                <descripcion>Huevos L</descripcion>
                                <precio_unidad>1,19</precio_unidad>
                                <descuento>0,12</descuento>
                            </producto>
                            <producto>
                                <descripcion>Frutos secos</descripcion>
                                <precio_unidad>2,69</precio_unidad>
                            </producto>
                            <producto>
                                <descripcion>Hummus</descripcion>
                                <precio_unidad>0,95</precio_unidad>
                            </producto>
                            <producto>
                                <descripcion>Zumo naranja</descripcion>
                                <precio_unidad>1,49</precio_unidad>
                                <unidades>2</unidades>
                            </producto>
                            <producto>
                                <descripcion>Ciruelas</descripcion>
                                <precio_unidad>2,99</precio_unidad>
                                <unidades>0,464</unidades>
                            </producto>
                        </ticket>
                    </compra>
                    <compra>
                        <fecha>12-09-2022</fecha>
                        <ticket>
                            <producto>
                                <descripcion>Mozzarella</descripcion>
                                <precio_unidad>0,95</precio_unidad>
                                <unidades>2</unidades>
                            </producto>
                            <producto>
                                <descripcion>Margarina</descripcion>
                                <precio_unidad>1,49</precio_unidad>
                                <descuento>0,45</descuento>
                            </producto>
                            <producto>
                                <descripcion>Uva</descripcion>
                                <precio_unidad>1,79</precio_unidad>
                            </producto>
                        </ticket>
                    </compra>
                </Compras>
            </Comercio>""";
    public static void main(String[] args) {
//LOS ARCHIVOS SE ESCRIBEN EN UNA SOLA LINEA CON PULSAR CTRL+ALT+L EN INTELLIJ SE FORMATEAN SOLOS

        try {
            //Creamos el objeto que nos ayudara a convertir el XML en JSON
            JSONObject json = XML.toJSONObject(XML_PRUEBA);
            //Identamos el json, le damos formato
            String jsonFormatado = json.toString();
            BufferedWriter bfw = new BufferedWriter(new FileWriter("src/compras.json"));
            bfw.write(json.toString());
            bfw.close();
            System.out.println(jsonFormatado);
        } catch (JSONException je) {
            System.out.println(je.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}