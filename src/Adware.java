import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Adware extends Boss {
    private SpaceImpact spaceImpact;

    private int yUpper; 
    private int yLower;

    public Adware(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
        setDefaultValues();
        loadImage();
    }

    @Override
    public void setDefaultValues() {
        x = spaceImpact.screenWidth - spaceImpact.tileSize * 7;
        y = spaceImpact.tileSize * 2;
        
        width = spaceImpact.tileSize * 7;
        height = spaceImpact.tileSize * 7;

        speed = 8;
        movementChange = 5;

        yUpper = spaceImpact.tileSize * 2;
        yLower = spaceImpact.screenHeight - (spaceImpact.tileSize * 7);

        // health width and height ** separate attributes **
        healthWidth = spaceImpact.tileSize * 5;
        healthHeight = 10;
    
        isSpawning = false;
    }

    @Override
    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/bossSprites/adware.png")); 
            spawnImg = ImageIO.read(getClass().getResourceAsStream("img/bossSprites/adwareSpawn.png")); 
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        movementCounter++;
        if(movementCounter >= movementChange) {
            y += speed;
            if(y >= yLower || y <= yUpper) {
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
        
        // g2D.drawRect(x, y, width, height);

        // boss image
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
        isSpawning = true;

        int numEnemies = 5;
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
    }
}