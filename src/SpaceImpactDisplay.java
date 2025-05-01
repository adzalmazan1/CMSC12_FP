import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpaceImpactDisplay extends JPanel implements Deployable {
    SpaceImpact spaceImpact;
    BufferedImage heart;

    int x, y;

    int lifeCount = 3;

    public SpaceImpactDisplay(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
        this.setLayout(new BorderLayout());
        
        setDefaultValues();
        loadImage();

        JPanel innerPanel = new JPanel();
        innerPanel.setBackground(new Color(25,32,38));
        innerPanel.setLayout(new GridLayout(1, 3));

        JPanel panel1 = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = (Graphics2D) g;

                g2D.setColor(Color.WHITE);
                for(int i = 0; i < lifeCount; i++){
                    g2D.drawImage(heart, x * (2 * i + 1), y, spaceImpact.tileSize, spaceImpact.tileSize, null);
                }
            }
        };
        panel1.setOpaque(false);


        JPanel scoreBoard = new JPanel();
        scoreBoard.setLayout(new GridLayout(2, 1));
        scoreBoard.setOpaque(false);

        JLabel scoreLabel = new JLabel("SCORE", JLabel.CENTER); // center a JLabel
        scoreLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreBoard.add(scoreLabel, BorderLayout.NORTH);

        JLabel scoreCount = new JLabel("00", JLabel.CENTER);
        scoreCount.setFont(new Font("Cambria", Font.BOLD, 20));
        scoreCount.setForeground(Color.WHITE);
        scoreBoard.add(scoreCount, BorderLayout.SOUTH);
        
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());

        JLabel statusLabel = new JLabel("First Wave", JLabel.CENTER);
        statusLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
        panel3.add(statusLabel, BorderLayout.CENTER);
        panel3.setOpaque(false);

        innerPanel.add(panel1);
        innerPanel.add(scoreBoard);
        innerPanel.add(panel3);

        this.add(innerPanel, BorderLayout.CENTER);
    }

    @Override
    public void setDefaultValues() {
        x = spaceImpact.tileSize;
        y = spaceImpact.tileSize / 2;
    }

    @Override
    public void loadImage() {
        try {
            heart = ImageIO.read(getClass().getResourceAsStream("img/life.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
