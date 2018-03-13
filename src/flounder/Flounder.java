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
        String[] c = {"3h","4d","Qs"};
        String[] q = {"3d","4h"};
        String[] r = {"Qh","5s"};
        BitSet x = Tools.cardtobit(q);
        BitSet y = Tools.cardtobit(r);
        BitSet[] d = {x,y};
        double[] array = Calculate.equity(c, q, r);
        for (double i: array){
            System.out.println(i);
        }
        //String[] a = {"As","Qs","Ks","Js","Ts"};
        //String[] b = {"2d","3s","4d","5s","6d"};
        //System.out.println("a: " + Handranker.handrank(Tools.cardtobit(a)));
        //System.out.println("b: " + Handranker.handrank(Tools.cardtobit(b)));
    }
}
