import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Space Impact");
        
        SpaceImpact spaceImpact = new SpaceImpact(); // JPanel
        frame.add(spaceImpact);
        frame.pack(); // fits the preferred size of its subcomponents
    
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        spaceImpact.startGameThread();
    }
}
