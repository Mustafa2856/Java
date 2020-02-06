/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingpong;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Pingpong extends JFrame {

    public Pingpong() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new game());
        setResizable(false);
        pack();
        
        setTitle("Ping-Pong");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Pingpong();
            ex.setVisible(true);
        });
    }
}
