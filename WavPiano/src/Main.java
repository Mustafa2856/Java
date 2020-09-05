import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class Main extends JFrame {
    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        JFrame win = new JFrame("Wav_Piano");
        win.setSize(1000,600);
        win.setVisible(true);
        win.setLayout(null);
        Player player = new Player();
        win.add(player);
        player.setBounds(0,0,400,200);
        win.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
