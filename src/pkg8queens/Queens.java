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
        generateBoardStates();
    }

    /**
     * Initialize game variables
     */
    public void init() {
        this.n = 8;
        this.board = new int[n][n];
        this.boardStates = new ArrayList<>(16777216);
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
        eval.findAllQueens();
        ArrayList<int[]> queensPos = Fitness.queenPositions;
        int[][] newBoard = new int[8][8];

        try {

            //Generate all possible board combinations
            while (!boardStates.contains(newBoard)) {
                //Loop through all queens
                for (int i = 0; i < queensPos.size(); i++) {
                    int[] queen = queensPos.get(i);
                    for (int k = 0; k < board.length; k++) {
                        //Pick random row in column to move to
                        int nextPos = rand.nextInt(n);

                        //Reset if nextPos is off the board
                        if (nextPos >= board.length) {
                            nextPos = 0;
                        }

                        //Move queen
                        int orig_idx = queen[0];
                        queen = new int[]{nextPos, queen[1]};
                        queensPos.set(i, queen);

                        //Change queen's pos on the board
                        newBoard = board;
                        newBoard[orig_idx][queen[1]] = 0;
                        newBoard[nextPos][queen[1]] = 1;

                        //Add new board state to list
                        boardStates.add(newBoard);
                    }
                }

            }
        } catch (Exception ie) {
            ie.printStackTrace();
        }
    }

    public void findLowestState() {

    }

    /**
     * Prints the board's current state
     */
    public void printState() {
        System.out.println("Board State: ");

        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(boardStates.get(0)[i]));
        }

        System.out.println("State Error: " + eval.error(boardStates.get(0)));

        System.out.println("\nNumber of generated states: " + boardStates.size());

        System.out.println("\nBoard State #2:");

        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(boardStates.get(12)[i]));
        }
        System.out.println("State Error: " + eval.error(boardStates.get(12)));

    }
}
