package pkg8queens;

import java.util.Arrays;

/**
 * Wrapper class for game board
 * 
 * @author kward60
 */
public class Board {
    
    private int[][] board;
    private int size;
    private Fitness eval;
    
    /**
     * Main constructor
     * @param board 2D int board representation
     */
    public Board(int[][] board)
    {
        this.board = board;
        this.size = Queens.n;
        this.eval = new Fitness();
    }
    
    /**
     * Prints the board
     */
    public void print()
    {
        System.out.println("\nCurrent State: ");
        
        //Print each row of the board
        for(int[] row : board)
        {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("Board error: " + error());
    }
    
    /**
     * return board's error
     */
    public int error()
    {
        return eval.error(board);
    }
    
    /**
     * Returns 2D int array of the board
     * @return 
     */
    public int[][] getBoard()
    {
        return board;
    }
    
}
