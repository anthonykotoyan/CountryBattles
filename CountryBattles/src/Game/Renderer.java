package Game;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    private LevelManager levelManager;
    private Level[] levels = {
            new Level(new int[]{2, 0, 0, 0}, 30),
            new Level(new int[]{4, 1, 0, 0}, 50),
            new Level(new int[]{3, 1, 1, 0}, 70),
            new Level(new int[]{3, 1, 1, 1}, 120),
            new Level(new int[]{10, 5, 0, 0}, 300),
            new Level(new int[]{15, 10, 5, 2}, 400),
            new Level(new int[]{0, 20, 0, 0}, 230),


    };
    public Renderer() {


        setLayout(new BorderLayout()); // Use BorderLayout to organize components
        setBackground(new Color(0, 100, 0)); // Background color
        levelManager = new LevelManager(levels);
        levelManager.setNewLevel(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        levelManager.playCurrLevel(g,this);
    }


}
