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
    private ArrayList<int[][]> boardStates; //Holds all generated board states
    private Random rand;
    public static int n; //Game Size
    private Fitness eval; //Evaluates and scores the boards

    public Queens() {
        init();
        initBoard();
        eval = new Fitness(board);
    }

    /**
     * Initialize game variables
     */
    public void init() {
        this.n = 8;
        this.board = new int[n][n];
        this.boardStates = new ArrayList<>();
        this.rand = new Random();
    }

    /**
     * Generates initial board by placing a queen in a random row of each column
     */
    public void initBoard() {
        int col = 0;

        //Loop and place queens (1)
        while (col < n) {
            int randRow = rand.nextInt(n);

            //Place queen on board
            board[randRow][col] = 1;

            col++;
        }
        //Add board state to list
        boardStates.add(board);
    }

    /**
     * Generate all possible board states by moving each columns queen to all
     * other rows in that column
     */
    public void generateBoardStates() {
        ArrayList<int[]> queensPos = Fitness.queenPositions;

        //Loop through all queens
        for (int i = 0; i < queensPos.size(); i++) {
            int[] queen = queensPos.get(i);
            int nextPos = queen[0] + 1;

            //Reset if nextPos is off the board
            if (nextPos >= board.length) {
                nextPos = 0;
            }
            
            //Move queen
            int orig_idx = queen[0];
            queen = new int[]{nextPos, queen[1]};
            queensPos.set(i, queen);
            
            //Change queen's pos on the board
            board[orig_idx][queen[1]] = 0;
            board[nextPos][queen[1]] = 1;
            
            //Add new board state to list
            boardStates.add(board);
            
        }
        
    }

    /**
     * Prints the board's current state
     */
    public void printState() {
        System.out.println("Board State: ");

        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(board[i]));
        }

        System.out.println("\nState Error: " + eval.error());
        
        generateBoardStates();
        
        System.out.println("Number of generated states: " + boardStates.size());

    }
}
