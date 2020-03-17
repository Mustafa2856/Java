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
        Scanner sc = new Scanner("a.pr");
        while(sc.hasNext()){
            String a = sc.nextLine();
            if(a.startsWith("#String:-: ")){
                int x = Integer.parseInt(a.substring(10,a.indexOf(' ',10)));
                int y = Integer.parseInt(a.substring(a.indexOf(' ',10),a.indexOf(' ',a.indexOf(' ',10))));
                String s = sc.nextLine();
                g.drawString(s,x,y);
            }
            if(a.startsWith("#Rect:-: ")){
                a=a.substring(a.indexOf(' '));
                a = a.trim();
                String l[] = a.split(" ");
                int x = Integer.parseInt(l[0]);
                int y = Integer.parseInt(l[1]);
                int len = Integer.parseInt(l[2]);
                int bre = Integer.parseInt(l[3]);
                g.drawRect(x,y,len,bre);
            }
        }
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