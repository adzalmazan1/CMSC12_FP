import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ComputerVirus extends Entity implements Deployable {
    private SpaceImpact spaceImpact;

    public ComputerVirus(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;

        setDefaultValues();
        loadImage();
    }

    public void setDefaultValues() {
        int xMin = ((4 * spaceImpact.columns) /  5) * spaceImpact.tileSize;
        int xMax = (spaceImpact.columns - 1) * spaceImpact.tileSize;

        int yMin = spaceImpact.tileSize * 2;
        int yMax = spaceImpact.screenHeight - (spaceImpact.tileSize * 2);

        x = xMin + (int)(Math.random() * ((xMax - xMin)));
        y = yMin + (int)(Math.random() * ((yMax - yMin)));

        direction = "up";
        speed = 50;

        width = spaceImpact.tileSize + 8;
        height = (spaceImpact.tileSize / 2) + 8;

        frameChange = 20;
        movementChange = 60; // (1 sec, 1 movement)
    }

    public void loadImage() {
        try {
            up = ImageIO.read(getClass().getResourceAsStream("img/computerVirusSprites/up.png"));
            down = ImageIO.read(getClass().getResourceAsStream("img/computerVirusSprites/down.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        // update information about computer virus object created
        frameCounter++;
        if (frameCounter >= frameChange) {
            if (spriteNum == 1) {
                direction = "down";
                spriteNum = 2; 
            } else if (spriteNum == 2) {
                direction = "up";
                spriteNum = 1;
            }
            frameCounter = 0;
        }
        
        // previous: move -> delete in the middle of movement
        movementCounter++;
        if (movementCounter >= movementChange) {
            double yMovement;
            int newY;

            do {
                yMovement = -speed + Math.random() * (speed * 2);
                newY = (int)(y - yMovement);
            } while(newY <= spaceImpact.tileSize * 2 || newY >= spaceImpact.screenHeight - (spaceImpact.tileSize * 3));
            
            int newX = (int) (x - speed);
            
            // check bounds 
            if (newX <= -spaceImpact.tileSize) {
                outOfBounds = true;
            } 
            else {
                x = newX;
                y = newY;
            }
            
            movementCounter = 0;
        }
    }

    public void draw(Graphics2D g2D) {
        BufferedImage img = null;
        switch (direction) {
            case "up":
                img = up;
                break;
            case "down":
                img = down;
                break;
        } 
        // height tile/2 = 50, orig ratio = 55
        // boolean java.awt.Graphics.drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
        g2D.drawImage(img, x, y, width, height, null);
    }
}

/* 
 * About Math.rand()
 * Math.rand() generates a floating point number, say x, in the interval [0,1]
 * multiplying x by 6 makes it in the interval [0,6]
 * converting it to int is a truncation (almost retaining the integer part of the original number), so the value is either 0, 1, 2, 3, 4 or 5 
 * then adding one give either 1, 2, 3, 4, 5 or 6.
*/