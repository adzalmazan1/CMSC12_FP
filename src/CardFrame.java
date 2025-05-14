import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CardFrame extends JFrame {
    protected static final int GAME_WIDTH = 875;
    protected static final int GAME_HEIGHT = 585;
    protected static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

    private CardLayout cardLayout;
    private JPanel container;

    private TitlePanel title;
    private StartPanel start;
    private Leaderboard leaderboard;
    private HowToPlay how;

    public CardFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setPreferredSize(SCREEN_SIZE);
        this.setResizable(false);
        this.setTitle("Space Impact");

        this.cardLayout = new CardLayout(); // instantiate once here
        this.container = new JPanel(cardLayout);

        title = new TitlePanel(cardLayout, container);
        start = new StartPanel(cardLayout, container);
        leaderboard = new Leaderboard(cardLayout, container);
        how = new HowToPlay(cardLayout, container);
        
        container.add(title, "Title");
        container.add(start, "Start");
        container.add(leaderboard, "Leaderboard");
        container.add(how, "HowToPlay");

        cardLayout.show(container, "Title");

        this.add(container);

        this.pack(); // sizes the frame so that all its contents are at or above their preferred sizes

        this.setLocationRelativeTo(null); 
        this.setVisible(true);
    }
}
