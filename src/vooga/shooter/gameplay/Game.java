package vooga.shooter.gameplay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.gameObjects.Bullet;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.graphics.Canvas;
import vooga.shooter.implementation.Level1;
import vooga.shooter.level_editor.Level;


/**
 * Initializes the top-down shooter game and owns all sprites and levels
 * initiated throughout the course of the game.
 *
 * @author Tommy Petrilak
 * @author Stephen Hunt
 */
public class Game {
    private List<Sprite> mySprites;
    private Player myPlayer;
    private List<Enemy> myEnemies;
    private Level myCurrentLevel;
    private Canvas myCanvas;
    private Image playerImage;
    private ImageIcon imageIcon;

    private void initializeGame (Canvas c) {
        imageIcon = new ImageIcon(this.getClass().
                getResource("../vooga/shooter/images/alien.png"));
        playerImage = imageIcon.getImage();
        myPlayer = new Player(new Point(400, 300), new Dimension(20, 20),
                new Dimension(100, 100), playerImage, 10);
        addSprite(myPlayer);
        Level firstLevel = new Level1(this);
        myCanvas = c;
        myCanvas.addKeyListener(new KeyboardListener());
        startLevel(firstLevel);
    }

    private void startLevel (Level level) {
        myCurrentLevel = level;
        update();
    }

    /**
     * Updates the sprites on the screen. Also checks
     * for collisions between two sprites (only if
     * both sprites are still alive (i.e. still visible
     * in the game). If there is a collision, this method
     * will tell each sprite that it was hit by a type of
     * the other sprite. Each sprite will then invoke the
     * correct method to deal with that type of collision.
     */
    public void update () {
        for (Sprite s : getSprites()) {
            s.update();
        }

        for (Sprite s1 : getSprites()) {
            for (Sprite s2 : getSprites()) {
                if (s1.getImage() == null || s2.getImage() == null) {
                    continue;
                }

                if (collisionCheck(s1, s2)) {
                    String key = "hitby" + s2.getType();
                    s1.doEvent(key, s2);

                    key = "hitby" + s1.getType();
                    s2.doEvent(key, s1);
                }
            }
        }
    }

    /**
     * Checks if two sprites are colliding with each other.
     * @param s1 The first sprite to check.
     * @param s2 The second sprite to check.
     * @return Returns true if sprites are colliding.
     */
    boolean collisionCheck(Sprite s1, Sprite s2) {
        Rectangle r1 = new Rectangle(new Point(
                s1.getLeft(), s1.getTop()), s1.getDimension());

        Rectangle r2 = new Rectangle(new Point(
                s2.getLeft(), s2.getTop()), s2.getDimension());

        return r1.intersects(r2);
    }

    /**
     * Paints all still-alive sprites on the screen.
     * Any sprites who have died (e.g. have health < 0)
     * are removed from the game.
     *
     * @param pen used to draw the images
     */
    public void paint (Graphics pen) {
        List<Sprite> deadSprites = new ArrayList<Sprite>();

        for (Sprite s : getSprites()) {
            if (s.getImage() == null) {
                deadSprites.add(s);
            }
            else {
                s.paint(pen);
            }
        }

        getSprites().removeAll(deadSprites);
    }

    /**
     * Add a sprite to the list of sprites currently existing in the Game.
     *
     * @param sprite to be added to list of existing sprites
     */
    public void addSprite (Sprite sprite) {
        getSprites().add(sprite);
    }

    /**
     * Add an enemy to the list of enemies currently existing in the Game.
     *
     * @param enemy to be added to list of existing enemies
     */
    public void addEnemy (Enemy enemy) {
        getEnemies().add(enemy);
        getSprites().add(enemy);
    }

    /**
     * @return the mySprites
     */
    public List<Sprite> getSprites () {
        return mySprites;
    }

    /**
     * @param mySprites the mySprites to set
     */
    public void setSprites (List<Sprite> mySprites) {
        this.mySprites = mySprites;
    }

    /**
     * @return the myEnemies
     */
    public List<Enemy> getEnemies () {
        return myEnemies;
    }

    /**
     * @param myEnemies the myEnemies to set
     */
    public void setEnemies (List<Enemy> myEnemies) {
        this.myEnemies = myEnemies;
    }

    /**
     * Listens for input and sends input to the method mapper.
     * @author Stephen Hunt
     *
     */
    private class KeyboardListener implements KeyListener {

        private int numKeysPressed;

        public KeyboardListener () {
            super();
            numKeysPressed = 0;
        }

        /**
         * Sends info about keys pressed to method mapper.
         */
        @Override
        public void keyPressed (KeyEvent e) {
            myPlayer.doEvent(Integer.toString(e.getKeyCode()), null);
            numKeysPressed++;
        }

        /**
         * Checks if any keys are being pressed. If not, sends to key mapper
         * that no keys are currently pressed.
         */
        @Override
        public void keyReleased (KeyEvent e) {
            numKeysPressed--;
            if (numKeysPressed == 0) {
                myPlayer.doEvent("-1", null);
            }
        }

        @Override
        public void keyTyped (KeyEvent e) {
        }

    }
}
