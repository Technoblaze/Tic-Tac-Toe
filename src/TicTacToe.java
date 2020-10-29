import java.util.Random;
import java.util.Scanner;

/**
 * @author Fromssa Olana & Terrence Daniels
 *
 *
 * Tic Tac Toe.
 * This is a game for two people. Each person will be represented with  X’s and O’s.
 * You have a 3 by 3 grid in which two players place x or o. Three characters in a row wins!
 * Rules of the game.
 * Each person will take a turn to mark any of a spot that is open on the bord.
 * To win the game, a player needs to occupy 3 consecutive positions in any direction on the board.
 */
public class TicTacToe {
    // Initialize Scanner object
    static Scanner scanner = new Scanner(System.in);

    //Create a 3x3 array that represents the tic tac toe board
    static char[][] board = new char[3][3];


    // Player list variable
    static String guestPlayer;
    static String jacki = "Jacki";

    //Create a player1 boolean that is true if it is player 1's turn and false if it is player 2's turn
    static boolean player1 = true;

    //Create a gameEnded boolean to determine if the game is ended
    static boolean gameOver = false;
    // replay frag
    static boolean replay = false;

    /**
     * It initiates the process of the game
     */
    public static void play() {
        // variables for row an column inputs
        int row;
        int column;


        // Repopulate the board from "0" with "-"
        clearBord(board);

        // If it is not replay, greet normally.
        if (!replay) {
            greetPlayer();
        }


        while (!gameOver) {  // as long as the game has not ended loop

            //Draw the board
            drawBoard(board);

            //Print whose turn it is to play to the console
            printTurn();

            // variables for position on the board and symbol to mark it.
            int[] inputPosition;
            char symbol;

            if (player1) {  // player1 is the gust player. Allow to play if it is guest player turn.
                inputPosition = playerResponse();  // collects gust players response
                symbol = 'X';

            } else { // else allow the computer to play
                inputPosition = jackiResponse(); // Collects the computers response
                symbol = 'O';

            }

            // extract the row and column values from input positions
            row = inputPosition[0];
            column = inputPosition[1];

            // Set the position on the board at row, col to symbol
            board[row][column] = symbol;


            //Check to see if either player has won
            gameOver = isAnyOneWon();

        }

        //Draw the board at the end of the game to show the END result
        drawBoard(board);

        //check if a player wants to play again and restart the game
        askForReplay();


    }

    /**
     * It will ask for a re play and initiate replay
     */
    private static void askForReplay() {
        System.out.println("PLAY AGAIN? Y/N");
        Scanner in = new Scanner(System.in);
        String playAgain = in.next().toLowerCase();
        if (playAgain.equals("y")) {
            replay = true;
            gameOver = false;
            reGreet(); // re greet the player
            play(); // initiate replay recursively
        } else {
            // if not
            System.out.println("Thanks for playing. Goodbye!");
        }
    }

    /**
     * re greets the player for a consecutive plays
     */
    private static void reGreet() {
        System.out.println("HELLO AGAIN! Let's see who's going to win this time! ");
    }


    /**
     * It will check if anyone won the game. If the game is won, it will mark gameEnded to True
     */
    private static boolean isAnyOneWon() {

        //Check to see if either player has won
        if (playerHasWon(board) == 'X') {
            System.out.println("FANTASTIC!!!! YOU WON!");
            return true;
        } else if (playerHasWon(board) == 'O') {
            System.out.println("AWESOME!!!  I WON!");
            return true;
        } else {

            //If neither player has won, check to see if there has been a tie (if the board is full)
            if (boardIsFull(board)) {
                System.out.println("It's a tie!");
                return true;
            } else {
                //If player1 is true, make it false, and vice versa; this way, the players alternate each turn
                player1 = !player1;

            }

        }

        return false;

    }

    /**
     * It provokes the player to input row and column
     *
     * @return the response of the player in the form of array since se have to inputs
     */
    private static int[] playerResponse() {
        int[] response = new int[2];
        int column;
        int row;

        while (true) {
            System.out.print("Enter a row number (1, 2, or 3): ");
            if (!scanner.hasNextInt()) {
                System.out.println("please input an Integer number");
                scanner.next();
                continue;
            }
            row = scanner.nextInt() - 1;

            System.out.print("Enter a column number (1, 2, or 3): ");
            if (!scanner.hasNextInt()) {
                System.out.println("please input an Integer number for both row and column AGAIN!");
                scanner.next();
                continue;
            }
            column = scanner.nextInt() - 1; //

            if (isBoundOk(row, column) && isaValidSpot(board[row][column])) {
                break;
            } else {
                System.out.println("Either position is off the bounds of the board " +
                        "\nOR Someone has already made a move at this position! Try again.");
            }
        }
        response[0] = row;
        response[1] = column;
        return response;
    }

    /**
     * It imitates the second player which is the computer. it will generate a random row and column to respond.
     *
     * @return the response of the computer in the form of array since se have to inputs
     */
    private static int[] jackiResponse() {
        int[] response = new int[2];
        Random random = new Random();
        int row;
        int column;

        // generates and verifies if the values are ok.
        while (true) {
            row = random.nextInt(3);
            column = random.nextInt(3);

            if (isBoundOk(row, column) && isaValidSpot(board[row][column])) {
                break;
            }

        }
        // put the value  in the array and return it.
        response[0] = row;
        response[1] = column;
        return response;

    }

    /**
     * It method validates if the spot on the board is available to mark by the players
     *
     * @param input character at the spot on the board
     * @return boolean value: True if it valid and False if not.
     */
    private static boolean isaValidSpot(char input) {
        return input == '-';
    }

    /**
     * It method validates if the inputs from player are in bound of the board.
     *
     * @param row row number
     * @param col column number
     * @return A boolean value: True if it valid and False if not.
     */
    private static boolean isBoundOk(int row, int col) {
        return row >= 0 && col >= 0 && row <= 2 && col <= 2;
    }

    /**
     * It method prints who's turn it is to the console
     */
    private static void printTurn() {
        if (player1) {
            System.out.println(guestPlayer + "'s Turn (x):");
        } else {
            System.out.println(jacki + "'s Turn (o):");
        }
    }

    /**
     * It greets player and initializes the second player's name.
     */
    private static void greetPlayer() {
        System.out.println("Hello Stranger, Let's play Tic Tac Toe!\nI am Jacki the computer. What is your name?");
        guestPlayer = scanner.nextLine();
        System.out.println("Nice to meet you " + guestPlayer + "!\nHere is our board, let's start playing....");

    }

    /**
     * It resets the bord to initial status.
     *
     * @param board
     */
    private static void clearBord(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    /**
     * It draws the tic tac toe board
     */
    private static void drawBoard(char[][] board) {
        System.out.println("Board:");
        for (int i = 0; i < 3; i++) {
            //The inner for loop prints out each row of the board
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
            }
            //This print statement makes a new line so that each row is on a separate line
            System.out.println();
        }
    }

    /**
     * It checks if any of the players won the game.
     *
     * @param board
     * @return It returns the symbol of the winner's player
     */
    private static char playerHasWon(char[][] board) {

        //Check each row
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {
                return board[i][0];
            }
        }

        //Check each column
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j] != '-') {
                return board[0][j];
            }
        }

        //Check the diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
            return board[0][0];
        }
        if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != '-') {
            return board[2][0];
        }

        //Otherwise nobody has not won yet
        return ' ';

    }

    /**
     * It checks if all of the spots on the board have been filled
     *
     * @param board
     * @return A boolean value: True if  all spots on the bord are marked
     */
    private static boolean boardIsFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') { // if there is at list one open spot, return false.
                    return false;
                }
            }
        }
        return true;
    }
}
