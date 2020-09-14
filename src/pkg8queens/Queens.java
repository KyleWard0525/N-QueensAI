/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8queens;

import java.util.*;
import java.lang.Math;

/**
 * Implementation of the Hill-Climbing with random restart algorithm to solve
 * the 8-Queens puzzle
 *
 * @author kward60
 */
public class Queens {

    private int[][] board; //Game Board
    private ArrayList<Board> boardStates; //Holds all generated board states
    private Random rand;
    public static int n; //Game Size
    private Fitness eval; //Evaluates and scores the boards
    private int max_combs = 16777216; //Maximum possible board states (8 choose 1)^8
    private ArrayList<Board> neighbors;

    public Queens() {
        init();
        initBoard();
        generateBoardStates();
    }

    /**
     * Initialize game variables
     */
    public void init() {
        this.n = 8;
        this.board = new int[n][n];
        this.rand = new Random();
        boardStates = new ArrayList<>();
        this.eval = new Fitness();
        this.neighbors = new ArrayList<>(100);
    }

    /**
     * Generates initial board by placing a queen in a random row of each column
     */
    public void initBoard() {
        int col = 0;
        
        boardStates.clear();
        
        //Loop and place queens (1)
        while (col < n) {
            int randRow = rand.nextInt(n);

            //Place queen on board
            board[randRow][col] = 1;

            col++;
        }

        //Add board state to list
        boardStates.add(new Board(board));
        eval.setBoard(board);
    }
    
    

    /**
     * Generate all possible board states by moving each columns queen to all
     * other rows in that column
     */
    public void generateBoardStates() {
        eval.findAllQueens();
        int[][] newBoard = new int[8][8];
        int colIdx = 0;
        int lastQueenIdx = 0;
        
        //Copy board to newBoard
        for(int i = 0; i < board.length; i++)
        {
            System.arraycopy(board[i], 0, newBoard[i], 0, board.length);
        }
        
        //Loop through all columns
        while(colIdx < board.length)
        {
         //Loop through and clear newBoard's current column
         for(int i = 0; i < board.length; i++)
         {
             newBoard[i][colIdx] = 0;
        }
         
         //Loop through current column
         for(int i = 0; i < board.length; i++)
         {
             //Check if theres a queen there
             if(board[i][colIdx] == 1)
             {
                 
                 //Save queen index
                 lastQueenIdx = i;
                 continue;
             }
             //Place queen, save new board state, then reset
             newBoard[i][colIdx] = 1;
             boardStates.add(new Board(newBoard));
             newBoard[i][colIdx] = 0;
             
         }
         //Add position of queen found from board into new board
         newBoard[lastQueenIdx][colIdx] = 1;
         
         
         
         //Move to next column
        colIdx++;
    }
        
    }
        /**
         * Find the board with the lowest error
         */
    public void findLowestState() {

    }

    /**
     * Prints the board's current state
     */
    public void printState() {

        
        System.out.println("Original board : ");
        boardStates.get(0).print();
        
        System.out.println("");

        System.out.println("Number of states: " + boardStates.size());
        
        //Print all boards
        
        
        try {
            for (int j = 0; j < boardStates.size(); j++) {
                boardStates.get(j).print();
                Thread.sleep(3000);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
       
        

        //findLowestState();
    }

    // Function to remove duplicates from an ArrayList 
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new LinkedHashSet 
        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set 
        set.addAll(list);

        // Clear the list 
        list.clear();

        // add the elements of set 
        // with no duplicates to the list 
        list.addAll(set);

        // return the list 
        return list;
    }
}
