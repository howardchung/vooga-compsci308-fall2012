package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.gameObjects.spriteUtilities.SpriteActionInterface;
import vooga.shooter.gameObjects.spriteUtilities.SpriteMethodMap;

/**
 * This class encompasses the basic layout for any sprites that appear in the
 * game. Any specific type of sprite (e.g. player, enemy, boss, etc.) will
 * extend this class and contain any additional information or behaviors
 * particular to that new type of sprite (e.g. the player could have a health
 * limit).
 *
 */
public abstract class Sprite implements SpriteActionInterface {
    
    protected static final String HIT_BY_BULLET = "hitbybullet";
    protected static final String HIT_BY_ENEMY = "hitbyenemy";
    protected static final String HIT_BY_PLAYER = "hitbyplayer";
    protected static final String PLAYER_TYPE = "player";
    protected static final String BULLET_TYPE = "bullet";
    protected static final String ENEMY_TYPE = "enemy";
    protected static final String LEFT_BOUND = "left";
    protected static final String RIGHT_BOUND = "right";
    protected static final String TOP_BOUND = "top";
    protected static final String BOTTOM_BOUND = "bottom";
    private static final int BULLET_SIZE = 10;
    private Point myPosition;
    private Point myVelocity;
    private Dimension mySize;
    private Dimension myBounds;
    private Image myImage;
    private List<Bullet> myShotsFired;
    private int myHealth;
    private SpriteMethodMap myMapper;

    /**
     * Construct a sprite initializing only position, size, and image.
     * Initial health is set to -1 to symbolize a sprite that does not
     * have health.
     * (something stationary with no health, e.g. barrier).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param bounds the size of the canvas holding the sprite
     * @param image the image of the sprite
     */
    public Sprite (Point position, Dimension size,
            Dimension bounds, Image image) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myVelocity = new Point(0, 0);
        myHealth = Integer.MAX_VALUE;
        myBounds = bounds;
        myMapper = new SpriteMethodMap();
        myShotsFired = new ArrayList<Bullet>();
        setMethods();
    }

    /**
     * Constructs a sprite with position, size, image, and starting velocity.
     * Initial health is set to -1 to symbolize a sprite that does not
     * have health.
     * (something with starting velocity but no health, e.g. moving asteroid).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param bounds the size of the canvas holding the sprite
     * @param image the image of the sprite
     * @param velocity the starting velocity of the sprite
     */
    public Sprite (Point position, Dimension size,
            Dimension bounds, Image image, Point velocity) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myVelocity = velocity;
        myHealth = Integer.MAX_VALUE;
        myBounds = bounds;
        myMapper = new SpriteMethodMap();
        myShotsFired = new ArrayList<Bullet>();
        setMethods();
    }

    /**
     * Constructs a sprite with position, size, image, and health.
     * (something with starting health but no starting velocity, e.g. player).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param bounds the size of the canvas holding the sprite
     * @param image the image of the sprite
     * @param health the starting health of the sprite
     */
    public Sprite (Point position, Dimension size,
            Dimension bounds, Image image, int health) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myHealth = health;
        myVelocity = new Point(0, 0);
        myBounds = bounds;
        myMapper = new SpriteMethodMap();
        myShotsFired = new ArrayList<Bullet>();
        setMethods();
    }

    /**
     * Constructs a sprite with position, size, image, velocity, and health.
     * (something with both starting velocity and health, e.g. enemy).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param bounds the size of the canvas holding the sprite
     * @param image the image of the sprite
     * @param velocity the starting velocity of the sprite
     * @param health the starting health of the sprite
     */
    public Sprite (Point position, Dimension size,
            Dimension bounds, Image image, Point velocity, int health) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myVelocity = velocity;
        myHealth = health;
        myBounds = bounds;
        myMapper = new SpriteMethodMap();
        myShotsFired = new ArrayList<Bullet>();
        setMethods();
    }

    abstract void setMethods();

    /**
     * Decreases the health of this sprite by the
     * amount specified (e.g. after being hit by a bullet).
     * @param damage the amount to decrease health by
     */
    public void decreaseHealth(int damage) {
        this.myHealth -= damage;
    }

    /**
     * This method draws the image at the sprite's
     * current position. If a sprite needs to draw anything
     * else (e.g. its bullets) then it can implement the
     * continuePaint method, if not, just leave it blank.
     * @param pen used for drawing the image
     */
    public void paint(Graphics pen) {
        pen.drawImage(myImage, getLeft(), getTop(),
                mySize.width, mySize.height, null);
        continuePaint(pen);
    }

    protected abstract void continuePaint(Graphics pen);

    /**
     * This method will update the position for every
     * sprite, then call an abstract method. This abstract
     * method will be specific to a certain sprite (only if
     * they need to do something other than update position).
     * This allows for easy implementation of new results specific
     * to each sprite when calling the update method.
     */
    public void update() {
        // if this sprite is out of bounds (top or bottom) then
        // it is out of the game
        if (getHealth() < 0) {
            this.die();
        }
        else {
            getPosition().translate(getVelocity().x, getVelocity().y);
            continueUpdate();
        }
    }

    protected abstract void continueUpdate();

    /**
     * Tells the method mapper (class that holds strings to methods)
     * which key to use (which method to choose).
     * If parameters are added here, they should also be added to
     * SpriteMethodMap -> doEvent method. Also, newly added parameters
     * should be added at the end (after all other previous parameters).
     *
     * @param key the string (key) that maps to the right method to do
     * @param s the sprite that this one collides with
     */
    public void doEvent(String key, Sprite s) {
        myMapper.doEvent(key, s);
    }

    /**
     * Sets the sprite's default action as
     * doing nothing (continuing on its current
     * velocity vector).
     *
     * @param o a (possibly empty) list of
     * parameters to be used in the action
     * (only used if the sprite overrides the do
     * action method itself)
     */
    @Override
    public void doAction (Object ... o) {

    }

    /**
     * Erases the sprite's image, which will be
     * checked for during paint methods and erased
     * from the game if null.
     */
    public void die() {
        this.setImage(null);
    }

    /**
     * Has the player fire a bullet.
     * The bullet is added to the player's list of fired bullets
     * and will be painted during the player's paint method.
     */
    public void fireBullet() {
        ImageIcon iib = new ImageIcon(this.getClass().getResource(
                "../images/playerbullet.png"));
        Image bulletImage = iib.getImage();

        Bullet b = new Bullet(new Point(getPosition().x, getPosition().y), new Dimension(
                BULLET_SIZE, BULLET_SIZE), getBounds(), bulletImage,
                new Point(0, -5), 1, this);

        this.getBulletsFired().add(b);
    }

    public boolean checkBounds(String s) {
        //don't go past right
        if (RIGHT_BOUND.equals(s) && getRight() >= getBounds().width) {
            return false;
        }
        //don't pass left
        else if (LEFT_BOUND.equals(s) && getLeft() <= 0) {
            return false;
        }
        //don't go past top
        else if (TOP_BOUND.equals(s) && getTop() <= 0) {
            return false;        
        }
        //don't pass bottom
        else if (BOTTOM_BOUND.equals(s) && getBottom() >= getBounds().height) {
            return false;
        }

        return true;
    }

    /*
     * Getters and setters below here.
     */

    /**
     * Returns a string representing this sprite's type.
     *
     * @return lowercase string representing type of this sprite
     */
    public abstract String getType();

    /**
     * Returns this sprite's position of the
     * center of its image.
     * @return myPosition
     */
    public Point getPosition() {
        return this.myPosition;
    }

    /**
     * Sets this sprite's position to the specified
     * point.
     * @param position the new position
     */
    public void setPosition(Point position) {
        this.myPosition = position;
    }

    /**
     * Returns this sprite's velocity (velocity is
     * stored as a point to encompass both x and
     * y parts.
     * @return myVelocity
     */
    public Point getVelocity() {
        return this.myVelocity;
    }

    /**
     * Sets this sprite's velocity to a new one.
     * @param velocity the new velocity to set to
     */
    public void setVelocity(Point velocity) {
        this.myVelocity = velocity;
    }

    /**
     * Can set a new velocity with x and y values
     * instead of a new point.
     * @param x the x value of the new velocity
     * @param y the y value of the new velocity
     */
    public void setVelocity(int x, int y) {
        Point v = new Point(x, y);
        this.setVelocity(v);
    }

    /**
     * Returns a list of the bullets fired by this sprite.
     * @return myShotsFired
     */
    public List<Bullet> getBulletsFired() {
        return this.myShotsFired;
    }
    /**
     * Returns the image representing this sprite.
     * @return myImage
     */
    public Image getImage() {
        return this.myImage;
    }

    /**
     * Sets this sprite's image to something else.
     * @param image the new image to use
     */
    public void setImage(Image image) {
        this.myImage = image;
    }

    /**
     * Returns the health of the player.
     * @return myHealth
     */
    public int getHealth() {
        return this.myHealth;
    }

    /**
     * Sets the sprite's health to
     * a new number (e.g. after being damaged).
     * @param h the new health of the sprite
     */
    public void setHealth(int h) {
        this.myHealth = h;
    }

    protected void setMapper (SpriteMethodMap mapper) {
        this.myMapper = mapper;
    }

    protected SpriteMethodMap getMapper () {
        return this.myMapper;
    }

    protected Dimension getBounds() {
        return this.myBounds;
    }

    /**
     * Returns the leftmost x coordinate for the sprite.
     * @return the left coordinate of the image
     */
    public int getLeft() {
        return myPosition.x - mySize.width / 2;
    }

    /**
     * Returns the topmost y coordinate for the sprite.
     * @return the top coordinate of the image
     */
    public int getTop() {
        return myPosition.y - mySize.height / 2;
    }

    /**
     * Returns the rightmost y coordinate for the sprite.
     * @return the right coordinate of the image
     */
    public int getRight() {
        return myPosition.x + mySize.width / 2;
    }

    /**
     * Returns the bottommost y coordinate for the sprite.
     * @return the bottom coordinate of the image
     */
    public int getBottom() {
        return myPosition.y + mySize.height / 2;
    }

    /**
     * Returns the dimensions of the sprite.
     * @return the dimensions of the sprite.
     */
    public Dimension getSize() {
        return this.mySize;
    }
}