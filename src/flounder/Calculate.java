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
    static Handranker hr = new Handranker();
    static BitSet h = new BitSet(52);
    static float[] rankings = new float[9];
    static int r = 0;
    static String[] strarr;
    static int sum = 0;
    static int rank = 0;
    static int count = 0;
    
    public static float[][] equity(String[] board, String[][] hands){
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
                rankarr[j] = hr.handranklookup(h);
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
        int handrank = hr.handranklookup(Tools.add(hand, board));
        Combinator handcombos = new Combinator(2, h);
        for (int i = 0; i < handcombos.alliter; i++){
            h.clear();
            h.or(handcombos.combinations());
            h.or(board);
            if (handrank < hr.handranklookup(h)){
                percentile++;
            }
        }
        percentile = percentile/handcombos.alliter;
        return percentile;
    }
    
    public static double boardtexture(String[] board){
        BitSet bsboard = new BitSet(52);
        bsboard.or(Tools.cardtobit(board));
        return boardtexture(bsboard);
    }
    public static float boardtexture(BitSet board){
        BitSet b = new BitSet(52);
        BitSet runout = new BitSet(52);
        float texture = 0;
        float avgtexture = 0;
        b.or(board);
        System.out.println(b);
        Combinator handcombos = new Combinator(2, b);
        for (int i = 0; i < handcombos.alliter; i++){
            texture = 0;
            b.clear();
            b.or(board);
            b.or(handcombos.combinations());
            Combinator boardcombos = new Combinator(1, b);
            b.andNot(board);
            //System.out.println("b: " + b);
            float handperc = handpercentile(b, board);
            for (int j = 0; j < boardcombos.alliter; j++){
                runout.clear();
                runout.or(boardcombos.combinations());
                runout.or(board);
                //System.out.println("runout: " + runout);
                texture = texture + (float) Math.pow(handperc - handpercentile(b, runout), 2);
            }
            avgtexture = avgtexture + (float) Math.sqrt(texture)/boardcombos.alliter;
        }
        avgtexture = avgtexture/(handcombos.alliter);
        return avgtexture;
    }
    
    public static float[] rankpercentile(String[] hand, String[] board){
        BitSet bshand = new BitSet(52);
        BitSet bsboard = new BitSet(52);
        bshand.or(Tools.cardtobit(hand));
        bsboard.or(Tools.cardtobit(board));
        return rankpercentile(bshand, bsboard);
    } 
    public static float[] rankpercentile(BitSet hand, BitSet board){
        int rank;
        h.clear();
        h.or(hand);
        h.or(board);
        Combinator handcombos = new Combinator(2, h);
        for (int i = 0; i < handcombos.alliter; i++){
            h.clear();
            h.or(handcombos.combinations());
            h.or(board);
            rank = hr.handranklookup(h);
            if (rank < 10){rankings[8]++;}
            else if (rank < 166){rankings[7]++;}
            else if (rank < 322){rankings[6]++;}  
            else if (rank < 1599){rankings[5]++;}
            else if (rank < 1609){rankings[4]++;}
            else if (rank < 2467){rankings[3]++;}
            else if (rank < 3325){rankings[2]++;}
            else if (rank < 6185){rankings[1]++;}
            else {rankings[0]++;}
        }
        for (int i = 0; i < rankings.length; i++){
            if (i > 0){
                rankings[i] = rankings[i]/handcombos.alliter + rankings[i - 1];
            }
            else{
                rankings[i] = rankings[i]/handcombos.alliter;
            }
        }
        for (int i = 0; i < rankings.length; i++){
            rankings[i] = (float) Math.pow(rankings[i], 2);
        }
        for (float i: rankings){
            System.out.print(i + " ");
        }
        return rankings;
    }
}
