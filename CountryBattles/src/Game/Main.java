package Game;

import javax.swing.*;
import java.io.IOException;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main {


    public static Object[] data;
    public static String soundPath = "CountryBattles/src/Data/punch2.wav";
    public static String musicPath = "CountryBattles/src/Data/music.wav";
    public static void main(String[] args) {


        Window window = new Window();

        playSound(musicPath, .05f);

        Timer gameLoopTimer = new Timer(1000 / 60, e -> {

           
            // Repaint the renderer
            window.getRenderer().repaint();

        });

        // Start the game loop
        gameLoopTimer.start();

    }


    public static void playSound(String filePath, float volume) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Set volume using FloatControl
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Volume is a logarithmic scale, where 0.0 is the original volume.
            // To adjust volume, we calculate gain in decibels.
            float gain = Math.min(Math.max(volume, 0.0f), 1.0f); // Clamp between 0.0 (mute) and 1.0 (full volume)
            float dB = (float) (20.0 * Math.log10(gain));
            gainControl.setValue(dB);

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}