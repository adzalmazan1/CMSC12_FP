import java.awt.image.BufferedImage;

public class Entity {
    protected int x, y;
    protected double speed;
    
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // for animation purposes
    protected String direction;

    protected int frameCounter = 0;
    protected int movementCounter = 0;

    protected int spriteNum = 1;
}