import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TitlePanel extends JPanel {
    private Image backgroundImage;
    
    public TitlePanel() {
        this.setPreferredSize(Main.SCREEN_SIZE);
        this.setLayout(new BorderLayout());

        backgroundImage = new ImageIcon(getClass().getResource("img/bg1.png")).getImage();

        JLabel startIcon1 = new JLabel("START");
        startIcon1.setFont(new Font("Race Sport", Font.BOLD, 20));
        startIcon1.setForeground(Color.white);
        startIcon1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startIcon1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(TitlePanel.this);
                topFrame.getContentPane().removeAll();
                topFrame.add(new StartPanel());
                topFrame.revalidate();
                topFrame.repaint();
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(875, 400));
        topPanel.setOpaque(false);
        // topPanel.add(title, BorderLayout.CENTER);

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
