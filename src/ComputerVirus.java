import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ComputerVirus extends Entity {
    SpaceImpact spaceImpact;
    EventHandler eventH;

    // make an Array List for Computer Virus
    // make it randomly appear
    // ArrayList<ComputerVirus> compViruses = new ArrayList<ComputerVirus>();

    public ComputerVirus(SpaceImpact spaceImpact, EventHandler eventH) {
        this.spaceImpact = spaceImpact;
        this.eventH = eventH;

        setDefaultValues();
        loadCompVirusImage();
    }

    public void setDefaultValues() {
        int xMin = ((4 * spaceImpact.columns) /  5);
        int xMax = spaceImpact.columns;

        int yMin = 2;
        int yMax = spaceImpact.rows;

        x = xMin + (int)(Math.random() * ((xMax - xMin)));
        y = yMin + (int)(Math.random() * ((yMax - yMin)));
    }

    public void loadCompVirusImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("img/computerVirus.png")); // load an img
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        // update information about computer virus object created
    }

    public void draw(Graphics2D g2D) {
        BufferedImage img = up1;
        // boolean java.awt.Graphics.drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
        g2D.drawImage(img, x * spaceImpact.tileSize, y * spaceImpact.tileSize, spaceImpact.tileSize, spaceImpact.tileSize, null);
    }
}