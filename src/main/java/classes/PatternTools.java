package classes;

import java.util.ArrayList;
import java.util.List;

public class PatternTools {
    private final static int lettersInAlphabet = 26; //using small letters in latin alphabet
    private final static int modNumber = 29; //"good prime number"

    public static List<Integer> searchPattern(String text, String pattern) throws IllegalArgumentException{

        /* Karp - Rabin algorithm */

        List <Integer> indexes = new ArrayList<>();
        int textLength = text.length();
        int patternLength = pattern.length();
        int h = (int) Math.pow(lettersInAlphabet,patternLength - 1) % modNumber;

        int p = 0, t0 = 0;
        int j,s;

        //possible exceptions
        if(textLength == 0 || patternLength == 0 || patternLength > textLength){
            throw new IllegalArgumentException();
        }

        //hashing
        for(int i = 0; i < patternLength; i++){
            p = (lettersInAlphabet*p + pattern.charAt(i))%modNumber;
            t0 = (lettersInAlphabet*t0 + text.charAt(i))%modNumber;
        }

        for(s = 0; s <= (textLength-patternLength); s++){
            if(p == t0){
                //by characters
                for(j = 0; j < patternLength; j++){
                    if(pattern.charAt(j) != text.charAt(s+j)) { //if chars are not equal
                        break;
                    }
                }
                if(j == patternLength) { //if previous loop was executed without break
                    indexes.add(s);
                }
            }

            //hashing
            if(s < (textLength - patternLength)){
                t0 = (lettersInAlphabet*(t0 - text.charAt(s) * h) + text.charAt(s+patternLength))%modNumber;

                //convert negative values//
                if (t0 < 0) {
                    t0 = (t0 + modNumber);
                }
            }
        }
        return indexes;
    }
}
