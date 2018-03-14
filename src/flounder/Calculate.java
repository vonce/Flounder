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
    
    public static float[][] equity(String[] board, String[] ... hands){
        BitSet bsboard = new BitSet(52);
        BitSet[] bshand = new BitSet[hands.length];
        for (int i = 0; i < hands.length; i++){
            bshand[i] = new BitSet(52);
            bshand[i].or(Tools.cardtobit(hands[i]));
        }
        bsboard.or(Tools.cardtobit(board));
        return equity(bsboard, bshand);
    }
    public static float[][] equity(BitSet board, BitSet[] hands){
        h.clear();
        boolean tie;
        float[][] wins = new float[hands.length][2];
        int[] rankarr = new int[hands.length];
        int rankindex = 0;
        for (BitSet i: hands){
            h.or(i);
        }
        h.or(board);
        Combinator boardcombos = new Combinator(5 - Tools.bittocard(board).length, h);
        for (int i = 0; i < boardcombos.alliter; i++){
            tie = false;
            h.clear();
            r = 7643;
            Arrays.fill(rankarr, 0);
            h.or(boardcombos.combinations());
            h.or(board);
            for (int j = 0; j < hands.length; j++){
                h.or(hands[j]);
                rankarr[j] = Handranker.handrank(h);
                h.andNot(hands[j]);
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
            wins[i][0] = wins[i][0]/boardcombos.alliter;
            wins[i][1] = wins[i][1]/boardcombos.alliter;
        }
        return wins;
    }
    
    public static float handpercentile(String[] hand, String[] board){
        BitSet bshand = new BitSet(52);
        BitSet bsboard = new BitSet(52);
        bshand.or(Tools.cardtobit(hand));
        bsboard.or(Tools.cardtobit(board));
        return handpercentile(bshand, bsboard);
    } 
    public static float handpercentile(BitSet hand, BitSet board){
        h.clear();
        h.or(hand);
        h.or(board);
        float percentile = 0;
        Combinator handcombos = new Combinator(2, h);
        for (int i = 0; i < handcombos.alliter; i++){
            h.clear();
            h.or(handcombos.combinations());
            h.or(board);
            if (Handranker.handrank(Tools.add(hand, board)) <= Handranker.handrank(h)){
                percentile++;
            }
        }
        percentile = percentile/handcombos.alliter;
        return percentile;
    }
    
    public static float boardtexture(String[] board){
        BitSet bsboard = new BitSet(52);
        bsboard.or(Tools.cardtobit(board));
        return boardtexture(bsboard);
    }
    public static float boardtexture(BitSet board){
        BitSet b = new BitSet(52);
        BitSet runout = new BitSet(52);
        float texture = 0;
        float avgtexture = 0;
        float k;
        b.or(board);
        Combinator handcombos = new Combinator(2, b);
        for (int i = 0; i < handcombos.alliter; i++){
            b.clear();
            b.or(handcombos.combinations());
            b.or(board);
            Combinator boardcombos = new Combinator(5 - Tools.bittocard(board).length, b);
            b.andNot(board); 
            for (int j = 0; j < boardcombos.alliter; j++){
                runout.clear();
                runout.or(board);
                runout.or(boardcombos.combinations());
                k = handpercentile(b, board) - handpercentile(b, runout);
                texture = texture + k * k;
            }
            avgtexture = avgtexture + (float) Math.sqrt(texture);
        }
        avgtexture = avgtexture/handcombos.alliter;
        return avgtexture;
    }
    
    public static double[] rankpercentile(BitSet hand, BitSet board){
        return rankings;
    }
}
