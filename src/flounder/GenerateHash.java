/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import static flounder.Handranker.h;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vince
 */
public class GenerateHash {
    static HashMap<Integer, Integer> rankhash5 = new HashMap<>();
    static HashMap<Integer, Integer> flushhash5 = new HashMap<>();
    static HashMap<Integer, Integer> rankhash6 = new HashMap<>();
    static HashMap<Integer, Integer> flushhash6 = new HashMap<>();
    static HashMap<Integer, Integer> rankhash7 = new HashMap<>();
    static HashMap<Integer, Integer> flushhash7 = new HashMap<>();
    
    static HashMap<Integer, Float> boardtexturehash3 = new HashMap<>();
    static HashMap<Integer, Float> boardtexturehash4 = new HashMap<>();
    
    static void generatehashhandrank(int cardnumber) throws FileNotFoundException, IOException{
        Combinator combo = new Combinator(cardnumber);
        for (int i = 0; i < combo.alliter; i++){
            int sum = 0;
            int rank = 0;
            int count = -1;
            int[] suitcount = new int [cardnumber];
            h.clear();
            //BitSet b = new BitSet(52);
            h.or(combo.combinations());
            if (i % 100000 == 0){
                System.out.println(i + " of " + combo.alliter);
            }
            for (int j = 0; j < h.cardinality(); j++){
                count = h.nextSetBit(count + 1);
                rank = count;
                suitcount[j] = (rank % 4);
                rank = ((rank - rank % 4)/4);
                rank = numconv(rank);
                sum = sum + rank;
            }
            
            rank = Handranker.handrank(h);
            
            if ((rank <= 10) | ((rank > 322) & (rank <= 1599))){
                int flushsuit = -1;
                for (int j = 0; j < 4; j++){
                    count = 0;
                    for (int k = 0; k < suitcount.length; k++){
                        if (suitcount[k] == j){
                            count++;
                            if (count > 4){
                                flushsuit = j;
                            }
                        }
                    }
                }
                for (int j = 0; j < suitcount.length; j++){
                    if (suitcount[j] == flushsuit){
                        suitcount[j] = 0;
                    }
                    else{
                        suitcount[j] = 1;
                        if (j == 0){sum = sum + 1 * 113088217;}
                        if (j == 1){sum = sum + 2 * 113088217;}
                        if (j == 2){sum = sum + 4 * 113088217;}
                        if (j == 3){sum = sum + 7 * 113088217;}
                        if (j == 4){sum = sum + 12 * 113088217;}
                        if (j == 5){sum = sum + 20 * 113088217;}
                        if (j == 6){sum = sum + 33 * 113088217;}
                    }   
                }
                if (cardnumber == 5){flushhash5.put(sum, rank);}
                if (cardnumber == 6){flushhash6.put(sum, rank);}
                if (cardnumber == 7){flushhash7.put(sum, rank);}
            }
            else{
                if (cardnumber == 5){rankhash5.put(sum, rank);}
                if (cardnumber == 6){rankhash6.put(sum, rank);}
                if (cardnumber == 7){rankhash7.put(sum, rank);}
            }
        }
        System.out.println("done loading");

        File file = new File("rankhash" + cardnumber);
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = null;
        try {
            s = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Handranker.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cardnumber == 5){s.writeObject(rankhash5);}
        if (cardnumber == 6){s.writeObject(rankhash6);}
        if (cardnumber == 7){s.writeObject(rankhash7);}
        
        File file2 = new File("flushhash" + cardnumber);
        FileOutputStream f2 = new FileOutputStream(file2);
        ObjectOutputStream s2 = null;
        try {
            s2 = new ObjectOutputStream(f2);
        } catch (IOException ex) {
            Logger.getLogger(Handranker.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cardnumber == 5){s2.writeObject(flushhash5);}
        if (cardnumber == 6){s2.writeObject(flushhash6);}
        if (cardnumber == 7){s2.writeObject(flushhash7);}
        s2.flush();
        s2.close();
        f2.close();
        s.flush();
        s.close();
        f.close();
    }
    
    static void generatehashboardtexture(int cardnumber) throws FileNotFoundException, IOException{
        Combinator combo = new Combinator(cardnumber);
        for (int i = 0; i < combo.alliter; i++){
            float boardtext = 0;
            int sum = 0;
            int rank = 0;
            int count = -1;
            int[] suitcount = new int [cardnumber];
            h.clear();
            //BitSet b = new BitSet(52);
            h.or(combo.combinations());
            if (i % 10 == 0){
                System.out.println(i + " of " + combo.alliter);
            }
            for (int j = 0; j < h.cardinality(); j++){
                count = h.nextSetBit(count + 1);
                rank = count;
                suitcount[j] = (rank % 4);
                rank = ((rank - rank % 4)/4);
                rank = numconv(rank);
                sum = sum + rank;
            }
            count = 0;
            HashMap<Integer, Integer> suits = new HashMap<>();
            for (int j = 0; j < suitcount.length; j++){
                if (suits.containsKey(suitcount[j]) == false){
                    suits.put(suitcount[j], count);
                    count++;
                }
            }
            for (int j = 0; j < suitcount.length; j++){
                suitcount[j] = suits.get(suitcount[j]);
                if (j == 0){sum = sum + suitcount[j] * 0 * 113088217;}
                if (j == 1){sum = sum + suitcount[j] * 1 * 113088217;}
                if (j == 2){sum = sum + suitcount[j] * 5 * 113088217;}
                if (j == 3){sum = sum + suitcount[j] * 24 * 113088217;}
            }
            
            
            if (cardnumber == 3){
                if (boardtexturehash3.containsKey(sum) == false){
                    boardtext = Calculate.boardtexture(h);
                    boardtexturehash3.put(sum, boardtext);
                }
            }
            
            if (cardnumber == 4){
                if (boardtexturehash4.containsKey(sum) == false){
                    boardtext = Calculate.boardtexture(h);
                    boardtexturehash4.put(sum, boardtext);
                }
            }
        }

        System.out.println("done loading");

        File file = new File("boardtexturehash" + cardnumber);
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = null;
        try {
            s = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Handranker.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cardnumber == 3){s.writeObject(boardtexturehash3);}
        if (cardnumber == 4){s.writeObject(boardtexturehash4);}
    }
    
    public static int numconv(int value){
        if (value == 0){return 0;}
        if (value == 1){return 1;}
        if (value == 2){return 5;}
        if (value == 3){return 24;}
        if (value == 4){return 112;}
        if (value == 5){return 521;}
        if (value == 6){return 2421;}
        if (value == 7){return 11248;}
        if (value == 8){return 52256;}
        if (value == 9){return 242769;}
        if (value == 10){return 1127845;}
        if (value == 11){return 5239688;}
        if (value == 12){return 24342288;}
        return 0;
    }
}
