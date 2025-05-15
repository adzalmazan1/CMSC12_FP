import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Anonymous extends Boss {
    private SpaceImpact spaceImpact;
    
    private int xLeftMost; 
    private int xRightMost;

    public Anonymous(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
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
        /* 
        This is boss 1 spawning and it works.
        System.out.println("Anon spawn is running");
        System.out.println("Anon Health: " + healthWidth);
        isSpawning = true;

        int numEnemies = 8;
        double angleStep = 2 * Math.PI / numEnemies;
        int radius = spaceImpact.tileSize;

        int centerX = x - radius;
        int centerY = y + height / 2;

        for (int i = 0; i < numEnemies; i++) {
            double angle = i * angleStep;
            int virusX = (int)(centerX + radius * Math.cos(angle));
            int virusY = (int)(centerY + radius * Math.sin(angle));

            ComputerVirus virus = new ComputerVirus(spaceImpact);
            virus.x = virusX;
            virus.y = virusY;
            spaceImpact.compViruses.add(virus);
        }
        */
    }
}