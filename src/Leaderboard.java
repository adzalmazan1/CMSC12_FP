import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Leaderboard extends JPanel {
    private Image backgroundImage;
    private GridBagConstraints gbc = new GridBagConstraints();
    private ArrayList<Integer> scores = new ArrayList<>();
 
    private int leading = 5;
    private String[] score = new String[leading];

    private JPanel leaderboardscorepanel;

    public Leaderboard(CardLayout cardLayout, JPanel container) {
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
        middlePanel.setPreferredSize(new Dimension(100, 100));
        middlePanel.setOpaque(false);

        JPanel topMiddlePanel = new JPanel();
        topMiddlePanel.setOpaque(false);
      
        setScores();
        sortScores();

        JLabel leaderboard = new JLabel("Leaderboard");
        leaderboard.setBorder(new EmptyBorder(20, 0, 0, 0));
        leaderboard.setForeground(Color.WHITE);
        leaderboard.setFont(new Font("Roboto", Font.BOLD, 30));
        leaderboard.setHorizontalAlignment(JLabel.CENTER);

        topMiddlePanel.add(leaderboard, BorderLayout.NORTH);

        JPanel centerMiddlePanel = new JPanel();
        centerMiddlePanel.setPreferredSize(new Dimension(100, 300));
        centerMiddlePanel.setBackground(Color.blue);
        centerMiddlePanel.setOpaque(false);

        leaderboardscorepanel = new JPanel();
        leaderboardscorepanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        leaderboardscorepanel.setOpaque(false);
        leaderboardscorepanel.setLayout(new GridLayout(5,1));

        for(int i = 0; i < leading; i++) {
            addLeaderboardScore(i);
        }

        centerMiddlePanel.add(leaderboardscorepanel, BorderLayout.CENTER);

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
                cardLayout.show(container, "Start");
            }
        });

        middlePanel.add(topMiddlePanel, BorderLayout.NORTH);
        middlePanel.add(centerMiddlePanel, BorderLayout.CENTER);
        middlePanel.add(bottomMiddlePanel, BorderLayout.SOUTH);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.WEST);
        this.add(leftPanel, BorderLayout.EAST);
        this.add(rightPanel, BorderLayout.SOUTH);
        this.add(middlePanel, BorderLayout.CENTER);
    }

    public void addLeaderboardScore(int index) {
        JLabel leaderboardScore = new JLabel(score[index]);
        leaderboardScore.setBorder(new EmptyBorder(0, 0, 10, 0));
        leaderboardScore.setForeground(Color.WHITE);
        leaderboardScore.setFont(new Font("Roboto", Font.BOLD, 20));
        leaderboardScore.setHorizontalAlignment(JLabel.CENTER);
        leaderboardscorepanel.add(leaderboardScore);
    }

    public void sortScores(){
        File scoreFile = new File("src/txt/scores.txt");
        if (scoreFile.exists()) {
            try (Scanner scanner = new Scanner(scoreFile)) {
                while (scanner.hasNextInt()) {
                    scores.add(scanner.nextInt());
                }

                scanner.close();
                Collections.sort(scores, Collections.reverseOrder());

                // loop to remove scores if over 5
                for(int i =  scores.size() ; scores.size() > 5; i--){
                    scores.remove(i-1);
                }
            
                // loop to set scores default too --- if there are no scores
                for(int i = 0; i < scores.size(); i++){
                    score[i] = String.valueOf(scores.get(i));
                }

                // FileWriter fw = new FileWriter(file, true);
                FileWriter fw = new FileWriter(scoreFile);
                BufferedWriter bw = new BufferedWriter(fw); //check differences between FileWriter and BufferedWriter

                // loop to write new organized scores into file			
                for(int i = 0; i < scores.size(); i++) { //reading from main memory instead of disk
                    bw.write(String.valueOf(scores.get(i)) + "\n");
			    }
			
                    bw.flush();
                    bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }	
        }   
    }

    public void setScores(){
        // loop to set scores default too --- if there are no scores
        for(int i = 0; i < 5; i++){
            score[i] = "---";
        }
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
