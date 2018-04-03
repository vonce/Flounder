/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flounder;

import static flounder.Handranker.h;
import java.util.BitSet;
import java.util.TreeSet;
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
    static HashMap<Long, Float> boardtexturehash3 = new HashMap<>();
    static HashMap<Long, Float> boardtexturehash4 = new HashMap<>();
    static HashMap<Long, Float> effectivehandpercentilehash3 = new HashMap<>();
    static HashMap<Long, Float> effectivehandpercentilehash4 = new HashMap<>();
    
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
                rank = Tools.numconv(rank);
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
            long sum = 0L;
            int rank = 0;
            int count = -1;
            int[] suitcount = new int [cardnumber];
            h.clear();
            //BitSet b = new BitSet(52);
            h.or(combo.combinations());
            if (i % 100 == 0){
                System.out.println(i + " of " + combo.alliter);
            }
            for (int j = 0; j < h.cardinality(); j++){
                count = h.nextSetBit(count + 1);
                rank = count;
                suitcount[j] = (rank % 4);
                rank = ((rank - rank % 4)/4);
                rank = Tools.numconv(rank);
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
                if (j == 0){sum = sum + suitcount[j] * 1 * 113088217L;}
                if (j == 1){sum = sum + suitcount[j] * 4 * 113088217L;}
                if (j == 2){sum = sum + suitcount[j] * 16 * 113088217L;}
                if (j == 3){sum = sum + suitcount[j] * 64 * 113088217L;}
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
    
    static void generatehasheffectivehandpercentile(int cardnumber) throws FileNotFoundException, IOException{
        Combinator boardcombo = new Combinator(cardnumber);
        for (int i = 0; i < boardcombo.alliter; i++){
            BitSet b = new BitSet(52);
            float effperc = 0;
            long sum = 0L;
            long sumh = 0L;
            long sumb = 0L;
            int rank = 0;
            int count = -1;
            int[] suitcount = new int[cardnumber + 2];
            TreeSet handboardkey = new TreeSet();
            b.clear();
            //BitSet b = new BitSet(52);
            b.or(boardcombo.combinations());
            if (i % 1 == 0){
                System.out.println(i + " of " + boardcombo.alliter);
            }
            for (int j = 0; j < b.cardinality(); j++){
                count = b.nextSetBit(count + 1);
                rank = count;
                suitcount[j] = (rank % 4);
                rank = ((rank - rank % 4)/4);
                rank = Tools.numconv(rank);
                sumb = sumb + rank;
            }
            Combinator handcombo = new Combinator(2, b);
            for (int j = 0; j < handcombo.alliter; j++){
                sumh = 0L;
                if (j % 100 == 0){
                    System.out.println(j + " of " + handcombo.alliter);
                }
                h.clear();
                h.or(handcombo.combinations());
                for (int k = 0; k < h.cardinality(); k++){
                    count = h.nextSetBit(count + 1);
                    rank = count;
                    suitcount[k+cardnumber] = (rank % 4);
                    rank = ((rank - rank % 4)/4);
                    rank = Tools.numconv(rank);
                    sumh = sumh + rank * 525379733;
                }
                count = 0;
                HashMap<Integer, Integer> suits = new HashMap<>();
                for (int k = 0; k < suitcount.length; k++){
                    if (suits.containsKey(suitcount[k]) == false){
                        suits.put(suitcount[k], count);
                        count++;
                    }
                }
                if (suits.size() < 4){
                    count = suits.size();
                    for (int k = 0; k < 4; k++){
                        if (suits.containsKey(k) == false){
                            suits.put(k, count);
                            count++;
                        }
                    }
                }
                
                for (int k = 0; k < suitcount.length - 2; k++){
                    suitcount[k] = suits.get(suitcount[k]);
                    if (k == 0){sumb = sumb + suitcount[k] * 1 * 525379733L;}
                    if (k == 1){sumb = sumb + suitcount[k] * 4 * 525379733L;}
                    if (k == 2){sumb = sumb + suitcount[k] * 16 * 525379733L;}
                    if (k == 3){sumb = sumb + suitcount[k] * 64 * 525379733L;}
                }
                for (int k = suitcount.length - 2; k < suitcount.length; k++){
                    suitcount[k] = suits.get(suitcount[k]);
                    if (k == suitcount.length - 2){sumh = sumh + suitcount[k] * 256 * 525379733L;}
                    if (k == suitcount.length - 1){sumh = sumh + suitcount[k] * 1023 * 525379733L;}
                }
                sum = sumh + sumb;
                System.out.println(sum);
                //System.out.println(handboardkey);
                if (cardnumber == 3){
                    if (effectivehandpercentilehash3.containsKey(sum) == false){
                        effperc = Calculate.effectivepercentile(h,b);
                        effectivehandpercentilehash3.put(sum, effperc);
                    }
                    else{
                        System.out.println("SKIP");
                    }
                }
                if (cardnumber == 4){
                    if (effectivehandpercentilehash4.containsKey(sum) == false){
                        effperc = Calculate.effectivepercentile(h,b);
                        effectivehandpercentilehash4.put(sum, effperc);
                    }
                }
            }
        }

        System.out.println("done loading");

        File file = new File("effectivehandpercentilehash" + cardnumber);
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream s = null;
        try {
            s = new ObjectOutputStream(f);
        } catch (IOException ex) {
            Logger.getLogger(Handranker.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cardnumber == 3){s.writeObject(effectivehandpercentilehash3);}
        if (cardnumber == 4){s.writeObject(effectivehandpercentilehash4);}
    }
}
