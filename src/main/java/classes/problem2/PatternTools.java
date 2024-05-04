package classes.problem2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PatternTools {
    private static final int lettersInAlphabet = 27; //using small letters in latin alphabet + space

    public static String generateRandomString(int length){
        Random random = new Random();
        char[] arr = new char[length];
        for(int i = 0; i < length; i++){
            arr[i] = (char)(random.nextInt(lettersInAlphabet)+'a');
        }
        return new String(arr);
    }
    public static String changePattern(String text, String wrongPattern, String goodPattern) throws IllegalArgumentException{
        if(text.isEmpty() || wrongPattern.isEmpty() || goodPattern.isEmpty()
        || wrongPattern.length() != goodPattern.length()) // in this case there is no possibility of 3rd dimension interference
        {
            throw new IllegalArgumentException();
        }
        List<Integer> indexes = patternSearchKR(text,wrongPattern); //temporary (need to add function to decide which algorithm is optimal)
        List<Integer> differentLettersInPattern = new ArrayList<>();

        /*this single loop saves time in case of long patterns
        for example "poli" and "boli" are almost equal, so there is no need
        in comparing letters except the first ones
         */
        for(int i = 0; i < wrongPattern.length(); i++){
            if(wrongPattern.charAt(i) != goodPattern.charAt(i)){
                differentLettersInPattern.add(i);
            }
        }

        char[] textArr = text.toCharArray();

        for(int i : indexes){
            for(int j : differentLettersInPattern){
                textArr[i+j] = goodPattern.charAt(j);
            }
        }
        return new String(textArr);
    }

    /*to implement*/
    public static List<Integer> patternSearchOptimal(String text,String pattern){
        return null;
    }

    public static List<Integer> patternSearchKR(String text, String pattern) throws IllegalArgumentException{

        /* Karp - Rabin algorithm */
        int modNumber = 29; //"good prime number"

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

    /*to implement*/
    public static List<Integer> patternSearchBM(String text, String pattern) throws IllegalArgumentException{
        
        return null;
    }

    /*to implement*/
    public static List<Integer> patternSearchKMP(String text, String pattern) throws IllegalArgumentException{

        return null;
    }

    public static int getLettersInAlphabet(){
        return lettersInAlphabet;
    }


}
