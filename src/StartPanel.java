import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
    private Image backgroundImage;
    private GridBagConstraints gbc = new GridBagConstraints();
    
    private JLayeredPane layeredPane;
    private SpaceImpact spaceImpact;
    private SpaceImpactDisplay display;
    
    JLabel play, settings, credits, how, back;
    CardLayout cardLayout;
    JPanel container;

    public StartPanel(CardLayout cardLayout, JPanel container){
        this.setPreferredSize(new Dimension(CardFrame.SCREEN_SIZE));
        this.setLayout(new BorderLayout());

        this.cardLayout = cardLayout;
        this.container = container;

        // both inherits layout and container from startPanel
        this.layeredPane = new JLayeredPane();
        this.display = new SpaceImpactDisplay(cardLayout, container);
        this.spaceImpact = new SpaceImpact(cardLayout, container, display); // note on this
        
        layeredPane.setPreferredSize(new Dimension(spaceImpact.screenWidth, spaceImpact.screenHeight));
        layeredPane.setLayout(null);

        spaceImpact.setBounds(0, 0, spaceImpact.screenWidth, spaceImpact.screenHeight);
        display.setBounds(0, 0, spaceImpact.screenWidth, 2 * spaceImpact.tileSize);

        // ADD components to the layered pane
        layeredPane.add(spaceImpact, Integer.valueOf(0)); // background layer
        layeredPane.add(display, Integer.valueOf(1));     // display overlay

        container.add(layeredPane, "LayeredPane");
        
        backgroundImage = new ImageIcon(getClass().getResource("img/bg/titleBackdrop.png")).getImage();

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 560));
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.black);
        
        JLabel play = new JLabel("PLAY");
        play.setFont(new Font("Race Sport", Font.BOLD, 20));
        play.setForeground(Color.white);
        play.setHorizontalAlignment(JLabel.LEFT); 
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.insets = new Insets(0, 60, 25, 0);
        gbc.anchor = GridBagConstraints.WEST;    
        
        play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //changes the cursor into a hand cursor

        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // For sound
                Sound.clicksound();
                Sound.bgmGame();

                cardLayout.show(container, "LayeredPane");
                spaceImpact.requestFocusInWindow(); 
                spaceImpact.startGameThread();
            }
        });

        leftPanel.add(play, gbc);

        JLabel how = new JLabel("HOW TO PLAY");
        how.setFont(new Font("Race Sport", Font.BOLD, 20));
        how.setForeground(Color.white);
        how.setHorizontalAlignment(JLabel.LEFT); 
        gbc.gridx = 0;
        gbc.gridy = 1;

        how.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //changes the cursor into a hand cursor

        how.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sound.clicksound();
                cardLayout.show(container, "HowToPlay");
            }
        });
    
        leftPanel.add(how, gbc);

        JLabel settings = new JLabel("LEADERBOARD");
        settings.setFont(new Font("Race Sport", Font.BOLD, 20));
        settings.setForeground(Color.white);
        gbc.gridx = 0;
        gbc.gridy = 2;

        settings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        settings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sound.clicksound();
                cardLayout.show(container, "Leaderboard");
            }
        });
        
        leftPanel.add(settings, gbc);       

        JLabel back = new JLabel("BACK");
        gbc.gridx = 0;
        gbc.gridy = 4;
    
        leftPanel.add(back, gbc);
 
        back.setFont(new Font("Race Sport", Font.BOLD, 20));
        back.setForeground(Color.white);

        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Sound.clicksound();
                cardLayout.show(container, "Title");
            }
        });

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.black);

        leftPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        
        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}