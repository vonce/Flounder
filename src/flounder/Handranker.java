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
    public static int choose(int n, int k){//combinator n!/(k!(n-k)!)
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
    
    public static int handrank(boolean[] hand){//ranks hands returning value between 1 and 7462, there are 7462 unique ranks for 5 card hands
        count = 0; 
        for (int i = 0; i < 52; i++){//counts size of hand
            if (hand[i] == true){
                count++;
            }
        }
        int j = 0;
        int[] cardnumber = new int[count];//creates array with size of hand
        for (int i = 0; i < 52; i++){
            if (hand[i] == true){
                cardnumber[j] = i;
                j++;
            }
        }
        //return straightflush(hand, cardnumber);
        //return fourkind(hand, cardnumber);
        //return fullhouse(hand, cardnumber);
        //return flush(hand, cardnumber);
        //return straight(hand, cardnumber);
        //return threekind(hand, cardnumber);
        return twopair(hand, cardnumber);
    }
    
    private static int straightflush(boolean[] boolarr, int[] cardnum){//Assess cards for straight flush. returns 1-10
        count = 0;
        for (int num : cardnum){//for every card starting from highest value lowest index (0:A - 51:2)
            count = 0;
            for (int i = 1; i < 5; i++){//from num card, add to count if next card with same suit == true
                if ((num < 36) && (boolarr[num + i * 4] == true)){
                    count++;
                }
                if ((num >= 36) && (num < 40) && (i < 4) && (boolarr[num + i * 4] == true)){//wheel case
                    count++;
                }
                if ((num >= 36) && (num < 40) && (count == 3) && (boolarr[num % 4])){//count wrap around ace
                    count++;
                }
                if (count == 4){//once 4 are counted return highest value lowest index
                    return ((num - num % 4)/4) + 1;//return 1-10
                }
            }
        }
        return -1;
    }
    private static int fourkind(boolean[] boolarr, int[] cardnum){//Assess cards for four of a kind. returns 1-156
        count = 0;
        for (int num : cardnum){
            count = 0;
            for (int i = 0; i < 4; i++){//count from multiple of 4 for 4 trues
                if (boolarr[num - num % 4 + i] == true){
                    count++;
                }
                if (count == 4){
                    rankarray[0] = ((num - num % 4)/4);//get value of four of a kind
                    for (int num2 : cardnum){//if 4 trues are found, find kicker 
                        if (num - num % 4 != num2 - num2 % 4){
                            if (num < num2){rankarray[1] = ((num2 - num2 % 4)/4) - ((num - num % 4)/4);}//adjust number ranking based on if quad or kicker is higher
                            if (num > num2){rankarray[1] = ((num2 - num2 % 4)/4) - ((num - num % 4)/4) + 1;}
                            return rankarray[0] * 13 + rankarray[1];//return 1-156
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
                if (boolarr[num - num % 4 + i] == true){//count from multiple of 4 for 3 trues
                    count++;
                }
                if (count >= 3){
                    rankarray[0] = ((num - num % 4)/4);
                    for (int num2 : cardnum){
                        if (num - num % 4 != num2 - num2 % 4){//find pair != trip value
                            count = 0;
                            for (int j = 0; j < 4; j++){
                                if (boolarr[num2 - num2 % 4 + j] == true){//count from multiple of 4 for 2 trues
                                    count++;
                                }
                                if (count >= 2){
                                    if (num < num2){rankarray[1] = ((num2 - num2 % 4)/4) - ((num - num % 4)/4);}
                                    if (num > num2){rankarray[1] = ((num2 - num2 % 4)/4) - ((num - num % 4)/4) + 1;}
                                    return rankarray[0] * 13 + rankarray[1];//return 1-156
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
        int sum;
        int k = 0;
        int c = 0;
        for (int num : cardnum){
            count = 0;
            for (int i = 0; i < 13; i++){//check within suit for flush
                if ((num + 4 * i < 52) && (boolarr[num + 4 * i] == true)){
                    sum = 0;
                    rankarray[count] = ((num + 4 * i) + (num + 4 * i) % 4)/4;//record value of each flush card
                    if (count == 0){
                        c = rankarray[count];//removing straights from the final number means we need to reduce the score based on highest card. 
                        if (c != 0){//Aces have 2 instances where it will be the highest card, others have 1, so from A to K we have to add our extra straight
                            c++;
                        }
                    }
                    for (int j = k; j < rankarray[count]; j++){//summing combination totals to get accurate ranks
                        sum = sum + choose(Math.abs(j - 12), Math.abs(count - 4));
                    }
                    k = rankarray[count] + 1;
                    if (count == 0){rankarray[count] = sum -rankarray[count];}
                    rankarray[count] = sum;
                    count++;
                }
                if (count == 5){
                    return rankarray[0] + rankarray[1] + rankarray[2] + rankarray[3] + rankarray[4] - c;//return 1-1277
                }
            }
        }
        return -1;
    }

    private static int straight(boolean[] boolarr, int[] cardnum){//Assess cards for straight. returns 1-10
        count = 0;
        for (int num : cardnum){
            count = 0;
            for (int i = 1; i < 5; i++){
                if (num < 36){
                    for (int j = 0; j < 4; j++){//from starting card, check next multiple of 4 for a true
                        if (boolarr[num - num % 4 + i * 4 + j] == true){
                            count++;
                            break;
                        }
                    }
                }
                if ((num >= 36) && (num < 40) && (i < 4)){//wheel
                    for ( int j = 0; j < 4; j++){
                        if (boolarr[num - num % 4 + i * 4 + j] == true){
                            count++;
                            break;
                        }
                    }
                }
                if ((num >= 36) && (num < 40) && (count == 3)){//count A for wheel case
                    for ( int j = 0; j < 4; j++){
                        if (boolarr[num - num % 4 + j] == true){
                            count++;
                            break;
                        }
                    }
                }
                if (count == 4){
                    return ((num - num % 4)/4) + 1;//return 1-10
                }
            }
        }
        return -1;
    }
    
    private static int threekind(boolean[] boolarr, int[] cardnum){//Assess cards for three of a kind. returns 1-858
        for (int num : cardnum){
            int sum = 0;
            count = 0;
            for (int i = 0; i < 4; i++){//check for 3 trues within card value
                if (boolarr[num - num % 4 + i] == true){
                    count++;
                }
                if (count == 3){
                    rankarray[0] = ((num - num % 4)/4);
                    for (int num2 : cardnum){
                        if (num - num % 4 != num2 - num2 % 4){
                            rankarray[1] = ((num2 - num2 % 4)/4);//kicker
                            if (rankarray[1] < rankarray[0]){rankarray[1]++;}
                            for (int j = 1; j < rankarray[1]; j++){//summing combination totals to get accurate ranks
                                sum = sum + Math.abs(j - 11);
                            }
                            rankarray[1] = sum;
                            for (int num3 : cardnum){
                                if ((num - num % 4 != num3 - num3 % 4) && (num2 - num2 % 4 != num3 - num3 % 4)){
                                    rankarray[2] = ((num3 - num3 % 4)/4);//kicker2
                                    if (rankarray[2] < rankarray[0]){rankarray[2]++;}
                                    return rankarray[0] * 66 + rankarray[1] + rankarray[2] - 1;//return 1-858
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    private static int twopair(boolean[] boolarr, int[] cardnum){//Assess cards for two pair. returns 1-858
        for (int num : cardnum){
            int sum = 0;
            count = 0;
            for (int i = 0; i < 4; i++){//check for 2 trues within card value
                if (boolarr[num - num % 4 + i] == true){
                    count++;
                }
                if (count == 2){
                    rankarray[0] = ((num - num % 4)/4);
                    for (int num2 : cardnum){
                        count = 0;
                        for (int j = 0; j < 4; j++){//check for 2 trues within card value
                            if ((boolarr[num2 - num2 % 4 + j] == true) && (num - num % 4 != num2 - num2 % 4)){
                                count++;
                            }
                            if (count == 2){
                                rankarray[1] = ((num2 - num2 % 4)/4) - 1;
                                for (int num3 : cardnum){
                                    if ((num - num % 4 != num3 - num3 % 4) && (num2 - num2 % 4 != num3 - num3 % 4)){
                                    rankarray[2] = ((num3 - num3 % 4)/4);//kicker
                                    if (rankarray[2] < rankarray[1]){rankarray[2]++;}
                                    if (rankarray[2] < rankarray[0]){rankarray[2]++;}
                                    for (int k = 1; k < rankarray[0]; k++){//summing combination totals to get accurate ranks
                                        sum = sum + Math.abs(k - 11);
                                    }
                                    rankarray[0] = sum;
                                    for (int a : rankarray){
                                        System.out.println("a" + a);
                                    }
                                    return (rankarray[0] + rankarray[1]) * 11 + rankarray[2] - 1;//return 1-858
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    return -1;
    }
}
