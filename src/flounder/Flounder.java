/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;
import java.util.*;
/**
 *
 * @author Vince
 */
public class Flounder {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        // TODO code application logic here
        Deck.main();
        String[] c = {};
        String[] f = {"6d","6c"};
        String[] g = {"7h","7s"};
        String[] h = {"Ah","3s"};
        String[] z = {"Qc","Jc"};
        String[] x = {"3h","6h"};
        String[] k = {"3c","6c"};
        String[] l = {"8s","7s"};
        String[] m = {"8c","7c"};
        String[] n = {"6s","5s"};
        String[] o = {"6c","5c"};
        //System.out.println(Calculate.rankpercentile(g, c));
        //System.out.println(Calculate.handpercentile(g, c));
        //System.out.println(Calculate.boardtexture(c));
        //float[][] array = Calculate.equity(c, f, g);
        //for (float[] i: array){
        //    for (float j: i){
        //        System.out.print(j + " ");
        //    }
        //    System.out.println(" ");
        //}
        //System.out.println(" ");
        //array = Calculate.equity(c, q, r);

        String[] a = {"As","Ah","Ks","Qd","Jd"};
        //String[] b = {"As","Ks","Qs","9s","8d"};
        System.out.println("a: " + Handranker.handrank(Tools.cardtobit(a)));
        //System.out.println("b: " + Handranker.handrank(Tools.cardtobit(b)));
    }
}
