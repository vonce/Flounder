/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import java.io.IOException;
import java.util.BitSet;


/**
 *
 * @author Vince
 */
public class Flounder {
    public static void main(String[] args) throws IOException {
        //Handranker.generatehash();
        System.out.println("Hello, World!");
        // TODO code application logic here
        Calculate calc = new Calculate();
        //int[] array = new int[7463];
        String[] c = {};
        String[] f = {"7h","6h"};
        String[] g = {"7s","8s"};
        //String[] h = {"Ah","3s"};
        //String[] z = {"Qc","Jc"};
        //String[] x = {"3h","6h"};
        //String[] k = {"3c","6c"};
        //String[] l = {"8s","7s"};
        //String[] m = {"8c","7c"};
        //String[] n = {"6s","5s"};
        //String[] o = {"6c","5c"};
        String[][] allhands = {f, g};
        Combinator combo = new Combinator(7);
        BitSet h = new BitSet(52);
        Handranker hr = new Handranker();
        /*for (int i = 0; i < combo.alliter; i++){//test for lookup table
            h = combo.combinations();
            if (hr.handrank(h) != hr.handranklookup7(h)){
                System.out.println("!!!!!!");
            }
            if (i % 1000000 == 0){
                System.out.println(i);
            }
        }*/
        //for (int i: array){
        //    System.out.println(i);
        //}
        //System.out.println(Calculate.rankpercentile(g, c));
        //System.out.println(Calculate.handpercentile(g, c));
        //System.out.println(Calculate.boardtexture(c));
        float[][] array = calc.equity(c, allhands);
        for (float[] i: array){
            for (float j: i){
                System.out.print(j + " ");
            }
            System.out.println(" ");
        }
        //System.out.println(" ");
        //array = Calculate.equity(c, q, r);

        //String[] a = {"As","Ah","Ks","Qd","Jd"};
        //String[] b = {"As","Ks","Qs","9s","8d"};
        //System.out.println("a: " + Handranker.handrank(Tools.cardtobit(a)));
        //System.out.println("b: " + Handranker.handrank(Tools.cardtobit(b)));
    }
}
