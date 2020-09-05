package travellingSalesPerson;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main extends JFrame{

    public Main(){
    	setLayout(null);
    	setMinimumSize(new Dimension(1200,600));
    	ArrayList<Point> arr = new ArrayList<Point>();
    	for(int i=0;i<12;i++) {arr.add(new Point());arr.get(i).setLocation(Math.random()*600,Math.random()*600);}
    	Pathfinder2 p = new Pathfinder2(arr);
    	pathFinder p2 = new pathFinder(arr);
    	p.setBounds(0, 0, 600, 600);
    	p2.setBounds(600, 0, 600, 600);
        add(p);
        add(p2);
        setResizable(true);
        pack();
        setTitle("Tavelling SalesPerson");
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