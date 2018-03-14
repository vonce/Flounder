/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import java.util.BitSet;
import java.util.Arrays;
/**
 *
 * @author Vince
 */
public class Calculate {
    static BitSet h = new BitSet(52);
    static double[] rankings = new double[8];
    static int r = 0;
    static String[] strarr;
    public static double[][] equity(String[] board, String[] ... hands){
        h.clear();
        boolean tie = false;
        BitSet[] bh = new BitSet[hands.length];
        double[][] wins = new double[hands.length][2];
        int[] rankarr = new int[hands.length];
        int rankindex = 0;
        for (String[] i: hands){
            h.or(Tools.cardtobit(i));
            h.or(Tools.cardtobit(board));
        }
        for (int i = 0; i < hands.length; i++){
            bh[i] = new BitSet(52);
            bh[i].or(Tools.cardtobit(hands[i]));
        }
        Combinator combo = new Combinator(5 - board.length, h);
        for (int i = 0; i < combo.alliter; i++){
            tie = false;
            h.clear();
            r = 7643;
            Arrays.fill(rankarr, 0);
            rankindex = -1;

            h = combo.combinations();
            h.or(Tools.cardtobit(board));
            for (int j = 0; j < hands.length; j++){
                h.or(bh[j]);
                rankarr[j] = Handranker.handrank(h);
                h.andNot(bh[j]);
            }
            for (int j = 0; j < hands.length; j++){
                if (r > rankarr[j]){
                    r = rankarr[j];
                    rankindex = j;
                }
                else if(r == rankarr[j]){
                    tie = true;
                }
            }
            if (tie == false){
                wins[rankindex][0]++;
                if ((rankindex == 2) || (rankindex == 3)){
                    for (String s: Tools.bittocard(bh[rankindex])){
                        System.out.print(s + " ");
                    }
                    System.out.println(" ");
                    System.out.print(rankindex +": ");
                    for (String s: Tools.bittocard(h)){
                        System.out.print(s + " ");
                    }
                    System.out.println(" ");
                    for (int s: rankarr){
                        System.out.print(s + " ");
                    }
                    System.out.println(" ");
                }
            }
            else{
                for (int j = 0; j < hands.length; j++){
                    if (rankarr[j] == r){
                        wins[j][1]++;
                    }
                }
            }    
        }
        for (int i = 0; i < wins.length; i++){
            //wins[i][0] = wins[i][0]/combo.alliter;
            //wins[i][1] = wins[i][1]/combo.alliter;
        }
        return wins;
    }
    
    public static double boardtexture(BitSet board){
        return 2.0;
    }
    
    public static double handpercentile(BitSet hand, BitSet board){
        return 2.0;
    }
    
    public static double[] rankpercentile(BitSet hand, BitSet board){
        return rankings;
    }
}
