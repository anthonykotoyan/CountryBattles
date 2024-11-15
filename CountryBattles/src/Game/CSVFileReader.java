package Game;

import java.io.*;
import java.util.*;

public class CSVFileReader {
    private String filePath;

    public CSVFileReader(String filePath) {
        this.filePath = filePath;
    }

    public Object[] getData() throws IOException {
        List<List<String>> dataColumns = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Initialize columns on the first line (header)
                if (isFirstLine) {
                    for (int i = 0; i < values.length; i++) {
                        dataColumns.add(new ArrayList<>());
                    }
                    isFirstLine = false;
                }

                // Add values to the respective column
                for (int i = 0; i < values.length; i++) {
                    dataColumns.get(i).add(values[i]);
                }
            }
        }

        // Convert List<List<String>> to Object[]
        return dataColumns.toArray();
    }


}
