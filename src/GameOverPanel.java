import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GameOverPanel extends JPanel {
    private JPanel gameoverpanel, gameovertoppanel, gameoverleftpanel,
                   gameoverrightpanel, gameoverbottompanel, gameoverlabelpanel,
                   scorecontinuepanel, gameoverbuttonpanel;

    private JLabel gameoverlabel, continuelabel, finalscorelabel;
    private static SpaceImpactButton continuebutton, quitbutton;
 
    private JLayeredPane layeredPane;
    private SpaceImpact spaceImpact;
    private SpaceImpactDisplay display;

    public GameOverPanel() {
        this.setPreferredSize(CardFrame.SCREEN_SIZE);
        this.setBackground(Color.black);
        this.setLayout(new BorderLayout());
        
        this.layeredPane = new JLayeredPane();
        this.display = new SpaceImpactDisplay();
        this.spaceImpact = new SpaceImpact(display);
        
        // Top and side panels for spacing
        gameovertoppanel = new JPanel();
        gameovertoppanel.setPreferredSize(new Dimension(875, 100));
        gameovertoppanel.setBackground(new Color(0, 0, 0, 255));
        this.add(gameovertoppanel, BorderLayout.NORTH);

        gameoverleftpanel = new JPanel();
        gameoverleftpanel.setPreferredSize(new Dimension(100, 560));
        gameoverleftpanel.setBackground(new Color(0, 0, 0, 255));
        this.add(gameoverleftpanel, BorderLayout.WEST);

        gameoverrightpanel = new JPanel();
        gameoverrightpanel.setPreferredSize(new Dimension(100, 560));
        gameoverrightpanel.setBackground(new Color(0, 0, 0, 255));
        this.add(gameoverrightpanel, BorderLayout.EAST);

        gameoverbottompanel = new JPanel();
        gameoverbottompanel.setPreferredSize(new Dimension(875, 100));
        gameoverbottompanel.setBackground(new Color(0, 0, 0, 255));
        this.add(gameoverbottompanel, BorderLayout.SOUTH);

        // Main game over panel
        gameoverpanel = new JPanel();
        gameoverpanel.setBackground(new Color(0, 0, 0, 255));
        gameoverpanel.setLayout(new GridLayout(3, 1, 10, 0));
        this.add(gameoverpanel, BorderLayout.CENTER);

        // Game Over label
        gameoverlabelpanel = new JPanel();
        gameoverlabelpanel.setBackground(new Color(0, 0, 0, 255));

        gameoverlabel = new JLabel("Game Over");
        gameoverlabel.setFont(new Font("Race Sport", Font.BOLD, 80));
        gameoverlabel.setForeground(Color.WHITE);
        gameoverlabelpanel.add(gameoverlabel);

        gameoverpanel.add(gameoverlabelpanel);

        // Score + Continue label
        scorecontinuepanel = new JPanel();
        scorecontinuepanel.setBackground(new Color(0, 0, 0, 255));
        scorecontinuepanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        scorecontinuepanel.setLayout(new GridLayout(2, 1));

        finalscorelabel = new JLabel("Final Score: " + spaceImpact.currentScore, SwingConstants.CENTER);
        finalscorelabel.setFont(new Font("Race Sport", Font.BOLD, 45));
        finalscorelabel.setForeground(Color.WHITE);
        scorecontinuepanel.add(finalscorelabel);

        continuelabel = new JLabel("Continue?", SwingConstants.CENTER);
        continuelabel.setFont(new Font("Race Sport", Font.BOLD, 35));
        continuelabel.setForeground(Color.WHITE);
        scorecontinuepanel.add(continuelabel);

        gameoverpanel.add(scorecontinuepanel);

        // Buttons panel
        gameoverbuttonpanel = new JPanel();
        gameoverbuttonpanel.setLayout(new GridLayout(1, 2));
        gameoverbuttonpanel.setBackground(Color.BLACK);

        continuebutton = new SpaceImpactButton("CONTINUE");
        gameoverbuttonpanel.add(continuebutton);

        quitbutton = new SpaceImpactButton("QUIT");
        gameoverbuttonpanel.add(quitbutton);

        gameoverpanel.add(gameoverbuttonpanel);

        // Event handling
        EventHandler handler = new EventHandler();
        continuebutton.addActionListener(handler);
        continuebutton.addMouseMotionListener(handler);
        quitbutton.addActionListener(handler);
        quitbutton.addMouseMotionListener(handler);

        revalidate();
        repaint();
    }

    public class EventHandler extends MouseMotionAdapter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(GameOverPanel.this);
            if (e.getSource() == continuebutton) {
                System.out.println("Continue button pressed");
                topFrame.getContentPane().removeAll();

                layeredPane.setPreferredSize(new Dimension(spaceImpact.screenWidth, spaceImpact.screenHeight));
                layeredPane.setLayout(null);

                spaceImpact.setBounds(0, 0, spaceImpact.screenWidth, spaceImpact.screenHeight);
                display.setBounds(0, 0, spaceImpact.screenWidth, 2 * spaceImpact.tileSize);

                // ADD components to the layered pane
                layeredPane.add(spaceImpact, Integer.valueOf(0)); // background layer
                layeredPane.add(display, Integer.valueOf(1));     // display overlay

                topFrame.add(layeredPane);
                topFrame.revalidate();
                topFrame.repaint();

                spaceImpact.requestFocusInWindow(); 
                spaceImpact.startGameThread();
            } else if (e.getSource() == quitbutton) {
                CardFrame card = new CardFrame();
                card.setVisible(true);
            }
        }
    }
}
