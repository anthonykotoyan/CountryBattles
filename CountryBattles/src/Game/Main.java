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

        try {
            CSVFileReader reader = new CSVFileReader("CountryBattles/src/Data/country_data.csv");
            data = reader.getData();


        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create the game window
        Window window = new Window();

        playSound(musicPath);
        // Create the game loop timer (60 FPS)
        Timer gameLoopTimer = new Timer(1000 / 60, e -> {

            window.update();

            // Repaint the renderer
            window.getRenderer().repaint();

        });

        // Start the game loop
        gameLoopTimer.start();

    }
    public static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}