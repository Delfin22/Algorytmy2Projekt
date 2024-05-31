package classes.problem2.patternSearchAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class KnuthMorrisPratt {
    private static List<Integer> createLPS(String pattern){
        // create lps that will hold the longest
        // prefix suffix values for pattern

        int patternLength = pattern.length();
        List<Integer> lps = new ArrayList<>(patternLength);
        int len = 0;
        int i;
        for(i = 0; i < patternLength; i++){
            lps.add(0);
        }
        i = 1;

        while (i < patternLength) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps.set(i,len);
                i++;
            }
            else
            {
                if (len != 0) {
                    len = lps.get(len-1);
                }
                else
                {
                    lps.set(i,len);
                    i++;
                }
            }
        }
        return lps;
    }
    public static List<Integer> patternSearch(String text, String pattern){
        int patternLength = pattern.length();
        int textLength = text.length();

        if(textLength == 0 || patternLength == 0 || patternLength > textLength){
            throw new IllegalArgumentException();
        }

        List<Integer> indexes = new ArrayList<>();
        List<Integer> lps = createLPS(pattern);
        int j = 0;

        int i = 0; // index for text
        while ((textLength - i) >= (patternLength - j)) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == patternLength) {
                indexes.add(i-j);
                j = lps.get(j-1);
            }

            else if (i < textLength && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps.get(j - 1);
                }
                else {
                    i = i + 1;
                }
            }
        }
        return indexes;
    }
}
