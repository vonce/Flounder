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
        //GenerateHash.generatehashhandrank(5);
        //GenerateHash.generatehashhandrank(6);
        //GenerateHash.generatehashhandrank(7);
        GenerateHash.generatehasheffectivehandpercentile(3);
        GenerateHash.generatehasheffectivehandpercentile(4);
        GenerateHash.generatehashboardtexture(3);
        GenerateHash.generatehashboardtexture(4);
        
        System.out.println("Hello, World!");
        // TODO code application logic here
        Calculate calc = new Calculate();
        
        //int[] array = new int[7463];
        String[] c = {"Jh","Th","9h","2c"};
        String[] g = {"3d","2h","Kc","Ad"};
        //String[] h = {"Ah","3s"};
        String[] z = {"Qc","Jc"};
        String[] x = {"3h","6h"};
        String[] k = {"3c","6c"};
        //String[] l = {"8s","7s"};
        //String[] m = {"8c","7c"};
        //String[] n = {"6s","5s"};
        //String[] o = {"6c","5c"};
        //String[][] allhands = {x, g, z};
        //Combinator combo = new Combinator(7);
        for (int i = 0; i < 1000; i++){
        System.out.println("boardtexture: " + calc.effectivepercentile(z,c));
        }
        //System.out.println("boardtexture: " + calc.boardtexture(c));
        //System.out.println("boardtexture: " + calc.boardtexturelookup(g));
        //System.out.println("boardtexture: " + calc.boardtexture(g));
        //System.out.println("boardtexture: " + calc.boardtexture(c));
        //System.out.println("boardtexture: " + calc.boardtexture(c));
        //BitSet h = new BitSet(52);
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

        //System.out.println(Calculate.boardtexture(c));
        //float[][] array = calc.equity(c, allhands);
        //for (float[] i: array){
        //    for (float j: i){
        //        System.out.print(j + " ");
        //    }
        //    System.out.println(" ");
        //}
        //System.out.println(" ");
        //array = Calculate.equity(c, q, r);

        //String[] a = {"As","Ah","Ks","Qd","Jd"};
        //String[] b = {"As","Ks","Qs","9s","8d"};
        //System.out.println("a: " + Handranker.handrank(Tools.cardtobit(a)));
        //System.out.println("b: " + Handranker.handrank(Tools.cardtobit(b)));
    }
}
