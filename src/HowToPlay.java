import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HowToPlay extends JPanel {
    private Image backgroundImage;
    private GridBagConstraints gbc = new GridBagConstraints();

    public HowToPlay(CardLayout cardLayout, JPanel container) {
        this.setLayout(new BorderLayout());
        backgroundImage = new ImageIcon(getClass().getResource("img/bg/titleBackdrop.png")).getImage();

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(100, 100));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(100, 100));

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(100, 100));

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(100, 100));

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setOpaque(false);
        middlePanel.setPreferredSize(new Dimension(100, 100));

        JPanel topMiddlePanel = new JPanel(new BorderLayout());
        topMiddlePanel.setOpaque(false);

       JLabel instructions = new JLabel(
            "<html><div style='text-align: center; font-size: 12px; color: white; font-family: \"Race Sport\", sans-serif;'>"
            + "<h2> HOW TO PLAY </h2>"
            + "<p>Destroy invading computer viruses,<br>"
            + "defeat bosses, and protect your system! <br>" 
            + "Survive each wave and rack up the highest score!</p><br>"

            + "<b>CONTROLS:</b><br>"
            + "← / → : Move left or right<br>"
            + "↑ / ↓ : Move up or down<br>"
            + "Spacebar : Shoot antivirus beams<br>"
        );
        instructions.setHorizontalAlignment(JLabel.CENTER);
        
        topMiddlePanel.add(instructions, BorderLayout.CENTER);

        JPanel bottomMiddlePanel = new JPanel();
        bottomMiddlePanel.setPreferredSize(new Dimension(100, 40));
        bottomMiddlePanel.setOpaque(false);

        JLabel back = new JLabel("BACK");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
        bottomMiddlePanel.add(back, gbc);

        back.setFont(new Font("Race Sport", Font.BOLD, 15));
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setForeground(Color.WHITE);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(container, "Start");
            }
        });

        middlePanel.add(topMiddlePanel, BorderLayout.CENTER);
        middlePanel.add(bottomMiddlePanel, BorderLayout.SOUTH);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.WEST);
        this.add(leftPanel, BorderLayout.EAST);
        this.add(rightPanel, BorderLayout.SOUTH);
        this.add(middlePanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        float opacity = 0.5f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(100, 100, 665, 315);
        g2d.dispose();
    }
}
