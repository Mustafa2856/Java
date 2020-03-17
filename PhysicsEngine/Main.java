import javax.swing.JFrame;

/**
 *
 * @author mustafa
 */
public class Main extends JFrame{

    public Main(){
        add(new engine());
        setResizable(false);
        pack();
        
        setTitle("Physics Engine");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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