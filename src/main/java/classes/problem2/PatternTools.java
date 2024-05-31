package classes.problem2;

import classes.problem2.patternSearchAlgorithms.BoyerMoore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class PatternTools {
    private static final int lettersInAlphabet = 26; //using small letters in latin alphabet
    private static final char firstLetter = 'a'; //for shifting unused chars and reducing memory usage

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
        List<Integer> indexes = BoyerMoore.patternSearch(text,wrongPattern); //optimal
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

    //getters
    public static int getLettersInAlphabet(){
        return lettersInAlphabet;
    }
    public static char getFirstLetter(){ return firstLetter;}

}
