// This is boss 1

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Adware extends Entity implements Deployable {
    private SpaceImpact spaceImpact;

    public Adware(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
        setDefaultValues();
        loadImage();
    }

    @Override
    public void setDefaultValues() {
        x = 20;
        y = 20;
        direction = "def";
        speed = 10;
        movementChange = 60;
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

        g2D.drawImage(img, x, y, spaceImpact.tileSize * 3, spaceImpact.tileSize * 3, null);
    }
}