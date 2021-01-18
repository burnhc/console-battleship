import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Battleship {
    public static final boolean DEBUG = false;

    private static void initGame(Ocean game) {
        Scanner input = new Scanner(System.in);

        while (!game.gameIsOver()) {
            System.out.print("\nMOVE: ");
            int ship = game.attack(input.nextLine());
            String indicator = "";

            if (ship > 0) {
                int shipHealth = game.getShipHealth(ship);
                if (shipHealth > 0) {
                    int shipTotalHealth = game.getTotalShipHealth(ship);
                    System.out.println("\n[COMMANDER]:" +
                            "\nHIT!" +
                            "\nYou've damaged Ship " + ship + "!" +
                            "\nShip " + ship + " has " + "[" + shipHealth + " of "
                            + shipTotalHealth + "]" + " health remaining.");
                } else {
                    System.out.println("\n[COMMANDER]:" +
                            "\nYou've sunk ship " + ship + "!");
                }
                indicator = "X";
                if (game.gameIsOver()) {
                    break;
                }
            } else if (ship == 0){
                indicator = "-";
                System.out.println("\n[COMMANDER]:" +
                        "\nDarn, you missed!");
            } else if (ship == Ocean.INVALID_MOVE){
                System.out.println("\n[COMMANDER]:" +
                        "\nThat's not a valid move.");
                continue;
            } else if (ship == Ocean.REPEATED_MOVE) {
                System.out.println("\n[COMMANDER]:" +
                        "\nYou've already searched there.");
                continue;
            }

            System.out.println(
                    "\nI've marked this spot with \""
                            + indicator + "\". Keep searching!");
            game.printMap();
        }
    }

    private static void introMsg() {
        System.out.println(
                "───────────────────────────────────────────────" +
                "──────────────────────────────────────────────\n" +
                "                        W E L C O M E   T O   B A T T L E S H I P !\n" +
                "────────────────────────────────────────────────" +
                "─────────────────────────────────────────────\n" +
                "                                 -- Built by Chandra --\n");
        delay();
    }

    private static Ocean chooseGameMode() {
        Scanner input = new Scanner(System.in);
        System.out.println("Select a game mode.");
        System.out.println("1 = EASY        [8  x  8] board\n" +
                           "2 = MEDIUM      [12 x 12] board\n" +
                           "3 = HARD        [18 x 18] board\n" +
                           "4 = RIDICULOUS  [26 x 26] board\n");
        System.out.print("MODE: ");
        String mode = input.nextLine();
        while (!mode.equals("1") && !mode.equals("2") &&
                        !mode.equals("3") && !mode.equals("4")) {
            System.out.println("Invalid mode.");
            System.out.print("MODE: ");
            mode = input.nextLine();
        }

        int boardSize = 0;
        switch (mode) {
            case "1":
                boardSize = 8;
                break;
            case "2":
                boardSize = 12;
                break;
            case "3":
                boardSize = 18;
                break;
            case "4":
                boardSize = 26;
                break;
        }

        return new Ocean(boardSize);
    }

    private static void instructionMsg(Ocean game) {
        System.out.println(
                "\n_________________________________________________" +
                "____________________________________________\n" +
                " I N S T R U C T I O N S\n" +
                "¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯" +
                "¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        delay();
        System.out.println(
                "\n[COMMANDER]:" +
                        "\nWe're on a mission to destroy enemy ships. " +
                        "We just received this report from our radar:\n");
        delay();
        radarReport(game);
        System.out.println(
                "\nThe health of the ship determines the" +
                " number of units it occupies on the ocean." +
                "\nA ship will be horizontally or vertically positioned " +
                "on the ocean with its units\nadjacent to one another, so" +
                " attack strategically!");
        delay();
        System.out.println(
                "\nYour objective is to destroy all the ships" +
                " by guessing their locations on the ocean." +
                "\nThe order in which you destroy them does not matter." +
                "\n\nHere's a map to track your progress:");
        delay();
    }

    private static void radarReport(Ocean game) {
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        int numShips = game.getNumShips();
        System.out.println("       " + dtf.format(now) +
                "\n       NUMBER OF ENEMY SHIPS: " + numShips);
        delay();
        for (int i = 1; i <= numShips; i++) {
            System.out.println("       SHIP #" + i + ": " +
                    game.getTotalShipHealth(i) + " HP");
            delay();
        }
    }

    private static void promptMsg() {
        System.out.println(
                "\nK E Y : " +
                "\n>  ? = uncharted ocean" +
                "\n>  - = miss" +
                "\n>  X = hit");
        delay();
        System.out.println(
                "\n_______________________________________________" +
                "______________________________________________" +
                "\n M I S S I O N   S T A R T !" +
                "\n------------------------------------------------" +
                "---------------------------------------------" +
                "\nGuess the ship locations." +
                "\n[ EXAMPLE MOVE: A0 ]");
    }

    private static void endMsg() {
        System.out.println(
                "\n──────────────────────────────────────────────────────" +
                "\n           You've sunk all the enemy ships." +
                "\n                   V I C T O R Y !" +
                "\n──────────────────────────────────────────────────────");
    }

    private static void delay() {
        if (!DEBUG) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        introMsg();
        Ocean game = chooseGameMode();
        instructionMsg(game);
        game.printMap();
        promptMsg();
        initGame(game);
        endMsg();
    }
}