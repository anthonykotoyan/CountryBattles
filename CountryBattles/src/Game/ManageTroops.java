package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ManageTroops {

    Random rand = new Random();

    private Troop[] troops1;
    private ArrayList<Integer> troops1AliveIdx;
    private ArrayList<Integer> troops1DeadIdx;

    private Troop[] troops2;
    private ArrayList<Integer> troops2AliveIdx;
    private ArrayList<Integer> troops2DeadIdx;

    private int[] eachTroop1;
    private int numTroops1;

    private int[] eachTroop2;
    private int numTroops2;

    private String winnerCode = null;

    public boolean fighting = false;

    public int gameState = 0;

    public ManageTroops(int[] eachTroop2) {
        this.eachTroop1 = new int[eachTroop2.length];
        this.eachTroop2 = eachTroop2;

        numTroops1 = Arrays.stream(eachTroop1).sum();
        numTroops2 = Arrays.stream(eachTroop2).sum();
        createTroops();
    }

    public void addTroops1(int troopIdx) {
        this.eachTroop1[troopIdx] += 1;
        numTroops1 += 1;
        updateAmountTroops(troopIdx);
    }

    public void updateAmountTroops(int troopIdx) {
        Troop newTroop;
        Color troop1Color = new Color(0, 0, 255);
        troops1AliveIdx = new ArrayList<>();
        for (int i = 0; i < numTroops1; i++) {
            troops1AliveIdx.add(i);
        }
        if (troopIdx == 0) {
            newTroop = new Soldier(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    "s", troop1Color, 0
            );
        } else if (troopIdx == 1) {
            newTroop = new Tank(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    "t", troop1Color, 0);
        } else {
            newTroop = new Helicopter(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    "h", troop1Color, 0);
        }

        Troop[] updatedTroops1 = new Troop[troops1.length + 1];
        System.arraycopy(troops1, 0, updatedTroops1, 0, troops1.length);
        updatedTroops1[updatedTroops1.length - 1] = newTroop;
        troops1 = updatedTroops1; // Reassign to the original array variable

        setTargetsForTroops();
    }

    public void createTroops() {
        troops1 = new Troop[numTroops1];
        troops1AliveIdx = new ArrayList<>();
        for (int i = 0; i < numTroops1; i++) {
            troops1AliveIdx.add(i);
        }
        Color troop1Color = new Color(0, 0, 255);
        for (int i = 0; i < eachTroop1[0]; i++) {
            troops1[i] = new Soldier(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    "s", troop1Color, 0
            );
        }
        for (int i = 0; i < eachTroop1[1]; i++) {
            troops1[i + eachTroop1[0]] = new Tank(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    "t", troop1Color, 0
            );
        }
        for (int i = 0; i < eachTroop1[2]; i++) {
            troops1[i + eachTroop1[0] + eachTroop1[1]] = new Helicopter(
                    new Vector2D(rand.nextInt(600), rand.nextInt(900)),
                    "h", troop1Color, 0
            );
        }

        troops2 = new Troop[numTroops2];
        troops2AliveIdx = new ArrayList<>();
        for (int i = 0; i < numTroops2; i++) {
            troops2AliveIdx.add(i);
        }
        Color troop2Color = new Color(255, 0, 0);
        for (int i = 0; i < eachTroop2[0]; i++) {
            troops2[i] = new Soldier(
                    new Vector2D(rand.nextInt(600) + 800, rand.nextInt(900)),
                    "s", troop2Color, Math.PI
            );
        }
        for (int i = 0; i < eachTroop2[1]; i++) {
            troops2[i + eachTroop2[0]] = new Tank(
                    new Vector2D(rand.nextInt(600) + 800, rand.nextInt(900)),
                    "t", troop2Color, Math.PI
            );
        }
        for (int i = 0; i < eachTroop2[2]; i++) {
            troops2[i + eachTroop2[0] + eachTroop2[1]] = new Helicopter(
                    new Vector2D(rand.nextInt(600) + 800, rand.nextInt(900)),
                    "h", troop2Color, Math.PI
            );
        }

        setTargetsForTroops();
    }

    private void setTargetsForTroops() {
        for (int i = 0; i < troops1.length; i++) {
            troops1[i].setTarget(chooseTarget(troops2, troops2AliveIdx));
            if (troops1[i].type.equals("t")) {
                troops1[i].initEnemyTroops(troops2);
            }
        }
        for (int i = 0; i < troops2.length; i++) {
            troops2[i].setTarget(chooseTarget(troops1, troops1AliveIdx));
            if (troops2[i].type.equals("t")) {
                troops2[i].initEnemyTroops(troops1);
            }
        }
    }

    private Troop chooseTarget(Troop[] troops, ArrayList<Integer> aliveTroopsIdx) {
        if (aliveTroopsIdx.size() <= 0 || troops.length <= 0) {
            return null;
        }
        return troops[aliveTroopsIdx.get(rand.nextInt(aliveTroopsIdx.size()))];
    }

    public void updateTroopStates() {
        troops1AliveIdx.clear();
        troops2AliveIdx.clear();

        // Update alive and dead troops for country1
        for (int i = 0; i < troops1.length; i++) {
            if (troops1[i].isAlive()) {
                troops1AliveIdx.add(i);
            }
        }

        // Update alive and dead troops for country2
        for (int i = 0; i < troops2.length; i++) {
            if (troops2[i].isAlive()) {
                troops2AliveIdx.add(i);
            }
        }
    }

    public void updateTroops(Graphics g) {
        if (fighting) {
            if (troops2AliveIdx.isEmpty()) {
                fighting = false;
                gameState = 1;
            } else if (troops1AliveIdx.isEmpty()) {
                fighting = false;
                gameState = -1;
            }

            for (Troop troop : troops1) {
                if (troop != null){

                    if (!troop.getTarget().isAlive()) {
                        troop.setTarget(chooseTarget(troops2, troops2AliveIdx));
                    }
                    if (troop.getTarget() != null) {
                        troop.update(g);
                    }else{
                        troop.draw(g);
                    }
                }
            }

            for (Troop troop : troops2) {
                if (troop != null){

                        if (!troop.getTarget().isAlive()) {
                            troop.setTarget(chooseTarget(troops1, troops1AliveIdx));
                        }
                    if (troop.getTarget() != null) {
                        troop.update(g);
                    }else{
                        troop.draw(g);
                    }
                }
            }

            updateTroopStates();
        } else {
            drawStaticTroops(g);
        }
    }

    public void drawStaticTroops(Graphics g) {
        for (Troop troop : troops1) {
            troop.draw(g);
        }

        for (Troop troop : troops2) {
            troop.draw(g);
        }
    }

    public String getWinnerCode() {
        return winnerCode;
    }

    public void reset() {
        troops1 = null;
        this.eachTroop1 = new int[eachTroop2.length];
        numTroops1 = 0;
        troops2 = null;
        troops1AliveIdx = new ArrayList<>();
        troops2AliveIdx = new ArrayList<>();
        winnerCode = null;
        fighting = false;
        gameState = 0;

        // Reinitialize troops and states
        createTroops();
    }
}
