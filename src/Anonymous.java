import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Anonymous extends Boss {
    private SpaceImpact spaceImpact;
    private Player player;

    public Anonymous(SpaceImpact spaceImpact, Player player) {
        this.spaceImpact = spaceImpact;
        this.player = player;
        setDefaultValues();
        loadImage();
    }

    @Override
    public void setDefaultValues() {
        x = spaceImpact.screenWidth - spaceImpact.tileSize * 7;
        y = spaceImpact.tileSize * 5;
        
        width = spaceImpact.tileSize * 7;
        height = spaceImpact.tileSize * 7;

        speed = 8;
        movementChange = 5;

        // health width and height ** separate attributes **
        healthWidth = spaceImpact.tileSize * 5;
        healthHeight = 10;
    }

    @Override
    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/bossSprites/anonymous.png")); 
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // no movement update
        deployCounter++;
        if(deployCounter >= deployChange) {
            addSpawn();
            deployCounter = 0;
        }
    }

    public void draw(Graphics2D g2D) {
        // health bar
        g2D.setColor(Color.RED);
        g2D.fill3DRect(x + (spaceImpact.tileSize * 2), y - 10, healthWidth, healthHeight, true);
        
        g2D.drawRect(x, y, width, height);

        // boss image
        BufferedImage img = defaultImg;
        g2D.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void addSpawn() {    
        int centerX = player.x + player.width / 2;
        int centerY = player.y + player.height / 2;

        int radius = 150; 
        int virusCount = Math.min(spaceImpact.compViruses.size(), 8); // Only teleport up to 8 viruses

        for (int i = 0; i < virusCount; i++) {
            ComputerVirus virus = spaceImpact.compViruses.get(i);
            double angle = 2 * Math.PI * i / virusCount;

            int newX = (int) (centerX + radius * Math.cos(angle)) - virus.width / 2;
            int newY = (int) (centerY + radius * Math.sin(angle)) - virus.height / 2;

            virus.x = newX;
            virus.y = newY;
        }
    }
}