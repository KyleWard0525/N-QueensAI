/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8queens;

import java.util.Arrays;

/**
 * Wrapper class for game board
 * 
 * @author kward60
 */
public class Board {
    
    private int[][] board;
    private int size = Queens.n;
    private Fitness eval = new Fitness();
    
    /**
     * Main constructor
     * @param board 2D int board representation
     */
    public Board(int[][] board)
    {
        this.board = board;
    }
    
    /**
     * Prints the board
     */
    public void print()
    {
        System.out.println("\nCurrent State: ");
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
     * Print locations of all queens
     */
    public void queenLocs()
    {
        eval.findAllQueens();
        
        //Loop through and print queen locations
        for(int[] queen : Fitness.queenPositions)
        {
            System.out.println("Queen found at: " + Arrays.toString(queen));
        }
        
    }
    
    public int[][] getBoard()
    {
        return board;
    }
    
}
