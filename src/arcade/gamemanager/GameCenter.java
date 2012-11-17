package arcade.gamemanager;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlParser;
import arcade.IArcadeGame;
import arcade.usermanager.SocialCenter;
import arcade.utility.ReadWriter;


/**
 * This class keeps a list of Games and returns appropriate Games at GUI's request.
 * @author Jei Min Yoo
 * 
 */
public class GameCenter {

    private XmlParser myXmlParser;
    private List<Game> myGames;
    private SocialCenter socialCenter;

    public GameCenter () {
        initialize();
    }

    /**
     * initializes the class by reading information from game.xml file.
     */
    public void initialize () {
//        socialCenter = SocialCenter.getInstance();
        File f = new File("../vooga-compsci308-fall2012/src/arcade/database/game.xml");
        myXmlParser = new XmlParser(f);
        myGames = new ArrayList<Game>();
        refreshGames();

    }

    private void refreshGames () {
        myGames.clear();
        NodeList nList =
                myXmlParser.getElementsByName(myXmlParser.getDocumentElement(), "filepath");

        for (int i = 0; i < nList.getLength(); i++) {
            String filePath = nList.item(i).getTextContent();
            try {
                IArcadeGame arcade = (IArcadeGame) Class.forName(filePath).newInstance();
//                Game game = new Game(arcade);
//                myGames.add(game);
            }
            catch (IllegalAccessException e) {
                System.out.println("illegal access: " + filePath);
            }
            catch (InstantiationException e) {
                System.out.println("failed to instantiate class: " + filePath);
            }
            catch (ClassNotFoundException e) {
                System.out.println("class does not exist: " + filePath);
            }
        }
    }
    
    public List<String> getGameList () {
        List<String> gameList = new ArrayList<String>();
        for (Game game : myGames) {
            gameList.add(game.getGameName());
        }
        return gameList;
    }

    public Game getGame (String gameName) {
        for (Game gm : myGames) {
            if (gm.getGameName().equals(gameName)) { return gm; }
        }
        return null;
    }

    public List<Game> getGameListByTagName (String tag) {
        List<Game> games = new ArrayList<Game>();
        for (Game gm : myGames) {
            if (gm.getGenre().contains(tag)) {
                games.add(gm);
            }
        }
        return games;
    }

//     public static void main(String args[]) {
//     System.out.println("haha");
//     GameCenter gc = new GameCenter();
//     List<String> list = gc.getGameList();
//     gc.refreshGames();
//     System.out.println(list);
//     }
}
