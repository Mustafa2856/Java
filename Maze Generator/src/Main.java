
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author mustafa
 */
public class Main extends JFrame{

    public Main(){
        add(new Generator(60,60));
        setResizable(true);
        pack();
        setTitle("Maze Generator");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800,650));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame o = new Main();
        o.setVisible(true);
    }
    
}