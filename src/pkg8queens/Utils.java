/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8queens;

/**
 *
 * This class is for calculating
 * unnecessary, but interesting 
 * statistics.
 * 
 * @author kward60
 */
public class Utils {
    
    /**
     * Compute factorial of n
     * @param n
     * @return 
     */
    public long factorial(int n)
    {
        if(n <= 2)
        {
            return n;
        }
        return n * factorial(n);
    }
    
    /**
     * Computes the number of combinations
     * given the set n and subset r
     * @param n
     * @param r
     * @return 
     */
    public long nCr(int n, int r)
    {
        return (factorial(n) / (factorial(r) * factorial(n-r)));
    }
    
}
