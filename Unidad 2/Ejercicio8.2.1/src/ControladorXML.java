import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControladorXML extends DefaultHandler {

    List<Contact> contacts = new ArrayList<>();

    private boolean insideName = false;
    private boolean insideCell = false;
    private boolean insideWorkEmail = false;
    private String currentName = "";
    private String currentCell = "";
    private String currentEmail = "";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(qName.equalsIgnoreCase("name")){
            insideName = true;
        }
        if(qName.equalsIgnoreCase("cell")){
            insideCell = true;
        }
        if(qName.equalsIgnoreCase("work")){
            insideWorkEmail = true;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length).trim();

        if(insideName) { currentName = content; }
        if(insideCell) { currentCell = content; }
        if(insideWorkEmail) { currentEmail = content; }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("name")){
            insideName = false;
        }
        if(qName.equalsIgnoreCase("cell")){
            insideCell = false;
        }
        if(qName.equalsIgnoreCase("work")){
            insideWorkEmail = false;
        }
        if(qName.equalsIgnoreCase("contact")){
            contacts.add(new Contact(currentName, currentCell, currentEmail));
            System.out.println("Nuevo contacto agregado: " + currentName + ", " + currentCell + ", " + currentEmail);
            // Limpiar datos temporales
            currentName = "";
            currentCell = "";
            currentEmail = "";
        }
    }
}
