package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
public class LevelManager {
    private int currLevel = 0;
    private Level[] levels;
    private boolean transitioning = false;

    public LevelManager(Level[] levels) {
        this.levels = levels;
    }

    public void setNewLevel(Renderer renderer) {
        renderer.removeAll(); // Clear previous level components
        levels[currLevel].setupUI();
        renderer.add(levels[currLevel].getButtonPanel(), BorderLayout.WEST);
        renderer.revalidate();
        renderer.repaint();
    }

    public void playCurrLevel(Graphics g, Renderer renderer) {
        // Game rendering logic
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));


        levels[currLevel].updateLevel(g);
        int[] troops = levels[currLevel].getTroops();
        for (int i = 0; i < troops.length; i++) {
            g.drawString("Troop " + (i + 1) + ": " + troops[i], 10, 800 + i * 20);
        }
        // Check for game end state and handle transitions
        if (levels[currLevel].troopManager.gameState != 0) {


            // Ensure the transition only happens once
            if (!transitioning) {
                transitioning = true;
                levels[currLevel].displayWinner(g);
                Timer timer = new Timer(2000, (ActionEvent e) -> {

                    if (levels[currLevel].troopManager.gameState == 1) {
                        currLevel++;

                    } else {
                        levels[currLevel].livesLeft -= 1;
                        if (levels[currLevel].livesLeft == 0){
                            currLevel = 0;
                        }
                    }
                    if (currLevel < levels.length) {
                        setNewLevel(renderer); // Load the next level
                    } else {
                        System.exit(0);
                        JOptionPane.showMessageDialog(renderer, "Game Over! You've completed all levels.");
                    }
                    levels[currLevel].reset();
                    transitioning = false; // Allow transitions again
                    ((Timer) e.getSource()).stop(); // Stop the timer after it runs
                });
                timer.start();
            }
        }
    }
}
