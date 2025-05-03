import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SpaceImpact extends JPanel implements Runnable {
    // Variables for the dimensions
    protected final int tileSize = 35, rows = 16, columns = 25;
    protected final int screenWidth = columns * tileSize, screenHeight = rows * tileSize;

    // Display
    private ImageIcon background = new ImageIcon(getClass().getResource("img/gameBackground.gif")); // background animation for SpaceImpact Panel 
    private SpaceImpactDisplay display;

    // Game FPS
    private int FPS = 60;
    private Thread gameThread;

    // Computer virus/normals spawn time
    private long lastSpawnTime = System.currentTimeMillis();
    private int spawnInterval = 2000;

    // To do: update spawnInterval / give it a multiplier depending on what wave you're in

    // Bullet spawn tile
    private long lastBulletTime = System.currentTimeMillis();
    private int bulletInterval = 500;
    
    // Scoring system variables
    private int currentScore = 0;
    private int scorePlus = 50;

    // Waves quota
    private int[] enterWaveScore = {0, 2000, 3000};

    // Array Lists
    private ArrayList<ComputerVirus> compViruses;
    private ArrayList<Bullet> bullets; 
    
    // Player and event handler
    private Player player;
    private EventHandler eventH;
    Sound sound = new Sound();

    // Bosses declaration
    private Adware adware;

    public SpaceImpact(SpaceImpactDisplay display) {
        this.display = display;
        this.eventH = new EventHandler();
        this.compViruses = new ArrayList<ComputerVirus>();
        this.bullets = new ArrayList<Bullet>();
        this.player = new Player(this, eventH);
        this.adware = new Adware(this);

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

                Thread.sleep((long) remainingTime); // Thread.sleep() method can be used to pause the execution of the current thread for a specified time in milliseconds.
                nextDrawTime += drawInterval; // nextDrawTime gets updated
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update(); // player update -> sprite + movement

        if (eventH.spacePressed) { // key listener in Space Impact panel
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastBulletTime >= bulletInterval) {
                bullets.add(new Bullet(this, player));
                lastBulletTime = currentTime;
            }
        }
    
        // virus update *priority*
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
                
                // collission check for bullet and computer virus
                if (detectCollission(b, cv) && !bulletUsed) {
                    bullets.remove(i);
                    compViruses.remove(j);
                    bulletUsed = true;
                    currentScore += scorePlus;
                    
                    if (display != null) { // null checking for best practices
                        display.setCurrentScore(currentScore);
                    }
                    break;
                }
            }
            
            // collission check for bullet and adware
            if(detectCollission(b, adware) && !bulletUsed && adware.getAdwareHealth() > 0) {
                bullets.remove(i);
                adware.setAdwareHealth();
                bulletUsed = true;
            }
            
            if (bulletUsed) { // one bullet one virus ratio
                break;
            }
        }

        // update this logic for boss appearance, wave 1
        if(currentScore >= enterWaveScore[0] && adware.getAdwareHealth() > 0) {
            adware.update();
            // display.setGamePlayStatus("Boss Level, Wave 1");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g; 

        // paints background icon
        if (background != null) {
            background.paintIcon(this, g2D, 0, 0);
        }
        
        draw(g2D); // draw in Space Impact Panel

        player.draw(g2D); // draws Player icon

        // draws computer viruses from ArrayList
        for(ComputerVirus i : compViruses) {
            i.draw(g2D);
        }

        // draws bullets viruses from ArrayList
        for(Bullet i : bullets) {
            i.draw(g2D);
        }

        // draw wave 1 Elements
        if(currentScore >= enterWaveScore[0] && adware.getAdwareHealth() > 0) {
            adware.draw(g2D);
        }
        
        g2D.dispose();        
    }

    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.BLACK); 
        for(int i = 0; i <= columns; i++) {
            g2D.drawLine(i * tileSize, 0, i * tileSize, screenHeight);
            if(i <= rows) {
                g2D.drawLine(0, i * tileSize, screenWidth, i * tileSize);
            }
        }
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSFX(int i) {
        sound.setFile(i);
        sound.play();
    }

    // collission detection formula from Kenny Yip: https://www.youtube.com/watch?v=UILUMvjLEVU
    public boolean detectCollission(Bullet a, ComputerVirus b) {
        return a.x < b.x + b.width &&
        a.x + a.width > b.x &&
        a.y < b.y + b.height &&
        a.y + a.height > b.y;
    }

    public boolean detectCollission(Bullet a, Adware b) {
        int hitboxOffsetX = tileSize * 2; 
        int adjustedX = b.x + hitboxOffsetX;
        int adjustedWidth = b.width - hitboxOffsetX;
    
        return a.x < adjustedX + adjustedWidth &&
               a.x + a.width > adjustedX &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }
}

/* 
Collission Logic
 * return a.x < b.x + b.width &&
        a.x + a.width > b.x &&
        a.y < b.y + b.height &&
        a.y + a.height > b.y;
*/