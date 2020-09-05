import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Player extends JPanel implements ActionListener {

    private ArrayList<ArrayList<Double>> data = new ArrayList<>();
    private final double[] notes = new double[97];
    private final byte[] wavhead = new byte[44];

    public Player(){
        initvar();
        setSize(400,200);
        setBackground(Color.BLACK);
        setLayout(null);
        JButton openfile = new JButton("Open Project");
        Font f = new Font("Arial",Font.PLAIN,10);
        ButtonUI bi = new javax.swing.plaf.basic.BasicButtonUI();
        openfile.setUI(bi);
        openfile.setFont(f);
        JButton newFile = new JButton("New Project");
        newFile.setUI(bi);
        newFile.setFont(f);
        JButton playAud = new JButton("Play");
        playAud.setUI(bi);
        playAud.setFont(f);
        JTextField note = new JTextField();
        note.setUI(new javax.swing.plaf.basic.BasicTextFieldUI());
        note.setFont(f);
        JButton nBeat = new JButton("Next beat");
        nBeat.setUI(bi);
        nBeat.setFont(f);
        add(openfile);
        add(newFile);
        add(playAud);
        add(note);
        add(nBeat);
        openfile.setBounds(0,0,200,20);
        newFile.setBounds(200,0,200,20);
        playAud.setBounds(0,20,200,20);
        note.setBounds(0,50,200,30);
        nBeat.setBounds(200,50,200,30);
        
        openfile.addActionListener((ActionEvent evt)-> openTranscript());
        newFile.addActionListener((ActionEvent evt)-> makeFile());
        playAud.addActionListener((ActionEvent evt)-> playf());
        nBeat.addActionListener((ActionEvent evt)-> {
            data.add(new ArrayList<>());
            String[] x = note.getText().split(" ");
            for(int i=0;i<x.length;i++){
                data.get(data.size()-1).add(notes[60+Integer.parseInt(x[i])]);
            }
        });
    }

    private void playf() {
        try {
            FileOutputStream fout = new FileOutputStream("output.wav");
            int size = data.size()*8000 + 36;
            wavhead[4] = (byte) (size&0xff);
            wavhead[5] = (byte) ((size>>8)&0xff);
            wavhead[6] = (byte) ((size>>16)&0xff);
            wavhead[7] = (byte) ((size>>24)&0xff);
            size-=36;
            wavhead[40] = (byte) (size&0xff);
            wavhead[41] = (byte) ((size>>8)&0xff);
            wavhead[42] = (byte) ((size>>16)&0xff);
            wavhead[43] = (byte) ((size>>24)&0xff);
            fout.write(wavhead);
            double d;
            for(int i=0;i<data.size();i++){
                for(int j=0;j<4000;j++){
                    d=0;
                    for(int k=0;k<data.get(i).size();k++)d+=5000 * Math.sin(j * 2.0 * Math.PI *  data.get(i).get(k) / 16000.0);
                    d *= (4000.0-j)/4000.0;
                    fout.write(((int)d)&0xff);
                    fout.write((((int)d)>>8)&0xff);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void makeFile() {
        data = new ArrayList<>();
    }

    private void initvar() {
        double[] notesi = new double[]{16.35,17.32,18.35,19.45,20.60,21.83,23.12,24.50,25.96,27.50,29.14,30.87};
        for(int i=0;i<8;i++){
            for(int j=0;j<12;j++)notes[i*12+j] = notesi[j] * Math.pow(2,i);
        }
        notes[96]=0;
        for(int i=0;i<40;i++)wavhead[i]=0;
        wavhead[0] = 'R';wavhead[1] = 'I';wavhead[2] = 'F';wavhead[3] = 'F';
        wavhead[8] = 'W';wavhead[9] = 'A';wavhead[10] = 'V';wavhead[11] = 'E';
        wavhead[12] = 'f';wavhead[13] = 'm';wavhead[14] = 't';wavhead[15] = ' ';
        wavhead[16] = 0x10;wavhead[20] = 1;wavhead[22] = 1;wavhead[34]=0x10;
        wavhead[24] = (byte) 0x80;wavhead[25] = 0x3e;wavhead[29] = 0x7d;wavhead[32]=2;
        wavhead[36] = 'd';wavhead[37] = 'a';wavhead[38] = 't';wavhead[39] = 'a';
    }

    private void openTranscript(){
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);
        try{
            FileInputStream in = new FileInputStream(fc.getSelectedFile());
            while(in.available()>0){
                data.add(new ArrayList<>());
                int n = in.read();
                while(n-->0)
                data.get(data.size()-1).add(notes[in.read()]);
            }
        }catch(Exception e){e.printStackTrace();}
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
