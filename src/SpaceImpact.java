import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private int currentScore;
    private int scorePlus = 50;

    // Waves quota
    private int[] enterWaveScore = {20, 200, 300};

    // Array Lists
    protected CopyOnWriteArrayList<ComputerVirus> compViruses;  // copyonwrite is the thread safe version of ArrayList
    protected CopyOnWriteArrayList<Bullet> bullets; 
    
    // Player and event handler
    private Player player;
    private EventHandler eventH;
    // private Sound sound = new Sound();

    // Bosses declaration
    private Adware adware;
    private Anonymous anon;
    private Trojan trojan;

    protected int gameState;
    protected int playState = 0;
    protected int gameWonState = 1;
    protected int gameOverState = 2;

    private GameWon gameWon;
    private GameOver gameOver;

    private boolean gameThreadRunning = true;

    public SpaceImpact(SpaceImpactDisplay display) {
        this.display = display;
        this.eventH = new EventHandler();
        this.compViruses = new CopyOnWriteArrayList<ComputerVirus>();
        this.bullets =  new CopyOnWriteArrayList<Bullet>();
        this.player = new Player(this, eventH);
        this.adware = new Adware(this);
        this.anon = new Anonymous(this, player);
        this.trojan = new Trojan(this);

        this.gameWon = new GameWon();
        this.gameOver = new GameOver();

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
        while (gameThreadRunning) {
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

        // modify this line of code
        if(eventH.enterPressed) {
            gameState = playState; // condition for game over state
        }
    
        // virus update *priority*
        for (int i = compViruses.size() - 1; i >= 0; i--) {
            ComputerVirus cv = compViruses.get(i);
            cv.update();
            
            if(detectCollission(player, cv)) {
                display.setLifeCount(display.getLifeCount() - 1);
                compViruses.remove(i);

                if(display.getLifeCount() == 0) {
                    gameState = gameOverState; // condition for game over state
                }
            }

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
                    bulletUsed = true;
                    cv.setHealth();

                    if(cv.getHealth() == 0) {
                        compViruses.remove(j);
                        currentScore += scorePlus;
                    }

                    if (display != null) { // null checking for best practices
                        display.setCurrentScore(currentScore);
                    }
                    break;
                }
            }
    
            // Check for collision with adware or other enemies
            if(currentScore >= enterWaveScore[0] && detectCollission(b, adware, 3) && !bulletUsed && adware.getHealth() >= 0) {
                bullets.remove(i); 
                adware.setHealth();
                bulletUsed = true;
                
                // one time stop of adware's spawn thread when health reaches zero
                stopBossSpawn(adware);
            }
            else if(currentScore >= enterWaveScore[1] && detectCollission(b, anon, 4) && !bulletUsed && anon.getHealth() > 0 && adware.getHealth() <= 0) {
                bullets.remove(i); 
                anon.setHealth();
                bulletUsed = true;

                // one time stop of adware's spawn thread when health reaches zero
                stopBossSpawn(anon);
            }
            else if(currentScore >= enterWaveScore[2] && detectCollission(b, anon, 3) && !bulletUsed && trojan.getHealth() > 0 && anon.getHealth() <= 0 && adware.getHealth() <= 0) {
                bullets.remove(i);
                trojan.setHealth();
                bulletUsed = true;

                // one time stop of adware's spawn thread when health reaches zero
                stopBossSpawn(trojan);
            }
    
            if (bulletUsed) {
                break;
            }
        }
        
        // Update boss appearance and health checks for different waves
        if(currentScore >= enterWaveScore[0] && adware.getHealth() > 0) {
            // System.out.println("adware update");
            adware.update();
            startBossSpawn(adware);
        }
        else if(currentScore >= enterWaveScore[1] && anon.getHealth() > 0 && adware.getHealth() <= 0) {
            anon.update();
            startBossSpawn(anon);            
        }
        else if(currentScore >= enterWaveScore[2] && trojan.getHealth() > 0 && anon.getHealth() <= 0 && adware.getHealth() <= 0) {
            trojan.update();
            startBossSpawn(trojan);
        }
    }

    public void startBossSpawn(Boss b) {
        // start only once the spawn thread
        if(!b.spawnStarted) {
            b.spawnThread = new Thread(b);
            b.spawnThread.start();
            b.spawnStarted = true; // spawn started from this moment
        }
    }

    public void stopBossSpawn(Boss b) {
        // one time stop of adware's spawn thread when health reaches zero
        if (b.getHealth() <= 0) {
            b.stopSpawnThread();
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g; 
            if(gameState == playState) {
                // Paint background
            if (background != null) {
                background.paintIcon(this, g2D, 0, 0);
            }
        
            draw(g2D); // Draw in Space Impact Panel
            player.draw(g2D); // Draw Player icon
        
            // draws computer viruses from ArrayList
            for(ComputerVirus i : compViruses) {
                i.draw(g2D);
            }

            // draws bullets viruses from ArrayList
            for(Bullet i : bullets) {
                i.draw(g2D);
            }
        
            // Draw wave elements based on score
            if(currentScore >= enterWaveScore[0] && adware.getHealth() >= 0) {
                adware.draw(g2D);
            }
            else if(currentScore >= enterWaveScore[1] && anon.getHealth() > 0 && adware.getHealth() <= 0) {
                anon.draw(g2D);
            }
            else if(currentScore >= enterWaveScore[2] && trojan.getHealth() > 0 && anon.getHealth() <= 0 && adware.getHealth() <= 0) {
                trojan.draw(g2D);
            }
        }
        else if(gameState == gameWonState) {
            display.setVisible(false);
            gameWon.draw(g2D, screenWidth, screenHeight);
        }
        else if(gameState == gameOverState) {
            display.setVisible(false);
            gameOver.draw(g2D, screenWidth, screenHeight);
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
    
    /* 
    // To do -> music part
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
    */

    // collission detection formula from Kenny Yip: https://www.youtube.com/watch?v=UILUMvjLEVU
    public boolean detectCollission(Entity a, ComputerVirus b) {
        return a.x < b.x + b.width &&
        a.x + a.width > b.x &&
        a.y < b.y + b.height &&
        a.y + a.height > b.y;
    }

    // entity chosen

    public boolean detectCollission(Bullet a, Boss b, int offSetMult) {
        int hitboxOffsetX = tileSize * offSetMult; 
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