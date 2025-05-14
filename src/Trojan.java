import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Trojan extends Boss {
    private SpaceImpact spaceImpact;

    public Trojan(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
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

        isSpawning = false;
    }

    @Override
    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/bossSprites/trojan.png")); 
            spawnImg = ImageIO.read(getClass().getResourceAsStream("img/bossSprites/trojanSpawn.png")); 
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // nothing here
    public void update() {
    }

    public void draw(Graphics2D g2D) {
        // health bar
        g2D.setColor(Color.RED);
        g2D.fill3DRect(x + (spaceImpact.tileSize * 2), y - 10, healthWidth, healthHeight, true);
        
        // g2D.drawRect(x, y, width, height);

        // boss image
        if(!isSpawning) {
            img = defaultImg;
        }
        else {
            img = spawnImg;
        }

        g2D.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void addSpawn() {  
        System.out.println("Trojan spawn is running");
        isSpawning = true;
        int numEnemies = 4;
        int spacing = spaceImpact.tileSize + 10;

        int spawnX = x - spaceImpact.tileSize;
        int centerY = y + (height / 2);
        int startY = centerY - ((numEnemies - 1) * spacing) / 2;

        for (int i = 0; i < numEnemies; i++) {
            int virusX = spawnX;
            int virusY = startY + (i * spacing);

            ComputerVirus virus = new ComputerVirus(spaceImpact);
            virus.setHealthBuff(2);
            virus.setIsBuffed(true);
            
            virus.x = virusX;
            virus.y = virusY;
            spaceImpact.compViruses.add(virus);
        }
    }
}