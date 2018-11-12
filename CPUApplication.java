
import cpu.CPUPanel;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Jandre
 */
public class CPUApplication {
    
    public static void CreateAndShowGUI() //creates application
    {
        JFrame frame = new JFrame("CPU Simulator");
        frame.setPreferredSize(new Dimension (700, 620)); //Sets dimension to 800 x 800 px
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Allows application to close
        frame.setEnabled(true); //Allows application to accept certain keynotes
        CPUPanel panel = new CPUPanel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) 
    {
        CreateAndShowGUI();
    }
}
