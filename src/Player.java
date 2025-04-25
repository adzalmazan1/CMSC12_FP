import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {
    SpaceImpact spaceImpact;
    EventHandler eventH;

    private BufferedImage defaultImg2;

    public Player(SpaceImpact spaceImpact, EventHandler eventH) {
        this.spaceImpact = spaceImpact;
        this.eventH = eventH;

        setDefaultValues();
        loadImage();
    }

    public void setDefaultValues() {
        x = spaceImpact.tileSize;
        y = 3 * spaceImpact.tileSize;
        direction = "def";
        speed = 5;
        frameChange = 35;
    }

    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/default1.png")); // load an img
            defaultImg2 = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/default2.png")); // load an img
            up = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/up.png")); 
            down = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/down.png")); 
            left = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/left.png")); 
            right = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/right.png")); 
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // add sprite update
    public void update() {
        // update information about computer virus object created
        frameCounter++;
        if (frameCounter >= frameChange) {
            if (spriteNum == 1) {
                spriteNum = 2; 
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            direction = "def";
            frameCounter = 0;
        }
    
        if(eventH.upPressed || eventH.downPressed || eventH.leftPressed || eventH.rightPressed) {
            if(eventH.upPressed) {
                direction = "up";
                y -= speed;
            }
            else if(eventH.downPressed) {
                direction = "down";
                y += speed;
            }
            else if(eventH.leftPressed) {
                direction = "left";
                x -= speed;
            }
            else if(eventH.rightPressed) {
                direction = "right";
                x += speed;
            }
        }
        else {
            direction = "def";
        }
    }

    // currently uses default image only
    public void draw(Graphics2D g2D) {
        // draw player
        BufferedImage img = null;
        switch (direction) {
            case "def":
                if(spriteNum == 1) {
                    img = defaultImg;
                }
                else if(spriteNum == 2) {
                    img = defaultImg2;
                }
                break;
            case "up":
                img = up;
                break;
            case "down":
                img = down;
                break;
            case "left":
                img = left;
                break;
            case "right":
                img = right;
                break;
            default:
                break;
        }

        g2D.drawImage(img, x, y, spaceImpact.tileSize * 4, spaceImpact.tileSize * 4, null);
    }
}