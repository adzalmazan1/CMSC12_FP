import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SpaceImpactDisplay extends JPanel {
    private SpaceImpact spaceImpact;
    private Life life;
    private int lifeCount = 3;
    
    // global var for modification
    private JLabel scoreCount;
    private JLabel statusLabel;

    public SpaceImpactDisplay(CardLayout cardLayout, JPanel container) {
        this.spaceImpact = new SpaceImpact(cardLayout, container, this);
        this.life = new Life(spaceImpact, true);
        
        this.setLayout(new BorderLayout());
        
        JPanel innerPanel = new JPanel();
        innerPanel.setBackground(new Color(25,32,38));
        innerPanel.setLayout(new GridLayout(1, 3));

        JPanel lifePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = (Graphics2D) g;
                draw(g2D);
            }

            public void draw(Graphics2D g2D) {
                for(int i = 0; i < lifeCount; i++) {
                    life.draw(g2D, i);
                }
                
            }
        };

        lifePanel.setOpaque(false);

        // .scoreboard .thisfunction()
        JPanel scoreBoard = new JPanel();
        scoreBoard.setLayout(new GridLayout(2, 1));
        scoreBoard.setBorder(new EmptyBorder(20, 0, 10, 0));
        scoreBoard.setOpaque(false);

        JLabel scoreLabel = new JLabel("SCORE", JLabel.CENTER); // center a JLabel
        scoreLabel.setFont(new Font("Race Sport", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreBoard.add(scoreLabel);

        scoreCount = new JLabel("0", JLabel.CENTER);
        scoreCount.setFont(new Font("Race Sport", Font.BOLD, 20));
        scoreCount.setForeground(Color.WHITE);
        scoreBoard.add(scoreCount);

        JPanel statusBoard = new JPanel();
        statusBoard.setLayout(new BorderLayout());
        statusBoard.setOpaque(false);

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Race Sport", Font.BOLD, 15));
        statusLabel.setForeground(Color.WHITE);
        statusBoard.add(statusLabel, BorderLayout.CENTER);
        
        innerPanel.add(lifePanel);
        innerPanel.add(scoreBoard);
        innerPanel.add(statusBoard);

        this.add(innerPanel, BorderLayout.CENTER);
    }

    public void setCurrentScore(int currentScore) {
        scoreCount.setText(String.valueOf(currentScore));
    }
    
    public void setGamePlayStatus(String gpStat) {
        statusLabel.setText(gpStat);
    }

    public int getLifeCount() {
        return lifeCount;
    }

    public void setLifeCount(int newLifeCount) {
       lifeCount = newLifeCount;
    }
}
