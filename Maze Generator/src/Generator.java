import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Generator extends JPanel implements ActionListener{
	
	private int[][] maze;
	private Stack<Point> prevsteps = new Stack<Point>();
	private Stack<Point> solvsteps = new Stack<Point>();
	private ArrayList<Integer> avail = new ArrayList<Integer>();
	private JButton solve;
	private int toggle=0;
	private int solved=0;
	Timer t;
	
	Generator(int row,int col){
		maze = new int[row*2-1][col*2-1];
		for(int i=0;i<(row*2-1);i++) {
			for(int j=0;j<(col*2-1);j++) {
				if((i&1)==1 || (j&1)==1)
					maze[i][j]=1;
				else
					maze[i][j]=0;
			}
		}
		generate();
	}
	
	private void generate() {
		setPreferredSize(new Dimension(600,600));
		setBackground(Color.BLACK);
		prevsteps.push(new Point(0,0));
		solvsteps.push(new Point(0,0));
		t = new Timer(1,this);
		t.start();
		setLayout(null);
	}
	
	private void generatemore() {
		if(prevsteps.size()>0) {
		Point currentstep = prevsteps.peek();
		if(currentstep.x==maze.length-1 && currentstep.y==maze[0].length-1)solved=1;
		maze[currentstep.x][currentstep.y]=1;
		check(currentstep.x,currentstep.y);
		Point newstep = new Point();
		int r = avail.size();
		if(r==0) {
			int l = prevsteps.size();
			if(l>0) {
			prevsteps.pop();
			if(solved==0)solvsteps.pop();
			generatemore();}
		}else {
			r = (int)(r*Math.random());
			r = avail.get(r);
			if(r==0) {
				maze[currentstep.x-1][currentstep.y]=0;
				maze[currentstep.x-2][currentstep.y]=1;
				newstep.x = currentstep.x-2;
				newstep.y = currentstep.y;
			}
			if(r==1) {
				maze[currentstep.x][currentstep.y+1]=0;
				maze[currentstep.x][currentstep.y+2]=1;
				newstep.x = currentstep.x;
				newstep.y = currentstep.y+2;
			}
			if(r==2) {
				maze[currentstep.x+1][currentstep.y]=0;
				maze[currentstep.x+2][currentstep.y]=1;
				newstep.x = currentstep.x+2;
				newstep.y = currentstep.y;
			}
			if(r==3) {
				maze[currentstep.x][currentstep.y-1]=0;
				maze[currentstep.x][currentstep.y-2]=1;
				newstep.x = currentstep.x;
				newstep.y = currentstep.y-2;
			}
			prevsteps.push(newstep);
			if(solved==0)solvsteps.push(newstep);
		}
		repaint();
		}
		else {
			try {
				FileWriter fr = new FileWriter("genmaze.txt");
				BufferedWriter br = new BufferedWriter(fr);
				PrintWriter pr= new PrintWriter(br);
				for(int i=0;i<maze.length;i++) {
					for(int j=0;j<maze[0].length;j++)
						pr.print(maze[i][j]);
					pr.println();
				}
				pr.close();
				br.close();
				fr.close();
			}catch(Exception e) {System.out.println(e);}
			toggle=1;
			t.stop();repaint();
		}
	}
	
	private void check(int x,int y) {
		avail.clear();
		if(x>0 && maze[x-2][y]==0) {
			avail.add(0);
		}
		if(y<(maze[0].length-1) && maze[x][y+2]==0) {
			avail.add(1);
		}
		if(x<(maze.length-1) && maze[x+2][y]==0) {
			avail.add(2);
		}
		if(y>0 && maze[x][y-2]==0) {
			avail.add(3);
		}
	}
	
	private void checks(int x,int y,int l) {
		//todo
	}
	
	private void solve() {
		Point current = prevsteps.peek();
		checks(current.x,current.y,0);//todo
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int row=maze.length;
		int col=maze[0].length;
		g.setColor(Color.CYAN);
		g.fillRect(2,2,(row/2+1)*10,(col/2+1)*10);
		g.setColor(Color.GREEN);
		g.drawRect(0, 0, (row/2+1)*10,(col/2+1)*10);
		g.drawRect(1, 1, (row/2+1)*10,(col/2+1)*10);
		if(toggle==0) {
		g.setColor(Color.WHITE);
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				if((i&1)==0 && (j&1)==0) {
					if(maze[i][j]==1)
						g.setColor(Color.CYAN);
					else
						g.setColor(Color.DARK_GRAY);
					g.fillRect((i/2)*10+2,(j/2)*10+2,8,8);
				}
				else if((i%2)==0 || (j%2)==0) {
					if(maze[i][j]==1) {
						g.setColor(Color.GREEN);
						if(i%2==0)
							g.fillRect((i/2)*10,(j/2+1)*10,10,2);
						else
							g.fillRect((i/2+1)*10,(j/2)*10,2,10);
					}
				}
			}
		}
		}else {
			while(!solvsteps.isEmpty()){
				Point p = solvsteps.pop();
				maze[p.x][p.y]=0;
			}
			for(int i=0;i<row;i++) {
				for(int j=0;j<col;j++) {
					if((i&1)==0 && (j&1)==0) {
						if(maze[i][j]==1)
							g.setColor(Color.CYAN);
						else
							g.setColor(Color.RED);
						g.fillRect((i/2)*10+2,(j/2)*10+2,10,10);
					}
					else if((i%2)==0 || (j%2)==0) {
						if(maze[i][j]==1) {
							g.setColor(Color.GREEN);
							if(i%2==0)
								g.fillRect((i/2)*10,(j/2+1)*10,10,2);
							else
								g.fillRect((i/2+1)*10,(j/2)*10,2,10);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(toggle==0)generatemore();
		else solve();
	}
}
