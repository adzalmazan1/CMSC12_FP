import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {
    SpaceImpact spaceImpact;
    EventHandler eventH;

    public Player(SpaceImpact spaceImpact, EventHandler eventH) {
        this.spaceImpact = spaceImpact;
        this.eventH = eventH;

        setDefaultValues();
        loadPlayerImage();
    }

    public void setDefaultValues() {
        x = 20;
        y = 20;
        direction = "up";
        speed = 5;
    }

    public void loadPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("img/player.png")); // load an img
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        // if any of the keys pressed
        // update
        if(eventH.upPressed || eventH.downPressed || eventH. leftPressed || eventH .rightPressed) {
            if(eventH.upPressed) {
                direction = "up";
                y -= speed;
            }
            else if(eventH.downPressed) {
                direction = "up"; // should be down
                y += speed;
            }
            else if(eventH.leftPressed) {
                direction = "up";
                x -= speed;
            }
            else if(eventH.rightPressed) {
                direction = "up";
                x += speed;
            }
        }
    }

    public void draw(Graphics2D g2D) {
        BufferedImage img = null;
        switch (direction) {
            case "up":
                if(spriteNum == 1) {
                    img = up1;
                }
                /* 
                else if(spriteNum == 2) {
                    img = down2;
                }
                */
                break;
            default:
                break;
        }

        // boolean java.awt.Graphics.drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
        g2D.drawImage(img, x, y, spaceImpact.tileSize * 3, spaceImpact.tileSize * 3, null);
    }

    public class Bullet {
        
    }
}