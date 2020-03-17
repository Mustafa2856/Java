package todolist;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 *
 * @author mustafa
 */
public class Main extends JFrame{

    public Main(){
        add(new listpanel());
        setResizable(true);
        pack();
        setTitle("To Do List");
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
