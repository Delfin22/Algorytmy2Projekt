package classes.problem2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

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

    ////////////////////////////////////////////////////////
   ///////// TEMPORARY ZONE/////////////////////////////////
//////////////////////////////////////////////////////////////

    static int NO_OF_CHARS = 256;

    // A utility function to get maximum of two integers
    static int max(int a, int b) { return (a > b) ? a : b; }

    // The preprocessing function for Boyer Moore's
    // bad character heuristic
    static void badCharHeuristic(String str, int size,
                                 int badchar[])
    {

        // Initialize all occurrences as -1
        for (int i = 0; i < NO_OF_CHARS; i++)
            badchar[i] = -1;

        // Fill the actual value of last occurrence
        // of a character (indices of table are ascii and
        // values are index of occurrence)
        for (int i = 0; i < size; i++)
            badchar[(int)str.charAt(i)] = i;
    }

    /* A pattern searching function that uses Bad
    Character Heuristic of Boyer Moore Algorithm */
    public static List<Integer> patternSearchBM(String txt, String pat) throws IllegalArgumentException
    {

        int m = pat.length();
        int n = txt.length();

        if(m == 0 || n == 0 || m > n){
            throw new IllegalArgumentException();
        }

        List<Integer> list = new ArrayList<>();
        int[] badChar = new int[NO_OF_CHARS];

        /* Fill the bad character array by calling
           the preprocessing function badCharHeuristic()
           for given pattern */
        badCharHeuristic(pat, m, badChar);

        int s = 0; // s is shift of the pattern with
        // respect to text
        // there are n-m+1 potential alignments
        while (s <= (n - m)) {
            int j = m - 1;

            /* Keep reducing index j of pattern while
               characters of pattern and text are
               matching at this shift s */
            while (j >= 0 && pat.charAt(j) == txt.charAt(s +j))
                j--;

            /* If the pattern is present at current
               shift, then index j will become -1 after
               the above loop */
            if (j < 0) {
                list.add(s);

                /* Shift the pattern so that the next
                   character in text aligns with the last
                   occurrence of it in pattern.
                   The condition s+m < n is necessary for
                   the case when pattern occurs at the end
                   of text */
                // txt[s+m] is character after the pattern
                // in text
                s += (s + m < n) ? m - badChar[txt.charAt(s + m)]
                        : 1;
            }

            else
                /* Shift the pattern so that the bad
                   character in text aligns with the last
                   occurrence of it in pattern. The max
                   function is used to make sure that we get
                   a positive shift. We may get a negative
                   shift if the last occurrence  of bad
                   character in pattern is on the right side
                   of the current character. */
                s += max(1, j - badChar[txt.charAt(s+j)]);
        }
        return list;
    }





    ////////////////////////////////////////////////////////
    ///////// TEMPORARY ZONE/////////////////////////////////
//////////////////////////////////////////////////////////////
    /*to implement*/
    public static List<Integer> patternSearchKMP(String text, String pattern) throws IllegalArgumentException{

        return null;
    }

    public static int getLettersInAlphabet(){
        return lettersInAlphabet;
    }


}
