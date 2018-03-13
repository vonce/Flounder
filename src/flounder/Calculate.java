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
public class Calculate {
    static BitSet h = new BitSet(52);
    static BitSet hh = new BitSet(52);
    static double[] rankings = new double[8];
    static int r = 0;
    static String[] strarr;
    public static double[] equity(String[] board, String[] ... hands){
        BitSet[] bh = new BitSet[hands.length];
        double[] wins = new double[hands.length];
        int rank;
        int rankindex;
        for (String i: hands[1]){
            System.out.println(i);
        }
        for (String[] i: hands){
            h.or(Tools.cardtobit(i));
            h.or(Tools.cardtobit(board));
        }
        BitSet b = Tools.cardtobit(board);
        for (int i = 0; i < hands.length; i++){
            //bh[i] = Tools.cardtobit(hands[i]);
        }
        bh[0] = Tools.cardtobit(hands[0]);
        bh[1] = Tools.cardtobit(hands[1]);
        for (String i: Tools.bittocard(bh[0])){
            System.out.println(":::" + i);
        }
        Combinator combo = new Combinator(5 - board.length, h);
        for (String i: Tools.bittocard(bh[0])){
            System.out.println(":" + i);
        }
        for (int i = 0; i < combo.alliter; i++){
            h.clear();
            r = 7643;
            rank = 7643;
            rankindex = 0;

            hh = combo.combinations();
            
            hh.or(Tools.cardtobit(board));
            for (String s: Tools.bittocard(bh[0])){
                System.out.println("::" + s);
            }
            for (int j = 0; j < hands.length; j++){
                h.or(bh[j]);
                for (String k: Tools.bittocard(h)){
                    System.out.print("!"+ k);
                }
                System.out.println(" ");
                r = Handranker.handrank(hh);
                System.out.println(r);
                if (rank > r){
                    rank = r;
                    rankindex = j;
                }
            }
            wins[rankindex]++;
        }
        return wins;
    }
    public static double[] equity(boolean board, String[] ... hands){
        h.clear();
        return equity(strarr, strarr);
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
