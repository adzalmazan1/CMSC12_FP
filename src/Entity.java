import java.awt.image.BufferedImage;

public class Entity {
    protected int x, y; // x and y coords
    protected int width, height; // image width and height
    protected double speed; // movement speed
    
    // animation purposes
    protected BufferedImage defaultImg, up, down, left, right;
    protected String direction; 

    protected boolean outOfBounds = false;

    protected int frameCounter = 0;
    protected int frameChange;
    
    protected int movementCounter = 0;
    protected int movementChange;

    protected int spriteNum = 1; // sprite picker
}