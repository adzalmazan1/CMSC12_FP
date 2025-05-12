import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class GameWon {
    public void draw(Graphics2D g2D, int canvasWidth, int canvasHeight) {
        // backdrop
        g2D.setColor(new Color(25,32,38));
        g2D.fillRect(0, 0, canvasWidth, canvasHeight);

        // convention
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("Cambria", Font.BOLD, 48));
        String title = "GAME OVER";
        FontMetrics metrics = g2D.getFontMetrics();
        
        int titleX = (canvasWidth - metrics.stringWidth(title)) / 2;
        int titleY = ((canvasHeight - metrics.getHeight()) / 2);
        g2D.drawString(title, titleX, titleY);

        g2D.setFont(new Font("Cambria", Font.PLAIN, 24));
        String instruction = "Press 'ENTER' to play again.";
        metrics = g2D.getFontMetrics();

        int instX = titleX;
        int instY = titleY + 50;

        g2D.drawString(instruction, instX, instY);
    }
} 
