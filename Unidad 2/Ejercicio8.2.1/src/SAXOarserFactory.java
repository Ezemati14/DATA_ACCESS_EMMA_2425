import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXOarserFactory {
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    SAXParser saxParser;

    {
        try {
            saxParser = saxParserFactory.newSAXParser();
            File file = new File("contactos.xml");
            saxParser.parse(file,new ControladorXML());
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
