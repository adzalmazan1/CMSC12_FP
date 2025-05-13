
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends Entity implements Deployable {
    private SpaceImpact spaceImpact;
    private Player player;

    private int movementChange = 10;

    public Bullet(SpaceImpact spaceImpact, Player player) {
        this.spaceImpact = spaceImpact;
        this.player = player;

        setDefaultValues();
        loadImage();
    }

    // bullet default values
    public void setDefaultValues() {
        x = player.x + (spaceImpact.tileSize  * 3);
        y = player.y + 18;

        width = (spaceImpact.tileSize / 2) + 5;
        height = (spaceImpact.tileSize / 4) + 5;

        speed = 50;
    }

    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/otherSprites/bullet.png"));
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
        g2D.drawRect(x, y, width, height);
        
        // default image for bullet
        BufferedImage img = defaultImg;
        g2D.drawImage(img, x, y, width, height, null); // uses SpaceImpact
    }
}