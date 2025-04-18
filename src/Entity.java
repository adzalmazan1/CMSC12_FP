import java.awt.image.BufferedImage;

public class Entity {
    protected int x, y;
    protected double speed;
    
    protected BufferedImage defaultImg, up1, down1, left1, right1; // animation purposes
    protected String direction;

    protected int frameCounter = 0;
    protected int movementCounter = 0;

    protected int spriteNum = 1; // sprite picker
}