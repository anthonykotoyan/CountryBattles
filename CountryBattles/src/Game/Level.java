package Game;

import javax.swing.*;
import java.awt.*;

public class Level {
    private int[] enemyTroops;
    private int[] troops;
    private double money;
    private double finalMoney;

    private JPanel buttonPanel;
    private JLabel costLabel;
    public ManageTroops troopManager;

    public Level(int[] enemyTroops, double money) {
        troopManager = new ManageTroops(enemyTroops);
        this.enemyTroops = enemyTroops;
        this.money = money;
        this.finalMoney = money;
        this.troops = new int[enemyTroops.length];

        this.buttonPanel = new JPanel(); // Initialize the button panel
        this.buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // Align to left and add padding
        this.buttonPanel.setBackground(new Color(0, 0, 0, 0)); // Make panel background transparent
    }

    public void setupUI() {
        buttonPanel.removeAll();

        // Add the cost label
        costLabel = new JLabel("Money: $" + money, SwingConstants.CENTER);
        costLabel.setFont(new Font("Arial", Font.BOLD, 20));
        costLabel.setForeground(Color.WHITE); // Make text visible if on dark background
        buttonPanel.add(costLabel);

        // Create troop buttons
        for (int i = 0; i < enemyTroops.length; i++) {
            int index = i;
            JButton troopButton = new JButton("Troop " + (i + 1) + " ($" + (index + 1) * 10 + ")");
            troopButton.setFont(new Font("Arial", Font.PLAIN, 18));

            // Set a smaller preferred size for the buttons
            troopButton.setPreferredSize(new Dimension(150, 40));  // Smaller buttons

            troopButton.addActionListener(e -> {
                double cost = (index + 1) * 10;
                if (money >= cost) {
                    troops[index]++;
                    troopManager.addTroops1(index);
                    money -= cost;
                    updateCostLabel();
                } else {
                    JOptionPane.showMessageDialog(buttonPanel, "Not enough money!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            buttonPanel.add(troopButton);
        }

        // Fight button
        JButton fightButton = new JButton("Fight");
        fightButton.setFont(new Font("Arial", Font.BOLD, 20));
        fightButton.setBackground(Color.RED);
        fightButton.setForeground(Color.WHITE);

        // Set a preferred size for the fight button
        fightButton.setPreferredSize(new Dimension(150, 50)); // Make it smaller

        fightButton.addActionListener(e -> troopManager.fighting = true);
        buttonPanel.add(fightButton);

        // Revalidate and repaint after UI changes
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public void updateLevel(Graphics g){
        troopManager.updateTroops(g);
    }

    private void updateCostLabel() {
        costLabel.setText("Money: $" + money);
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public int[] getTroops() {
        return troops;
    }



    public void displayWinner(Graphics g){
        if (troopManager.gameState == 1){

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));

            g.drawString("You win", 700  , 450 );


        }
        if (troopManager.gameState == -1){

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 30));

            g.drawString("You lose", 700  , 450 );


        }
    }
    public void reset(){
        this.money = finalMoney;
        updateCostLabel();
        this.troopManager.reset();

    }
}
