package Game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class Renderer extends JPanel {
    Random rand = new Random();
    Troop troop1 = new Troop(new Vector2D(rand.nextInt(800), rand.nextInt(600)), "A");
    Troop troop2 = new Troop(new Vector2D(rand.nextInt(800), rand.nextInt(600)), "B");
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        troop1.update(g);
        troop2.update(g);
        troop1.target(troop2);
        troop2.target(troop1);
    }

    public void update() {
    }
}
