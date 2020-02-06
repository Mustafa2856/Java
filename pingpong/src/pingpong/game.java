/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingpong;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author mustafa
 */
public class game extends JPanel implements ActionListener{
    
    int pposition=250;int cposition=250;int ball[] = new int[2];int vel[] = new int[2];int vell=8;
    Timer t;int cp=0,pp=0;
    public game() {
        ball[0]=150;ball[1]=250;
        vel[0]=vell;vel[1]=(int)(4*Math.random());
        initBoard();
    }
    
    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(500,500));
        t=new Timer(60,this);
        t.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        g.setColor(Color.WHITE);
        Font f = new Font("ArialBlack",Font.BOLD,20);
        g.setFont(f);
        g.drawString(Integer.toString(pp),260,30);
        g.drawString(Integer.toString(cp)+" : ",220,30);
        g.fillRect(0,0,500,10);
        g.fillRect(0,490,500,10);
        g.fillRect(10,cposition-30,15,60);
        g.fillRect(475,pposition-30,15,60);
        g.fillOval(ball[0]-5,ball[1]-5,10,10);
    }
    
    private void move(){
        if(ball[0]>490)
        {
            cp++;ball[0]=150;ball[1]=250;vel[0]=vell;vel[1]=(int)(4*Math.random());
        }
        if(ball[0]<10)
        {
            pp++;ball[0]=150;ball[1]=250;vel[0]=vell;vel[1]=(int)(4*Math.random());
        }
        if(ball[1]>490 || ball[1]<10)
            vel[1]*=-1;
        if(ball[0]>470 && Math.abs(pposition-ball[1])<30){
            vel[0]*=-1;vel[1]-=(pposition-ball[1])/8;
        }
        if(ball[0]<30 && Math.abs(cposition-ball[1])<30){
            vel[0]*=-1;vel[1]-=(cposition-ball[1])/8;
        }
        ball[0]+=vel[0];
        ball[1]+=vel[1];
        if(vel[0]<0 && ball[0]<400){
            int hd = ball[0]-25;
            int vd = ball[1]-(hd/vel[0])*vel[1];
            for(int i=0;i<5;i++){
            vd=vd>0?vd:-vd;
            vd=vd>490?980-vd:vd;
                    }
            if(cposition-vd>10)
                cposition-=3*vell/4;
            else if(vd-cposition>10)
                cposition+=3*vell/4;
        }
        //comment this for multiplayer
        /*if(vel[0]>0 && ball[0]>100){
            int hd = 475-ball[0];
            int vd = ball[1]-(hd/vel[0])*vel[1];
            for(int i=0;i<5;i++){
            vd=vd>0?vd:-vd;
            vd=vd>490?980-vd:vd;
                    }
            if(pposition-vd>10)
                pposition-=3*vell/4;
            else if(vd-pposition>10)
                pposition+=3*vell/4;
        }*/
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        move();repaint();
    }
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(key==KeyEvent.VK_W || key==KeyEvent.VK_UP || key==KeyEvent.VK_NUMPAD8)
            {
                if(pposition>35)
                pposition-=5;repaint();
            }
            if(key==KeyEvent.VK_S || key==KeyEvent.VK_DOWN || key==KeyEvent.VK_NUMPAD2)
            {
                if(pposition<465)
                pposition+=5;repaint();
            }
        }
    }
}