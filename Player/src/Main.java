import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;

public class Main {
    static String URI = "";
    static int pausedOnFrame = 0;
    static boolean b = false;
    static Thread tmp;
    static String copy = "";

    public static void main(String[] args) {
        System.out.println("Hello World!");
        JFrame frame = new JFrame();
        JSlider slider = new JSlider();
        slider.setMinimum(0);
        slider.addChangeListener(e -> {
            int len = GetFramesLenght();
            slider.setMaximum(len);
            PlayHandler(slider.getValue());
        });
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(6, 7));
        JButton play = new JButton("Play/Pause");
        play.setSize(100, 100);
        JButton upload = new JButton("Choose file...");
        upload.setSize(100, 100);
        upload.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Upload();
            }
        });
        play.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (b) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ignored) {

                    }
                    b = false;
                } else
                    tmp.notify();

            }
        });
        frame.add(play);
        frame.add(upload);
        frame.add(slider);
        frame.pack();
    }

    private static int GetFramesLenght() {
        try {
            File file = new File(URI);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            return (int) audioInputStream.getFrameLength();
        } catch (Exception e) {
            return 0;
        }
    }

    private static void Upload() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        URI = dialog.getDirectory() + dialog.getFile();
        PlayHandler(0);
    }

    private static void PlayHandler(int value) {
        if (!URI.equals("")) {
            Thread thread = new Thread(() -> Play(value));
            thread.start();
            if (!b) {
                tmp = thread;
                copy = URI;
            } else {
                tmp.stop();
                if (copy.equals(URI)) {
                    try {
                        thread.stop();
                    } catch (Exception ignored) {
                    }
                }
                tmp = thread;
                copy = URI;
            }
            b = true;
        }
    }

    private static void Play(int value) {
        try {
            FileInputStream fis = new FileInputStream(URI);
            AdvancedPlayer player = new AdvancedPlayer(fis);
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent event) {
                    pausedOnFrame = event.getFrame();
                }
            });
            player.play();
        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }
}
