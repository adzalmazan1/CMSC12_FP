import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity {
    SpaceImpact spaceImpact;
    EventHandler eventH;

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public Player(SpaceImpact spaceImpact, EventHandler eventH) {
        this.spaceImpact = spaceImpact;
        this.eventH = eventH;

        setDefaultValues();
        loadPlayerImage();
    }

    public void setDefaultValues() {
        x = 20;
        y = 20;
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
        if(eventH.upPressed || eventH.downPressed || eventH.leftPressed || eventH.rightPressed || eventH.spacePressed || eventH.spacePressed) {
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

        if(eventH.spacePressed) {
            bullets.add(new Bullet(spaceImpact));
        }

        for(Bullet i : bullets) {
            i.update();
        }
    }

    public void draw(Graphics2D g2D) {
        BufferedImage img = null;
        switch (direction) {
            case "def":
                img = defaultImg;
                break;
            default:
                break;
        }

        // boolean java.awt.Graphics.drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
        g2D.drawImage(img, x, y, spaceImpact.tileSize * 3, spaceImpact.tileSize * 3, null);

        for(Bullet i : bullets) {
            i.draw(g2D);
        }
    }
}