/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import java.util.Arrays;

/**
 *
 * @author Vince
 */
public class Combinator {
    static boolean[] h = new boolean[52];
    static int[] numarr;
    static int number;
    static int iter = 0;
    static int alliter = 0;

    public static boolean[] combinations(int n, int[] r){
        number = n - 1;
        if (iter == 0){
            alliter = Tools.choose(52, n);
            numarr = new int[n];
            for(int i = 0; i < n; i++){
                numarr[i] = i;
            }
        }
        Arrays.fill(h, false);
        for (int num : numarr){
            h[num] = true;
        }
        if (iter == 0){
            iter++;
            return h;
        }
        if (iter < alliter){
            for (int i = 0; i <= number; i++){
                if ((numarr[i] == 51 - number + i) && (i - 1 >= 0)){
                    numarr[i - 1]++;
                    numarr[i] = numarr[i - 1];
                }
            }
            numarr[number]++;
            iter++;
            for (int a : numarr){
                //System.out.println("numarr: " + a);
            }
        }
        else{
            System.out.println("done");
        }
        return h;
    }
    public static void remove(boolean[] boolarr){
        
    }
}
