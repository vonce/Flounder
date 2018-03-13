/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import java.util.BitSet;
import java.io.*;

/**
 *
 * @author Vince
 */
public class Combinator{
    Combinator(int num){
        this.n = num;
        this.alliter = Tools.choose(52, num);
        this.numarr = new int[52];
        this.removal = Tools.bittonum(h);
        this.iterarr = new int[num];
        for(int i = 0; i < 52; i++){
            this.numarr[i] = i;
        }
    }
    Combinator(int num, BitSet r){
        this.n = num;
        this.alliter = Tools.choose(52 - r.cardinality(), num);
        this.numarr = new int[52 - r.cardinality()];
        this.removal = Tools.bittonum(r);
        this.iterarr = new int[num];
        this.k = 0;
        for (int i = 0; i < 52; i++){
            numarr[i - k] = i;
            for (int j : removal){
                if (j == i){
                    k++;
                    break;
                }
            }
        }
    }
    static int n;
    static BitSet h = new BitSet(52);
    static int alliter;
    static int[] numarr;
    static int[] removal;
    static int[] iterarr;
    static int k;
    static int iter = 0;
    
    public static BitSet combinations(){
        h.clear();
        if (iter == 0){
            for (int i = 0; i < iterarr.length; i++){
                h.set(numarr[i]);
                iterarr[i] = i;
            }
            iter++;
            return h;
        }
        if (iter < alliter){
            for (int i = 0; i < iterarr.length; i++){
                if ((iterarr[i] == numarr.length + (i - n)) && (i - 1 >= 0)){
                    iterarr[i - 1]++;
                    iterarr[i] = iterarr[i - 1];
                }
            }
        iterarr[n - 1]++;
        iter++;
            for (int i: iterarr){
                h.set(numarr[i]);
            }
        }
        return h;
    }
}
