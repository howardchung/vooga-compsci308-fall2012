package arcade.datatransfer;

import java.util.List;
import arcade.gamemanager.Game;
import arcade.gamemanager.GameCenter;
import arcade.gui.Arcade;
import arcade.usermanager.SocialCenter;


/**
 * Methods that facilitate exchange of data between
 * the view and models of the Arcade
 * 
 * @author Michael Deng
 * 
 */
public class ModelInterface {

    private Arcade myArcade;
    private GameCenter myGameCenter;
    private SocialCenter mySocialCenter;

    /**
     * 
     * @param a
     */
    public ModelInterface (Arcade a) {
        myArcade = a;
        mySocialCenter=new SocialCenter();
        // myGameCenter = new GameCenter(); // GameCenter is currently broken.
        // mySocialCenter = SocialCenter.getInstance();

    }

    // #############################################################
    // To be Implemented by the Game team
    // #############################################################

    /**
     * returns the list of available games.
     * 
     * @return list of available games
     */
    public List<String> getGameList () {
        return myGameCenter.getGameList();
    }

    /**
     * returns Game object specified by the game's name. If no game is found,
     * returns null.
     * 
     * @param gameName name of requested game
     * @return requested Game object or null if no such game is found
     */
    public Game getGame (String gameName) {
        return myGameCenter.getGame(gameName);
    }

    /**
     * returns a list of games that have the tag.
     * 
     * @param tag a tag that games have in common
     * @return list of games that have the tag.
     */
    public List<String> getGameListByTagName (String tag) {
        return myGameCenter.getGameListByTagName(tag);
    }

    // #############################################################
    // To be Implemented by the User team
    // #############################################################

    /**
     * Should change the user of UserLink to whatever user is successfully
     * logged in.
     * 
     * @param username String of the username to be logged in.
     * @param password string password
     * @return True if successful login, false if unsuccessful.
     */
    public boolean executeLogin (String username, String password) {
        // TODO Implement this. Return true if login successful, false if
        // unsuccessful.
        /*
         * // test code
         * if (username.equals("mdeng1990") && password.equals("pswd")) { return
         * true; }
         * 
         * return false;
         */
        try {
            return mySocialCenter.logOnUser(username, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method creates a new user
     * 
     * @param username
     * @param password
     * @param fn
     * @param ln
     * @return true if successful, false if unsuccessful
     */
    public boolean executeNewUser (String username, String password, String fn, String ln) {

        // test code
        if (username.equals("mdeng1990")) { return false; }

        return true;
    }

}
