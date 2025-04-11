import java.awt.*;
import javax.swing.*;

public class SpaceImpact extends JPanel implements Runnable {
    // temporary variables
    private final int tileSize = 35, rows = 16, columns = 25;
    private final int screenWidth = columns * tileSize, screenHeight = rows * tileSize;

    Thread gameThread;
    EventHandler eventH = new EventHandler();

    public SpaceImpact() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // drawing from this component will be done in an offscreen painting buffer
        
        this.addKeyListener(eventH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this); // passes SpaceImpact
        gameThread.start();
    }

    // for gameThread
    @Override
    public void run() {
        while(gameThread != null) {
            update();
            repaint();
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // draw a grid in the screen
        g.setColor(Color.gray); 
        for(int i = 0; i < columns; i++) {
            // vertical
            // starts the line at the top of the screen (i * tileSize, 0) and ends at the bottom (i * tileSize, screenHeight)
            g.drawLine(i * tileSize, 0, i * tileSize, screenHeight);
            // horizontal
            // starts the line at the left side (0, i * tileSize) and ends at the right side (screenWidth, i * tileSize)
            g.drawLine(0, i * tileSize, screenWidth, i * tileSize);
        }
    }

}