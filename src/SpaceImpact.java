import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SpaceImpact extends JPanel implements Runnable {
    protected final int tileSize = 35, rows = 16, columns = 25;
    protected final int screenWidth = columns * tileSize, screenHeight = rows * tileSize;

    private ImageIcon background = new ImageIcon(getClass().getResource("img/gameBackground.gif")); // background animation for SpaceImpact Panel 
    private int FPS = 60;
    private long lastBulletTime = System.currentTimeMillis();
    private int bulletInterval = 500;
    
    Thread gameThread;
    EventHandler eventH = new EventHandler();

    // have to check for collission
    ArrayList<ComputerVirus> compViruses = new ArrayList<ComputerVirus>();
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    
    Player player = new Player(this, eventH);
    Adware adware = new Adware(this);

    public SpaceImpact() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
        int spawnInterval = 2000;
        
        // while loop keeps updating regardless if interacting with the screen or not
        while (gameThread != null) {
            // quick processes
            update();
            repaint();

            long currentTime = System.currentTimeMillis();
            if(currentTime - lastSpawnTime >= spawnInterval) {
                compViruses.add(new ComputerVirus(this));
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
        player.update();
        // adware.update();
    
        if (eventH.spacePressed) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastBulletTime >= bulletInterval) {
                bullets.add(new Bullet(this, player));
                lastBulletTime = currentTime;
            }
        }
    
        // virus update priority 
        for (int i = compViruses.size() - 1; i >= 0; i--) {
            ComputerVirus cv = compViruses.get(i);
            cv.update();
            if (cv.outOfBounds) {
                compViruses.remove(i);
            }
        }
    
        // bullet update 
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet b = bullets.get(i);
            b.update();
    
            if (b.outOfBounds) {
                bullets.remove(i);
                continue;
            }
    
            boolean bulletUsed = false;
    
            for (int j = compViruses.size() - 1; j >= 0; j--) {
                ComputerVirus cv = compViruses.get(j);
    
                if (detectCollission(cv, b)) {
                    bullets.remove(i);
                    compViruses.remove(j);
                    bulletUsed = true;
                    break;
                }
            }
            
            if (bulletUsed) {
                // breaks out from bullet update loop when bullet is used
                break;
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        /* ImageIcon
            paints Icons from Images
            Images that are created from a URL, filename or byte array are preloaded using MediaTracker to monitor the loaded state of the image.
            
            Arguments/Parameters
            c - the component to be used as the observer if this icon has no image observer
            g - the graphics context
            x - the X coordinate of the icon's top-left corner
            y - the Y coordinate of the icon's top-left corner
        */
        if (background != null) {
            background.paintIcon(this, g2D, 0, 0);
        }
        
        draw(g2D);

        for(ComputerVirus i : compViruses) {
            i.draw(g2D);
        }

        player.draw(g2D);
        // adware.draw(g2D);

        // draw bullets
        for(Bullet i : bullets) {
            i.draw(g2D);
        }

        g2D.dispose();
    }

    // temporary grid for the panel
    public void draw(Graphics2D g2D) {
        
        /* 
        g2D.setColor(Color.CYAN); 
        for(int i = 0; i <= columns; i++) {
            g2D.drawLine(i * tileSize, 0, i * tileSize, screenHeight);
            if(i <= rows) {
                g2D.drawLine(0, i * tileSize, screenWidth, i * tileSize);
            }
        }
        */

        // g2D.setColor(new Color(25,32,38));
        // g2D.fillRect(0, 0, columns * tileSize, 2 * tileSize);
    }

    // collission detection formula from Kenny Yip: https://www.youtube.com/watch?v=UILUMvjLEVU
    public boolean detectCollission(ComputerVirus a, Bullet b) {
        return a.x < b.x + ((tileSize / 2) + 12) &&
        a.x + tileSize + 10 > b.x &&
        a.y < b.y + ((tileSize / 4) + 12) &&
        a.y + tileSize + 10 > b.y;
    }
}