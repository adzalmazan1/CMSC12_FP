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
        x = spaceImpact.tileSize;
        y = 3 * spaceImpact.tileSize;
        direction = "def";
        speed = 5;
    }

    public void loadPlayerImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/player.png")); // load an img
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // add sprite update
    public void update() {
        if(eventH.upPressed || eventH.downPressed || eventH.leftPressed || eventH.rightPressed) {
            if(eventH.upPressed) {
                direction = "def";
                y -= speed;
            }
            else if(eventH.downPressed) {
                direction = "def";
                y += speed;
            }
            else if(eventH.leftPressed) {
                direction = "def";
                x -= speed;
            }
            else if(eventH.rightPressed) {
                direction = "def";
                x += speed;
            }
        }
    }

    // currently uses default image only
    public void draw(Graphics2D g2D) {
        // draw player
        BufferedImage img = null;
        switch (direction) {
            case "def":
                img = defaultImg;
                break;
            default:
                break;
        }

        g2D.drawImage(img, x, y, spaceImpact.tileSize * 3, spaceImpact.tileSize * 3, null);
    }
}