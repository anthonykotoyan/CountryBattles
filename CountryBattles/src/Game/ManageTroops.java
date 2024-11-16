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

    private Object troopsData;
    private Object countryCodeData;

    private String winnerCode = null;



    public ManageTroops(String country1, String country2) {
        this.country1 = country1;
        this.country2 = country2;
        this.troopsData = Main.data[2];
        this.countryCodeData = Main.data[1];


        troops1DeadIdx = new ArrayList<Integer>();
        troops2DeadIdx = new ArrayList<Integer>();
        CreateTroops();


    }

    public void CreateTroops() {

        // Assuming countryCodeData is an ArrayList of Strings and troopsData is an ArrayList of Strings
        ArrayList<String> countryCodes = (ArrayList<String>) countryCodeData;
        ArrayList<String> troopCountStr = (ArrayList<String>) troopsData;

        // Convert the ArrayList of Strings (troopCountStr) to an ArrayList of Integers
        ArrayList<Integer> troopCount = new ArrayList<>();
        for (String countStr : troopCountStr) {
            try {
                troopCount.add(Integer.parseInt(countStr)); // Convert String to Integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid troop count value: " + countStr);
                troopCount.add(0); // Default to 0 if there's an issue with the value
            }
        }

        int index1 = findCountryIndex(countryCodes, country1);
        int index2 = findCountryIndex(countryCodes, country2);


        int numTroops1 = troopCount.get(index1)/500;
        int numTroops2 = troopCount.get(index2)/500;
        // Initialize troops for country1
        troops1 = new Troop[numTroops1];
        troops1AliveIdx = new ArrayList<Integer>();
        for (int i = 0; i < numTroops1; i++) {
            troops1AliveIdx.add(i);
        }

        for (int i = 0; i < troops1.length; i++) {
            troops1[i] = new Troop(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    country1
            );


        }



        // Initialize troops for country2
        troops2 = new Troop[numTroops2];
        troops2AliveIdx = new ArrayList<Integer>();
        for (int i = 0; i < numTroops2; i++) {
            troops2AliveIdx.add(i);
        }
        for (int i = 0; i < troops2.length; i++) {
            troops2[i] = new Troop(
                    new Vector2D(rand.nextInt(600) + 800, rand.nextInt(900)),
                    country2
            );

        }

        for (int i = 0; i < troops1.length; i++) {
            troops1[i].setTarget(chooseTarget(troops2, troops2AliveIdx));

        }
        for (int i = 0; i < troops2.length; i++) {
            troops2[i].setTarget(chooseTarget(troops1, troops1AliveIdx));

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






