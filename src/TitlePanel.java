import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {
    private Image backgroundImage;
    
    public TitlePanel(CardLayout cardLayout, JPanel container) {
        this.setName( "titlePanel");
        this.setPreferredSize(CardFrame.SCREEN_SIZE);
        this.setLayout(new BorderLayout());

        backgroundImage = new ImageIcon(getClass().getResource("img/bg/titleBackdrop.png")).getImage();

        ImageIcon titleIcon = new ImageIcon(getClass().getResource("img/title.png")); 
        Image scaledTitle = titleIcon.getImage().getScaledInstance(700, 460, Image.SCALE_SMOOTH);
        JLabel title = new JLabel(new ImageIcon(scaledTitle));
        title.setOpaque(false);

        JLabel startIcon1 = new JLabel("START");
        startIcon1.setFont(new Font("Race Sport", Font.BOLD, 20));
        startIcon1.setForeground(Color.white);
        startIcon1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startIcon1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(container, "Start");
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(875, 400));
        topPanel.setOpaque(false);
        topPanel.add(title, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setPreferredSize(new Dimension(875, 100));
        bottomPanel.setOpaque(false); 
        bottomPanel.add(startIcon1);

        this.add(topPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
