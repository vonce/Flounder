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
public class Handranker { 
    static BitSet h = new BitSet(52);
    static int[] rankarray = new int[5];
    static int count;
    static int sum;
    static int rank;
    public static int handrank(BitSet hand){//ranks hands returning value between 1 and 7462, there are 7462 unique ranks for 5 card hands
        int[] numarr = Tools.bittonum(hand);
        rank = straightflush(hand, numarr);
        if (rank != -1){return rank;}
        rank = fourkind(hand, numarr);
        if (rank != -1){return rank + 10;}
        rank = fullhouse(hand, numarr);
        if (rank != -1){return rank + 166;}
        rank = flush(hand, numarr);
        if (rank != -1){return rank + 322;}
        rank = straight(hand, numarr);
        if (rank != -1){return rank + 1599;}
        rank = threekind(hand, numarr);
        if (rank != -1){return rank + 1609;}
        rank = twopair(hand, numarr);
        if (rank != -1){return rank + 2467;}
        rank = pair(hand, numarr);
        if (rank != -1){return rank + 3325;}
        rank = highcard(hand);
        return rank + 6185;
    }
    
    private static int straightflush(BitSet bitarr, int[] cardnum){//Assess cards for straight flush. returns 1-10
        for (int num : cardnum){
            count = 0;
            for (int i = 1; i < 5; i++){//from num card, add to count if next card with same suit == true
                if ((num < 36) && (bitarr.get(num + i * 4) == true)){
                    count++;
                }
                if ((num >= 36) && (num < 40) && (i < 4) && (bitarr.get(num + i * 4) == true)){//wheel case
                    count++;
                }
                if ((num >= 36) && (num < 40) && (count == 3) && (bitarr.get(num % 4))){//count wrap around ace
                    count++;
                }
                if (count == 4){//once 4 are counted return highest value lowest index
                    return ((num - num % 4)/4) + 1;//return 1-10
                }
            }
        }
        return -1;
    }
    private static int fourkind(BitSet bitarr, int[] cardnum){//Assess cards for four of a kind. returns 1-156
        for (int num : cardnum){
            count = 0;
            for (int i = 0; i < 4; i++){//count from multiple of 4 for 4 trues
                if (bitarr.get(num - num % 4 + i) == true){
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
    private static int fullhouse(BitSet bitarr, int[] cardnum){//Assess cards for fullhouse. returns 1-156
        for (int num : cardnum){
            count = 0;
            for (int i = 0; i < 4; i++){
                if (bitarr.get(num - num % 4 + i) == true){//count from multiple of 4 for 3 trues
                    count++;
                }
                if (count >= 3){
                    rankarray[0] = ((num - num % 4)/4);
                    for (int num2 : cardnum){
                        if (num - num % 4 != num2 - num2 % 4){//find pair != trip value
                            count = 0;
                            for (int j = 0; j < 4; j++){
                                if (bitarr.get(num2 - num2 % 4 + j) == true){//count from multiple of 4 for 2 trues
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
    private static int flush(BitSet bitarr, int[] cardnum){//Assess cards for flush. returns 1-1277
        int k = 0;
        int c = 0;
        count = 0;
        for (int num : cardnum){
            count = 0;
            for (int i = 0; i < 13; i++){//check within suit for flush
                if ((num + 4 * i < 52) && (bitarr.get(num + 4 * i) == true)){
                    rankarray[count] = (num - num % 4 + 4 * i)/4;//record value of each flush card
                    count++;
                    if (count >= 5){
                        for (count = 0; count < 5; count++){
                            sum = 0;
                            if (count == 0){
                                c = rankarray[count];//removing straights from the final number means we need to reduce the score based on highest card. 
                                if (c != 0){//Aces have 2 instances where it will be the highest card, others have 1, so from A to K we have to add our extra straight
                                    c++;
                                }
                            }
                            for (int j = k; j < rankarray[count]; j++){//summing combination totals to get accurate ranks
                                sum = sum + Tools.choose(Math.abs(j - 12), Math.abs(count - 4));
                            }
                            k = rankarray[count] + 1;
                            rankarray[count] = sum;
                            if (count == 4){
                                return rankarray[0] + rankarray[1] + rankarray[2] + rankarray[3] + rankarray[4] - c;//return 1-1277
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    private static int straight(BitSet bitarr, int[] cardnum){//Assess cards for straight. returns 1-10
        for (int num : cardnum){
            count = 0;
            for (int i = 1; i < 5; i++){
                if (num < 36){
                    for (int j = 0; j < 4; j++){//from starting card, check next multiple of 4 for a true
                        if (bitarr.get(num - num % 4 + i * 4 + j) == true){
                            count++;
                            break;
                        }
                    }
                }
                if ((num >= 36) && (num < 40) && (i < 4)){//wheel
                    for ( int j = 0; j < 4; j++){
                        if (bitarr.get(num - num % 4 + i * 4 + j) == true){
                            count++;
                            break;
                        }
                    }
                    for ( int j = 0; j < 4; j++){
                        if ((bitarr.get(j) == true) && (count == 3)){
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
    
    private static int threekind(BitSet bitarr, int[] cardnum){//Assess cards for three of a kind. returns 1-858
        for (int num : cardnum){
            sum = 0;
            count = 0;
            for (int i = 0; i < 4; i++){//check for 3 trues within card value
                if (bitarr.get(num - num % 4 + i) == true){
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
    
    private static int twopair(BitSet bitarr, int[] cardnum){//Assess cards for two pair. returns 1-858
        for (int num : cardnum){
            sum = 0;
            count = 0;
            for (int i = 0; i < 4; i++){//check for 2 trues within card value
                if (bitarr.get(num - num % 4 + i) == true){
                    count++;
                }
                if (count == 2){
                    rankarray[0] = ((num - num % 4)/4);
                    for (int num2 : cardnum){
                        count = 0;
                        for (int j = 0; j < 4; j++){//check for 2 trues within card value
                            if ((bitarr.get(num2 - num2 % 4 + j) == true) && (num - num % 4 != num2 - num2 % 4)){
                                count++;
                            }
                            if (count == 2){
                                rankarray[1] = ((num2 - num2 % 4)/4);
                                for (int num3 : cardnum){
                                    if ((num - num % 4 != num3 - num3 % 4) && (num2 - num2 % 4 != num3 - num3 % 4)){
                                    rankarray[2] = ((num3 - num3 % 4)/4);//kicker
                                    if (rankarray[1] >= rankarray[2]){rankarray[2]++;}
                                    if (rankarray[0] >= rankarray[2]){rankarray[2]++;}
                                    for (int k = 0; k < rankarray[0]; k++){//summing combination totals to get accurate ranks
                                        sum = sum + Math.abs(k - 12);
                                    }
                                    rankarray[1] = rankarray[1] - rankarray[0] - 1;
                                    rankarray[0] = sum;
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
    
    private static int pair(BitSet bitarr, int[] cardnum){//Assess cards for pair. returns 1-2860
        for (int num : cardnum){
            int l = 1;
            sum = 0;
            count = 0;
            for (int i = 0; i < 4; i++){//check for 2 trues within card value
                if (bitarr.get(num - num % 4 + i) == true){
                    count++;
                }
                if (count == 2){
                    rankarray[0] = (num - num % 4)/4;
                    for (int num2 : cardnum){
                        if (rankarray[0] != (num2 - num2 % 4)/4){//find highest kicker cards
                            rankarray[count - 1] = (num2 - num2 % 4)/4;
                            count++;
                            if (count == 5){//once we have 5 cards total, find combinations to get proper ranks
                                for (int j = 1; j < 4; j++){
                                    sum = 0;
                                    if (rankarray[j] < rankarray[0]){
                                        rankarray[j]++;
                                    }
                                    for (int k = l; k < rankarray[j]; k++){//summing combination totals to get accurate ranks
                                        sum = sum + Tools.choose(Math.abs(k - 12), Math.abs(j - 3));
                                    }
                                    l = rankarray[j] + 1;
                                    rankarray[j] = sum;
                                }
                                return rankarray[0] * 220 + rankarray[1] + rankarray[2] + rankarray[3] + 1;
                            }
                        }
                    }    
                }
            }
        }
    return -1;
    }
    
    private static int highcard(BitSet bitarr){//Assess cards for best high cards. returns 1-1277
        int k = 0;
        int bitnum = -1;
        int l = 0;
        for (int i = 0; i < bitarr.cardinality(); i++){
            bitnum = bitarr.nextSetBit(bitnum + 1);
            sum = 0;
            rankarray[i] = (bitnum - bitnum % 4)/4;//record value of each high card
            if (i == 0){
                l = rankarray[0];//removing straights from the final number means we need to reduce the score based on highest card. 
                if (rankarray[0]!= 0){//Aces have 2 instances where it will be the highest card, others have 1, so from A to K we have to add our extra straight
                    l++;
                }
            }
            for (int j = k; j < rankarray[i]; j++){//summing combination totals to get accurate ranks
                sum = sum + Tools.choose(Math.abs(j - 12), Math.abs(i - 4));
            }
            k = rankarray[i] + 1;
            rankarray[i] = sum;
            if (i == 4){
                return rankarray[0] + rankarray[1] + rankarray[2] + rankarray[3] + rankarray[4] - l;//return 1-1277
            }
        }
        return -1;
    }
}
