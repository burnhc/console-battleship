import java.util.*;

public class Battleship {

    public static void main(String[] args) {
        int[][] ocean = new int[10][10];        //setting up the ocean map (manually placing ships)
        ocean[2][0] = 1;                        //ship 1 takes up 2 units
        ocean[3][0] = 1;
        ocean[1][4] = 2;                        //ship 2 takes up 3 units
        ocean[1][5] = 2;
        ocean[1][6] = 2;
        ocean[4][8] = 3;                        //ship 3 takes up 4 units
        ocean[5][8] = 3;
        ocean[6][8] = 3;
        ocean[7][8] = 3;

        header();
        System.out.println();
        System.out.println("_____________________________________________________________________________________________");        //instructions on how to play
        System.out.println(" I N S T R U C T I O N S");
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        System.out.println("[COMMANDER]:");
        System.out.println("Currently, there are three enemy ships at sea: Ship 1, Ship 2, and Ship 3.");
        System.out.println("According to our intel, Ship 1 has 2 lives, Ship 2 has 3 lives, and Ship 3 has 4 lives.");
        System.out.println("The lives of the ship determines the number of units it occupies on the ocean.");
        System.out.println();
        System.out.println("Your objective is to destroy all three ships by guessing their locations on the ocean!");
        System.out.println("The order in which you destroy them does not matter.");
        System.out.println();
        System.out.println("Here's a map to track your mission progress:");
        System.out.println("__________________________________________________");
        System.out.println("  0    1    2    3    4    5    6    7    8    9");
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

        String[][] mapArray = new String[10][10];           //creates the map that prints out and shows the user their progress
        int row, column;
        for (row = 0; row < 10; row++) {
            for (column = 0; column < 10; column++) {
                mapArray[row][column] = "?";                //right now, the map is filled with question marks because the user has not explored them yet
                System.out.print("| " + mapArray[row][column] + " |");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("K E Y : ");
        System.out.println(">  ? = uncharted territory");
        System.out.println(">  - = empty ocean (you'll see as the mission progresses)");
        System.out.println(">  X = you've hit a ship here");
        System.out.println();
        System.out.println("_____________________________________________________________________________________________");
        System.out.println(" M I S S I O N   S T A R T !");
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        System.out.println("Now, guess the ship locations between (0,0) and (9,9).");
        System.out.println("[ INPUT FORMAT: (row,column) → row(space)column ]");
        System.out.println("[ TIP: The map begins at (0,0), not (1,1). ]");

        int[] health = {0, 2, 3, 4};        //keeps track of each ship's health
        int[] fullHealth = {0, 2, 3, 4};    //stores the original health of the ship
        int totalHealth = 9;                //keeps track of the total lives left in the game to know when all the ships have been destroyed

        Scanner input = new Scanner(System.in);
        while (totalHealth > 0) {
            String userAttack = input.nextLine();           //user inputs the index that they think the ship is on in the form of "0 0"
            Scanner coordinates = new Scanner(userAttack);
            int xAttack = coordinates.nextInt();            //takes the first int in the user input and stops at whitespace
            int yAttack = coordinates.nextInt();            //from the whitespace, it takes the next int of the user input
            int shipNum = ocean[xAttack][yAttack];

            if (ocean[xAttack][yAttack] > 0) {              //when the user guesses an index that contains a ship...
                if (health[shipNum] >= 1) {                 //if that ship still has lives...
                    System.out.println();
                    System.out.println("[COMMANDER]:");
                    System.out.println("HIT!");             //the user has hit the ship!
                    System.out.println("You've damaged Ship " + shipNum + "!");
                } else {                                    //if the ship does not have any lives remaining...
                    System.out.println("You've sunk ship " + shipNum + "!");    //the ship has sunk!
                }
                health[shipNum]--;          //the health of the ship decreases by one
                totalHealth--;              //the total health of the game decreases by one as well
                System.out.println();
                System.out.println("You mark this location on your map with an 'X':");
                System.out.println("__________________________________________________");
                System.out.println("  0    1    2    3    4    5    6    7    8    9");
                System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

                for (row = 0; row < 10; row++) {
                    for (column = 0; column < 10; column++) {
                        mapArray[xAttack][yAttack] = "X";                       //wherever they hit the ship, an "X" will replace the "?" on the map
                        System.out.print("| " + mapArray[row][column] + " |");  //updated map prints out for the user to see their progress
                    }
                    System.out.println();
                }

                System.out.println();
                if (health[shipNum] > 0) {
                    System.out.println("[COMMANDER]:");
                    System.out.println("Ship " + shipNum + " has [" + health[shipNum] + " out of " + fullHealth[shipNum] + "] lives remaining.");   //prints out the remaining health of the ship
                    System.out.println("Keep going!");
                } else if (health[shipNum] == 0) {                                          //if the ship has sunk...
                    if (totalHealth > 0) {                                                  //but there are still other ships remaining...
                        System.out.println("[COMMANDER]:");
                        System.out.println("Ship " + shipNum + " has been destroyed!");     //informs user that the ship has been destroyed!
                        System.out.println("Good work! Now, enter another guess; let's get the other ship!");
                    } else {                                                                //once all the ships have sunk...
                        endMsg();                                                           //game terminates: VICTORY!!
                    }
                }
            } else {                                                                        //if the user guesses an index that does NOT contain a ship...
                System.out.println();
                System.out.println("[COMMANDER]:");
                System.out.println("Darn it; looks like you've missed!");                   //informs user that their guess sucked
                System.out.println();
                System.out.println("You mark this empty spot with '-'. Try again!");
                System.out.println("__________________________________________________");
                System.out.println("  0    1    2    3    4    5    6    7    8    9");
                System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

                for (row = 0; row < 10; row++) {
                    for (column = 0; column < 10; column++) {
                        mapArray[xAttack][yAttack] = "-";                       //wherever they missed, a "-" will replace the "?" on the map
                        System.out.print("| " + mapArray[row][column] + " |");  //again, updated map prints out for the user to see their progress
                    }
                    System.out.println();
                }
            }
        }
    }
    public static void header () {  //header; prints at the top of the console (I made methods for these two things just because they seemed chunky idk)
        System.out.println("──────────────────────────────────────────────────────");
        System.out.println("                        W E L C O M E   T O   B A T T L E S H I P !");
        System.out.println("──────────────────────────────────────────────────────");
        System.out.println("                                 -- Built by Chandra --                                 ");
        System.out.println("                     -- MSUB 2016 Computer Science Final Project --                                 ");
    }
    public static void endMsg () {  //ending message; prints when the user destroys all the ships
        System.out.println();
        System.out.println("──────────────────────────────────────────────────────");
        System.out.println("                               You've sunk all the ships!");
        System.out.println("                                     V I C T O R Y !!");
        System.out.println("──────────────────────────────────────────────────────");
        System.out.println();
    }
}