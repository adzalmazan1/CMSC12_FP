import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ComputerVirus extends Entity {
    SpaceImpact spaceImpact;

    private int frameChange = 20;
    private int movementChange = 60; // (1 sec, 1 movement)

    public ComputerVirus(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;

        setDefaultValues();
        loadCompVirusImage();
    }

    public void setDefaultValues() {
        int xMin = ((4 * spaceImpact.columns) /  5) * spaceImpact.tileSize;
        int xMax = (spaceImpact.columns - 1) * spaceImpact.tileSize;

        int yMin = 2 * spaceImpact.tileSize;
        int yMax = (spaceImpact.rows - 1) * spaceImpact.tileSize;

        x = xMin + (int)(Math.random() * ((xMax - xMin)));
        y = yMin + (int)(Math.random() * ((yMax - yMin)));

        direction = "down";
        speed = 5;
    }

    public void loadCompVirusImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("img/computerVirus.png")); // load an img
            down2 = ImageIO.read(getClass().getResourceAsStream("img/computerVirus2.png")); // load an img
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        // update information about computer virus object created
        frameCounter++;
        if(frameCounter >= frameChange) {
            // everything happens in one frame
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            frameCounter = 0;
        }

        movementCounter++;
        if(movementCounter >= movementChange) {
            x -= speed;
            y -= -speed + Math.random() * (speed * 2);
            movementCounter = 0;
        }
    }

    public void draw(Graphics2D g2D) {
        BufferedImage img = null;
        switch (direction) {
            case "down":
                if(spriteNum == 1) {
                    img = down1;
                }
                else if(spriteNum == 2) {
                    img = down2;
                }
                break;
            default:
                break;
        }

        // boolean java.awt.Graphics.drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
        g2D.drawImage(img, x, y, spaceImpact.tileSize, spaceImpact.tileSize, null);
    }
}

/* 
 * About Math.rand()
 * Math.rand() generates a floating point number, say x, in the interval [0,1]
 * multiplying x by 6 makes it in the interval [0,6]
 * converting it to int is a truncation (almost retaining the integer part of the original number), so the value is either 0, 1, 2, 3, 4 or 5 
 * then adding one give either 1, 2, 3, 4, 5 or 6.
*/