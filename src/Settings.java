import java.awt.AlphaComposite;
import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Settings extends JPanel {
    private Image backgroundImage;
    private GridBagConstraints gbc = new GridBagConstraints();

    public Settings() {
        this.setLayout(new BorderLayout()); 
        backgroundImage = new ImageIcon(getClass().getResource("/img/bg1.png")).getImage();

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.red);
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(100, 100));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.blue);
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(100, 100));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.green);
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(100, 100));

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.yellow);
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(100, 100));

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.pink);
        middlePanel.setPreferredSize(new Dimension(100, 100));
        middlePanel.setOpaque(false);

         JPanel topMiddlePanel = new JPanel();
        topMiddlePanel.setOpaque(false);
      
        JPanel bottomMiddlePanel = new JPanel();
        bottomMiddlePanel.setPreferredSize(new Dimension(100, 40));
        bottomMiddlePanel.setOpaque(false);

        JLabel back = new JLabel("BACK");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 5, 0);
    
        bottomMiddlePanel.add(back, gbc);

        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        back.setFont(new Font("Race Sport", Font.BOLD, 15));
        back.setForeground(Color.white);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Settings.this);
                topFrame.getContentPane().removeAll();
                topFrame.add(new StartPanel());
                topFrame.revalidate();
                topFrame.repaint();
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
        Graphics2D g2d = (Graphics2D) 
        g.create(); 
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        float opacity = 0.5f; 
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.setColor(Color.black); 
        g2d.fillRect(100, 100, 665, 315);
        g2d.dispose(); 
    }
    
}
