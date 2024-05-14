package classes.problem2;

import classes.problem2.patternSearchAlgorithms.PatternTools;

import java.util.ArrayList;
import java.util.Collections;

public class HuffmanString {
    private String encodedString;


    public HuffmanString() {
    }
    private BinaryTree generateOptimalPrefixCode(String text){
        int n = text.length();
        ArrayList<Integer> amount = new ArrayList<>(Collections.nCopies(PatternTools.getLettersInAlphabet(),0)); //initial zeros

        for(int i = 0; i < n; i++){ //number of particular letters
            amount.set(
                    amount.get(text.charAt(i) - 'a'),
                    amount.get(text.charAt(i) - 'a') +1
            );

        }

        //to complete
        return null;
    }
}
