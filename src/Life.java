import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Life extends Entity implements Deployable {
    private SpaceImpact spaceImpact;

    public Life(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
        setDefaultValues();
        loadImage();
    }

    @Override
    public void setDefaultValues() {
        x = spaceImpact.tileSize;
        y = spaceImpact.tileSize / 2;
    }

    @Override
    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/life.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2D, int index) {
        BufferedImage img = defaultImg;
        g2D.drawImage(img, x * (2 * index + 1), y, spaceImpact.tileSize, spaceImpact.tileSize, null);
    }
}