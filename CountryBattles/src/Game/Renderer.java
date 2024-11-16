package Game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class Renderer extends JPanel {
    ManageTroops troops = new ManageTroops("ARM", "CUB");
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        troops.updateTroops(g);
    }

    public void update() {
    }
}
