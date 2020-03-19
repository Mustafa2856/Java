/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author mustafa
 */
public class ui extends JPanel implements ActionListener {

    int count = 0;
    JButton print;
    JButton AddItem;
    JLabel Itemlabel;
    JLabel ItemQtylabel;
    JLabel Pricelabel;
    JComboBox Itemselect;
    JTextField Price;
    JSpinner Qty;
    JButton ModList;
    //JList List;
    JTable list;
    JScrollPane tableScroll;
    double total=0;

    ui() {
        initFrame();
    }

    private void initFrame() {
        Color c = new Color(0.9f, 0.9f, 0.9f);
        setBackground(c);
        setFocusable(true);
        setPreferredSize(new Dimension(1350, 650));
        setLayout(null);

        AddItem = new JButton();
        Pricelabel = new JLabel();
        Itemselect = new JComboBox();
        Price = new JTextField();
        Qty = new JSpinner();
        Itemlabel = new JLabel();
        ItemQtylabel = new JLabel();
        print = new JButton();
        ModList = new JButton();
        //List = new JList();
        list=new JTable();
        tableScroll = new JScrollPane();

        add(print);
        add(Pricelabel);
        add(Itemselect);
        add(Price);
        add(Qty);
        add(Itemlabel);
        add(ItemQtylabel);
        add(AddItem);
        add(ModList);
        //add(List);

        print.setText("Print Bill");
        Pricelabel.setText("Price :      Rs.");
        Price.setText("0");
        Itemlabel.setText("Item :");
        ItemQtylabel.setText("Quantity :");
        AddItem.setText("Add Item");
        ModList.setText("Modify Product List");
        Qty.setValue(1);

        ModList.addActionListener((java.awt.event.ActionEvent evt) -> {
            modActionPerformed(evt);
        });
        AddItem.addActionListener((java.awt.event.ActionEvent evt) -> {
            additmActionPerformed(evt);
        });
        Itemselect.addActionListener((java.awt.event.ActionEvent evt) -> {
            itemSelectedActionPerformed(evt);
        });
        print.addActionListener((java.awt.event.ActionEvent evt)-> {
            printbill();
        });
        Itemselect.setEditable(true);
        ComboBoxEditor cd = Itemselect.getEditor();
        try {
            FileWriter fr = new FileWriter("list.olb");
            fr.close();
        } catch (Exception exp) {
            System.out.println(exp);
        }
        try {
            Scanner sc = new Scanner(new File("Itemlist.olb"));
            int i = 0;
            while (sc.hasNext()) {
                String it = sc.nextLine();
                String p = sc.nextLine();
                if (i == 0) {
                    i = 1;
                    Price.setText(p);
                }
                Itemselect.addItem(it);
            }
        } catch (Exception exp) {
        }
        list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Item","Price","Quantity","Total"
            }
        ));
        tableScroll.setViewportView(list);
        list.getColumnModel().getColumn(0).setResizable(false);
        list.getColumnModel().getColumn(0).setPreferredWidth(600);
        list.getColumnModel().getColumn(1).setResizable(false);
        list.getColumnModel().getColumn(1).setPreferredWidth(150);
        list.getColumnModel().getColumn(2).setResizable(false);
        list.getColumnModel().getColumn(2).setPreferredWidth(100);
        list.getColumnModel().getColumn(3).setResizable(false);
        list.getColumnModel().getColumn(3).setPreferredWidth(150);

        add(tableScroll);
        
        Price.setHorizontalAlignment(JTextField.CENTER);
        //List.addListSelectionListener(new Lman());

        Itemlabel.setBounds(20, 15, 50, 30);
        Itemselect.setBounds(120, 15, 400, 30);
        ItemQtylabel.setBounds(20, 55, 100, 30);
        Qty.setBounds(120, 55, 50, 30);
        Pricelabel.setBounds(200, 55, 100, 30);
        Price.setBounds(300, 55, 60, 30);
        AddItem.setBounds(400, 95, 120, 30);
        ModList.setBounds(20, 95, 200, 30);
        tableScroll.setBounds(20, 150, 1000, 450);
        print.setBounds(720,95,100,30);

    }

    private void modActionPerformed(ActionEvent e) {
        ComboBoxEditor cd = Itemselect.getEditor();
        String itm=cd.getItem().toString();
        List<String> it = new ArrayList<String>();
        List<String> p = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new File("Itemlist.olb"));
            int i=0,flag=0;
                while (sc.hasNext()) {
                    it.add(sc.nextLine());
                    p.add(sc.nextLine());
                    if(it.get(i++).equals(itm))
                    {
                        p.set(i-1,Price.getText());flag=1;
                    }
                }
                if(flag==0){
                    it.add(itm);
                    p.add(Price.getText());
                }
                Itemselect.removeAllItems();
            FileWriter fr = new FileWriter("Itemlist.olb");
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            for(int ii=0;ii<p.size();ii++){
                pr.println(it.get(ii)+"\n"+p.get(ii));
                    if (ii == 0) {
                    Price.setText("1");
                }
                Itemselect.addItem(it.get(ii));
            }
            pr.close();
            br.close();
            fr.close();
        } catch (Exception exp) {
            System.out.println(exp);
        }
        itemSelectedActionPerformed((ActionEvent) Itemselect.getAction());
        
    }

    private void itemSelectedActionPerformed(ActionEvent e) {
        int si = Itemselect.getSelectedIndex();
        String price = "0";
        if (si != -1) {
            
            String sitem = Itemselect.getSelectedItem().toString();
            try {
                Scanner sc = new Scanner(new File("Itemlist.olb"));
                while (sc.hasNext()) {
                    String it = sc.nextLine();
                    String p = sc.nextLine();
                    if (it.equals(sitem)) {
                        price = p;
                    }
                }
            } catch (Exception exp) {
                System.out.println(exp);
            }
            Price.setText(price);
            Qty.setValue(1);
        }
    }

    private void additmActionPerformed(ActionEvent e) {
        List j = new ArrayList<ArrayList<String>>();    
        List<String> i1 = new ArrayList<String>();
        List<String> p1 = new ArrayList<String>();
        List<String> q1 = new ArrayList<String>();
        try {
            ComboBoxEditor cd = Itemselect.getEditor();
            Scanner sc = new Scanner(new File("list.olb"));
            int i=0,flag=0;
            while(sc.hasNext()){
                i1.add(sc.nextLine());
                p1.add(sc.nextLine());
                q1.add(sc.nextLine());
                if(i1.get(i++).equals(cd.getItem().toString())){
                    q1.set(i-1,Integer.toString(Integer.valueOf(q1.get(i-1))+(int)Qty.getValue()));
                    flag=1;
                }
            }
            
            FileWriter fr = new FileWriter("list.olb");
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            for(int ii=0;ii<i;ii++)
                pr.println(i1.get(ii)+"\n"+p1.get(ii)+"\n"+q1.get(ii));
            total=0;
            for(int ii=0;ii<i;ii++)
                total+=Double.valueOf(p1.get(ii))*Double.valueOf(q1.get(ii));
            
            if(flag==0){
                pr.println(cd.getItem().toString()+"\n"+Price.getText()+"\n"+Qty.getValue());
                total+=Double.valueOf(Price.getText())*Double.valueOf(Qty.getValue().toString());
            }
            repaint();
            pr.close();
            br.close();
            fr.close();
            
        } catch (Exception exp) {
            System.out.println(exp);
        }
        
        try {
            int i=0;
            Scanner sc = new Scanner(new File("list.olb"));
            while (sc.hasNext()) {
                /*j[i][0]=sc.nextLine();
                j[i][1]=sc.nextLine();
                j[i][2]=sc.nextLine();
                j[i][3]=Double.toString(Double.valueOf(j[i][1])*Double.valueOf(j[i][2]));*/
                List r = new ArrayList<String>();
                r.add(sc.nextLine());
                r.add(sc.nextLine());
                r.add(sc.nextLine());
                r.add(Double.toString(Math.round(Double.valueOf(r.get(1).toString())*Double.valueOf(r.get(2).toString())*100)/100.0));
                j.add(r);
            }
        } catch (Exception exp) {
        }
        var m=list.getModel();
        Object x[][] = new Object[j.size()][4];
        for(int i=0;i<j.size();i++)
        {
            List c = (ArrayList)j.get(i);
            x[i][0]=c.get(0);
            x[i][1]=c.get(1);
            x[i][2]=c.get(2);
            x[i][3]=c.get(3);
        }
        list.setModel(new javax.swing.table.DefaultTableModel(
            x,
            new String [] {
                "Item","Price","Quantity","Total"
            }
        ));
        list.getColumnModel().getColumn(0).setResizable(false);
        list.getColumnModel().getColumn(0).setPreferredWidth(600);
        list.getColumnModel().getColumn(1).setResizable(false);
        list.getColumnModel().getColumn(1).setPreferredWidth(150);
        list.getColumnModel().getColumn(2).setResizable(false);
        list.getColumnModel().getColumn(2).setPreferredWidth(100);
        list.getColumnModel().getColumn(3).setResizable(false);
        list.getColumnModel().getColumn(3).setPreferredWidth(150);
    }

    private void printbill() {
        try{
            FileWriter fr = new FileWriter("a.pr");
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            pr.println("#Rect:-: 0 0 W 100");
            pr.println("#String:-: 20 20");
            pr.println("Store Name");
            pr.println("#String:-: 20 150");
            pr.println("Items");
            pr.println("#String:-: 150 150");
            pr.println("Quantity");
            pr.println("#String:-: 220 150");
            pr.println("Price");
            pr.println("#String:-: 280 150");
            pr.println("Total");
            Scanner sc = new Scanner(new File("list.olb"));
            int i=0;double total=0;
            while(sc.hasNext()){
                String item = sc.nextLine();
                String price = sc.nextLine();
                String qty = sc.nextLine();
                pr.println("#String:-: 20 "+Integer.toString(i*30+180));
                pr.println(item);
                pr.println("#String:-: 150 "+Integer.toString(i*30+180));
                pr.println(qty);
                pr.println("#String:-: 220 "+Integer.toString(i*30+180));
                pr.println(price);
                pr.println("#String:-: 280 "+Integer.toString(i*30+180));
                pr.println(Double.toString(Double.valueOf(qty)*Double.valueOf(price)));
                total+=Double.valueOf(qty)*Double.valueOf(price);
                i++;
            }
            pr.println("#Rect:-: 15 120 130 "+Integer.toString(i*30+50));
            pr.println("#Rect:-: 145 120 70 "+Integer.toString(i*30+50));
            pr.println("#Rect:-: 215 120 60 "+Integer.toString(i*30+50));
            pr.println("#Rect:-: 275 120 55 "+Integer.toString(i*30+50));
            pr.println("#String:-: 20 "+Integer.toString(i*30+200));
            pr.println("Total: "+Double.toString(total));
            sc.close();
            pr.close();
            br.close();
            fr.close();
        }catch(Exception e){System.out.println(e);}
        Printer a = new Printer();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        Font f = new Font("Arial", 1, 25);
        g.setFont(f);
        g.drawRect(10, 10, 1330, 630);
        g.drawRect(10, 10, 520, 125);
        g.drawRect(530,10,300,60);
        g.drawRect(530,70,300,65);
        g.drawString("Total: Rs. "+Double.toString(Math.round(total*100)/100.0),550,50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class Lman implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int s = e.getFirstIndex();
            int l = e.getLastIndex();
            System.out.println(s + "\t" + l);
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER);
            System.out.println(e.toString());
        }
    }
}
