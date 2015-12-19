import java.util.Scanner;

/**
 * 
 * @author James Spargo This class controls the game play for the Max Connect-Four game. To compile the program, use the
 *         following command from the maxConnectFour directory: javac *.java
 * 
 *         the usage to run the program is as follows: ( again, from the maxConnectFour directory )
 * 
 *         -- for interactive mode: java MaxConnectFour interactive [ input_file ] [ computer-next / human-next ] [ search
 *         depth]
 * 
 *         -- for one move mode java maxConnectFour.MaxConnectFour one-move [ input_file ] [ output_file ] [ search depth]
 * 
 *         description of arguments: [ input_file ] -- the path and filename of the input file for the game
 * 
 *         [ computer-next / human-next ] -- the entity to make the next move. either computer or human. can be abbreviated to
 *         either C or H. This is only used in interactive mode
 * 
 *         [ output_file ] -- the path and filename of the output file for the game. this is only used in one-move mode
 * 
 *         [ search depth ] -- the depth of the minimax search algorithm
 * 
 * 
 */

public class Maxconnect4 {

    public static Scanner input_stream = null;
    public static GameBoard currentGame = null;
    public static AiPlayer aiPlayer = null;

    public static final int ONE = 1;
    public static final int TWO = 2;
    public static int HUMAN_PIECE;
    public static int COMPUTER_PIECE;
    public static int INVALID = 99;
    public static final String FILEPATH_PREFIX = "../";
    public static final String COMPUTER_OPT_FILE = "computer.txt";
    public static final String HUMAN_OPT_FILE = "human.txt";

    public enum MODE {
        INTERACTIVE,
        ONE_MOVE
    };

    public enum PLAYER_TYPE {
        HUMAN,
        COMOUTER
    };

    public static void main(String[] args) throws CloneNotSupportedException {
        // check for the correct number of arguments
        if (args.length != 4) {
            System.out.println("Four command-line arguments are needed:\n"
                + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

            exit_function(0);
        }

        // parse the input arguments
        String game_mode = args[0].toString(); // the game mode
        String inputFilePath = args[1].toString(); // the input game file
        int depthLevel = Integer.parseInt(args[3]); // the depth level of the ai search

        // create and initialize the game board
        currentGame = new GameBoard(inputFilePath);

        // create the Ai Player
        aiPlayer = new AiPlayer(depthLevel, currentGame);

        if (game_mode.equalsIgnoreCase("interactive")) {
            currentGame.setGameMode(MODE.INTERACTIVE);
            if (args[2].toString().equalsIgnoreCase("computer-next") || args[2].toString().equalsIgnoreCase("C")) {
                // if it is computer next, make the computer make a move
                currentGame.setFirstTurn(PLAYER_TYPE.COMOUTER);
                MakeComputerPlay4Interactive();
            } else if (args[2].toString().equalsIgnoreCase("human-next") || args[2].toString().equalsIgnoreCase("H")){
                currentGame.setFirstTurn(PLAYER_TYPE.HUMAN);
                MakeHumanPlay();
            } else {
                System.out.println("\n" + "value for 'next turn' doesn't recognized.  \n try again. \n");
                exit_function(0);
            }
//
//            System.out.print("\nMaxConnect-4 game:\n");
//            System.out.print("Game state before move:\n");
//
//            // print the current game board
//            currentGame.printGameBoard();
//
//            // print the current scores
//            System.out.println("Score: Player-1 = " + currentGame.getScore(ONE) + ", Player-2 = " + currentGame.getScore(TWO)
//                + "\n ");

            if (currentGame.isBoardFull()) {
                System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
                exit_function(0);
            }

        } else if (!game_mode.equalsIgnoreCase("one-move")) {
            System.out.println("\n" + game_mode + " is an unrecognized game mode \n try again. \n");
            exit_function(0);
        } else {
            // /////////// one-move mode ///////////
            currentGame.setGameMode(MODE.ONE_MOVE);
            String outputFileName = args[2].toString(); // the output game file
            MakeComputerPlay4OneMove(outputFileName);
        }
    } // end of main()
    
    /**
     * This method handles computer's move for one-move mode
     * 
     * @param outputFileName path for output file to save game state
     */
    private static void MakeComputerPlay4OneMove(String outputFileName) throws CloneNotSupportedException {
        // TODO Auto-generated method stub

        // variables to keep up with the game
        int playColumn = 99; // the players choice of column to play
        boolean playMade = false; // set to true once a play has been made

        System.out.print("\nMaxConnect-4 game:\n");

        System.out.print("Game state before move:\n");

        // print the current game board
        currentGame.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentGame.getScore(ONE) + ", Player-2 = " + currentGame.getScore(TWO)
            + "\n ");

        if (currentGame.isBoardFull()) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // ****************** this chunk of code makes the computer play

        int current_player = currentGame.getCurrentTurn();

        // AI play - random play
        playColumn = aiPlayer.findBestPlay(currentGame);

        if (playColumn == INVALID) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // play the piece
        currentGame.playPiece(playColumn);

        // display the current game board
        System.out.println("move " + currentGame.getPieceCount() + ": Player " + current_player + ", column "
            + (playColumn + 1));

        System.out.print("Game state after move:\n");

        // print the current game board
        currentGame.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentGame.getScore(ONE) + ", Player-2 = " + currentGame.getScore(TWO)
            + "\n ");

        currentGame.printGameBoardToFile(outputFileName);

        // ************************** end computer play

    }

    /**
     * This method handles computer's move for interactive mode
     * 
     */
    private static void MakeComputerPlay4Interactive() throws CloneNotSupportedException {

        printBoardAndScore();

        System.out.println("\n Computer's turn:\n");

        int playColumn = INVALID; // the players choice of column to play

        // AI play - random play
        playColumn = aiPlayer.findBestPlay(currentGame);

        if (playColumn == INVALID) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // play the piece
        currentGame.playPiece(playColumn);

        System.out.println("move: " + currentGame.getPieceCount() + " , Player: Computer , Column: " + (playColumn + 1));

        currentGame.printGameBoardToFile(COMPUTER_OPT_FILE);

        if (currentGame.isBoardFull()) {
            printBoardAndScore();
            printResult();
        } else {
            MakeHumanPlay();
        }
    }

    /**
     * This method prints final result for the game.
     * 
     */
    
    private static void printResult() {
        int human_score = currentGame.getScore(Maxconnect4.HUMAN_PIECE);
        int comp_score = currentGame.getScore(Maxconnect4.COMPUTER_PIECE);
        
        System.out.println("\n Final Result:");
        if(human_score > comp_score){
            System.out.println("\n Congratulations!! You won this game."); 
        } else if (human_score < comp_score) {
            System.out.println("\n You lost!! Good luck for next game.");
        } else {
            System.out.println("\n Game is tie!!");
        }
    }

    /**
     * This method handles human's move for interactive mode.
     * 
     */
    private static void MakeHumanPlay() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        printBoardAndScore();

        System.out.println("\n Human's turn:\nKindly play your move here(1-7):");

        input_stream = new Scanner(System.in);

        int playColumn = INVALID;

        do {
            playColumn = input_stream.nextInt();
        } while (!isValidPlay(playColumn));

        // play the piece
        currentGame.playPiece(playColumn - 1);

        System.out.println("move: " + currentGame.getPieceCount() + " , Player: Human , Column: " + playColumn);
        
        currentGame.printGameBoardToFile(HUMAN_OPT_FILE);

        if (currentGame.isBoardFull()) {
            printBoardAndScore();
            printResult();
        } else {
            MakeComputerPlay4Interactive();
        }
    }

    private static boolean isValidPlay(int playColumn) {
        if (currentGame.isValidPlay(playColumn - 1)) {
            return true;
        }
        System.out.println("Opps!!...Invalid column , Kindly enter column value between 1 to 7.");
        return false;
    }

    /**
     * This method displays current board state and score of each player.
     * 
     */
    public static void printBoardAndScore() {
        System.out.print("Game state :\n");

        // print the current game board
        currentGame.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentGame.getScore(ONE) + ", Player-2 = " + currentGame.getScore(TWO)
            + "\n ");
    }

    /**
     * This method is used when to exit the program prematurly.
     * 
     * @param value an integer that is returned to the system when the program exits.
     */
    private static void exit_function(int value) {
        System.out.println("exiting from MaxConnectFour.java!\n\n");
        System.exit(value);
    }
} // end of class connectFour
