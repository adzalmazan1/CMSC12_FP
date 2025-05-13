import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Life extends Entity implements Deployable {
    private SpaceImpact spaceImpact;

    private boolean isForDisplay;

    public Life(SpaceImpact spaceImpact, boolean isForDisplay) {
        this.spaceImpact = spaceImpact;
        this.isForDisplay = isForDisplay;
        setDefaultValues();
        loadImage();
    }

    @Override
    public void setDefaultValues() {
        width = spaceImpact.tileSize;
        height = spaceImpact.tileSize;
        
        if(isForDisplay) {
            x = spaceImpact.tileSize;
            y = spaceImpact.tileSize / 2;
        }
        else {
            int xMin = 0;
            int xMax = spaceImpact.screenWidth / 2;

            int yMin = spaceImpact.tileSize * 2;
            int yMax = spaceImpact.screenHeight - height;

            x = xMin + (int)(Math.random() * ((xMax - xMin)));
            y = yMin + (int)(Math.random() * ((yMax - yMin)));
        }
    }

    @Override
    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/otherSprites/life.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2D, int index) {
        BufferedImage img = defaultImg;
        g2D.drawImage(img, x * (2 * index + 1), y, width, height, null);
    }

    // draw without the index
    public void draw(Graphics2D g2D) {
        BufferedImage img = defaultImg;
        g2D.drawImage(img, x, y, width, height, null);
    }
}