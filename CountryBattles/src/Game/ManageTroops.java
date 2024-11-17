package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;





public class ManageTroops {

    Random rand = new Random();

    private String country1;
    private Troop[] troops1;
    private ArrayList<Integer> troops1AliveIdx;
    private ArrayList<Integer> troops1DeadIdx;

    private String country2;
    private Troop[] troops2;
    private ArrayList<Integer> troops2AliveIdx;
    private ArrayList<Integer> troops2DeadIdx;


    private Object[] data;

    private String winnerCode = null;



    public ManageTroops(String country1, String country2) {
        this.country1 = country1;
        this.country2 = country2;
        this.data = Main.data;



        troops1DeadIdx = new ArrayList<Integer>();
        troops2DeadIdx = new ArrayList<Integer>();
        CreateTroops();


    }

    public void CreateTroops() {

        ArrayList<String> countryCodes = (ArrayList<String>) data[1];
        ArrayList<String> colorList = (ArrayList<String>) Main.data[7];

        ArrayList<String> soldierCountStr = (ArrayList<String>) data[2];

        ArrayList<String> tankCountStr = (ArrayList<String>) data[5];

        ArrayList<Integer> soldierCount = new ArrayList<>();
        for (String countStr : soldierCountStr) {
            try {
                soldierCount.add(Integer.parseInt(countStr)); // Convert String to Integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid troop count value: " + countStr);
                soldierCount.add(0); // Default to 0 if there's an issue with the value
            }
        }
        ArrayList<Integer> tankCount = new ArrayList<>();
        for (String tankStr : tankCountStr) {
            try {
                tankCount.add(Integer.parseInt(tankStr));
            } catch (NumberFormatException e) {
                System.out.println("Invalid troop count value: " + tankStr);
                tankCount.add(0);
            }
        }

        int index1 = findCountryIndex(countryCodes, country1);
        int index2 = findCountryIndex(countryCodes, country2);


        int numSoldiers1 = soldierCount.get(index1)/2000;
        int numTanks1 = tankCount.get(index1)/100;
        int numTroops1 = numSoldiers1 + numTanks1;

        int numSoldiers2 = soldierCount.get(index2)/2000;
        int numTanks2 = tankCount.get(index2)/100;
        int numTroops2 = numSoldiers2 + numTanks2;

        troops1 = new Troop[numTroops1];
        troops1AliveIdx = new ArrayList<Integer>();
        for (int i = 0; i < numTroops1; i++) {
            troops1AliveIdx.add(i);
        }

        for (int i = 0; i < numSoldiers1; i++) {
            troops1[i] = new Soldier(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    "s", Color.decode(colorList.get(index1)), 0
            );
        }
        for (int i = 0; i < numTanks1; i++) {
            troops1[i+numSoldiers1] = new Tank(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    "t", Color.decode(colorList.get(index1)), 0
            );
        }




        troops2 = new Troop[numTroops2];
        troops2AliveIdx = new ArrayList<Integer>();
        for (int i = 0; i < numTroops2; i++) {
            troops2AliveIdx.add(i);
        }
        for (int i = 0; i < numSoldiers2; i++) {
            troops2[i] = new Soldier(
                    new Vector2D(rand.nextInt(600)+800, rand.nextInt(900)),
                    "s", Color.decode(colorList.get(index2)), Math.PI
            );
        }
        for (int i = 0; i < numTanks2; i++) {
            troops2[i+numSoldiers2] = new Tank(
                    new Vector2D(rand.nextInt(600)+800, rand.nextInt(900)),
                    "t", Color.decode(colorList.get(index2)), Math.PI
            );
        }

        for (int i = 0; i < troops1.length; i++) {
            troops1[i].setTarget(chooseTarget(troops2, troops2AliveIdx));
            if (troops1[i].type == "t"){
                troops1[i].initEnemyTroops(troops2);
            }

        }
        for (int i = 0; i < troops2.length; i++) {
            troops2[i].setTarget(chooseTarget(troops1, troops1AliveIdx));
            if (troops2[i].type == "t"){
                troops2[i].initEnemyTroops(troops1);
            }

        }
    }



    private Troop chooseTarget(Troop[] troops, ArrayList<Integer> aliveTroopsIdx) {

        return troops[aliveTroopsIdx.get(rand.nextInt(aliveTroopsIdx.size()))];

    }

    public void updateTroopStates() {
        // Clear the alive and dead indexes
        troops1AliveIdx.clear();
        troops2AliveIdx.clear();
        troops1DeadIdx.clear();
        troops2DeadIdx.clear();

        // Update alive and dead troops for country1
        for (int i = 0; i < troops1.length; i++) {
            if (troops1[i].isAlive()) {

                troops1AliveIdx.add(i);
            } else {
                troops1DeadIdx.add(i);
            }
        }

        // Update alive and dead troops for country2
        for (int i = 0; i < troops2.length; i++) {
            if (troops2[i].isAlive()) {
                troops2AliveIdx.add(i);
            } else {
                troops2DeadIdx.add(i);
            }
        }


    }

    public int updateTroops(Graphics g) {

        // Update troops1
        if (troops2AliveIdx.size() == 0){
            winnerCode = country1;
            return 1;
        }
        else if (troops1AliveIdx.size() == 0){
            winnerCode = country2;
            return -1;
        }

        for (Troop troop : troops1) {
            if (!troop.getTarget().isAlive()) {
                troop.setTarget(chooseTarget(troops2, troops2AliveIdx)); // Target from enemy

            }
            troop.update(g);
        }

        // Update troops2
        for (Troop troop : troops2) {
            if (!troop.getTarget().isAlive()) {
                troop.setTarget(chooseTarget(troops1, troops1AliveIdx)); // Target from enemy
            }
            troop.update(g);
        }
        updateTroopStates();
        return 0;
    }

    private int findCountryIndex(ArrayList<String> countryCodes, String targetCountry) {
        for (int i = 0; i < countryCodes.size(); i++) {
            if (countryCodes.get(i).equals(targetCountry)) {
                return i;
            }
        }
        return -1; // Return -1 if the country is not found
    }
    public String getWinnerCode(){
        return winnerCode;
    }
}






