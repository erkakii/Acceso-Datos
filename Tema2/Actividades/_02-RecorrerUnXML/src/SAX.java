import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAX {
    public static void main(String[] args) {
        //Momento en el que iniciamos el programa
        long inicio=System.currentTimeMillis();
        SAXParserFactory spf= SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            SaxHelper handler= new SaxHelper();
            sp.parse("Comercio.xml", handler);
            //Mostramos el tiempo que ha tardado para compararlo con el DOM
            System.out.println(System.currentTimeMillis()-inicio);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
