package vooga.platformer.leveleditor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vooga.platformer.util.xml.XMLUtils;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Instances of this class are created using the path to an XML level data file.
 * Once the instance is created, the getter methods can be used to get the
 * contents of this data file as java types (ints and Sprites, rather than
 * string values).
 * 
 * @author Grant Oakley
 * @author Zach Michaelov (modified)
 */
public class LevelFileReader {

    private final Document document;
    private Element root;
    private File levelFile;

    /**
     * Creates a new LevelFileReader using the level data file specified.
     * 
     * @param levelFilePath path to the level data file (XML format)
     */
    public LevelFileReader (String levelFilePath) {
        this(new File(levelFilePath));
    }

    /**
     * Creates a new LevelFileReader using the File specified.
     * 
     * @param levelFile File in XML format representing the level to be read
     */
    public LevelFileReader (File levelFile) {
        document = XMLUtils.initializeDocument(levelFile);
        root = document.getElementById("level");
    }

    /**
     * Gets the name of the level.
     * 
     * @return name of the level as a String
     */
    public String getLevelID () {
        return XMLUtils.getTagValue("id", root);
    }

    /**
     * Gets the overall width of the level.
     * 
     * @return width of the level as an int
     */
    public int getWidth () {
        return XMLUtils.getTagInt("width", root);
    }

    /**
     * Gets the overall height of the level.
     * 
     * @return height of the level as an int
     */
    public int getHeight () {
        return XMLUtils.getTagInt("height", root);
    }

    /**
     * Gets the image that is to be the background scenery of the level. This
     * will be rendered behind the Sprites.
     * 
     * @return Image representing the background of the level
     */
    public Image getBackgroundImage () {
        return XMLUtils.fileNameToImage(levelFile, XMLUtils.getTagValue("backgroundImage", root));
    }

    /**
     * Gets all the elements in the level data file tagged as gameObjects. The
     * Sprite objects are built using the parameters specified in level data
     * file.
     * 
     * @return a collection of Sprite objects representing the level's
     *         gameObjects
     */
    public Collection<Sprite> getSprites () {
        NodeList spritesNode = document.getElementsByTagName("gameobject");
        Collection<Sprite> spritesList = new ArrayList<Sprite>(spritesNode.getLength());

        for (int i = 0; i < spritesNode.getLength(); i++) {
            Node spriteNode = spritesNode.item(i);
            if (spriteNode.getNodeType() == Node.ELEMENT_NODE) {
                Sprite builtSprite = buildSprite(spriteNode);
                spritesList.add(builtSprite);
            }
        }

        return spritesList;
    }

    private Sprite buildSprite (Node spriteNode) {
        Element spriteElement = (Element) spriteNode;
        String tag = spriteElement.getAttribute("type");
        int x = XMLUtils.getTagInt("x", spriteElement);
        int y = XMLUtils.getTagInt("y", spriteElement);
        int width = XMLUtils.getTagInt("width", spriteElement);
        int height = XMLUtils.getTagInt("height", spriteElement);
        String imagePath = XMLUtils.getTagValue("imagePath", spriteElement);
        Sprite builtSprite = new Sprite(tag, x, y, width, height, imagePath);
        return builtSprite;
    }

}
