package travellingSalesPerson;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pathfinder2 extends JPanel{
	
	private int noCities=0;
	private ArrayList<Point> cities = new ArrayList<Point>();
	private int interval=1;
	private double bestdist = Double.MAX_VALUE;
	private ArrayList<Integer> Order = new ArrayList<Integer>();
	private double percentcom;
	private long count=0;
	private long total;
	private Timer t;
	private JButton addpt;
	private int toggle=0;
	
	Pathfinder2(ArrayList<Point> arr){
		for(int i=0;i<arr.size();i++)nextCity(arr.get(i));
		initFrame();
	}
	
	private void initFrame() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(600,600));
		//addMouseListener(new Mouselistener());
		setLayout(null);
		/*addpt = new JButton();
		addpt.setText("Click here to activate, 'Points add mode'");
		addpt.setBounds(100,10,400,25);
		addpt.setBackground(Color.WHITE);
		addpt.addMouseListener(new Mouselistener());
		add(addpt);*/
	}
	
	private void nextCity(Point p) {
		double minDistance = Double.MAX_VALUE;
		ArrayList<Point> newcities = new ArrayList<Point>();
		noCities++;
		for(int i=0;i<noCities;i++) {
			cities.add(i,p);
			double d=calcDistance();
			if(d<minDistance) {
				minDistance=d;
				newcities=(ArrayList<Point>)cities.clone();
			}
			cities.remove(i);
		}
		cities.add(p);
		double d=calcDistance();
		if(d<minDistance) {
			minDistance=d;
			newcities=(ArrayList<Point>)cities.clone();
		}
		cities.remove(cities.size()-1);
		cities = newcities;
		repaint();
	}
	
	private double calcDistance() {
		double distance=0;
		for(int i=0;i<noCities-1;i++) {
			distance+=dist(cities.get(i),cities.get(i+1));
		}
		distance+=dist(cities.get(noCities-1),cities.get(0));
		return distance;
	}
	
	private double dist(Point p,Point q) {
		return (p.x-q.x)*(p.x-q.x)+(p.y-q.y)*(p.y-q.y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		noCities=cities.size();
		int[][] path = new int[2][cities.size()];
		for(int i=0;i<noCities;i++) {
			g.fillOval(cities.get(i).x-4,cities.get(i).y-4,8,8);
			path[0][i]=cities.get(i).x;
			path[1][i]=cities.get(i).y;
		}
		g.drawPolyline(path[0],path[1],noCities);
		g.drawLine(path[0][noCities-1],path[1][noCities-1],path[0][0],path[1][0]);
	}
	
	private class Mouselistener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(addpt)) {
				if(toggle==0) {
					toggle=1;
					addpt.setText("Click here to deactivate, 'Points add mode'");
				}else {
					toggle=0;
					addpt.setText("Click here to activate, 'Points add mode'");
				}
				repaint();
			}else if(toggle==1) {
				int x = e.getX();
				int y = e.getY();
				nextCity(new Point(x,y));
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {	
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}
	
}
