package util.input.tests.android;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JPanel;
import util.input.android.events.LineSegment;


public class AndroidDrawGame extends JPanel implements Runnable {

    /**
     * 
     * @param gameSurface
     */
    public static final int GAME_FPS = 30;
    /**
     * Delay time.
     */
    private final int myDelay = 1000 / GAME_FPS;
    private boolean myIsRunning;
    private Thread myGameLoop;

    private ArrayList<LineSegment> mySegments;

    public AndroidDrawGame () {
        mySegments = new ArrayList<LineSegment>();
        TestAndroidController myController = new TestAndroidController(this);
        setDoubleBuffered(true);
      
        myGameLoop = new Thread(this);
    }

    /**
     * Paint the board by telling Update active level
     * 
     * @param g Graphics used to draw.
     */
    @Override
    public void paint (Graphics g) {
        Graphics2D pen = (Graphics2D) g;
        pen.setColor(Color.BLACK);
        pen.fillRect(0, 0, getWidth(), getHeight());
        pen.setColor(Color.BLUE);
        Stroke stroke = new BasicStroke (5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        pen.setStroke(stroke);
        for (int i = mySegments.size() - 1; i >= 0; i--) {
            LineSegment l = mySegments.get(i);
            pen.drawLine((int) (l.getRelativeStartX() * getWidth()),
                         (int) (l.getRelativeStartY() * getHeight()),
                         (int) (l.getRelativeEndX() * getWidth()),
                         (int) (l.getRelativeEndY() * getHeight()));
        }

    }

    @Override
    public void run () {
        long beforeTime;
        long timeDiff;
        long sleep;
        beforeTime = System.currentTimeMillis();
        while (true) {
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = myDelay - timeDiff;
            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    /**
     * Starts the JPanel's animation thread
     */
    public void startGame () {
        if (!myIsRunning) {
            myGameLoop.start();
            myIsRunning = true;
        }
    }

    /**
     * Pause the game thread.
     */
    @SuppressWarnings("deprecation")
    public void pauseGame () {
        if (myIsRunning) {
            myGameLoop.stop();
            myIsRunning = false;
        }
    }

    public void addLine (LineSegment l) {
        mySegments.add(l);

    };

}
