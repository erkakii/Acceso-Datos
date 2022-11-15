import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.crypto.dsig.XMLObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class json_xml {

    //Contenido del archivo json
    public static String JSON_PRUEBA = """
            {
              "Comercio": {
                "Compras": {
                  "compra": [
                    {
                      "fecha": "16-08-2022",
                      "ticket": {
                        "producto": [
                          {
                            "descripcion": "Tomates",
                            "precio_unidad": "1,99"
                          },
                          {
                            "descripcion": "Taboulé",
                            "precio_unidad": "1,95"
                          },
                          {
                            "descripcion": "Tortilla",
                            "precio_unidad": "2,69"
                          }
                        ]
                      }
                    },
                    {
                      "fecha": "29-08-2022",
                      "ticket": {
                        "producto": [
                          {
                            "descripcion": "Ensalada",
                            "precio_unidad": "1,09"
                          },
                          {
                            "descripcion": "Pasta",
                            "descuento": "0,35",
                            "unidades": 2,
                            "precio_unidad": "1,65"
                          },
                          {
                            "descripcion": "Taboulé",
                            "precio_unidad": "1,95"
                          },
                          {
                            "descripcion": "Huevos L",
                            "descuento": "0,12",
                            "precio_unidad": "1,19"
                          },
                          {
                            "descripcion": "Frutos secos",
                            "precio_unidad": "2,69"
                          },
                          {
                            "descripcion": "Hummus",
                            "precio_unidad": "0,95"
                          },
                          {
                            "descripcion": "Zumo naranja",
                            "unidades": 2,
                            "precio_unidad": "1,49"
                          },
                          {
                            "descripcion": "Ciruelas",
                            "unidades": "0,464",
                            "precio_unidad": "2,99"
                          }
                        ]
                      }
                    },
                    {
                      "fecha": "12-09-2022",
                      "ticket": {
                        "producto": [
                          {
                            "descripcion": "Mozzarella",
                            "unidades": 2,
                            "precio_unidad": "0,95"
                          },
                          {
                            "descripcion": "Margarina",
                            "descuento": "0,45",
                            "precio_unidad": "1,49"
                          },
                          {
                            "descripcion": "Uva",
                            "precio_unidad": "1,79"
                          }
                        ]
                      }
                    }
                  ]
                },
                "lugar": {
                  "cp": 41920,
                  "content": "San Juan de Aznalfarache"
                }
              }
            }""";

    public static void main(String[] args) {
        try {
            //Creamos el objeto que nos ayudara a convertir el JSON en XML
            JSONObject json = new JSONObject(JSON_PRUEBA);
            String xml = XML.toString(json);
            System.out.println(xml);
            //Abrimos un bufferWriter para escribir en el archivo
            BufferedWriter bfw = new BufferedWriter(new FileWriter("src/compras2.xml"));
            bfw.write(xml.toString());
            bfw.close();
        } catch (JSONException je) {
            System.out.println(je.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
