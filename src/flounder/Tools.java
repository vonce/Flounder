/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import java.util.BitSet;

/**
 *
 * @author Vince
 */
public class Tools {
    static BitSet h = new BitSet(52);
    static int count;
    static String[] s;
    static Deck deck = new Deck();

    public static BitSet cardtobit(String[] cardarr){//convert card string array to bitset
        h.clear();
        for (String card : cardarr){//for every card string look in hashmap for index number 0-51(A-2)
            h.set(deck.cardtoint.get(card));//change bit in bitset to true with index number
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
            s[i] = deck.inttocard.get(cardnum[i]);
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
        int j = 0;
        long numer = 1;
        long denom = 1;
        for (int i = n - k + 1; i <= n; i++){
            numer = numer * i;
        }
        for (int i = 1; i <= k; i++){
            denom = denom * i;
        }
        numer = numer/denom;
        j = (int) numer;
        return j;
    }

    public static BitSet add(BitSet...cards){//ors 2 or more bitsets
        h.clear();
        for (BitSet i : cards){
            h.or(i);
        }
        return h;
    }
    
    public static int numconv(int value){
        if (value == 0){return 0;}
        if (value == 1){return 1;}
        if (value == 2){return 5;}
        if (value == 3){return 24;}
        if (value == 4){return 112;}
        if (value == 5){return 521;}
        if (value == 6){return 2421;}
        if (value == 7){return 11248;}
        if (value == 8){return 52256;}
        if (value == 9){return 242769;}
        if (value == 10){return 1127845;}
        if (value == 11){return 5239688;}
        if (value == 12){return 24342288;}
        return 0;
    }
}
