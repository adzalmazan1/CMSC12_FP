import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class SpaceImpact extends JPanel implements Runnable {
    // temporary variables
    protected final int tileSize = 35, rows = 16, columns = 25;
    protected final int screenWidth = columns * tileSize, screenHeight = rows * tileSize;

    private int FPS = 60;

    Thread gameThread;
    EventHandler eventH = new EventHandler();

    ArrayList<ComputerVirus> compViruses = new ArrayList<ComputerVirus>();

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

    @Override
    // Reference for gameThread logic -> RyiSnow (Youtube)
    // Link: https://www.youtube.com/watch?v=VpH33Uw-_0E&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&t=2084s
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        long lastSpawnTime = System.currentTimeMillis();
        int spawnDelay = 500; 

        // while loop keeps updating regardless if interacting with the screen or not
        while (gameThread != null) {
            // quick processes
            update();
            repaint();
            
            // To do: update this logic via constraints later
            long currentTime = System.currentTimeMillis(); // keeps getting update via loop
            if (currentTime - lastSpawnTime >= spawnDelay) {
                compViruses.add(new ComputerVirus(this, eventH));
                lastSpawnTime = currentTime;
            }
        
            // updated system nano time after update and repaint
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0)  {
                    remainingTime = 0;
                }

                // Thread.sleep() method can be used to pause the execution of the current thread for a specified time in milliseconds.
                Thread.sleep((long) remainingTime);
                // nextDrawTime gets updated
                nextDrawTime += drawInterval;
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        /* 
        for (ComputerVirus virus : compViruses) {
            virus.update();
        }
        */
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

        for (ComputerVirus virus : compViruses) {
            virus.draw((Graphics2D) g);
        }
    }
}