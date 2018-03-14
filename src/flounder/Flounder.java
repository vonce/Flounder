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
        String[] c = {"6d","5d","4d","7d"};
        String[] f = {"Ks","Kc"};
        String[] g = {"Ac","Kc"};
        String[] h = {"Qs","Js"};
        String[] z = {"Qc","Jc"};
        String[] x = {"Ts","9s"};
        String[] k = {"Tc","9c"};
        String[] l = {"8s","7s"};
        String[] m = {"8c","7c"};
        String[] n = {"6s","5s"};
        String[] o = {"6c","5c"};
        System.out.println(Calculate.handpercentile(f, c));
        //float[][] array = Calculate.equity(c, n);
        //for (float[] i: array){
        //    for (float j: i){
        //        System.out.print(j + " ");
        //    }
        //    System.out.println(" ");
        //}
        //System.out.println(" ");
        //array = Calculate.equity(c, q, r);

        //String[] a = {"Jd","Qd","4d","3d","2d","2s","Qs"};
        //String[] b = {"As","Ks","Qs","9s","8d"};
        //System.out.println("a: " + Handranker.handrank(Tools.cardtobit(a)));
        //System.out.println("b: " + Handranker.handrank(Tools.cardtobit(b)));
    }
}
