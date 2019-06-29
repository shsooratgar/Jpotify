import com.mpatric.mp3agic.Mp3File;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    static String URI = "";
    static int pausedOnFrame = 0;
    static boolean b = false;
    static Thread tmp;
    static String copy = "";
    static JFrame frame;
    static JLabel label;
    static Thread thread;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        frame = new JFrame();
        JSlider slider = new JSlider();
        slider.setMinimum(0);
        slider.addChangeListener(e -> {
            int len = GetFramesLenght();
            slider.setMaximum(len);
            PlayHandler(slider.getValue());
        });
        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setOrientation(1);
        volumeSlider.addChangeListener(e -> {
            float v = (float) volumeSlider.getValue() / 100;
            ChangeVolume(v);
        });
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setLayout(new GridLayout());
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
                        thread.stop();
                    } catch (Exception ignored) {

                    }
                    b = false;
                } else
                    PlayHandler(0);

            }
        });
        label = new JLabel();
        frame.add(label);
        frame.add(play);
        frame.add(upload);
        frame.add(slider);
        frame.add(volumeSlider);
        frame.pack();
    }

    private static int GetFramesLenght() {
        try {
            Mp3File mp3File = new Mp3File(new File(URI));
            return mp3File.getFrameCount();
        } catch (Exception e) {
            return 0;
        }
    }

    private static void ImageIcon() {
        Icon icon = new ImageIcon(GetImage(300, 300));
        label.setIcon(icon);
        frame.pack();
    }

    private static BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    private static BufferedImage GetImage(int width, int height) {
        try {
            Mp3File mp3File = new Mp3File(new File(URI));
            byte[] bytes = mp3File.getId3v2Tag().getAlbumImage();
            return getScaledImage(createImageFromBytes(bytes), width, height);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void Upload() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        URI = dialog.getDirectory() + dialog.getFile();
        PlayHandler(0);
        ImageIcon();
    }

    private static void PlayHandler(int value) {
        if (!URI.equals("")) {
            thread = new Thread(() -> Play(value));
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
            if (value == 0) {
                player.play();

            } else
                player.play(value, GetFramesLenght());
        } catch (Exception exc) {
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }

    private static void ChangeVolume(float v) {
        Port.Info source = Port.Info.SPEAKER;
        if (AudioSystem.isLineSupported(source)) {
            try {
                Port outline = (Port) AudioSystem.getLine(source);
                outline.open();
                FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
                System.out.println("volume: " + volumeControl.getValue());
                volumeControl.setValue(v);
                System.out.println("new volume: " + volumeControl.getValue());
            } catch (LineUnavailableException ex) {
                System.err.println("source not supported");
                ex.printStackTrace();
            }
        }
    }
}
