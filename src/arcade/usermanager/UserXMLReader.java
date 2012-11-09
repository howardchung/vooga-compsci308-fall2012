package arcade.usermanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Reads in user data from an XML file and creates list of User objects
 * 
 * @author Howard
 * 
 */
public class UserXMLReader {

    private List<User> myUsers;
    private Document dom;

    public UserXMLReader () {
        // create a list to hold the employee objects
        myUsers = new ArrayList<User>();
    }

    private void parseXmlFile () {
        // get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file
            dom = db.parse("testxml.xml");

        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        catch (SAXException se) {
            se.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void parseDocument () {
        // get the root elememt
        Element docEle = dom.getDocumentElement();

        // get a nodelist of elements
        NodeList nl = docEle.getElementsByTagName("user");
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                // get an element
                Element el = (Element) nl.item(i);

                // create the User object
                User e = getUser(el);

                // add it to list
                myUsers.add(e);
            }
        }
    }

    /**
     * Creates a user object from XML data.
     * 
     * @param el
     * @return
     */
    private User getUser (Element el) {

        String name = getTextValue(el, "name");
        String picture = getTextValue(el, "picture");
        int credits = getIntValue(el, "credits");

        // Create a new User with the value read from the xml nodes
        User e = new User(name, picture);

        return e;
    }

    /**
     * I take a xml element and the tag name, look for the tag and get
     * the text content
     * i.e for <employee><name>John</name></employee> xml snippet if
     * the Element points to employee node and tagName is name I will return
     * John
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private String getTextValue (Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    /**
     * Calls getTextValue and returns a int value
     * 
     * @param ele
     * @param tagName
     * @return
     */
    private int getIntValue (Element ele, String tagName) {
        // in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    /**
     * Iterate through the list and print the
     * content to console
     */
    private void printData () {

        System.out.println("# Users '" + myUsers.size() + "'.");
        for (int i = 0; i < myUsers.size(); i++) {
            System.out.println(myUsers.get(i).getName());
            System.out.println(myUsers.get(i).getPicture());
        }
    }

    public void runExample () {
        // parse the xml file and get the dom object
        parseXmlFile();

        // get each employee element and create a Employee object
        parseDocument();

        // Iterate through the list and print the data
        printData();
    }

    public static void main (String[] args) {
        // create an instance
        UserXMLReader dpe = new UserXMLReader();

        // call run example
        dpe.runExample();
    }

}