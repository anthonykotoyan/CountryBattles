package Game;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static Object[] data;
    public static void main(String[] args) {
        try {
            CSVFileReader reader = new CSVFileReader("CountryBattles/src/Data/country_data.csv");
            data = reader.getData();

            // Print each column for demonstration
            for (int i = 0; i < data.length; i++) {
                System.out.println("Column " + i + ": " + data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create the game window
        Window window = new Window();

        // Create the game loop timer (60 FPS)
        Timer gameLoopTimer = new Timer(1000 / 60, e -> {

            window.update();

            // Repaint the renderer
            window.getRenderer().repaint();

        });

        // Start the game loop
        gameLoopTimer.start();

    }
}