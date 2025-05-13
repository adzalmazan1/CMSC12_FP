import java.awt.Dimension;
import javax.swing.JFrame;

public class Main extends JFrame {
    protected static final int GAME_WIDTH = 875;
    protected static final int GAME_HEIGHT = 585;
    protected static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    
    private TitlePanel titlePanel;
    public Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setPreferredSize(SCREEN_SIZE);
        this.setResizable(false);
        this.setTitle("Space Impact");
        
        titlePanel = new TitlePanel();
        
        this.add(titlePanel);
        this.pack(); // sizes the frame so that all its contents are at or above their preferred sizes

        this.setLocationRelativeTo(null); 
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
