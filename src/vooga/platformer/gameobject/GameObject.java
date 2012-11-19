package vooga.platformer.gameobject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.camera.Camera;
import vooga.platformer.level.Level;


/**
 * 
 * @author Niel Lebeck
 * @author Yaqi Zhang (revised)
 * @author Grant Oakley (modified)
 * 
 */
public abstract class GameObject {
    protected static final String X_TAG = "x";
    protected static final String Y_TAG = "y";
    protected static final String WIDTH_TAG = "width";
    protected static final String HEIGHT_TAG = "height";
    protected static final String DEFAULT_IMAGE_TAG = "imagePath";

    private boolean removeFlag;
    private List<UpdateStrategy> strategyList;
    private double x;
    private double y;
    private double width;
    private double height;
    private String defaultImage;

    private GameObject () {
        strategyList = new ArrayList<UpdateStrategy>();
    }

    // /**
    // *
    // * @param inX starting x position
    // * @param inY starting y position
    // */
    // public GameObject (double inX, double inY, double inWidth, double
    // inHeight) {
    // this();
    // x = inX;
    // y = inY;
    // width = inWidth;
    // height = inHeight;
    // }

    /**
     * @param configString containing key-value pairs for the GameObject's
     *        parameters. The
     *        config string should contain a sequence of these pairs separated
     *        by commas (','), and within
     *        each pair, the key should be separated from the value by an '='
     *        character.
     */
    public GameObject (String configString) {
        this();
        Map<String, String> configMap = parseConfigString(configString);
        x = Double.parseDouble(configMap.get(X_TAG));
        y = Double.parseDouble(configMap.get(Y_TAG));
        width = Double.parseDouble(configMap.get(WIDTH_TAG));
        height = Double.parseDouble(configMap.get(HEIGHT_TAG));
        defaultImage = configMap.get(DEFAULT_IMAGE_TAG);
    }

    /**
     * Gets a map in which the keys are parameter tag names, and the strings
     * that they map to are the descriptions of the values that should be passed
     * for that tag.
     * 
     * A null return value means that this GameObject subclass requires no
     * additional parameters be passed in its config string in order to be
     * instantiated.
     * 
     * @return a map of config string parameter tags and descriptions of the
     *         values that should be passed with these tags
     * @author Grant Oakley
     */
    public Map<String, String> getConfigStringParams () {
        Map<String, String> params = new HashMap<String, String>();
        params.put(X_TAG, "x position of the object");
        params.put(Y_TAG, "y position of the object");
        params.put(WIDTH_TAG, "width of the object");
        params.put(HEIGHT_TAG, "height of the object");
        params.put(DEFAULT_IMAGE_TAG, "file name of the image to the be the default image.");
        return params;
    }

    protected Map<String, String> parseConfigString (String configString) {
        Map<String, String> configMap = new HashMap<String, String>();
        String[] pairs = configString.split(",");
        for (String entry : pairs) {
            String[] entrySplit = entry.split("=");
            configMap.put(entrySplit[0], entrySplit[1]);
        }
        return configMap;
    }

    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public void setX (double inX) {
        x = inX;
    }

    public void setY (double inY) {
        y = inY;
    }

    /**
     * Add a strategy to this GameObject's strategy list.
     * 
     * @param strat
     */
    public void addStrategy (UpdateStrategy strat) {
        strategyList.add(strat);
    }

    /**
     * Remove a strategy from the list.
     * 
     * @param strat
     */
    public void removeStrategy (UpdateStrategy strat) {
        strategyList.remove(strat);
    }

    /**
     * Used by concrete subclasses to work with the strategy list.
     * 
     * @return the strategy list
     */
    protected Iterable<UpdateStrategy> getStrategyList () {
        return strategyList;
    }

    /**
     * Update the GameObject. This method is called once per update cycle.
     * 
     * @param elapsedTime time duration of the update cycle
     */
    public void update (Level level, long elapsedTime) {
        for (UpdateStrategy us : strategyList) {
            us.applyAction();
        }
    }

    /**
     * Paints the GameObject to the given Graphics object.
     * 
     * @param pen Graphics object to paint on
     */
    public void paint (Graphics pen, Camera cam) {
        double x = getX();
        double y = getY();
        Rectangle2D rect = cam.getBounds();
        double xOffset = rect.getX();
        double yOffset = rect.getY();

        if (getShape().intersects(rect)) {
            pen.drawImage(getCurrentImage().getScaledInstance((int) width, (int) height,
                                                              Image.SCALE_DEFAULT),
                          (int) (x - xOffset), (int) (y - yOffset), null);
        }
    }

    /**
     * @return the current Image of this GameObject
     */
    public abstract Image getCurrentImage ();

    /**
     * Mark the GameObject for removal by the Level. The level should delete
     * all marked GameObjects at the end of the update cycle.
     */
    public void markForRemoval () {
        removeFlag = true;
    }

    /**
     * Used to determine whether a GameObject should be removed.
     * 
     * @return true if the GameObject is marked for removal
     */
    public boolean checkForRemoval () {
        return removeFlag;
    }

    /**
     * Gives the GameObject's bounds.
     * 
     * @return GameObject's bounds.
     */
    public Rectangle2D getShape () {
        return new Rectangle2D.Double(x, y, width, height);
    }

}
