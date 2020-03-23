/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dengine;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * @author mustafa
 */
public class engine extends JPanel implements ActionListener{
    
    private Triangle[] a = new Triangle[12000];Cube c[] = new Cube[1000];
    double[] x = new double[1000];
    double[] y = new double[1000];
    double[] z = new double[1000];
    double[] l = new double[1000];
    double[] h = new double[1000];
    double[] d = new double[1000];
    double[] vf = new double[3];
    double[] vu = new double[3];
    double[] vs = new double[3];
    private int count=0;
    private double p=0;private double q=0;private double r=0;
    public engine(){
        initComponents();
    }
    
    private void initComponents(){
        setVisible(true);addKeyListener(new TA());
        setBackground(Color.WHITE);
        setFocusable(true);
        setPreferredSize(new Dimension(500,500));
        //road
        for(int i=0;i<10;i++){
        x[i]=-50;y[i]=-20;z[i]=100*i;l[i]=100;h[i]=0;d[i]=100;}
        //buildings
        addb();
        for(int i=0;i<3;i++){
            vf[i]=0;vs[i]=0;vu[i]=0;
        }
        vf[2]=1;vu[1]=1;vs[0]=1;
        for(int n=0;n<100;n++)
        c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);
    }
    public void addb(){
        for(int i=11;i<20;i++){
            x[i]=50;y[i]=120;z[i]=60*(i-10);l[i]=60;h[i]=140;d[i]=50;
            c[i] = new Cube(x[i],y[i],z[i],l[i],h[i],d[i],i);
        }
    }
    public double[] cross(double[] a,double[] b){
        double c[] = new double[3];
        c[0]= a[1]*b[2]-a[2]*b[1];
        c[1]=-a[0]*b[2]+a[2]*b[0];
        c[2]=a[0]*b[1]-a[1]*b[0];
        double r =Math.sqrt(c[0]*c[0]+c[1]*c[1]+c[2]*c[2]);
        c[0]/=r;c[1]/=r;c[2]/=r;
        return c;
    }
    public void rotate(double theta,double phi){
        theta = 3.1415926535*theta/180.0;
        phi =3.1415926535*phi/180.0;
        double c1[] = cross(vf,vs);
        double r = Math.sqrt(c1[0]*c1[0]+c1[1]*c1[1]+c1[2]*c1[2]);
        c1[0]/=r;c1[1]/=r;c1[2]/=r;
        vf[0] = Math.cos(theta)*vf[0] + Math.sin(theta)*vs[0];
        vf[1] = Math.cos(theta)*vf[1] + Math.sin(theta)*vs[1];
        vf[2] = Math.cos(theta)*vf[2] + Math.sin(theta)*vs[2];
        double[] c2=cross(c1,vs);
        r = Math.sqrt(c2[0]*c2[0]+c2[1]*c2[1]+c2[2]*c2[2]);
        c2[0]/=r;c2[1]/=r;c2[2]/=r;
        vs[0] = Math.cos(theta)*vs[0] + Math.sin(theta)*c2[0];
        vs[1] = Math.cos(theta)*vs[1] + Math.sin(theta)*c2[1];
        vs[2] = Math.cos(theta)*vs[2] + Math.sin(theta)*c2[2];
        r =Math.sqrt(vf[0]*vf[0]+vf[1]*vf[1]+vf[2]*vf[2]);
        vf[0]/=r;vf[1]/=r;vf[2]/=r;
        r =Math.sqrt(vs[0]*vs[0]+vs[1]*vs[1]+vs[2]*vs[2]);
        vs[0]/=r;vs[1]/=r;vs[2]/=r;
        c1 = cross(vf,vu);
        r = Math.sqrt(c1[0]*c1[0]+c1[1]*c1[1]+c1[2]*c1[2]);
        c1[0]/=r;c1[1]/=r;c1[2]/=r;
        vf[0] = Math.cos(phi)*vf[0] + Math.sin(phi)*vu[0];
        vf[1] = Math.cos(phi)*vf[1] + Math.sin(phi)*vu[1];
        vf[2] = Math.cos(phi)*vf[2] + Math.sin(phi)*vu[2];
        c2=cross(c1,vu);
        r = Math.sqrt(c2[0]*c2[0]+c2[1]*c2[1]+c2[2]*c2[2]);
        c2[0]/=r;c2[1]/=r;c2[2]/=r;
        vu[0] = Math.cos(phi)*vu[0] + Math.sin(phi)*c2[0];
        vu[1] = Math.cos(phi)*vu[1] + Math.sin(phi)*c2[1];
        vu[2] = Math.cos(phi)*vu[2] + Math.sin(phi)*c2[2];
        r =Math.sqrt(vf[0]*vf[0]+vf[1]*vf[1]+vf[2]*vf[2]);
        vf[0]/=r;vf[1]/=r;vf[2]/=r;
        r =Math.sqrt(vu[0]*vu[0]+vu[1]*vu[1]+vu[2]*vu[2]);
        vu[0]/=r;vu[1]/=r;vu[2]/=r;
    }
    
    public Point convert(double x,double y,double z){
        Point pp = new Point();
        double fcomp = vf[0]*(x-p)+vf[1]*(y-q)+vf[2]*(z-r);
        double ucomp = vu[0]*(x-p)+vu[1]*(y-q)+vu[2]*(z-r);
        double scomp = vs[0]*(x-p)+vs[1]*(y-q)+vs[2]*(z-r);
        if(fcomp>0){
        pp.x=(int)Math.round(288*scomp/fcomp);
        pp.y=-(int)Math.round(288*ucomp/fcomp);}
        else
        {
        pp.x=(int)Math.round(288*scomp/.1);
        pp.y=-(int)Math.round(288*ucomp/.1); 
        }
        return pp;
    }
    
    private class Triangle{
        Point p1;
        Point p2;
        Point p3;float c=0;
        Triangle(double[] point1,double[] point2,double[] point3){
            if(check(point1,point2,point3)>0){
            p1=convert(point1[0],point1[1],point1[2]);
            p2=convert(point2[0],point2[1],point2[2]);
            p3=convert(point3[0],point3[1],point3[2]);c=check(point1,point2,point3);}
        }
    }
    
    private float check(double[] point1,double[] point2,double[] point3){
        double z = (point2[0]-point1[0])*(point2[1]-point3[1])-(point2[1]-point1[1])*(point2[0]-point3[0]);
        double x = (point2[1]-point1[1])*(point2[2]-point3[2])-(point2[2]-point1[2])*(point2[1]-point3[1]);
        double y = (point2[0]-point1[0])*(point2[2]-point3[2])-(point2[2]-point1[2])*(point2[0]-point3[0]);
        boolean pp = ((point1[0]-p)*x+(point1[1]-q)*y+(point1[2]-r)*z)>0&&((point2[0]-p)*x+(point2[1]-q)*y+(point2[2]-r)*z)>0&&((point3[0]-p)*x+(point3[1]-q)*y+(point3[2]-r)*z)>0;
        float r = (float)Math.sqrt(x*x+y*y+z*z);x/=r;y/=r;z/=r;
        float f=(float)(vf[0]*x+vf[1]*y+vf[2]*z);
        f=(float)Math.sqrt(1-f*f);
        return pp?f:1;
    }
            
    private class Cube{
        Triangle faces[][] = new Triangle[6][2];
        Cube(double x,double y,double z,double l,double h,double d,int i){
            double[] p1 = new double[3];double[] p2 = new double[3];double[] p3 = new double[3];count=i*12;
            //face1
            p1[0]=x;p1[1]=y;p1[2]=z;
            p2[0]=x+l;p2[1]=y;p2[2]=z;
            p3[0]=x;p3[1]=y-h;p3[2]=z;
            faces[0][0] = new Triangle(p1,p2,p3);
            p1[0]=x;p1[1]=y-h;p1[2]=z;
            p2[0]=x+l;p2[1]=y;p2[2]=z;
            p3[0]=x+l;p3[1]=y-h;p3[2]=z;
            faces[0][1] = new Triangle(p1,p2,p3);
            //face2
            p1[0]=x+l;p1[1]=y;p1[2]=z;
            p2[0]=x+l;p2[1]=y;p2[2]=z+d;
            p3[0]=x+l;p3[1]=y-h;p3[2]=z;
            faces[1][0] = new Triangle(p1,p2,p3);
            p1[0]=x+l;p1[1]=y-h;p1[2]=z;
            p2[0]=x+l;p2[1]=y;p2[2]=z+d;
            p3[0]=x+l;p3[1]=y-h;p3[2]=z+d;
            faces[1][1] = new Triangle(p1,p2,p3);
            //face3
            p3[0]=x;p3[1]=y;p3[2]=z+d;
            p2[0]=x+l;p2[1]=y;p2[2]=z+d;
            p1[0]=x;p1[1]=y-h;p1[2]=z+d;
            faces[2][0] = new Triangle(p1,p2,p3);
            p1[0]=x+l;p1[1]=y-h;p1[2]=z+d;
            p2[0]=x+l;p2[1]=y;p2[2]=z+d;
            p3[0]=x;p3[1]=y-h;p3[2]=z+d;
            faces[2][1] = new Triangle(p1,p2,p3);
            //face4
            p1[0]=x;p1[1]=y-h;p1[2]=z;
            p2[0]=x;p2[1]=y;p2[2]=z+d;
            p3[0]=x;p3[1]=y;p3[2]=z;
            faces[3][0] = new Triangle(p1,p2,p3);
            p1[0]=x;p1[1]=y-h;p1[2]=z+d;
            p2[0]=x;p2[1]=y;p2[2]=z+d;
            p3[0]=x;p3[1]=y-h;p3[2]=z;
            faces[3][1] = new Triangle(p1,p2,p3);
            //face5
            p1[0]=x+l;p1[1]=y;p1[2]=z+d;
            p2[0]=x;p2[1]=y;p2[2]=z+d;
            p3[0]=x;p3[1]=y;p3[2]=z;
            faces[4][0] = new Triangle(p1,p2,p3);
            p1[0]=x;p1[1]=y;p1[2]=z;
            p2[0]=x+l;p2[1]=y;p2[2]=z;
            p3[0]=x+l;p3[1]=y;p3[2]=z+d;
            faces[4][1] = new Triangle(p1,p2,p3);
            //face6
            p1[0]=x;p1[1]=y-h;p1[2]=z;
            p2[0]=x;p2[1]=y-h;p2[2]=z+d;
            p3[0]=x+l;p3[1]=y-h;p3[2]=z+d;
            faces[5][0] = new Triangle(p1,p2,p3);
            p1[0]=x+l;p1[1]=y-h;p1[2]=z+d;
            p2[0]=x+l;p2[1]=y-h;p2[2]=z;
            p3[0]=x;p3[1]=y-h;p3[2]=z;
            faces[5][1] = new Triangle(p1,p2,p3);
            for(int j=0;j<6;j++)
            {
                a[count]=faces[j][0];count++;a[count]=faces[j][1];count++;
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    private void draw(Graphics g){
        g.translate(250,250);int arr[][] = new int[2][3];
        for(int i=12000-1;i>=120;i--)
        {
            if(a[i]!=null && a[i].p1!=null && a[i].p2!=null && a[i].p3!=null){

                arr[0][0]=a[i].p1.x;
                arr[0][1]=a[i].p2.x;
                arr[0][2]=a[i].p3.x;
                
                arr[1][0]=a[i].p1.y;
                arr[1][1]=a[i].p2.y;
                arr[1][2]=a[i].p3.y;
                Color as = new Color(0.0f,0.0f,1.0f-a[i].c*0.2f);
                g.setColor(as);
                g.fillPolygon(arr[0],arr[1],3);
            }
        }
        for(int i=0;i<120;i++){
            if(a[i]!=null && a[i].p1!=null && a[i].p2!=null && a[i].p3!=null){

                arr[0][0]=a[i].p1.x;
                arr[0][1]=a[i].p2.x;
                arr[0][2]=a[i].p3.x;
                
                arr[1][0]=a[i].p1.y;
                arr[1][1]=a[i].p2.y;
                arr[1][2]=a[i].p3.y;
                Color as = new Color(0.3f,0.3f,0.3f-a[i].c*0.05f);
                g.setColor(as);
                g.fillPolygon(arr[0],arr[1],3);
            }
        }
        System.out.println();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        initComponents();
    }
    
    private class TA extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if(key==KeyEvent.VK_NUMPAD8){
                r+=vf[2];
                p+=vf[0];
                q+=vf[1];
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_NUMPAD2){
                r-=vf[2];
                p-=vf[0];
                q-=vf[1];
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_NUMPAD6){
                rotate(1,0);
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_NUMPAD4){
                rotate(-1,0);
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_NUMPAD3){
                rotate(0,1);
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_NUMPAD1){
                rotate(0,-1);
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_UP){
                p+=vu[0];
                q+=vu[1];
                r+=vu[2];
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_DOWN){
                p-=vu[0];
                q-=vu[1];
                r-=vu[2];
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_LEFT){
                p+=vs[0];
                q+=vs[1];
                r+=vs[2];
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
            if(key==KeyEvent.VK_RIGHT){
                p-=vs[0];
                q-=vs[1];
                r-=vs[2];
                for(int n=0;n<100;n++){
                c[n] = new Cube(x[n],y[n],z[n],l[n],h[n],d[n],n);}repaint();
            }
        }
    }
}
