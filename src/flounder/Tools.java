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
public class Tools {
    static boolean[] h = new boolean[52];
    static int count;
    static String[] s;
    public static boolean[] cardtobool(String[] cardarr){//convert card string array to bool array
        Arrays.fill(h, false);//fills 52 bool array with false
        for (String card : cardarr){//for every card string look in hashmap for index number 0-51(A-2)
            h[Deck.cardtoint.get(card)] = true;//change boolean in bool array to true with index number
        }
        return h;
    }
    
    public static String[] booltocard(boolean[] boolarr){//convert bool array to card string array
        count = 0; 
        for (int i = 0; i < 52; i++){//count number of true in bool array
            if (boolarr[i] == true){
                count++;
            }
        }
        int[] cardnum = new int[count];//index number array with size count
        String[] s = new String[count];//string array with size count
        int j = 0;
        for (int i = 0; i < 52; i++){//add index number to array
            if (boolarr[i] == true){
                cardnum[j] = i;
                j++;
            }
        }
        for (int i = 0; i < cardnum.length; i++){//use index numbers to find corresponding card string array
            s[i] = Deck.inttocard.get(cardnum[i]);
        }
        return s;
    }
    
    public static int choose(int n, int k){//n choose k = n!/(k!(n-k)!)
        int numer = 1;
        int denom = 1;
        for (int i = n - k + 1; i <= n; i++){
            numer = numer * i;
        }
        for (int i = 1; i <= k; i++){
            denom = denom * i;
        }
        return numer/denom;
    }
    public static boolean[] adder(boolean[]...cards){//finds the card array from boolean(s) for removal
        count = 0; 
        int j = 0;
        boolean[] boolarr = new boolean[52];
        for (boolean[] c: cards){
            for (int i = 0; i < 52; i++){//counts size of all cards
                if (c[i] == true){
                    count++;
                }
            }
        }
        for (boolean[] c: cards){
            for (int i = 0; i < 52; i++){
                if (c[i] == true){
                    boolarr[j] = true;
                    j++;
                }
            }
        }
        return boolarr;
    }
    public static int[] booltonum(boolean[] boolarr){//finds the card array from boolean(s) for removal
        count = 0; 
        int j = 0;
        for (int i = 0; i < 52; i++){//counts size of all cards
            if (boolarr[i] == true){
                count++;
            }
        }
        int[] numarr = new int[count];
        for (int i = 0; i < 52; i++){
            if (boolarr[i] == true){
                numarr[j] = i;
                j++;
            }
        }
        return numarr;
    }   
}
