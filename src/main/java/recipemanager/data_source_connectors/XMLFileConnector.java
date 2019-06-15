package main.java.recipemanager.data_source_connectors;

import main.java.recipemanager.custom_exceptions.XMLFileContentException;
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
import java.util.HashMap;
import java.util.Map;

public class XMLFileConnector implements DataSourceConnector {
    private Document document;
    private String path = "src/main/resources/restaurant-settings.xml";

    public boolean openConnection() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(new File(path));
            document.getDocumentElement().normalize();
            return true;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Map<String,String> getFileContent() throws XMLFileContentException {
        HashMap<String, String> data = new HashMap<>();

        try {
            Node titleNode = document.getElementsByTagName("title").item(0);
            data.put("restaurantTitle", titleNode.getTextContent());

            NodeList staffList = document.getElementsByTagName("staff");
            for (int i = 0; i < staffList.getLength(); i++) {
                Element staff = (Element) staffList.item(i);
                data.put("staffId", staff.getAttribute("id"));
                data.put("staffFirstName", staff.getElementsByTagName("firstname").item(0).getTextContent());
                data.put("staffLastName", staff.getElementsByTagName("lastname").item(0).getTextContent());
                data.put("staffPosition", staff.getElementsByTagName("position").item(0).getTextContent());
            }
        } catch (Exception e) {
            throw new XMLFileContentException(path);
        }
        return data;
    }
}
