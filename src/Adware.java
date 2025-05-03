import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Adware extends Entity implements Deployable {
    private SpaceImpact spaceImpact;

    private boolean movingDown = true;
    private boolean isTerminated = false;

    private int yUpper; 
    private int yLower;

    private int healthWidth;
    private int healthHeight;

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
    }

    @Override
    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/bossSprites/boss_1.png")); 
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        movementCounter++;
        if(movementCounter >= movementChange) {
            if(movingDown) {
                y += speed;
                if (y >= yLower) movingDown = false;
            }
            else if(!movingDown) {
                y -= speed;
                if (y <= yUpper) movingDown = true;
            }
            movementCounter = 0;
        }


    }

    public void draw(Graphics2D g2D) {
        // health bar
        g2D.setColor(Color.RED);
        g2D.fill3DRect(x + (spaceImpact.tileSize * 2), y - 10, healthWidth, healthHeight, true);
        
        // boss image
        BufferedImage img = defaultImg;
        g2D.drawImage(img, x, y, width, height, null);
    }

    public int getAdwareHealth() {
        return healthWidth;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setAdwareHealth() {
        healthWidth -= 10;
        System.out.println("Adware hit! New health width: " + healthWidth);
    }
    
}