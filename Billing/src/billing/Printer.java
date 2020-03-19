/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.io.File;
import java.util.Scanner;

public class Printer implements Printable {
    
    public Printer(){
        PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
    }
 
    @Override
    public int print(Graphics g, PageFormat pf, int page) throws
                                                        PrinterException {
 
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
 
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        try{
        Scanner sc = new Scanner(new File("a.pr"));
        while(sc.hasNext()){
            String a = sc.nextLine();
            if(a.startsWith("#String:-: ")){
                String l[]=a.substring(11).split(" ");
                int x = Integer.parseInt(l[0]);
                int y = Integer.parseInt(l[1]);
                String s = sc.nextLine();
                g2d.drawString(s,x,y);
            }
            if(a.startsWith("#Rect:-: ")){
                a=a.substring(a.indexOf(' '));
                a = a.trim();
                String l[] = a.split(" ");
                int x = Integer.parseInt(l[0]);
                int y = Integer.parseInt(l[1]);
                int len;
                if(l[2].equals("W"))
                    len=(int)pf.getImageableWidth();
                else
                len = Integer.parseInt(l[2]);
                int bre = Integer.parseInt(l[3]);
                g2d.drawRect(x,y,len,bre);
            }
        }
        sc.close();}catch(Exception e){System.out.println(e);}
        return PAGE_EXISTS;
    }
 
    /*@Override
    public void actionPerformed(ActionEvent e) {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete 
             }
         }
    }*/
}