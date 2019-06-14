package main.java.recipemanager.datasources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileConnector implements DataSource {
    private Document document;

    public boolean openConnection() {
        try {
            File file = new File("src/main/resources/restaurant-settings.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(file);
            document.getDocumentElement().normalize();
            return true;
        } catch (Exception e) {
            e.printStackTrace(); //TODO
            return false;
        }
    }

    public Map<String,String> getFileContent() {
        HashMap<String, String> data = new HashMap<>();

        Node titleNode = document.getElementsByTagName("title").item(0);
        data.put("restaurantTitle", titleNode.getTextContent());

        NodeList staffList = document.getElementsByTagName("staff");
        for (int i = 0; i < staffList.getLength(); i++) {
            Element staff = (Element) staffList.item(i);
            data.put("staffId", staff.getAttribute("id"));
            data.put("staffFirstName", staff.getElementsByTagName("firstname").item(0).getTextContent());
            data.put("staffLastName", staff.getElementsByTagName("lastname").item(0).getTextContent());
            data.put("staffPosition",  staff.getElementsByTagName("position").item(0).getTextContent());
        }

        return data;
    }
}
