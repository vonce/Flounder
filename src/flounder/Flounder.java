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
        //int count = 0;
        //int[] array = new int[7462];
        //for (int i = 0; i < Tools.choose(52, 5); i++){
        //    array[Handranker.handrank(Combinator.combinations(5)) - 1]++;
        //    count++;
        //}
        //for (int k = 0; k < 7462; k++){
        //    System.out.println(k + ": " + array[k]);
        //}
        String[] a = {"As","Qs","Ks","Js","9s"};
        String[] b = {"2d","3s","4d","5s","7d"};
        System.out.println("a: " + Handranker.handrank(Tools.cardtobool(a)));
        System.out.println("b: " + Handranker.handrank(Tools.cardtobool(b)));
        String[] c = Tools.booltocard(Tools.cardtobool(a));
    }
}
