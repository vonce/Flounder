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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        // TODO code application logic here
        Deck.main();
        String[] j = {"3c","4c","5c","7c","2c"};
        System.out.println(Handranker.handrank(Handranker.cardtobool(j)));
    }
}
