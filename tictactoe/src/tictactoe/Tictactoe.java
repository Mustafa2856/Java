/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Tictactoe extends JFrame {

    public Tictactoe() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new game());
        setResizable(false);
        pack();
        
        setTitle("Tic Tac Toe");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Tictactoe();
            ex.setVisible(true);
        });
    }
}