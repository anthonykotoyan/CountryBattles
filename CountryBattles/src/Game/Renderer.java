package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Renderer extends JPanel {
    private ManageTroops troops;
    private String country1;
    private String country2;
    private String code1 = "NGR";
    private String code2 = "USA";
    public Renderer() {
        setBackground(new Color(0, 100, 0)); // Light blue background
        setDoubleBuffered(true); // Enable double buffering
        troops = new ManageTroops(code1, code2);

        ArrayList<String> countryCodes = (ArrayList<String>) Main.data[1];
        int index1 = countryCodes.indexOf(code1);
        ArrayList<String> nameList= (ArrayList<String>) Main.data[0];
        this.country1 = nameList.get(index1);
        int index2 = countryCodes.indexOf(code2);
        this.country2 = nameList.get(index2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Enable anti-aliasing for smoother rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set font and color for country names (black color)
        g2d.setFont(new Font("Arial", Font.BOLD, 50)); // You can adjust the font size here
        g2d.setColor(Color.RED); // Set text color to black



        if (troops.updateTroops(g) != 0) {
            ArrayList<String> countryCodes = (ArrayList<String>) Main.data[1];
            ArrayList<String> nameList = (ArrayList<String>) Main.data[0];
            int index = countryCodes.indexOf(troops.getWinnerCode());

            String name = nameList.get(index);

            ArrayList<String> colorList = (ArrayList<String>) Main.data[7];
            String colorHex = colorList.get(index);

            // Parse the hex color to Color object
            Color colors = Color.decode(colorHex);
            g2d.setColor(colors); // Set the text color
            String winText = "Winner: " + name;
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(winText);
            int textHeight = fm.getHeight();

            // Calculate the position to center the text
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - textHeight) / 2 + fm.getAscent();

            // Draw the text
            g2d.drawString(winText, x, y);
        }
        // Define margin for positioning the names
        int margin = 10; // Margin from the top-left and top-right corners

        // Draw country 1 name in the top-left corner
        g2d.drawString(this.country1, margin, margin + 50); // Adjust Y-coordinate for better placement

        // Calculate and draw country 2 name in the top-right corner
        int country2X = getWidth() - margin - g2d.getFontMetrics().stringWidth(this.country2);
        g2d.drawString(this.country2, country2X, margin + 50); // Adjust Y-coordinate for better placement
    }

    public void update() {
    }
}
