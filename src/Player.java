import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity implements Deployable {
    private SpaceImpact spaceImpact;
    private EventHandler eventH;

    private BufferedImage defaultImg2;

    protected CopyOnWriteArrayList<Point> points = new CopyOnWriteArrayList<Point>();

    public Player(SpaceImpact spaceImpact, EventHandler eventH) {
        this.spaceImpact = spaceImpact;
        this.eventH = eventH;

        setDefaultValues();
        loadImage();
    }

    public void setDefaultValues() {
        x = spaceImpact.tileSize;
        y = 3 * spaceImpact.tileSize;

        width = spaceImpact.tileSize * 3;
        height = spaceImpact.tileSize * 3;

        direction = "def";
        speed = 5;
        frameChange = 35;
    }

    public void loadImage() {
        try {
            defaultImg = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/default1.png")); // load an img
            defaultImg2 = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/default2.png")); // load an img
            up = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/up.png")); 
            down = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/down.png")); 
            left = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/left.png")); 
            right = ImageIO.read(getClass().getResourceAsStream("img/playerSprites/right.png")); 
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // add sprite update
    public void update() {
        // update information about computer virus object created
        // this player update gets done in the mainGameThread
        frameCounter++;
        if (frameCounter >= frameChange) {
            if (spriteNum == 1) {
                spriteNum = 2; 
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            direction = "def";
            frameCounter = 0;
        }
    
        if(eventH.upPressed || eventH.downPressed || eventH.leftPressed || eventH.rightPressed) {
            if(eventH.upPressed  && y - speed >= spaceImpact.tileSize * 2) {
                direction = "up";
                y -= speed;
            }
            else if(eventH.downPressed &&  y + speed < spaceImpact.screenHeight - height) {
                direction = "down";
                y += speed;
            }
            else if(eventH.leftPressed && x - speed >= 0) {
                direction = "left";
                x -= speed;
            }
            else if(eventH.rightPressed && x + speed < spaceImpact.screenWidth - width) {
                direction = "right";
                x += speed;
            }

            points.add(new Point(x, y)); // note on this
        }
        else {
            direction = "def";
        }
    }

    // currently uses default image only
    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.RED);
        // g2D.drawRect(x, y, width, height);

        // draw player
        BufferedImage img = null;
        switch (direction) {
            case "def":
                if(spriteNum == 1) {
                    img = defaultImg;
                }
                else if(spriteNum == 2) {
                    img = defaultImg2;
                }
                break;
            case "up":
                img = up;
                break;
            case "down":
                img = down;
                break;
            case "left":
                img = left;
                break;
            case "right":
                img = right;
                break;
            default:
                break;
        }

        g2D.drawImage(img, x, y, width, height, null);
    }
}