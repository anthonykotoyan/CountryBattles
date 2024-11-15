package Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CSVFileReader reader = new CSVFileReader("CountryBattles/src/Data/country_data.csv");
            Object[] data = reader.getData();

            // Print each column for demonstration
            for (int i = 0; i < data.length; i++) {
                System.out.println("Column " + i + ": " + data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}