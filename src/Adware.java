import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Adware extends Entity implements Deployable {
    private SpaceImpact spaceImpact;

    private boolean movingDown = true;

    private int yUpper; 
    private int yLower; 

    public Adware(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
        
        setDefaultValues();
        loadImage();
    }

    @Override
    public void setDefaultValues() {
        x = spaceImpact.screenWidth - spaceImpact.tileSize * 5;
        y = spaceImpact.tileSize * 2;
        speed = 5;
        movementChange = 5;

        yUpper = spaceImpact.tileSize * 2;
        yLower = spaceImpact.screenHeight - (spaceImpact.tileSize * 5);
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
        BufferedImage img = defaultImg;
        g2D.drawImage(img, x, y, spaceImpact.tileSize * 5, spaceImpact.tileSize * 5, null);
    }
    
    // display health bar for adware
}