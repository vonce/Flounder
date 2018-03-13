/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import java.util.Arrays;
import java.util.BitSet;

/**
 *
 * @author Vince
 */
public class Tools {
    static BitSet h = new BitSet(52);
    static int count;
    static String[] s;
    public static BitSet cardtobit(String[] cardarr){//convert card string array to bitset
        h.clear();//fills 52 bitset with false
        for (String card : cardarr){//for every card string look in hashmap for index number 0-51(A-2)
            h.set(Deck.cardtoint.get(card));//change bit in bitset to true with index number
        }
        return h;
    }
    
    public static String[] bittocard(BitSet bitarr){//convert bool array to card string array
        count = 0; 
        for (int i = 0; i < 52; i++){//count number of true in bool array
            if (bitarr.get(i) == true){
                count++;
            }
        }
        int[] cardnum = new int[count];//index number array with size count
        String[] s = new String[count];//string array with size count
        int j = 0;
        for (int i = 0; i < 52; i++){//add index number to array
            if (bitarr.get(i) == true){
                cardnum[j] = i;
                j++;
            }
        }
        for (int i = 0; i < cardnum.length; i++){//use index numbers to find corresponding card string array
            s[i] = Deck.inttocard.get(cardnum[i]);
        }
        return s;
    }
    
    public static int[] bittonum(BitSet bitarr){//finds the card number array from bitset
        count = 0; 
        int j = 0;
        for (int i = 0; i < 52; i++){//counts size of all cards
            if (bitarr.get(i) == true){
                count++;
            }
        }
        int[] numarr = new int[count];
        for (int i = 0; i < 52; i++){
            if (bitarr.get(i) == true){
                numarr[j] = i;
                j++;
            }
        }
        return numarr;
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

    public static BitSet add(BitSet...cards){//ors 2 or more bitsets
        h.clear();
        for (BitSet i : cards){
            h.or(i);
        }
        return h;
    }
}
