import java.awt.image.BufferedImage;

public class Entity {
    protected int x, y;
    protected double speed;
    
    protected BufferedImage defaultImg, up, down, left, right; // animation purposes
    protected String direction;
    protected boolean outOfBounds = false;

    protected int frameCounter = 0;
    protected int frameChange;
    
    protected int movementCounter = 0;
    protected int movementChange;

    protected int spriteNum = 1; // sprite picker
}