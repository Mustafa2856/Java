/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dengine;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;
import java.io.*;
/**
 *
 * @author mustafa
 */
public class dengine extends JPanel implements ActionListener{
    
    point cam,frontv,upv,sidev;
    obj o;
    Timer t;double time=0,radiusr=5;long efficiency=0;
    
    public dengine(){
        cam = new point(0,0,0);
        frontv = new point(0,0,1);
        upv = new point(0,1,0);
        sidev = new point(1,0,0);
        initComponents();
    }
    
    private void initComponents(){
        setVisible(true);addKeyListener(new TA());
        setBackground(Color.WHITE);
        setFocusable(true);
        setPreferredSize(new Dimension(500,500));
        abcd();
        /*t = new Timer(50,this);
        t.start();*/
    }
    
    public void abcd()
    {
       o = new obj(); 
    }
    
    public point cross(point a,point b){
        point c = new point(
        a.y*b.z-a.z*b.y,
        -a.x*b.z+a.z*b.x,
        a.x*b.y-a.y*b.x);
        c = new point(c.x/c.r,c.y/c.r,c.z/c.r);
        return c;
    }
    
    public double dot(point a,point b){
        return a.x*b.x+a.y*b.y+a.z*b.z;
    }
    public void rotate(double theta,double phi){
        theta=3.1415926535*theta/180.0;
        phi=3.1415926535*phi/180.0;
        frontv.x = Math.cos(theta)*frontv.x+Math.sin(theta)*sidev.x;
        frontv.y = Math.cos(theta)*frontv.y+Math.sin(theta)*sidev.y;
        frontv.z = Math.cos(theta)*frontv.z+Math.sin(theta)*sidev.z;
        sidev.x = Math.cos(theta)*sidev.x-Math.sin(theta)*frontv.x;
        sidev.y = Math.cos(theta)*sidev.y-Math.sin(theta)*frontv.y;
        sidev.z = Math.cos(theta)*sidev.z-Math.sin(theta)*frontv.z;
        
        double r = Math.sqrt(frontv.x*frontv.x+frontv.y*frontv.y+frontv.z*frontv.z);
        frontv = new point(frontv.x/r,frontv.y/r,frontv.z/r);
        r = Math.sqrt(sidev.x*sidev.x+sidev.y*sidev.y+sidev.z*sidev.z);
        sidev = new point(sidev.x/r,sidev.y/r,sidev.z/r);
        
        frontv.x = Math.cos(phi)*frontv.x + Math.sin(phi)*upv.x;
        frontv.y = Math.cos(phi)*frontv.y + Math.sin(phi)*upv.y;
        frontv.z = Math.cos(phi)*frontv.z + Math.sin(phi)*upv.z;
        upv.x = Math.cos(phi)*upv.x - Math.sin(phi)*frontv.x;
        upv.y = Math.cos(phi)*upv.y - Math.sin(phi)*frontv.y;
        upv.z = Math.cos(phi)*upv.z - Math.sin(phi)*frontv.z;
        
        r = Math.sqrt(frontv.x*frontv.x+frontv.y*frontv.y+frontv.z*frontv.z);
        frontv = new point(frontv.x/r,frontv.y/r,frontv.z/r);
        r = Math.sqrt(upv.x*upv.x+upv.y*upv.y+upv.z*upv.z);
        upv = new point(upv.x/r,upv.y/r,upv.z/r);
    }
    
    public point convert(point p){ 
        point pp = new point();
        p = new point(p.x-cam.x,p.y-cam.y,p.z-cam.z+radiusr);
        pp.x = dot(p,sidev);
        pp.y = dot(p,upv);
        pp.z = dot(p,frontv);
        pp.x/=pp.z;pp.x*=250;
        pp.y/=pp.z;pp.y*=250;
        return pp;
    }
    
    private class point{
        double x,y,z,r;
        point(){
            x=0;y=0;z=0;r=0;
        }
        point(double x1,double y1,double z1){
            x=x1;y=y1;z=z1;
            r=x*x+y*y+z*z;r=Math.sqrt(r);
        }
    }
    
    private class Face{
        Vector points = new Vector();int l;point normal;double depth;
        Face(Vector p){
            l=p.size();depth=0;
            point p1 = (point)p.get(0);
            point p2 = (point)p.get(1);
            point p3 = (point)p.get(2);
            for(int i=0;i<l;i++){
                points.add(convert((point)p.get(i)));
                point pp = (point)p.get(i);
                depth = frontv.x*pp.x+frontv.y*pp.y+frontv.z*pp.z;
            }
            depth/=l;
            normal=cross(sub(p1,p2),sub(p3,p2));
            normal = new point(normal.x,normal.y,normal.z);
        }
    }
    private point sub(point p1,point p2){
        return new point(p1.x-p2.x,p1.y-p2.y,p1.z-p2.z);
    }
         
    private class obj{
        Vector points = new Vector();
        Vector tris = new Vector();
        Vector normals = new Vector();
        obj(){
            try{
                Scanner s = new Scanner(new File("teapot.obj"));int flag=0;
                while(s.hasNext())
                {
                    String a = s.nextLine();
                    if(a.charAt(0)=='v' && a.charAt(1)==' ')
                    {
                        a=a.substring(2,a.length());
                        String b[] = a.split(" ");
                        double x1=Double.parseDouble(b[0]);
                        double y1=Double.parseDouble(b[1]);
                        double z1=Double.parseDouble(b[2]);
                        point p = new point(x1,y1,z1);
                        points.add(p);
                    }else
                    if(a.charAt(0)=='v' && a.charAt(1)=='n')
                    {
                        a=a.substring(4,a.length());
                        String b[] = a.split(" ");
                        double x1=Double.parseDouble(b[0]);
                        double y1=Double.parseDouble(b[1]);
                        double z1=Double.parseDouble(b[2]);
                        point p = new point(x1,y1,z1);
                        normals.add(p);flag=1;
                    }
                    else
                    if(a.charAt(0)=='f'){
                        Vector vertice = new Vector();int p1;
                        a=a.substring(2,a.length());
                        String b[] = a.split(" ");
                        int l = b.length;
                        for(int i=0;i<l;i++){
                            CharSequence cs;
                            cs="/";
                            if(b[i].contains(cs))
                                p1=Integer.parseInt(b[i].substring(0,b[i].indexOf('/')));
                            else
                                p1=Integer.parseInt(b[i].substring(0,b[i].length()));
                           point p = (point)points.get(p1-1);
                           vertice.add(p);
                        }
                        Face t = new Face(vertice);
                        if(flag==1)
                        {
                            int p2 = Integer.parseInt(b[0].substring(b[0].lastIndexOf('/')+1,b[0].length()));
                        }
                        tris.add(t);
                    }
                }
                //sorting faces by depth
                int size = tris.size();
                for(int i=0;i<size;i++){
                    for(int j=0;j<size-i-1;j++){
                        double d1 =((Face) tris.get(j)).depth;
                        double d2 = ((Face)tris.get(j+1)).depth;
                        if(d1<d2)
                        {
                            tris.set(j,tris.set(j+1,tris.get(j)));
                        }
                    }
                }
                
            }catch(Exception e){System.out.println(e.toString());}
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    private void draw(Graphics g){
        g.translate(250,250);
        for(int i=0;i<o.tris.size();i++){
            Face a = (Face)o.tris.get(i);
            point n=new point(a.normal.x/a.normal.r,a.normal.y/a.normal.r,a.normal.z/a.normal.r);
            int s  = a.l;
            int[] x = new int[s];
            int[] y = new int[s];point aa = new point();double xa=0;double ya=0;double za=0;
            for(int j=0;j<s;j++){
                point a1 = (point)a.points.get(j);
                x[j] = -(int)a1.x;
                y[j] = -(int)a1.y;
                xa+=a1.x;ya+=a1.y;za+=a1.z;
            }
            aa = new point(xa/s-cam.x,ya/s-cam.y,ya/s-cam.y);
            aa = new point(aa.x/aa.r,aa.y/aa.r,aa.z/aa.r);
            float v1=(float)dot(n,frontv);
            if(v1>0){
            Color f = new Color(v1,v1,v1);
            g.setColor(f);
            g.fillPolygon(x,y,s);}
            else
            {
            Color f = new Color(0,0,0);
            g.setColor(f);
            g.fillPolygon(x,y,s);}
            }
        g.setColor(Color.BLACK);
        g.drawString(Long.toString(efficiency),150,-200);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
       
        double c_x=cam.x,c_z=cam.z;double th = time*3.1415926535/180.0;double a = 3.1415926535/2.0;
        cam.x = radiusr*Math.cos(th-a);
        cam.z = radiusr*Math.sin(th-a)+radiusr;
        rotate(-1,0);
        time++; abcd();
        repaint();
    }
    
    private class TA extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            long time = System.currentTimeMillis();
            if(key==KeyEvent.VK_NUMPAD8){
                cam.x+=upv.x*.01;
                cam.y+=upv.y*.01;
                cam.z+=upv.z*.01;
                abcd();repaint();
            }
            if(key==KeyEvent.VK_NUMPAD2){
                cam.x-=upv.x*.01;
                cam.y-=upv.y*.01;
                cam.z-=upv.z*.01;
                abcd();repaint();
            }
            if(key==KeyEvent.VK_NUMPAD4){
                cam.x+=sidev.x*.01;
                cam.y+=sidev.y*.01;
                cam.z+=sidev.z*.01;
                abcd();repaint();
            }
            if(key==KeyEvent.VK_NUMPAD6){
                cam.x-=sidev.x*.01;
                cam.y-=sidev.y*.01;
                cam.z-=sidev.z*.01;
                abcd();repaint();
            }
            if(key==KeyEvent.VK_W){
                cam.x+=frontv.x*.01;
                cam.y+=frontv.y*.01;
                cam.z+=frontv.z*.01;
                abcd();repaint();
            }
            if(key==KeyEvent.VK_S){
                cam.x-=frontv.x*.01;
                cam.y-=frontv.y*.01;
                cam.z-=frontv.z*.01;
                abcd();repaint();
            }
            if(key==KeyEvent.VK_A){
                rotate(1,0);
                abcd();repaint();
            }
            if(key==KeyEvent.VK_D){
                rotate(-1,0);
                abcd();repaint();
            }
            if(key==KeyEvent.VK_Q){
                rotate(0,1);
                abcd();repaint();
            }
            if(key==KeyEvent.VK_E){
                rotate(0,-1);
                abcd();repaint();
            }
            efficiency=System.currentTimeMillis()-time;repaint();
        }
    }
}