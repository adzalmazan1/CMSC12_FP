import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Space Impact");
        
        SpaceImpactDisplay display = new SpaceImpactDisplay();
        SpaceImpact spaceImpact = new SpaceImpact(display); // JPanel

        // manages layering of components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(spaceImpact.screenWidth, spaceImpact.screenHeight)); // properties from space impact
        layeredPane.setLayout(null); // set to null to be able to do the overlapping panels

        spaceImpact.setBounds(0, 0, spaceImpact.screenWidth, spaceImpact.screenHeight);
        display.setBounds(0, 0, spaceImpact.screenWidth, 2 * spaceImpact.tileSize);
        
        // set bounds for the components
        layeredPane.add(spaceImpact, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(display, JLayeredPane.PALETTE_LAYER);
        
        frame.add(layeredPane);
        frame.pack(); // fits the preferred size of its subcomponents 

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // this game thread shall only be started if we are led to this panel
        spaceImpact.startGameThread();
    }
}

