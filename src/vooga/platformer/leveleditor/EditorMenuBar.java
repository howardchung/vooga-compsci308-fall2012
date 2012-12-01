package vooga.platformer.leveleditor;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class EditorMenuBar extends JMenuBar{

    public EditorMenuBar(final LevelEditor myEditor) {
        JMenu levelMenu = new JMenu("Level");
        levelMenu.add(new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {
//                myEditor.load();
            }
        });
        levelMenu.add(new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent e) {
//                myEditor.clear();
            }
        });
        levelMenu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
//                myEditor.newLevel();
            }
        });
        levelMenu.add(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
//                myEditor.save();
            }
        });

        JMenu spriteMenu = new JMenu("Sprite");
        spriteMenu.add(new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //load();
            }
        });
        spriteMenu.add(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //newLevel();
            }
        });
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("ppoooooooopppp");
    }
}