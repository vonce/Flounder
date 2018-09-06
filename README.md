# Flounder
Flounder is a bunch of poker tools for Mackerel to use. Long iterative calculations were not very fast with the non-compiled python, so all of them are done here with Flounder. Flounder.jar is put in the Mackerel project, and methods are called with Pyjnius.

### Deck
Deck is a class containing the hashmap that relates a number 0-51 to the standard poker notation of (cardchar)(suitchar). Each card can be represented by a 2 character string, where a 10 is 'T'. For example a 10 of spades is 'Ts', king of clubs is 'Kc' etc. Aces are 0-3, Kings 4-7, Queens 8-11 etc., so 'As' = 0, 'Ah' = 1, 'Ad' = 2 etc. Within our calculations, combinations of cards will be represented by a 52 length bitset, with a full deck represented by all 52 bits true, and we can represent any number of cards where order doesn't matter by marking true on the corresponding card numbers.

### Tools
We need a way to convert our bitset representation to a string representation and vice versa. The methods cardtobit, and bittocard serve this purpose. We also need a bit to number array representation, which is done by the bittonum method. I also made a choose method which calculates the number of combinations we need to iterate through, and a bitset adder, which could OR multiple bitsets at once, just for my own convenience.

### Handranker
We assess hand ranks (pair, two pair, three of a kind, straight, flush, full house, four of a kind, straight flush) by moving through each set bitset and checking to see if the conditions are satisfied for that hand rank. The handrank method returns an int between 1 and 7462 and takes a bitset as an argument. There are 7462 unique ranks, even though 52 choose 5 is 2598960. This is because there are multiple ways to have the same hand rank. For example there are only 10 unique rankings of a straight flush, even though there are 40 combinations of ways we can make a straight flush. We start with assessing if the bitset fulfills a straight flush and move down in rank from there. This way we will always return the highest possible hand rank, because we wouldn't want to return a pair if we fulfill a higher hand rank. 

### Combinator
The combinator is an all purpose combinator that finds all combinations of a specified number of cards. When using this class, we instantiate a Combinator class with a number and optional bitset. The number specifies the number of combinations from the given 52 card set we want. The bitset specifies cards that are removed, either because they are in our hand, or already on the board.

```
    Combinator(int num){
        this.iter = 0;
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
        this.iter = 0;
        this.n = num;
        this.alliter = Tools.choose(52 - r.cardinality(), num);
        this.numarr = new int[52 - r.cardinality()];
        this.removal = Tools.bittonum(r);
        this.iterarr = new int[num];
        this.k = 0;
        for (int i = 0; i < 52; i++){
            if (i - k == numarr.length){
                break;
            }
            numarr[i - k] = i;
            for (int j : removal){
                if (j == i){
                    k++;
                    break;
                }
            }
        }
    }
```

after the combinator class is initialized, we can call the combinations method to move through the combinations. this method returns a bitset of the first combination, then the next combination when it is called again, each time returning the bitset representation of the cards in the next combination.

```
    int n;
    BitSet h = new BitSet(52);
    int alliter;
    int[] numarr;
    int[] removal;
    int[] iterarr;
    int k;
    int iter = 0;
    
    public BitSet combinations(){
        h.clear();
        if (iter == 0){
            for (int i = 0; i < iterarr.length; i++){
                h.set(numarr[i]);
                iterarr[i] = i;
            }
            iter++;
            return h;
        }
        if (iter <= alliter){
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
            return h;
        }
        h.clear();
        return h;
    }
```

If we are at the first iteration, we set the first n number of cards of the bitset on and return that bitset. Each iteration after that moves the farthest element one down and returns the corresponding bitset. If the last element reaches the end, the next farthest element is moved down one and the the farthest element is moved next to the second to last element, and so on. Now we can cycle through combinations of cards for our hand strength calculator.

### Calculate
The calculate class contains all the calculations we need for our poker bot. The equity method takes an array of strings or bitset representation of the board, and multiple hands, either a string or bitset representation. it returns the equity of the hands based on the board, which means it iterates through all possible runouts and returns a percentage that particular hand wins in all the possible combinations of runouts.

Hand percentile is a method that takess a hand (string or bitset) and a board (string or bitset) and returns a float, a percentage of how many hands it is beating out of all possible hands. This method iterates through all possible other hands, records if the hand argument is a better hand with the board state(0-7462, so a lower number)and then returns the percentile of how good that particular hand is with that particular board. For example if we had (As,Ad) with an (Ac, 2d, 8c) board, by iterating through all other possible 2 card hands, we find that no possible two card hand can beat our (As, Ad) on this board, so our handpercentile will return 1.

Effective percentile is similar to hand percentile but it takes into account draws that can change the strength of the hand. Instead of strictly beating a certain hand, it also takes into account how many cards can still come that would make our hand pull ahead or fall behind. It iterates through all possible runouts and sees how many runouts will change whether or not the iterating hand will pull ahead of our hand, or fall behind. Take our last example. If we had (As,Ad) with an (Ac, 2d, 8c) board, there are multiple cards that would make our (As, Ad) no longer the best hand, such as any club card. The effective percentile will give us a percentile that gives us our hand's chance to win against all possible runouts, and all possible other hands.
