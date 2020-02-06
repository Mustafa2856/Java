/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author mustafa
 */
public class game extends JPanel implements ActionListener{
    int arr[][] = new int[3][3];int sp=0;int sc=0;int tie=0;
    public game() {
        initBoard();
    }
    
    private void initBoard() {
        addMouseListener(new act());
        setBackground(Color.white);
        setFocusable(true);
        setPreferredSize(new Dimension(700,490));for(int i=0;i<3;i++){for(int j=0;j<3;j++)arr[i][j]=0;}
    }
    private int wincheck(int c,int q){
        for(int i=0;i<3;i++)
        {
            if(arr[0][i]==c && arr[1][i]==c && arr[2][i]==q)return 7+i;
            if(arr[2][i]==c && arr[1][i]==c && arr[0][i]==q)return i+1;
            if(arr[0][i]==c && arr[2][i]==c && arr[1][i]==q)return 4+i;
            
            if(arr[i][0]==c && arr[i][1]==c && arr[i][2]==q)return 3+3*i;
            if(arr[i][2]==c && arr[i][1]==c && arr[i][0]==q)return 1+3*i;
            if(arr[i][0]==c && arr[i][2]==c && arr[i][1]==q)return 2+3*i;
        }
        if(arr[0][0]==c && arr[1][1]==c && arr[2][2]==q)return 9;
        if(arr[0][0]==c && arr[2][2]==c && arr[1][1]==q)return 5;
        if(arr[2][2]==c && arr[1][1]==c && arr[0][0]==q)return 1;
        
        if(arr[0][2]==c && arr[1][1]==c && arr[2][0]==q)return 7;
        if(arr[0][2]==c && arr[2][0]==c && arr[1][1]==q)return 5;
        if(arr[2][0]==c && arr[1][1]==c && arr[0][2]==q)return 3;
        return -1;
    }
    private void play(){
        
        int a = wincheck(1,0);
        if(a==-1){
            a=wincheck(2,0);
            if(a==-1){
                if(arr[1][1]!=2){
                if(arr[1][1]==0)arr[1][1]=1;
                else if(arr[0][1]==0)arr[0][1]=1;
                else if(arr[2][1]==0)arr[2][1]=1;
                else if(arr[1][0]==0)arr[1][0]=1;
                else if(arr[1][2]==0)arr[1][2]=1;
                else if(arr[0][0]==0)arr[0][0]=1;
                else if(arr[0][2]==0)arr[0][2]=1;
                else if(arr[2][2]==0)arr[2][2]=1;
                else if(arr[2][0]==0)arr[2][0]=1;}
                else{
                    if(arr[1][1]==0)arr[1][1]=1;
                else if(arr[0][0]==0)arr[0][0]=1;
                else if(arr[0][2]==0)arr[0][2]=1;
                else if(arr[2][2]==0)arr[2][2]=1;
                else if(arr[2][0]==0)arr[2][0]=1;
                else if(arr[0][1]==0)arr[0][1]=1;
                else if(arr[2][1]==0)arr[2][1]=1;
                else if(arr[1][0]==0)arr[1][0]=1;
                else if(arr[1][2]==0)arr[1][2]=1;
                }
                
            }
            else{
                arr[(a-1)/3][(a-1)%3]=1;
            }
        }
        else
            arr[(a-1)/3][(a-1)%3]=1;
        
            
       int flag=0;
       for(int i=0;i<9;i++){
           if(arr[i/3][i%3]==0)flag=1;
       }
       if(flag==0)
       {
           java.util.TimerTask ga= new java.util.TimerTask() {
                    @Override
                    public void run() {
                        //To change body of generated methods, choose Tools | Templates.
                        initBoard();tie++;
                    }
                };
           t.schedule(ga,100);
       }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
    }
        
    private void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRoundRect(150,0,20,490,20,20);
        g.fillRoundRect(320,0,20,490,20,20);
        
        g.fillRoundRect(0,150,490,20,20,20);
        g.fillRoundRect(0,320,490,20,20,20);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
            if(arr[i][j]==1)
            {
                //g.translate(i*170, j*170);
                g.fillOval(15+i*170,15+j*170,120,120);
                g.setColor(Color.WHITE);
                g.fillOval(35+i*170,35+j*170,80,80);
                g.setColor(Color.BLACK);
            }
            else if(arr[i][j]==2){
                    //g.translate(i*170, j*170);
                    int x[] = {16+i*170,30+i*170,134+i*170,120+i*170};
                    int y[] = {30+j*170,16+j*170,120+j*170,134+j*170};
                    g.fillPolygon(x, y,4);
                    int[] x1 = {134+i*170,120+i*170,16+i*170,30+i*170};
                    int[] y1 = {30+j*170,16+j*170,120+j*170,134+j*170};
                    g.fillPolygon(x1, y1,4);
            }
        }}
        Font f = new Font("ArialBlack",Font.BOLD,20);
        g.setFont(f);
        g.drawString("Person:   "+Integer.toString(sp),500,30);
        g.drawString("Computer: "+Integer.toString(sc),500,80);
        g.drawString("Tie:      "+Integer.toString(tie),500,130);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        initBoard();
    }
    java.util.Timer t = new java.util.Timer();
    private class act extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            int x=e.getX();
            int y=e.getY();
            if(x<490 && y<490 && arr[x/170][y/170]==0){
            arr[x/170][y/170]=2;
            int p=wincheck(2,2);
            if(p!=-1){
                java.util.TimerTask a= new java.util.TimerTask() {
                    @Override
                    public void run() {
                        //To change body of generated methods, choose Tools | Templates.
                        initBoard();sp++;
                    }
                };
                t.schedule(a,1000);
                }
            else{
            play();p=wincheck(1,1);
            if(p!=-1){
                java.util.TimerTask a= new java.util.TimerTask() {
                    @Override
                    public void run() {
                        //To change body of generated methods, choose Tools | Templates.
                        initBoard();sc++;
                    }
                };
                t.schedule(a,1000);
                }
            }
            repaint();}
            //for(int i=0;i<3;i++){for(int j=0;j<3;j++)System.out.print(arr[i][j]);System.out.println();}
        }
    }
}
