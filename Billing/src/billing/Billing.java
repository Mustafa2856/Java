/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billing;

import javax.swing.JFrame;

/**
 *
 * @author mustafa
 */
public class Billing extends JFrame{

    public Billing(){
        add(new ui());
        setResizable(false);
        pack();
        
        setTitle("Billing");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame o = new Billing();
        o.setVisible(true);
    }
    
}
