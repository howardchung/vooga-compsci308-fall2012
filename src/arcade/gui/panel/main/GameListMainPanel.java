package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;

/**
 * A subclass of the Main Panel which displays a short, scrolling list
 * of all the Games available in the Arcade. When a Game is selected,
 * the User is taken to the Game's Profile Page.
 * 
 * @author Kannan
 *
 */
public class GameListMainPanel extends AMainPanel implements ScrollPaneConstants{

    //TODO:REFACTORING
    private List<String> myGameList;
    private String gameSelected;
    private JList gameList;
    
    public GameListMainPanel (Arcade a) {
        super(a);
         myGameList = a.getModelInterface().getGameList();

    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        String[] arrayOfGames = new String[myGameList.size()];
        for (int i = 0; i < myGameList.size(); i++) {
            System.out.println(myGameList.get(i));
            arrayOfGames[i] = myGameList.get(i);
        }
        System.out.println(arrayOfGames);
//        MigLayout layout = new MigLayout("align center, fill");
        MigLayout layout = new MigLayout("", "50[center]", "[]50[][300, grow]50[]");

        myPanel.setLayout(layout);

        
        JList listOfGames = new JList(arrayOfGames);
        listOfGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfGames.setLayoutOrientation(JList.VERTICAL);
        listOfGames.setVisibleRowCount(3);
        gameList = listOfGames;

        JScrollPane listScroller = new JScrollPane(gameList, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        
        
        JLabel gameSelectLabel = new JLabel("Select a Game to View: ");
        gameSelectLabel.setForeground(Color.WHITE);
        gameSelectLabel.setLabelFor(listScroller);
        
        
        JButton goButton = new JButton("Go!");

        goButton.setActionCommand("Go!");
        goButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                gameSelected = (String) gameList.getSelectedValue();
                if (gameSelected != null) {
                    getArcade().saveVariable("GameName", gameSelected);
                    getArcade().replacePanel("GameProfile");
                }
            }
              
          });
        ImageIcon icon = new ImageIcon("src/arcade/gui/images/ArcadeClassics2.jpg");
        JLabel displayPic = new JLabel(icon);
        
//        myPanel.add(gameSelectLabel, "dock north, span, grow, align center");
//        myPanel.add(listScroller, "span, grow");
//        myPanel.add(displayPic, "span, grow");
//        myPanel.add(goButton, "dock south, span, grow, align center");

        myPanel.add(displayPic, "align center, wrap");
        myPanel.add(gameSelectLabel, "align center, wrap");
        myPanel.add(listScroller, "grow, wrap");
        myPanel.add(goButton, "grow, align center");
        
        return myPanel;
        
    }
    
}


