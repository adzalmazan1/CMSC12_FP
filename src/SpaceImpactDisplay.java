import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpaceImpactDisplay extends JPanel {
    SpaceImpact spaceImpact;

    public SpaceImpactDisplay(SpaceImpact spaceImpact) {
        this.spaceImpact = spaceImpact;
        this.setBackground(new Color(25,32,38));

        JPanel innerPanel = new JPanel();
        JLabel text = new JLabel("hello, world");
        innerPanel.setLayout(new FlowLayout());
        innerPanel.add(text);
        this.add(innerPanel);
    }
}
