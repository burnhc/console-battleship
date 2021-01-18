import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Ocean {
    public static final int
            MIN_SHIPS = 3,
            INVALID_MOVE = -1,
            REPEATED_MOVE = -2;
    private static final String COLUMNS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int boardSize, numShips;
    private final Set<Move> movesMade;

    // integer at each element is the ship
    private final int[][] ocean;
    // each index corresponds to a ship
    private final int[] shipHealth, shipTotalHealth;
    // for console print out
    private final String[][] map;
    private int totalHealth;

    public Ocean(int boardSize) {
        this.boardSize = boardSize;
        this.map = new String[this.boardSize][this.boardSize];
        this.ocean = new int[this.boardSize][this.boardSize];
        this.movesMade = new HashSet<>();

        Random rand = new Random();
        this.numShips = rand.nextInt(
                this.boardSize / 3) + MIN_SHIPS;
        this.shipHealth = new int[this.numShips + 1];
        this.shipTotalHealth = new int[this.numShips + 1];

        // initialize map with all "?"
        for (int r = 0; r < this.boardSize; r++) {
            for (int c = 0; c < this.boardSize; c++) {
                this.map[r][c] = "?";
            }
        }

        this.setShips();
    }

    private void setShips() {
        ShipGenerator generator =
                new ShipGenerator(this.boardSize);

        for (int i = 1; i <= this.numShips; i++) {
            Set<ShipGenerator.Coord> coords =
                    generator.generateShip();
            for (ShipGenerator.Coord coord : coords) {
                ocean[coord.getR()][coord.getC()] = i;
            }

            int health = generator.getHealth();
            this.shipHealth[i] = health;
            this.shipTotalHealth[i] = health;
            this.totalHealth += health;
        }

        if (Battleship.DEBUG) {
            DEBUG();
        }
    }

    // returns the ship that was hit
    public int attack(String move) {
        try {
            Move attack = new Move(move, this.boardSize);
            if (!movesMade.contains(attack)) {
                movesMade.add(attack);
                int ship = this.ocean[attack.r][attack.c];

                if (ship > 0) {
                    this.shipHealth[ship]--;
                    this.totalHealth--;
                    this.map[attack.r][attack.c] = "X";
                } else {
                    this.map[attack.r][attack.c] = "-";
                }
                return ship;
            } else {
                return REPEATED_MOVE;
            }
        } catch (Exception e) {
            return INVALID_MOVE;
        }
    }

    public int getShipHealth(int ship) {
        return this.shipHealth[ship];
    }

    public int getNumShips() {
        return this.numShips;
    }

    public int getTotalShipHealth(int ship) {
        return this.shipTotalHealth[ship];
    }

    public boolean gameIsOver() {
        return this.totalHealth == 0;
    }

    public void printMap() {
        System.out.println(
                "__________________________________________________");
        System.out.println(generateColLabels(false));

        StringBuilder res = new StringBuilder();
        for (int r = 0; r < this.boardSize; r++) {
            if (this.boardSize > 10 &&
                    ((this.boardSize - 1) - r) < 10) {
                res.append(" ");
            }

            res.append(((this.boardSize - 1) - r)).append("  |  ");
            for (int c = 0; c < this.boardSize; c++) {
                res.append(" ").append(this.map[r][c]).append("  ");
            }
            res.append(" |  ").append((this.boardSize) - 1 - r).append("\n");
        }
        System.out.print(res);
        System.out.println(generateColLabels(true));
    }

    private String generateColLabels(boolean lineBelow) {
        StringBuilder res1 = new StringBuilder("   ");
        StringBuilder res2 = new StringBuilder("\n       ");
        if (this.boardSize > 9) {
            res1.append(" ");
            res2.append(" ");
        }

        res1.append("-----");
        for (int i = 0; i < this.boardSize; i++) {
            String next = Character.toString(COLUMNS.charAt(i));
            res1.append("----");
            res2.append(next).append("   ");
        }

        if (lineBelow) {
            return res1.toString() + res2;
        } else {
            return res2.toString() + "\n" + res1;
        }
    }

    private void DEBUG() {
        System.err.println("SHIP HEALTH: " +
                Arrays.toString(this.shipTotalHealth));
        for (int r = 0; r < this.boardSize; r++) {
            for (int c = 0; c < this.boardSize; c++) {
                System.err.print(" " + this.ocean[r][c] + "  ");
            }
            System.err.println();
        }
    }
}
