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
public class Handranker { 
    static boolean[] h = new boolean[52];
    static int[] rankarray = new int[5];
    static int count;
    public static boolean[] cardtobool(String[] cardarr){//convert cards to bool array
        Arrays.fill(h, false);
        for (String card : cardarr){
            h[Deck.cardtoint.get(card)] = true;
        }
        return h;
    }
    
    public static String[] booltocard(boolean[] boolarr){//convert bool array to cards
        count = 0; 
        for (int i = 0; i < 52; i++){
            if (boolarr[i] == true){
                count++;
            }
        }
        int[] cardnum = new int[count]; 
        String[] s = new String[count];
        int j = 0;
        for (int i = 0; i < 52; i++){
            if (boolarr[i] == true){
                cardnum[j] = i;
                j++;
            }
        }
        for (int i = 0; i < cardnum.length; i++){
            s[i] = Deck.inttocard.get(cardnum[i]);
        }
        return s;
    }
    
    public static int handrank(boolean[] hand){//ranks hands returning value between 1 and 7462, there are 7462 unique rankings
        count = 0; 
        for (int i = 0; i < 52; i++){
            if (hand[i] == true){
                count++;
            }
        } 
        int j = 0;
        int[] cardnumber = new int[count];
        for (int i = 0; i < 52; i++){
            if (hand[i] == true){
                cardnumber[j] = i;
                j++;
            }
        }
        //return straightflush(hand, cardnumber);
        //return fourkind(hand, cardnumber);
        //return fullhouse(hand, cardnumber);
        return flush(hand, cardnumber);
    }
    
    private static int straightflush(boolean[] boolarr, int[] cardnum){//Assess cards for straight flush. returns 1-10
        count = 0;
        for (int num : cardnum){
            count = 0;
            for (int i = 1; i < 5; i++){
                if ((num < 36) && (boolarr[num + i * 4] == true)){
                    count++;
                }
                if ((num >= 36) && (num < 40) && (i < 4) && (boolarr[num + i * 4] == true)){
                    count++;
                }
                if ((num >= 36) && (num < 40) && (count == 3) && (boolarr[num % 4])){
                    count++;
                }
                if (count == 4){
                    return ((num - num % 4)/4) + 1;
                }
            }
        }
        return -1;
    }
    private static int fourkind(boolean[] boolarr, int[] cardnum){//Assess cards for four of a kind. returns 1-156
        count = 0;
        for (int num : cardnum){
            count = 0;
            for (int i = 0; i < 4; i++){
                if (boolarr[num - num % 4 + i] == true){
                    count++;
                }
                if (count == 4){
                    rankarray[0] = ((num - num % 4)/4);
                    for (int num2 : cardnum){
                        if (num - num % 4 != num2 - num2 % 4){
                            if (num < num2){rankarray[1] = ((num2 - num2 % 4)/4) - ((num - num % 4)/4);}
                            if (num > num2){rankarray[1] = ((num2 - num2 % 4)/4) - ((num - num % 4)/4) + 1;}
                            return rankarray[0] * 13 + rankarray[1];
                        }
                    }
                }
            }
        }
        return -1;
    }
    private static int fullhouse(boolean[] boolarr, int[] cardnum){//Assess cards for fullhouse. returns 1-156
        count = 0;
        for (int num : cardnum){
            count = 0;
            for (int i = 0; i < 4; i++){
                if (boolarr[num - num % 4 + i] == true){
                    count++;
                }
                if (count >= 3){
                    rankarray[0] = ((num - num % 4)/4);
                    for (int num2 : cardnum){
                        if (num - num % 4 != num2 - num2 % 4){
                            count = 0;
                            for (int j = 0; j < 4; j++){
                                if (boolarr[num2 - num2 % 4 + j] == true){
                                    count++;
                                }
                                if (count >= 2){
                                    if (num < num2){rankarray[1] = ((num2 - num2 % 4)/4) - ((num - num % 4)/4);}
                                    if (num > num2){rankarray[1] = ((num2 - num2 % 4)/4) - ((num - num % 4)/4) + 1;}
                                    return rankarray[0] * 13 + rankarray[1];
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }
    private static int flush(boolean[] boolarr, int[] cardnum){//Assess cards for flush. returns 1-1277
        for (int num : cardnum){
            count = 0;
            for (int i = 0; i < 13; i++){
                if ((num + 4 * i < 52) && (boolarr[num + 4 * i] == true)){
                    rankarray[count] = ((num + 4 * i) + (num + 4 * i) % 4)/4;
                    count++;
                }
                if (count == 5){
                    for (int a : rankarray){
                        System.out.println(a);
                    }
                    return rankarray[0] * 99 + rankarray[1] * 9 + rankarray[2]  + rankarray[3] + rankarray[4];
                }
            }
        }
        return -1;
    }
}
