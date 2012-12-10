package arcade.gui.frame;

import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;


/**
 * 
 * Alternative frame creator. Creates the frame for the arcade.
 * 
 * @author Michael Deng
 * 
 */
public class AltFrameCreator extends MigFrameCreator {

    // specifies the sizes for each panel location in the frame
    private static final int LOGO_WIDTH = 300;
    private static final int LOGO_HEIGHT = 100;
    private static final int BLANK_WIDTH = 500;
    private static final int BLANK_HEIGHT = 100;
    private static final int USER_WIDTH = 300;
    private static final int USER_HEIGHT = 100;
    private static final int NAV_WIDTH = 1100;
    private static final int NAV_HEIGHT = 50;
    private static final int MAIN_WIDTH = 800;
    private static final int MAIN_HEIGHT = 450;
    private static final int SEARCH_WIDTH = 300;
    private static final int SEARCH_HEIGHT = 450;
    private static final int FOOT_WIDTH = 1100;
    private static final int FOOT_HEIGHT = 50;

    /**
     * Constructor for AltFrameCreator
     * 
     * @param a arcade
     */
    public AltFrameCreator (Arcade a) {
        super(a);
    }

    @Override
    protected void setLayoutManager () {
        getContentPanel().setLayout(new MigLayout("", "[]0[grow]0[]", "[]0[]0[grow]0[]"));
    }

    @Override
    protected void addSubPanels () {

        addPanelWithoutBorder(LOGO_WIDTH, LOGO_HEIGHT, "logo", "align center");
        addPanelWithoutBorder(BLANK_WIDTH, BLANK_HEIGHT, "blank", "growx, align center");
        addPanelWithBorder(USER_WIDTH, USER_HEIGHT, "user", "align center, wrap");
        addPanelWithoutBorder(NAV_WIDTH, NAV_HEIGHT, "nav", "span, growx, wrap");

        addPanelWithoutBorder(FOOT_WIDTH, FOOT_HEIGHT, "foot", "span, growx");

        addPanelWithBorder(MAIN_WIDTH, MAIN_HEIGHT, "main", "span 2, grow");
        addPanelWithBorder(SEARCH_WIDTH, SEARCH_HEIGHT, "search", "growy, wrap");

    }

}
