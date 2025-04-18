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

        direction = "def";
        speed = 5;
    }

    public void loadCompVirusImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/computerVirusDef.png")); 
            left1 = ImageIO.read(getClass().getResourceAsStream("img/computerVirusLeft.png")); 
            up1 = ImageIO.read(getClass().getResourceAsStream("img/computerVirusPink1.png")); 
            down1 = ImageIO.read(getClass().getResourceAsStream("img/computerVirusPink2.png")); 
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        // update information about computer virus object created
        frameCounter++;
        if(frameCounter >= frameChange) {
            if(spriteNum == 1) {
                spriteNum = 2; 
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            direction = "def";
            frameCounter = 0;
        }

        movementCounter++;
        if(movementCounter >= movementChange) {
            x -= speed;
            y -= -speed + Math.random() * (speed * 2);
            direction = "up";
            movementCounter = 0;
        }
    }

    public void draw(Graphics2D g2D) {
        BufferedImage img = null;
        switch (direction) {
            case "def":
                if(spriteNum == 1) {
                    img = defaultImg;
                }
                else if(spriteNum == 2) {
                    img = left1;
                }
                break;
            case "up":
                if(spriteNum == 1) {
                    img = up1;
                }
                else if(spriteNum == 2) {
                    img = down1;
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