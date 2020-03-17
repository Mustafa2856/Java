package todolist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class listpanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel sidebar;
	JPanel mainlist;
	List projectlist;
	JButton addproj;
	JTextField projname;
	JButton addtask;
	JTextField taskname;
	JPanel taskliste;JScrollPane tasklist;
	JButton delproj;
	String currentproj="";
	int HEIGHT=650,WIDTH=800;
	
	listpanel() {
        initFrame();
    }

    private void initFrame() {
        setBackground(Color.WHITE);
        setFocusable(true);
        setPreferredSize(new Dimension(800,650));
        setMinimumSize(new Dimension(800,650));
        setLayout(null);
        sidebar = new JPanel();
        sidebar.setLayout(null);
        add(sidebar);
        sidebar.setBounds(0,0,WIDTH*3/8,HEIGHT);
        projectlist = new List(10,false);
        sidebar.add(projectlist);
        projectlist.setBounds(30,110,sidebar.getWidth()-60,sidebar.getHeight()-140);
        projectlist.addItemListener(new Itemclass());
        addproj = new JButton();
        addproj.setText("Add Project");
        sidebar.add(addproj);
        addproj.setBounds(120,70,150,30);
        projname = new JTextField();
        projname.setText("");
        sidebar.add(projname);
        projname.setBounds(30,30,240,30);
        addproj.addActionListener((ActionEvent e)->{addprojActionPerformed();});
        taskliste = new JPanel();
        tasklist=new JScrollPane(taskliste);
        add(tasklist);
        tasklist.setBounds(WIDTH*3/8+20,110,WIDTH*5/8-40,HEIGHT-140);
        addtask = new JButton();
        addtask.setText("Add task");
        addtask.setBounds(WIDTH*3/8+330,70,150,30);
        addtask.addActionListener((ActionEvent e)->{taskadded();});
        delproj = new JButton();
        delproj.setText("Delete Project");
        delproj.setBounds(WIDTH*3/8+150,70,150,30);
        delproj.addActionListener((ActionEvent e)->{projectdelete();});
        add(delproj);
        taskname = new JTextField();
        taskname.setText("");
        taskname.setBounds(WIDTH*3/8+20,30,460,30);
        add(addtask);
        add(taskname);
        try {
			Scanner sc = new Scanner(new File("projlist"));
			while(sc.hasNext()) {
				String k=sc.nextLine();
				projectlist.add(k);
			}
			sc.close();
		}catch(Exception exp) {System.out.println(exp);try{FileWriter fr = new FileWriter("projlist");fr.close();}catch(Exception e) {}}
    }
    
   private void projectdelete() {
	   
	   ArrayList<String> plist = new ArrayList<String>();
	   try {
		   Scanner sc = new Scanner(new File("projlist"));
		   while(sc.hasNext()) {
			   String x= sc.nextLine();
			   if(x.equals(currentproj))
				   continue;
			   plist.add(x);
		   }
		   sc.close();
		   File f = new File(currentproj);
		   f.delete();
		   FileWriter fr = new FileWriter("projlist");
		   BufferedWriter br = new BufferedWriter(fr);
		   PrintWriter pr = new PrintWriter(br);
		   for(String i:plist)pr.println(i);
		   pr.close();
		   br.close();
		   fr.close();
		   projectlist.remove(currentproj);
		   repaint();
	   }catch(Exception e) {System.out.println(e);}
   }
 @Override
    public void paintComponent(Graphics g) {
    	HEIGHT = getHeight();
    	WIDTH = getWidth();
    	sidebar.setBounds(0,0,WIDTH*3/8,HEIGHT);
    	projectlist.setBounds(30,110,sidebar.getWidth()-60,sidebar.getHeight()-140);
    	addproj.setBounds(120,70,150,30);
    	projname.setBounds(30,30,240,30);
    	tasklist.setBounds(WIDTH*3/8+20,110,WIDTH*5/8-40,HEIGHT-140);
    	addtask.setBounds(WIDTH*3/8+330,70,150,30);
        taskname.setBounds(WIDTH*3/8+20,30,460,30);
    	super.paintComponent(g);
    }
    
    private void taskadded() {
		String a = taskname.getText();
		int flag=0;
		try {
			Scanner sc = new Scanner(new File(currentproj));
			taskliste.removeAll();
			int i=0;
			while(sc.hasNext()) {
				String s = sc.nextLine();
				if(s.equals(a)) {
					System.err.println("Task already exists");flag=1;
				}
				JPanel newtask = new JPanel();
				newtask.setBounds(10,10+i*40,440,30);
				i++;
				JButton completed = new JButton();
				completed.setText("X");
				completed.setBounds(380,0,50,30);
				completed.addActionListener((ActionEvent evt)->{taskcompleted(s);});
				newtask.add(completed);
				JLabel taskname = new JLabel();
				taskname.setText(s);
				taskname.setBounds(0,0,380,30);
				newtask.add(taskname);
				newtask.setBackground(Color.WHITE);
				taskliste.add(newtask);
			}
			sc.close();
			if(flag==0) {
				JPanel newtask = new JPanel();
				newtask.setBounds(10,10+i*40,440,30);
				i++;
				JButton completed = new JButton();
				completed.setText("X");
				completed.setBounds(380,0,50,30);
				completed.addActionListener((ActionEvent evt)->{taskcompleted(a);});
				newtask.add(completed);
				JLabel taskname = new JLabel();
				taskname.setText(a);
				taskname.setBounds(0,0,380,30);
				newtask.add(taskname);
				newtask.setBackground(Color.WHITE);
				taskliste.add(newtask);
				FileWriter fr = new FileWriter(currentproj,true);
				BufferedWriter br = new BufferedWriter(fr);
				PrintWriter pr = new PrintWriter(br);
				pr.println(a);
				pr.close();
				br.close();
				fr.close();
			}
			repaint();
		}catch(Exception exp) {System.out.println(exp);}
	}
    
	private void taskcompleted(String s) {
		ArrayList<String> a = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(new File(currentproj));
			while(sc.hasNext()) {
				a.add(sc.nextLine());
			}
			a.remove(s);
			sc.close();
			FileWriter fr = new FileWriter(currentproj);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter pr = new PrintWriter(br);
			for(String i:a)pr.println(i);
			pr.close();
			br.close();
			fr.close();
		}catch(Exception e) {System.out.println(e);}
		try {
			Scanner sc = new Scanner(new File(currentproj));
			taskliste.removeAll();repaint();
			int i=0;
			while(sc.hasNext()) {
				String ss = sc.nextLine();
				JPanel newtask = new JPanel();
				newtask.setBounds(10,10+i*40,440,30);
				i++;
				JButton completed = new JButton();
				completed.setText("X");
				completed.setBounds(380,0,50,30);
				completed.addActionListener((ActionEvent evt)->{taskcompleted(ss);});
				newtask.add(completed);
				JLabel taskname = new JLabel();
				taskname.setText(ss);
				taskname.setBounds(5,0,375,30);
				newtask.add(taskname);
				newtask.setBackground(Color.WHITE);
				taskliste.add(newtask);
				repaint();
			}
		}catch(Exception exp) {System.out.println(exp);}
		
	}

	private void addprojActionPerformed() {
		String s = projname.getText();
		ArrayList<String> projects = new ArrayList<String>();
		int flag=0;
		try {
			Scanner sc = new Scanner(new File("projlist"));
			while(sc.hasNext()) {
				String k=sc.nextLine();
				if(s.equals(k)) {
					System.err.println("Project with same name exists");flag=1;
				}
				projects.add(k);
			}
			if(flag==0) {
				projects.add(s);
				FileWriter fr = new FileWriter(s);
				fr.close();
				currentproj=s;
			}
			sc.close();
		}catch(Exception exp) {System.out.println(exp);}
		projectlist.removeAll();
		try {
			FileWriter fr = new FileWriter("projlist");
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter pr = new PrintWriter(br);
			for(int i=0;i<projects.size();i++) {
				pr.println(projects.get(i));
				projectlist.add(projects.get(i));
			}
			pr.close();
			br.close();
			fr.close();
		}catch(Exception exp) {System.out.println(exp);}
	}

	private class Itemclass implements ItemListener{
    	@Override
    	public void itemStateChanged(ItemEvent e){
    		if(projectlist.equals(e.getSource())) {
    			currentproj=projectlist.getItem((int)e.getItem());
    			try {
    				Scanner sc = new Scanner(new File(currentproj));
    				taskliste.removeAll();repaint();
    				int i=0;
    				while(sc.hasNext()) {
    					String s = sc.nextLine();
    					JPanel newtask = new JPanel();
    					newtask.setBounds(10,10+i*40,440,30);
    					i++;
    					JButton completed = new JButton();
    					completed.setText("X");
    					completed.setBounds(380,0,50,30);
    					completed.addActionListener((ActionEvent evt)->{taskcompleted(s);});
    					newtask.add(completed);
    					JLabel taskname = new JLabel();
    					taskname.setText(s);
    					taskname.setBounds(5,0,375,30);
    					newtask.add(taskname);
    					
    					newtask.setBackground(Color.WHITE);
    					taskliste.add(newtask);
    					repaint();
    				}
    			}catch(Exception exp) {System.out.println(exp);}
    		}
    	}
    }
}
