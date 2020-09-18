package pkg8queens;

/**
 * Used to evaluate and score the state of 
 * each board
 * 
 * @author kward60
 */
public class Fitness {
    
    private int[][] board;
    private int error;
    private int max;
    
    /**
     * Main constructor
     */
    public Fitness()
    {
        this.error = 0;
        this.board = new int[Queens.n][Queens.n]; 
    }
    
    /**
     * Evaluates the state of the board and
     * counts the number of errors.
     * @return 
     */
    public int error(int[][] board)
    {
        error = 0;
        
        setBoard(board);
        
        //Loop through board and check each space for a queen
        for (int i = 0; i < board.length; i++)
        {
           for(int j = 0; j < board.length; j++)
           {
                  //Queen found
               if(board[i][j] == 1)
               {
                    //Add to list of known queens
                   int[] queenIndex = {i,j};
                   
                   //Check for other queens in that row
                   if(checkRow(queenIndex))
                   {
                       error++;
                   }
                   
                   //Check for other queens in that column
                   if(checkColumn(queenIndex))
                   {
                       error++;
                   }
                   
                   //Check for other queens diagonally
                   checkDiagonals(queenIndex);
               }
           }
        }
        return error;
    }
    
    /**
     * Check the row of a given queen for other queens
     * @param queenIndex - index of queen being referenced
     * @return 
     */
    private boolean checkRow(int[] queenIndex)
    {
        int rowNum = queenIndex[0];
        boolean collision = false;
        
        //Loop through row and look for other 1's
        for(int i = 0; i < board.length; i++)
        {
            //Check if current column is original queen's column
            if(i == queenIndex[1])
            {
                continue;
            }
            
            //Search the rest of the row for other queens
            if(board[rowNum][i] == 1)
            {
                collision = true;
                
                int[] queen = new int[]{rowNum, i};
            }
                
        }
        return collision;
    }
    
    /**
     * Check the column of a given queen for other queens
     * @param queenIndex - index of queen being referenced
     * @return 
     */
    private boolean checkColumn(int[] queenIndex)
    {
        int colNum = queenIndex[1];
        boolean collision = false;
        
        //Loop through column and check for other 1's
        for(int i = 0; i < board.length; i++)
        {
            //Skip current queen
            if(i == queenIndex[0])
            {
                continue;
            }
            
            //Check column for other queens
            if(board[i][colNum] == 1)
            {
                collision = true;
                
                int[] queen = new int[]{colNum, i};
            }
        }
        return collision;
    }
    
    /**
     * Check the 4 diagonals around the queen for other queens
     * @param queenIndex - index of queen being referenced
     */
    private void checkDiagonals(int[] queenIndex)
    {
        int currSpace;
        
        //Start search at queen's position
        int[] currIdx = queenIndex;      
        
        int x = 1;
        int y = 1;
        
        //Check up-right collision
        while(x != max && y != 0)
        {
            int[] nextSpace = new int[] {queenIndex[0] + x, queenIndex[1] - y};
            
            if(queenIndex[0] + x > max || queenIndex[1] - y <= 0)
            {
                break;
            }
            
            currSpace = board[queenIndex[0] + x][queenIndex[1] - y];
            
            //Queen found
            if(currSpace == 1)
            {
               error++; 
            }
            
            x++;
            y++;
        }
        
        //Reset x and y before next diagonal
        x = 1;
        y = 1;
        
        
         //Check up-left collision
        while(x != 0 && y != 0)
        {
            int[] nextSpace = new int[] {queenIndex[0] - x, queenIndex[1] - y};
            
            if(queenIndex[0] - x <= 0 || queenIndex[1] - y <= 0)
            {
                break;
            }
            
            currSpace = board[queenIndex[0] - x][queenIndex[1] - y];
            
            //Queen found
            if(currSpace == 1)
            {
               error++; 
            }
            
            x++;
            y++;
        }
        
        //Reset x and y before next diagonal
        x = 1;
        y = 1;
        
        
         //Check down-left collision
        while(x != 0 && y != max)
        {
            int[] nextSpace = new int[] {queenIndex[0] - x, queenIndex[1] + y};
            
            if(queenIndex[0] - x <= 0 || queenIndex[1] + y > max)
            {
                break;
            }
            
            currSpace = board[queenIndex[0] - x][queenIndex[1] + y];
            
            //Queen found
            if(currSpace == 1)
            {
               error++; 
            }
            
            x++;
            y++;
        }
        
        //Reset x and y before next diagonal
        x = 1;
        y = 1;
        
        
         //Check down right collision
        while(x != max && y != max)
        {
            int[] nextSpace = new int[] {queenIndex[0] + x, queenIndex[1] + y};
            
            if(queenIndex[0] + x > max || queenIndex[1] + y > max)
            {
                break;
            }
            
            currSpace = board[queenIndex[0] + x][queenIndex[1] + y];
            
            //Queen found
            if(currSpace == 1)
            {
               error++; 
            }
            
            x++;
            y++;
        }
    }
    
    /**
     * Set the current board to be evaluated
     * @param b 
     */
    public void setBoard(int[][] b)
    {
        this.board = b;
        this.max = Queens.n-1;
    }
    
}
