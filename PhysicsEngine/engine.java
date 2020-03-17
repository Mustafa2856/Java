import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class engine extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private List<obj> objlist = new ArrayList<obj>();
	private List<Force> forces = new ArrayList<Force>();
	private Timer t;
	
	engine() {
        initFrame();
    }

    private void initFrame() {
    	addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setFocusable(true);
        setPreferredSize(new Dimension(600,600));
        setLayout(null);
        objlist.add(new obj(10,10,50));
        objlist.add(new obj(5,3,50));
        objlist.get(0).velocity.x=10;
        objlist.get(1).velocity.x=10;
        forces.add(new Force(10,0,1,'m'));
        t = new Timer(10,this);
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
    	g.translate(300,300);
        for(int o=0;o<objlist.size();o++) {
        	obj x = objlist.get(o);
        	for(int i=0;i<x.vertices.size()-1;i++) {
        		g.drawLine((int)(x.location.x+x.vertices.get(i).x),(int)(x.location.y+x.vertices.get(i).y),(int)(x.location.x+x.vertices.get(i+1).x),(int)(x.location.y+x.vertices.get(i+1).y));
        	}
        	g.drawLine((int)(x.location.x+x.vertices.get(0).x),(int)(x.location.y+x.vertices.get(0).y),(int)(x.location.x+x.vertices.get(x.vertices.size()-1).x),(int)(x.location.y+x.vertices.get(x.vertices.size()-1).y));
        }
    }
    
    private void move() {
    	Point resultantforce = new Point();
    	solvecollisions();
    	for(int i=0;i<objlist.size();i++) {
    		obj object=objlist.get(i);
    		resultantforce = new Point();
    		for(int j=0;j<forces.size();j++) {
    			if(forces.get(j).type=='i') 
    				resultantforce.setLocation(resultantforce.getX()+forces.get(j).magnitude*forces.get(j).direction.getX(),resultantforce.getY()+forces.get(j).magnitude*forces.get(j).direction.getY());
    			else
    				resultantforce.setLocation(resultantforce.getX()+forces.get(j).magnitude*object.mass*forces.get(j).direction.getX(),resultantforce.getY()+forces.get(j).magnitude*object.mass*forces.get(j).direction.getY());
    		}
    		
    		object.location.setLocation(object.location.getX()+object.velocity.getX()*.1,object.location.getY()+object.velocity.getY()*.1);
    		solvecollisions();
    		object.velocity.setLocation(object.velocity.getX()+resultantforce.getX()*.1/object.mass,object.velocity.getY()+resultantforce.getY()*.1/object.mass);
    		for(int j=0;j<object.vertices.size();j++) {
    			double angle=object.angularvel*.1;
    			Point p = object.vertices.get(j);
    			double x = p.x*Math.cos(angle)+p.y*Math.sin(angle);
    			double y = -p.x*Math.sin(angle)+p.y*Math.cos(angle);
    			/*double r=Math.sqrt(p.x*p.x+p.y*p.y);
    			p.x = r*x/Math.sqrt(x*x+y*y);
    			p.y = r*y/Math.sqrt(x*x+y*y);*/
    			p.x=x;p.y=y;
    			object.vertices.set(j,p);
    		}
    		object.angularvel*=0.95;
    		objlist.set(i,object);
    	}
    	repaint();
    }
    
    private void solvecollisions() {
    	for(int i=0;i<objlist.size();i++) {
    		obj object = objlist.get(i);
    		int flag=0;
    		for(int j=0;j<object.vertices.size();j++) {
    			if(object.location.getX()+object.vertices.get(j).x>=300) {
    				object.velocity.x=-0.7*Math.abs(object.velocity.x);
    				object.location.x=300-object.vertices.get(j).x;
    				object.angularvel+=object.vertices.get(i).getY()*.001;
    				flag=1;
    				}
    			if(object.location.getX()+object.vertices.get(j).x<=-300) {
    				object.velocity.x=0.7*Math.abs(object.velocity.x);
    				object.location.x=-300-object.vertices.get(j).x;
    				object.angularvel-=object.vertices.get(i).getY()*.001;
    				flag=1;
    				}
    			if(object.location.getY()+object.vertices.get(j).y>=300) {
    				object.velocity.y=-0.7*Math.abs(object.velocity.y);
    				object.location.y=300-object.vertices.get(j).y;
    				object.angularvel+=object.vertices.get(i).getX()*.001;
    				flag=1;
    				}
    			if(object.location.getY()+object.vertices.get(j).y<=-300) {
    				object.velocity.y=0.7*Math.abs(object.velocity.y);
    				object.location.y=-300-object.vertices.get(j).y;
    				object.angularvel-=object.vertices.get(i).getX()*.001;
    				flag=1;
    				}
    			if(flag==1)break;
    			objlist.set(i,object);
    		}
    	}
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	move();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER);
            System.out.println(e.toString());
        }
    }
    
    private class obj{
    	double mass;
    	Point location;
    	Point velocity;
    	double angularvel=0;
    	ArrayList<Point> vertices;
    	obj(double mass,int verticecount,double radialsize){
    		this.mass=mass;
    		location=new Point(0,0);
    		velocity=new Point(0,0);
    		vertices=new ArrayList<Point>();
    		for(int i=0;i<verticecount;i++) {
    			double theta = 2*Math.PI/verticecount;
    			Point v = new Point();
    			v.setLocation(radialsize*Math.cos(theta*i),radialsize*Math.sin(theta*i));
    			vertices.add(v);
    		}
    	}
    }
    
    private class Force{
    	double magnitude;
    	char type='i';
    	Point direction = new Point();
    	Force(double magnitude,double angle,char type){
    		this.magnitude=magnitude;
    		direction.setLocation(Math.cos(angle),Math.sin(angle));
    		this.type=type;
    	}
        Force(double magnitude,Point direction,char type){
        	this.magnitude=magnitude;
        	this.type=type;
        	this.direction.setLocation(direction.getX()/Math.sqrt(direction.getX()*direction.getX()+direction.getY()*direction.getY()),direction.getY()/Math.sqrt(direction.getX()*direction.getX()+direction.getY()*direction.getY()));
    	}
        Force(double magnitude,double x,double y,char type){
        	this.magnitude=magnitude;
        	this.type=type;
        	direction.setLocation(x/Math.sqrt(x*x+y*y),y/Math.sqrt(x*x+y*y));
    	}
    }
    
    private class Point{
    	double x;
    	double y;
    	Point(double x,double y){
    		this.x=x;
    		this.y=y;
    	}
    	Point(){
    		this.x=0;
    		this.y=0;
    	}
    	double getX() {
    		return x;
    	}
    	double getY() {
    		return y;
    	}
    	void setLocation(double x,double y) {
    		this.x=x;
    		this.y=y;
    	}
    }
}
