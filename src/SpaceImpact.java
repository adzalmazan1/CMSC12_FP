import java.awt.*;
import javax.swing.*;

public class SpaceImpact extends JPanel implements Runnable {
    // temporary variables
    protected final int tileSize = 35, rows = 16, columns = 25;
    protected final int screenWidth = columns * tileSize, screenHeight = rows * tileSize;

    private int FPS = 60;

    Thread gameThread;
    EventHandler eventH = new EventHandler();
    ComputerVirus compVirus = new ComputerVirus(this, eventH);

    public SpaceImpact() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // drawing from this component will be done in an offscreen painting buffer
        
        this.addKeyListener(eventH);
        this.setFocusable(true);
    }

    // starting the game thread
    public void startGameThread() {
        gameThread = new Thread(this); // passes SpaceImpact as argument
        gameThread.start(); // calls run
    }

    // for gameThread
    @Override
    public void run() {
        // second to nanosecond conversion 1 - 1e+9
        double drawInterval = 1000000000 / FPS; // 
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            update();
            repaint();

            // recheck logic here 
            // try KYC
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }    
        }
    }

    public void update() {
        compVirus.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // draw a grid in the screen
        g.setColor(Color.RED); 
        for(int i = 0; i <= columns; i++) {
            // vertical
            // starts the line at the top of the screen (i * tileSize, 0) and ends at the bottom (i * tileSize, screenHeight)
            g.drawLine(i * tileSize, 0, i * tileSize, screenHeight);
            if(i <= rows) {
                g.drawLine(0, i * tileSize, screenWidth, i * tileSize);
            }
        }

        // draw a virus here
        compVirus.draw((Graphics2D)g);
        
        
        
        // ???
        g.dispose();
    }
}