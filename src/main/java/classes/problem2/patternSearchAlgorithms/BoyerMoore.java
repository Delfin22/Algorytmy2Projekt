package classes.problem2.patternSearchAlgorithms;
import classes.problem2.PatternTools;

import java.util.ArrayList;
import java.util.List;
public class BoyerMoore {
    private static List<Integer> badCharHeuristic(String pattern){
        List<Integer> badChar = new ArrayList<>(PatternTools.getLettersInAlphabet());

        for (int i = 0; i < PatternTools.getLettersInAlphabet(); i++) { //indexes starts with 0 so initialise with -1
            badChar.add(-1);
        }

        // Fill the actual value of last occurrence
        for (int i = 0; i < pattern.length(); i++) {
            badChar.set(pattern.charAt(i)-PatternTools.getFirstLetter(), i);
        }
        return badChar;
    }

    public static List<Integer> patternSearch(String text, String pattern)
    {
        int patternLength = pattern.length();
        int textLength = text.length();

        //possible exceptions
        if(textLength == 0 || patternLength == 0 || patternLength > textLength){
            throw new IllegalArgumentException();
        }


        List<Integer> indexes = new ArrayList<>();
        List<Integer> badChar = badCharHeuristic(pattern);
        int s = 0;

        while (s <= (textLength - patternLength)) {
            int j = patternLength - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(s+j)) {
                j--;
            }
            if (j < 0) {
                indexes.add(s);
                s += (s + patternLength < textLength) ? patternLength - badChar.get(text.charAt(s+patternLength)-PatternTools.getFirstLetter()) : 1;
            }
            else {
                s += Math.max(1, (j - badChar.get(text.charAt(s + j)-PatternTools.getFirstLetter())));
            }
        }
        return indexes;
    }












































//    static int NO_OF_CHARS = 256;
//    private static List<Integer> badCharHeuristic(String str){
//        int size = str.length();
//        List<Integer> badchar = new ArrayList<>(NO_OF_CHARS);
//
//        for (int i = 0; i < NO_OF_CHARS; i++) { //indexes starts with 0
//            badchar.add(-1);
//        }
//        // Fill the actual value of last occurrence
//        for (int i = 0; i < size; i++) {
//            badchar.set(str.charAt(i), i);
//        }
//        return badchar;
//    }
//
//    public static List<Integer> patternSearch(String text, String pattern)
//    {
//        int m = pattern.length();
//        int n = text.length();
//
//        List<Integer> indexes = new ArrayList<>();
//        List<Integer> badChar = badCharHeuristic(pattern);
//
//        int s = 0;
//        while (s <= (n - m)) {
//            int j = m - 1;
//            while (j >= 0 && pattern.charAt(j) == text.charAt(s+j)) {
//                j--;
//            }
//            if (j < 0) {
//                indexes.add(s);
//                s += (s + m < n) ? m - badChar.get(text.charAt(s+m)) : 1;
//            }
//            else {
//                s += Math.max(1, (j - badChar.get(text.charAt(s + j))));
//            }
//        }
//        return indexes;
//    }


}
