package comtictactoe;

import java.util.Random;
import java.util.Scanner;


    public class TicTacToe {

        //Creating a default constructor
        public TicTacToe() {
            System.out.println("Welcome to Tic Tac Toe Game !!!");
        }

        //Declaring variables
        static char[] board = new char[10];//For board
        static char player, computer;//For assigning x or o
        static int playerLocation, computerLocation;//For player location
        static int toss;//For tossing between player and computer
        static boolean computerFlag = false, playerFlag = false;
        static Scanner scan = new Scanner(System.in);
        static Random random = new Random();

        public static void main(String[] args) {

            //Initialize the object
            TicTacToe obj = new TicTacToe();

            //Starting the game.
            startGame();

            //For playing again
            anotherGame();

        }

        //Asking user to play again.
        public static void anotherGame() {
            System.out.println("\nWant to play again???\nPress Y for continue N to stop.");
            char playAgain = scan.next().charAt(0);
            while (true) {
                if (playAgain == 'y' || playAgain == 'Y') {
                    startGame();
                    anotherGame();
                    break;
                } else if (playAgain == 'n' || playAgain == 'N') {
                    break;
                } else {
                    System.out.println("Wrong choice.\nProvide the valid choice");
                    break;
                }
            }
        }

        //Starts the game
        public static void startGame() {
            int freeSpace = 8;//after toss no of position remaining 8.
            initialize();//Initialize the board
            showBoard();//Showing the board
            chooseOption();//Check for player option
            toss();//Toss to check that who is going to play first
            while (freeSpace != 0)//1st run of game
            {
                System.out.println();
                freeSpace--;
                turn();
                if (checkWinner()) {
                    break;
                } else if (freeSpace == 0) {
                    System.out.println("Board is full");
                    System.out.println("It is a tie");
                }
            }
            System.out.println("\nThe game is over.");
            computerFlag = false;
            playerFlag = false;
        }

        public static boolean checkWinner() {
            if ((board[1] == player && board[2] == player && board[3] == player) ||
                    (board[4] == player && board[5] == player && board[6] == player) ||
                    (board[7] == player && board[8] == player && board[9] == player) ||
                    (board[1] == player && board[5] == player && board[9] == player) ||
                    (board[3] == player && board[5] == player && board[7] == player) ||
                    (board[1] == player && board[4] == player && board[7] == player) ||
                    (board[2] == player && board[5] == player && board[8] == player) ||
                    (board[3] == player && board[6] == player && board[9] == player)) {
                System.out.println("Wait we got winner\nWinner is Player");
                return true;
            } else if ((board[1] == computer && board[2] == computer && board[3] == computer) ||
                    (board[4] == computer && board[5] == computer && board[6] == computer) ||
                    (board[7] == computer && board[8] == computer && board[9] == computer) ||
                    (board[1] == computer && board[5] == computer && board[9] == computer) ||
                    (board[3] == computer && board[5] == computer && board[7] == computer) ||
                    (board[1] == computer && board[4] == computer && board[7] == computer) ||
                    (board[2] == computer && board[5] == computer && board[8] == computer) ||
                    (board[3] == computer && board[6] == computer && board[9] == computer)) {
                System.out.println("Wait we got winner\nWinner is Computer");
                return true;
            }
            return false;
        }

        //Initialization of game
        public static void initialize() {
            for (int i = 1; i < 10; i++) {
                board[i] = ' ';
            }
        }

        //Doing a toss for playing first
        public static void toss() {
            toss = random.nextInt(2);
            switch (toss) {
                case 0:
                    System.out.println("Flipping Tail.\nComputer starts first.");
                    computerMove();//Computer move
                    computerFlag = true;
                    break;
                case 1:
                    System.out.println("Flipping Head.\nPlayer starts first.");
                    playerMove();//Player move
                    playerFlag = true;
                    break;
            }
        }

        //Allow player to choose X or O
        public static void chooseOption() {
            System.out.println("Please Select Your Choice Letter : \nProvide 'X' or 'O'");
            player = check();
            System.out.println("Player choosing option : " + player);
        }

        public static char check()//Sub method of chooseOption
        {
            char choice = scan.next().charAt(0);
            if (choice == 'X' || choice == 'x') {
                player = 'X';
                computer = 'O';
            } else if (choice == 'O' || choice == 'o') {
                player = 'O';
                computer = 'X';
            } else {
                System.out.println("Invalid option.\nProvide the valid one");
                check();
            }
            return player;
        }

        //To see board
        public static void showBoard() {
            System.out.println(board[1] + " | " + board[2] + " | " + board[3]);
            System.out.println("---------");
            System.out.println(board[4] + " | " + board[5] + " | " + board[6]);
            System.out.println("---------");
            System.out.println(board[7] + " | " + board[8] + " | " + board[9]);
        }

        //To make the player move
        public static void playerMove() {
            checkFreeSpace();
            int position = blockUser(computer);
            essentialPosition();
            if (position == 0) {
                System.out.println("No position to be blocked.\nTry to win");
            } else if (position != 0) {
                System.out.println("Block you opponent on position : " + position);
                System.out.println("If you don't want to block try to win");
            }
            System.out.println("Enter the position between (1-9) you want to make your move :");
            playerLocation = scan.nextInt();
            if (playerLocation > 0 && playerLocation < 10) {
                if (board[playerLocation] == ' ') {
                    board[playerLocation] = player;
                    showBoard();
                } else if (board[playerLocation] != ' ') {
                    System.err.println("Ah-hh! Position is already chosen. Enter a valid position");
                    showBoard();playerMove();
                    showBoard();
                }
            } else {
                System.err.println("Invalid choice. Provide a valid position between (1-9)");
                playerMove();
            }
        }

        //Check for essential position
        public static void essentialPosition() {
            boolean corner = false;
            for (int i = 1; i < board.length; i++) {
                if (i == 1 && board[i] == ' ') {
                    System.out.println("Corner position 1 is available");
                    corner = true;
                } else if (i == 3 && board[i] == ' ') {
                    System.out.println("Corner position 3 is available");
                    corner = true;
                } else if (i == 7 && board[i] == ' ') {
                    System.out.println("Corner position 7 is available");
                    corner = true;
                } else if (i == 9 && board[i] == ' ') {
                    System.out.println("Corner position 9 is available");
                    corner = true;
                }
            }
            if (!corner) {
                System.out.println("No more corner position is available");
                checkSubsequentPosition();//Checking for subsequent position
            }
        }

        //Check Subsequent Position
        public static void checkSubsequentPosition() {
            boolean middle = false;
            if (board[5] == ' ') {
                System.out.println("Mid position 5 is available");
                middle = true;
            } else {
                for (int i = 2; i < board.length; i = i + 2)// i started from 2 because available sides are 2 & 8 for vertical
                // and 4 & 6 are horizontal sides.
                {
                    if (i == 2 && board[i] == ' ') {
                        System.out.println("Side position 2 is available");
                    } else if (i == 4 && board[i] == ' ') {
                        System.out.println("Side position 4 is available");
                    } else if (i == 6 && board[i] == ' ') {
                        System.out.println("Side position 6 is available");
                    } else if (i == 8 && board[i] == ' ') {
                        System.out.println("Side position 8 is available");
                    }
                }
            }
        }

        //To make the computer move
        public static void computerMove() {
            int position = blockUser(player);
            if (position == 0) {
                computerLocation = random.nextInt(8) + 1;//Random gives 0 to 8 so +1 give you 1 to 9 position.
            } else if (position != 0) {
                System.out.println("Computer block the position : " + position);
                computerLocation = position;
            }
            if (computerLocation > 0 && computerLocation < 10) {
                if (board[computerLocation] == ' ') {
                    board[computerLocation] = computer;
                    showBoard();
                } else if (board[computerLocation] != ' ') {
                    computerMove();
                }
            }
        }

        //To check free space
        public static void checkFreeSpace() {
            boolean isSpaceAvailable = false;
            int numOfFreeSpaces = 0;
            for (int index = 1; index < board.length; index++) {
                if ((board[index] == ' ')) {
                    isSpaceAvailable = true;
                    numOfFreeSpaces++;
                }
            }
            if (isSpaceAvailable == false) {
                System.err.println("Board is full! You can't make another move");
            } else {
                System.out.println("Free space is available! you have " + numOfFreeSpaces + " moves left");
            }
        }

        //Check for winning, tie or change turn
        public static void checkGame() {
            if
            ((board[1] == player && board[2] == player && board[3] == ' '
                    || board[2] == player && board[3] == player && board[1] == ' '
                    || board[1] == player && board[3] == player && board[2] == ' ')
                    || (board[4] == player && board[5] == player && board[6] == ' '
                    || board[5] == player && board[6] == player && board[4] == ' '
                    || board[4] == player && board[6] == player && board[5] == ' ')
                    || (board[7] == player && board[8] == player && board[9] == ' '
                    || board[8] == player && board[9] == player && board[7] == ' '
                    || board[7] == player && board[9] == player && board[8] == ' ')
                    || (board[1] == player && board[5] == player && board[9] == ' '
                    || board[5] == player && board[9] == player && board[1] == ' '
                    || board[1] == player && board[9] == player && board[5] == ' ')
                    || (board[3] == player && board[5] == player && board[7] == ' '
                    || board[5] == player && board[7] == player && board[3] == ' '
                    || board[3] == player && board[7] == player && board[5] == ' ')
                    || (board[1] == player && board[4] == player && board[7] == ' '
                    || board[4] == player && board[7] == player && board[1] == ' '
                    || board[1] == player && board[7] == player && board[4] == ' ')
                    || (board[2] == player && board[5] == player && board[8] == ' '
                    || board[5] == player && board[8] == player && board[2] == ' '
                    || board[2] == player && board[8] == player && board[5] == ' ')
                    || (board[3] == player && board[6] == player && board[9] == ' '
                    || board[6] == player && board[9] == player && board[3] == ' '
                    || board[3] == player && board[9] == player && board[6] == ' ')) {
                System.out.println("Player going to win");
            } else if
            ((board[1] == computer && board[2] == computer && board[3] == ' '
                            || board[2] == computer && board[3] == computer && board[1] == ' '
                            || board[1] == computer && board[3] == computer && board[2] == ' ')
                            || (board[4] == computer && board[5] == computer && board[6] == ' '
                            || board[5] == computer && board[6] == computer && board[4] == ' '
                            || board[4] == computer && board[6] == computer && board[5] == ' ')
                            || (board[7] == computer && board[8] == computer && board[9] == ' '
                            || board[8] == computer && board[9] == computer && board[7] == ' '
                            || board[7] == computer && board[9] == computer && board[8] == ' ')
                            || (board[1] == computer && board[5] == computer && board[9] == ' '
                            || board[5] == computer && board[9] == computer && board[1] == ' '
                            || board[1] == computer && board[9] == computer && board[5] == ' ')
                            || (board[3] == computer && board[5] == computer && board[7] == ' '
                            || board[5] == computer && board[7] == computer && board[3] == ' '
                            || board[3] == computer && board[7] == computer && board[5] == ' ')
                            || (board[1] == computer && board[4] == computer && board[7] == ' '
                            || board[4] == computer && board[7] == computer && board[1] == ' '
                            || board[1] == computer && board[7] == computer && board[4] == ' ')
                            || (board[2] == computer && board[5] == computer && board[8] == ' '
                            || board[5] == computer && board[8] == computer && board[2] == ' '
                            || board[2] == computer && board[8] == computer && board[5] == ' ')
                            || (board[3] == computer && board[6] == computer && board[9] == ' '
                            || board[6] == computer && board[9] == computer && board[3] == ' '
                            || board[3] == computer && board[9] == computer && board[6] == ' ')) {
                System.out.println("Computer going to win");
            } else
                System.out.println("It may be a tie.");
        }

        //Check to block opponent
        public static int blockUser(char input) {
            char ch = input;
            int blockPosition = 0;
            if (ch == input) {
                if ((board[1] == ch && board[2] == ch && board[3] == ' ') || (board[5] == ch && board[7] == ch && board[3] == ' ') || (board[6] == ch && board[9] == ch && board[3] == ' ')) {
                    blockPosition = 3;
                    return blockPosition;
                } else if ((board[2] == ch && board[3] == ch && board[1] == ' ') || (board[5] == ch && board[9] == ch && board[1] == ' ') || (board[4] == ch && board[7] == ch && board[1] == ' ')) {
                    blockPosition = 1;
                    return blockPosition;
                } else if ((board[1] == ch && board[3] == ch && board[2] == ' ') || (board[5] == ch && board[8] == ch && board[2] == ' ')) {
                    blockPosition = 2;
                    return blockPosition;
                } else if ((board[4] == ch && board[5] == ch && board[6] == ' ') || (board[3] == ch && board[9] == ch && board[6] == ' ')) {
                    blockPosition = 6;
                    return blockPosition;
                } else if ((board[5] == ch && board[6] == ch && board[4] == ' ') || (board[1] == ch && board[7] == ch && board[4] == ' ')) {
                    blockPosition = 4;
                    return blockPosition;
                } else if ((board[4] == ch && board[6] == ch && board[5] == ' ') || (board[1] == ch && board[9] == ch && board[5] == ' ')
                        || (board[3] == ch && board[7] == ch && board[5] == ' ') || (board[2] == ch && board[8] == ch && board[5] == ' ')) {
                    blockPosition = 5;
                    return blockPosition;
                } else if ((board[7] == ch && board[8] == ch && board[9] == ' ') || (board[1] == ch && board[5] == ch && board[9] == ' ') || (board[3] == ch && board[6] == ch && board[9] == ' ')) {
                    blockPosition = 9;
                    return blockPosition;
                } else if ((board[8] == ch && board[9] == ch && board[7] == ' ') || (board[3] == ch && board[5] == ch && board[7] == ' ') || (board[1] == ch && board[4] == ch && board[7] == ' ')) {
                    blockPosition = 7;
                    return blockPosition;
                } else if ((board[7] == ch && board[9] == ch && board[8] == ' ') || (board[2] == ch && board[5] == ch && board[8] == ' ')) {
                    blockPosition = 8;
                    return blockPosition;
                }
            }
            return blockPosition;
        }

        //Check for turn
        public static void turn() {
            checkGame();
            System.out.println("Turn Changed");
            if (computerFlag == true) {
                System.out.println("Now Player's Turn");
                playerMove();
                computerFlag = false;
                playerFlag = true;
            } else if (playerFlag == true) {
                System.out.println("Now Computer's Turn");
                computerMove();//Here it's how computer starts to play like me.
                playerFlag = false;
                computerFlag = true;
            }
        }
}
