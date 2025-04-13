import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class ComputerVirus extends Entity {
    SpaceImpact spaceImpact;
    EventHandler eventH;

    // make an Array List for Computer Virus
    // make it randomly appear
    ArrayList<ComputerVirus> compViruses = new ArrayList<ComputerVirus>();

    public ComputerVirus(SpaceImpact spaceImpact, EventHandler eventH) {
        this.spaceImpact = spaceImpact;
        this.eventH = eventH;

        setDefaultValues();
        loadCompVirusImage();
    }

    public void setDefaultValues() {
        // only for x values
        // local vars
        int min = ((4 * spaceImpact.columns) /  5);
        int max = spaceImpact.columns;

        x = min + (int)(Math.random() * ((max - min)));        
        y = (int)(Math.random() * spaceImpact.rows); // generates a random integer from 0 to spaceImpact.rows - 1

        System.out.println(min);
        System.out.println(max);
        speed = 4;
        direction = "up";
    }

    
    public void update() {

    }

    public void paintComponent() {
    }

    public void draw(Graphics2D g) {
        BufferedImage img = null;

        switch (direction) {
            case "up":
                img = up1;
                break;
        
            default:
                break;
        }

        // boolean java.awt.Graphics.drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
        g.drawImage(img, x * spaceImpact.tileSize, y * spaceImpact.tileSize, spaceImpact.tileSize, spaceImpact.tileSize, null);
    }


    public void loadCompVirusImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("img/computerVirus.png")); // load an img
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}