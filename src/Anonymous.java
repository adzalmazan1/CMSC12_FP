import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Anonymous extends Boss {
    private SpaceImpact spaceImpact;
    private Player player;
    
    private int xLeftMost; 
    private int xRightMost;

    public Anonymous(SpaceImpact spaceImpact, Player player) {
        this.spaceImpact = spaceImpact;
        this.player = player;
        setDefaultValues();
        loadImage();
    }

    @Override
    public void setDefaultValues() {
        x = spaceImpact.screenWidth - spaceImpact.tileSize * 7;
        y = spaceImpact.tileSize * 5;
        
        width = spaceImpact.tileSize * 7;
        height = spaceImpact.tileSize * 7;

        speed = 8;
        movementChange = 5;

        xLeftMost = spaceImpact.screenWidth / 3;
        xRightMost = spaceImpact.screenWidth - (spaceImpact.tileSize * 7);

        // health width and height ** separate attributes **
        healthWidth = spaceImpact.tileSize * 5;
        healthHeight = 10;

        isSpawning = false;
    }

    @Override
    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/bossSprites/anonymous.png")); 
            spawnImg = ImageIO.read(getClass().getResourceAsStream("img/bossSprites/anonSpawn.png")); 
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        movementCounter++;
        if(movementCounter >= movementChange) {
            x -= speed;
            if(x >= xRightMost || x <= xLeftMost) {
                outOfBounds = true;
                speed = -speed;
            }
            movementCounter = 0;
        }
    }

    public void draw(Graphics2D g2D) {
        // health bar
        g2D.setColor(Color.RED);
        g2D.fill3DRect(x + (spaceImpact.tileSize * 2), y - 10, healthWidth, healthHeight, true);
        
        /// boss image
        if(!isSpawning) {
            img = defaultImg;
        }
        else {
            img = spawnImg;
        }

        g2D.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void addSpawn() {  
        System.out.println("Anon spawn is running: " + player.x);
        isSpawning = true;
        /* 
        int centerX = player.x + player.width / 2;
        int centerY = player.y + player.height / 2;

        int radius = 150; 
        int virusCount = 5;

        for (int i = 0; i < virusCount; i++) {
            ComputerVirus virus = new ComputerVirus(spaceImpact);
            double angle = 2 * Math.PI * i / virusCount;

            int newX = (int) (centerX + radius * Math.cos(angle)) - virus.width / 2;
            int newY = (int) (centerY + radius * Math.sin(angle)) - virus.height / 2;

            virus.x = newX;
            virus.y = newY;
            spaceImpact.compViruses.add(virus);
            
        }
        */
    }
}