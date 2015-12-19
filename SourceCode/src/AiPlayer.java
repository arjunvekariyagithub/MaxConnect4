import java.util.*;

/**
 * This is the AiPlayer class. It simulates a minimax player for the max connect four game. The constructor essentially does
 * nothing.
 * 
 * @author james spargo
 * 
 */

public class AiPlayer {

    public int depth_level;
    public GameBoard gameBoardShallow;

    /**
     * The constructor essentially does nothing except instantiate an AiPlayer object.
     * 
     * @param currentGame
     * 
     */
    public AiPlayer(int depth, GameBoard currentGame) {
        this.depth_level = depth;
        this.gameBoardShallow = currentGame;
    }

    /**
     * This method makes the decision to make a move for the computer using 
     * the min and max value from the below given two functions.
     * 
     * @param currentGame The GameBoard object that is currently being used to play the game.
     * @return an integer indicating which column the AiPlayer would like to play in.
     * @throws CloneNotSupportedException
     */
    public int findBestPlay(GameBoard currentGame) throws CloneNotSupportedException {
        int playChoice = Maxconnect4.INVALID;
        if (currentGame.getCurrentTurn() == Maxconnect4.ONE) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.NOF_COLS; i++) {
                if (currentGame.isValidPlay(i)) {
                    // GameBoard nextMoveBoard = (GameBoard) currentGame.clone();//new GameBoard(currentGame.getGameBoard());
                    GameBoard nextMoveBoard = new GameBoard(currentGame.getGameBoard());
                    nextMoveBoard.playPiece(i);
                    int value = Calculate_Max_Utility(nextMoveBoard, depth_level, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v > value) {
                        playChoice = i;
                        v = value;
                    }
                }
            }
        } else {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.NOF_COLS; i++) {
                if (currentGame.isValidPlay(i)) {
                    // GameBoard nextMoveBoard = (GameBoard) currentGame.clone();//new GameBoard(currentGame.getGameBoard());
                    GameBoard nextMoveBoard = new GameBoard(currentGame.getGameBoard());
                    nextMoveBoard.playPiece(i);
                    int value = Calculate_Min_Utility(nextMoveBoard, depth_level, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v < value) {
                        playChoice = i;
                        v = value;
                    }
                }
            }
        }
        return playChoice;
    }

    /**
     * This method calculates the min value.
     * 
     * @param currentGame The GameBoard object that is currently being used to play the game.
     * @param depth_level depth to which computer will search for making best possible next move
     * @param alpha_value maximum utility value for MAX player
     * @param beta_value maximum utility value for MIN player 
     * @return an integer indicating which column the AiPlayer would like to play in.
     * @throws CloneNotSupportedException
     */

    private int Calculate_Min_Utility(GameBoard gameBoard, int depth_level, int alpha_value, int beta_value)
        throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        if (!gameBoard.isBoardFull() && depth_level > 0) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.NOF_COLS; i++) {
                if (gameBoard.isValidPlay(i)) {
                    // Create Shallow copy to improve time complexity
                    // GameBoard board4NextMove = (GameBoard) gameBoard.clone();//new GameBoard(gameBoard.getGameBoard());
                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
                    board4NextMove.playPiece(i);
                    int value = Calculate_Max_Utility(board4NextMove, depth_level - 1, alpha_value, beta_value);
                    if (v > value) {
                        v = value;
                    }
                    if (v <= alpha_value) {
                        //System.out.println("Pruning as (v <= alpha_value): v = " + v + ", alpha_value = " + alpha_value);
                        return v;
                    }
                    if (beta_value > v) {
                        beta_value = v;
                    }
                }
            }
            return v;
        } else {
            // this is a terminal state
            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);
        }
    }

    /**
     * This method calculates the max value.
     * 
     * @param currentGame The GameBoard object that is currently being used to play the game.
     * @param depth_level depth to which computer will search for making best possible next move
     * @param alpha_value maximum utility value for MAX player
     * @param beta_value maximum utility value for MIN player 
     * @return an integer indicating which column the AiPlayer would like to play in.
     * @throws CloneNotSupportedException
     */

    private int Calculate_Max_Utility(GameBoard gameBoard, int depth_level, int alpha_value, int beta_value)
        throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        if (!gameBoard.isBoardFull() && depth_level > 0) {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.NOF_COLS; i++) {
                if (gameBoard.isValidPlay(i)) {
                    // Create Shallow copy to improve time complexity
                    // GameBoard board4NextMove = (GameBoard) gameBoard.clone();//new GameBoard(gameBoard.getGameBoard());
                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
                    board4NextMove.playPiece(i);
                    int value = Calculate_Min_Utility(board4NextMove, depth_level - 1, alpha_value, beta_value);
                    if (v < value) {
                        v = value;
                    }
                    if (v >= beta_value) {
                        //System.out.println("Prunning as (v >= beta_value): v = " + v + ", beta = " + beta_value);
                        return v;
                    }
                    if (alpha_value < v) {
                        alpha_value = v;
                    }
                }
            }
            return v;
        } else {
            // this is a terminal state
            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);
        }
    }

}
