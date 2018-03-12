/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import java.util.HashMap;
/**
 *
 * @author Vince
 */
public class Deck {
    static HashMap<String, Integer> cardtoint = new HashMap<>();
    static HashMap<Integer, String> inttocard = new HashMap<>();
    static String[] deck = new String[52];
    public static void main(){//Iterates through all values and suits and creates an array with 52 cards
        char suitchar = 'c';
        char valchar = '2';
        int i = 0;
        char[] card = new char[2];
        for(int val = 14; val >= 2; val--){
            for(int suit = 0; suit <= 3; suit++){
                if(suit == 0){suitchar = 'c';}
                if(suit == 1){suitchar = 'd';}
                if(suit == 2){suitchar = 'h';}
                if(suit == 3){suitchar = 's';}
                if(val <= 9){valchar = Integer.toString(val).charAt(0);}
                else if(val > 9){
                    if(val == 10){valchar = 'T';}
                    if(val == 11){valchar = 'J';}
                    if(val == 12){valchar = 'Q';}
                    if(val == 13){valchar = 'K';}
                    if(val == 14){valchar = 'A';}
                }
                card[0] = valchar;
                card[1] = suitchar;
                deck[i] = new String(card);
                cardtoint.put(deck[i], i);//puts values for hashmap for strings and index int values
                inttocard.put(i, deck[i]);//reversed of above
                i++;  
            }    
        }
    }
}
