package travellingSalesPerson;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class pathFinder extends JPanel implements ActionListener{
	
	private int noCities=12;
	private Point[] cities=new Point[noCities];
	private int interval=1;
	private double bestdist = Double.MAX_VALUE;
	private int[] Order = new int[noCities];
	private int[] bestOrder = new int[noCities];
	private double percentcom;
	private long count=0;
	private long total;
	private Timer t;
	
	pathFinder(ArrayList<Point> arr){
		Object[] k = arr.toArray();
		for(int i=0;i<k.length;i++) {
			cities[i]=(Point)k[i];
		}
		initFrame();
	}
	
	private void initFrame() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(600,600));
		for(int i =0;i<noCities;i++) {
			//cities[i] = new Point();
			//cities[i].setLocation(Math.random()*600,Math.random()*600);
			Order[i]=i;
		}
		total = fact(noCities);
		percentcom = (double)count/(double)total;
		t = new Timer(interval,this);
		t.start();
	}
	
	private void nextStep() {
		int maxx=-1;int maxy=-1;
		for(int i=0;i<noCities-1;i++) {
			if(Order[i]<Order[i+1])
				maxx=i;
		}
		if(maxx==-1)t.stop();
		else {
			for(int i=0;i<noCities;i++) {
				if(Order[i]>Order[maxx])
					maxy=i;
			}
			int temp=Order[maxx];
			Order[maxx]=Order[maxy];
			Order[maxy]=temp;
			int[] rev = new int[noCities-maxx-1];
			for(int i=maxx+1;i<noCities;i++) {
				rev[i-maxx-1] = Order[maxx+noCities-i];
			}
			for(int i=maxx+1;i<noCities;i++) {
				Order[i] = rev[i-maxx-1];
			}
			double d=calcDistance();
			if(d<bestdist) {
				bestOrder = Order.clone();
				bestdist=d;
			}
		}
		count++;
		percentcom = (double)count/(double)total;
	}
	
	private double calcDistance() {
		double distance=0;
		for(int i=0;i<noCities-1;i++) {
			distance+=dist(cities[Order[i]],cities[Order[i+1]]);
		}
		distance+=dist(cities[Order[noCities-1]],cities[Order[0]]);
		return distance;
	}
	
	private double dist(Point p,Point q) {
		return (p.x-q.x)*(p.x-q.x)+(p.y-q.y)*(p.y-q.y);
	}
	
	private long fact(long n) {
		if(n==1)return 1;
		return n*fact(n-1);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<1000000;i++)nextStep();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int[][] path = new int[2][noCities];
		super.paintComponent(g);
		for(int i=0;i<noCities;i++) {
			g.setColor(Color.WHITE);
			g.fillOval(cities[i].x-4,cities[i].y-4,8,8);
			path[0][i]=cities[bestOrder[i]].x;
			path[1][i]=cities[bestOrder[i]].y;
		}
		if(percentcom>1)percentcom=1;
		g.drawString(String.format("%.2f", (100*percentcom))+"% completed",100,50);
		g.drawPolyline(path[0],path[1],noCities);
		g.drawLine(path[0][noCities-1],path[1][noCities-1],path[0][0],path[1][0]);
	}
}
