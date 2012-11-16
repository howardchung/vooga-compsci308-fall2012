package vooga.shooter.implementation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;


/**
 * First level (initializes enemies, sets winning conditions)
 * 
 * @author Tommy Petrilak
 * 
 */
public class Level1 extends Level {

    private Game myGame;
    private Level myNextLevel;
    private Image enemyImage;
    private ImageIcon imageIcon;

    private final int NUMBER_OF_ENEMIES = 3;

    public Level1 (Game myGame) {
        super();
        myNextLevel = null;
    }

    public void startLevel () {
        imageIcon = new ImageIcon(this.getClass().getResource("../vooga/shooter/images/alien.png"));
        enemyImage = imageIcon.getImage();
        for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
            myGame.addEnemy(new Enemy(new Point(100 + (10 * i), 100), new Dimension(20, 20),
                                      new Dimension(100,100), enemyImage, new Point(0, 5), 1));
        }
    }

    public boolean winningConditionsMet () {
        if (myGame.getEnemies().isEmpty()) { return true; }
        return false;
    }
}
