package games.nielgame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * This game was developed entirely in the level editor. Absolutely no code was used. It showcases
 * the power and flexibility of the level editor.
 * @author Niel Lebeck
 *
 */
public class MarioStyleGame implements IArcadeGame {

    private static final String myFirstLevelName = "src/games/nielgame/levels/mario-style2.xml";
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        start(myFirstLevelName);
    }
    
    public static void main (String[] args) {
        MarioStyleGame game = new MarioStyleGame();
        game.start(myFirstLevelName);
    }
    
    public void start(String levelName) {
        JFrame frame = new JFrame("Niel's Mario Style Game");
        PlatformerController controller = new PlatformerController(levelName,
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> imgList = new ArrayList<Image>();
        Image ss = null;
        try {
            ss = ImageIO.read(new File("src/games/nielgame/images/mario-style-ss.png"));
        }
        catch (IOException e) {
            System.out.println("Could not find screenshot");
        }
        imgList.add(ss);
        return imgList;
    }

    @Override
    public Image getMainImage () {
        Image img = null;
        try {
            img = ImageIO.read(new File("src/games/nielgame/images/mario.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find main image");
        }
        return img;
    }

    @Override
    public String getDescription () {
        return "A basic platforming game, similar to Mario but with the ability to shoot bullets," +
        		"developed entirely in the level editor";
    }

    @Override
    public String getName () {
        return "Niel's Mario Style Game";
    }

}
