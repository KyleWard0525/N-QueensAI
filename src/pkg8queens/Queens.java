/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8queens;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.lang.Math;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.*;

/**
 * Implementation of the Hill-Climbing with random restart algorithm to solve
 * the 8-Queens puzzle
 *
 * @author kward60
 */
public class Queens {

    private int[][] board; //Game Board
    private LinkedList<Board> boardStates; //Holds all generated board states
    private Random rand;
    public static int n; //Game Size
    private Fitness eval; //Evaluates and scores the boards
    private LinkedList<Integer> boardScores;
    private int states;
    private int numPrevStates;
    private int resets;

    /**
     * Default constructor
     * 
     * Sets board to 8x8
     */
    public Queens() {
        this.n = 8;
        init();
    }
    
    /**
     * Main constructor
     * Allows for dynamic game sizes
     * @param size 
     */
    public Queens(int size)
    {
        this.n = size;
        init();
    }

    /**
     * Initialize game variables
     */
    public void init() {
        this.states = 0;
        this.resets = 0;
        this.numPrevStates = 0;
        this.board = new int[n][n];
        this.rand = new Random();
        boardStates = new LinkedList<Board>();
        this.eval = new Fitness();
        this.boardScores = new LinkedList<>();
        randomizeBoard();
    }

    /**
     * Generates initial board by placing a queen in a random row of each column
     */
    public void initBoard() {
        states = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = 0;
            }
        }
        numPrevStates = 0;
    }

    public void randomizeBoard() {
        int col = 0;
        initBoard();

        //Loop and place queens (1)
        while (col < n) {
            int randRow = rand.nextInt(n);

            //Place queen on board
            board[randRow][col] = 1;

            col++;
        }

        //Add board state to list
        boardStates.add(new Board(board));
        boardStates.get(boardStates.size() - 1).print();
    }

    /**
     * Write the board state to a file.
     * 
     * @param board
     */
    public void writeToFile(int[][] board) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j] + "");

                if (j < board.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("states/board_state_" + states + ".txt"));
            states++;
            writer.write(sb.toString());
            writer.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Read the saved board states into the 
     * boardStates list
     * 
     * @throws IOException 
     */
    public void readStates() throws IOException {
        boardStates.clear();
        boardScores.clear();

        for (int i = 0; i < states - 1; i++) {
            int[][] newBoard = new int[n][n];

            try {
                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\Documents\\NetBeansProjects\\8Queens\\states\\board_state_" + i + ".txt"));
                String line = "";
                int rowIdx = 0;

                while ((line = br.readLine()) != null) {
                    String[] cols = line.split(",");
                    int colIdx = 0;

                    for (String c : cols) {
                        newBoard[rowIdx][colIdx] = Integer.parseInt(c);
                        colIdx++;
                    }
                    rowIdx++;
                }
                
                if (!boardStates.contains(new Board(newBoard))) {
                    boardStates.add(new Board(newBoard));
                }

                br.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Queens.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board.length);
        }

        //Loop through all columns
        while (colIdx < board.length) {
            //Loop through and clear newBoard's current column
            for (int i = 0; i < board.length; i++) {
                newBoard[i][colIdx] = 0;
            }

            //Loop through current column
            for (int i = 0; i < board.length; i++) {
                //Check if theres a queen there
                if (board[i][colIdx] == 1) {
                    //Save queen index
                    lastQueenIdx = i;
                    continue;
                }
                //Place queen, save new board state, then reset
                newBoard[i][colIdx] = 1;

                if (eval.error(board) > eval.error(newBoard)) {
                    writeToFile(newBoard);
                }

                newBoard[i][colIdx] = 0;

            }
            //Add position of queen found from board into new board
            newBoard[lastQueenIdx][colIdx] = 1;

            //Move to next column
            colIdx++;
        }
    }

    /**
     * Find the board with the lowest error and sets it as
     * the current board
     */
    public void moveToLowestState() {

        for (Board board : boardStates) {
            boardScores.add(board.error());
        }

        int lowestErr = Collections.min(boardScores);

        if (lowestErr < eval.error(board)) {
            Board b = boardStates.get(boardScores.indexOf(lowestErr));
            b.print();
            this.board = b.getBoard();
        }
    }

    /**
     * Trains the AI until the solution is found
     * 
     * @throws InterruptedException 
     */
    public void train(){

        while (eval.error(board) > 0) {
            try {
                generateBoardStates();
                readStates();

                int numLower = boardStates.size() - numPrevStates;
                System.out.println("\nNumber of states with lower h: " + numLower);
                if (numLower < 1) {
                    //Reset board
                    System.out.println("No neighbors found, resetting board!");
                    randomizeBoard();
                    numPrevStates = 0;
                    resets++;
                    continue;
                } else {

                    numPrevStates = boardStates.size();
                    new Board(board).print();
                    moveToLowestState();
                }

            } catch (Exception ex) {
                Logger.getLogger(Queens.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("\nSolution Found!");
        System.out.println("Number of switches: " + boardStates.size());
        System.out.println("Resets: " + resets);
    }
}
