import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EventHandler implements KeyListener {
    // note: protected can be accessed in the same pkg
    protected boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, enterPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            // enter
            case 10:
                enterPressed = true;
            // left
            case 37:
                leftPressed = true;
                break;
            // up
            case 38:
                upPressed = true;
                break;
            // right
            case 39:
                rightPressed = true;
                break;
            // down
            case 40:
                downPressed = true;
                break;
            // spacebar
            case 32:
                spacePressed = true;
            default:
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            // enter
            case 10:
                enterPressed = false;
            // left
            case 37:
                leftPressed = false;
                break;
            // up
            case 38:
                upPressed = false;
                break;
            // right
            case 39:
                rightPressed = false;
                break;
            // down
            case 40:
                downPressed = false;
                break;
            // spacebar
            case 32:
                spacePressed = false;
            default:
                break;
        }
    }
    
}