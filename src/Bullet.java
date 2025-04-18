import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends Entity {
    SpaceImpact spaceImpact;
    private int movementChange = 60;

    public Bullet(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
        setDefaultValues();
        loadBulletImage();
    }

    public void setDefaultValues() {
        x = 20;
        y = 20;
        direction = "def";
        speed = 100;
    }

    public void loadBulletImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/bullet.png")); // load an img
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        movementCounter++;
        if(movementCounter >= movementChange) {
            x += speed;
            movementCounter = 0;
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
        g2D.drawImage(img, x, y, spaceImpact.tileSize, spaceImpact.tileSize, null);
    }
}