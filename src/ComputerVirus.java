import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ComputerVirus extends Entity implements Deployable {
    private SpaceImpact spaceImpact;
    private int health;
    private boolean isBuffed;

    private BufferedImage upBuffed, downBuffed;

    public ComputerVirus(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;

        setDefaultValues();
        loadImage();
    }

    // These are default values for any type
    public void setDefaultValues() {
        int xMin = ((4 * spaceImpact.columns) / 5) * spaceImpact.tileSize;
        int xMax = (spaceImpact.columns - 1) * spaceImpact.tileSize;

        int yMin = spaceImpact.tileSize * 2;
        int yMax = spaceImpact.screenHeight - (spaceImpact.tileSize * 2);

        x = xMin + (int)(Math.random() * ((xMax - xMin)));
        y = yMin + (int)(Math.random() * ((yMax - yMin)));

        direction = "up";
        speed = 50;

        width = spaceImpact.tileSize + 8;
        height = (spaceImpact.tileSize / 2) + 8;
        
        health = 1;
        isBuffed = false;
   
        frameChange = 20;
        movementChange = 60;
    }

    public void setHealth() {
        health -= 1;
    }

    public void setHealthBuff(int buffedHealth) {
        health = buffedHealth;
        buffVirus(buffedHealth);
    }

    public int getHealth() {
        return health;
    }

    public void buffVirus(int buffMult) {
        speed *= buffMult;
        width *= buffMult;
        height *= buffMult;
        isBuffed = true;
    }

    public void loadImage() {
        try {
            up = ImageIO.read(getClass().getResourceAsStream("img/computerVirusSprites/up.png"));
            down = ImageIO.read(getClass().getResourceAsStream("img/computerVirusSprites/down.png"));
            upBuffed = ImageIO.read(getClass().getResourceAsStream("img/computerVirusSprites/cvBiggerUp.png"));
            downBuffed = ImageIO.read(getClass().getResourceAsStream("img/computerVirusSprites/cvBiggerDown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // update information about computer virus object created
        frameCounter++;
        if (frameCounter >= frameChange) {
            if (spriteNum == 1) {
                direction = "down";
                spriteNum = 2; 
            } else if (spriteNum == 2) {
                direction = "up";
                spriteNum = 1;
            }
            frameCounter = 0;
        }
        
        // previous: move -> delete in the middle of movement
        movementCounter++;
        if (movementCounter >= movementChange) {
            double yMovement;
            int newY;

            do {
                yMovement = -speed + Math.random() * (speed * 2);
                newY = (int)(y - yMovement);
            } while(newY <= spaceImpact.tileSize * 2 || newY >= spaceImpact.screenHeight - (spaceImpact.tileSize * 3));
            
            int newX = (int) (x - speed);
            
            // check bounds 
            if (newX <= -spaceImpact.tileSize) {
                outOfBounds = true;
            } 
            else {
                x = newX;
                y = newY;
            }
            
            movementCounter = 0;
        }
    }

    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.RED);
        g2D.drawRect(x, y, width, height);

        BufferedImage img = null;
        switch (direction) {
            case "up":
                if(isBuffed) {
                    img = upBuffed;
                }
                else {
                    img = up;
                }
                break;
            case "down":
                if(isBuffed) {
                    img = downBuffed;
                }
                else {
                    img = down;
                }
                break;
        } 

        // height tile/2 = 50, orig ratio = 55
        // boolean java.awt.Graphics.drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
        g2D.drawImage(img, x, y, width, height, null);
    }
}

