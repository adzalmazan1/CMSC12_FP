import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends Entity implements Deployable {
    SpaceImpact spaceImpact;
    Player player;

    private int movementChange = 10;

    public Bullet(SpaceImpact spaceImpact, Player player) {
        this.spaceImpact = spaceImpact;
        this.player = player;

        setDefaultValues();
        loadImage();
    }

    // bullet default values
    public void setDefaultValues() {
        x = player.x + (spaceImpact.tileSize  * 4);
        y = player.y + 20;
        speed = 50;
    }

    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/bullet.png"));
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

        if(x >= spaceImpact.screenWidth) {
            outOfBounds = true;
        }
    }

    public void draw(Graphics2D g2D) {
        // default image for bullet
        BufferedImage img =  defaultImg;
        g2D.drawImage(img, x, y, (spaceImpact.tileSize / 2) + 7, (spaceImpact.tileSize / 4) + 7, null); // uses SpaceImpact
    }
}