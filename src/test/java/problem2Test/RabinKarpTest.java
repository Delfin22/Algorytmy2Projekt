package problem2Test;

import classes.problem2.PatternTools;
import classes.problem2.patternSearchAlgorithms.RabinKarp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class RabinKarpTest {

    @Test
    public void patternSearchKRTest(){
        String pattern = "boli";
        String s1 = "kjjnbolimodsarfizmasmlkdboliassabolisad";
        String emptyString = "";

        List<Integer> list = new ArrayList<>(3);
        list.add(4);
        list.add(24);
        list.add(32);
        //Searching "boli". Expected at indexes above.
        Assertions.assertEquals(list, RabinKarp.patternSearch(s1, pattern));

        List<Integer> list1 = new ArrayList<>(1);
        list1.add(0);
        //Searching text in text - expected at index 0
        Assertions.assertEquals(list1, RabinKarp.patternSearch(s1, s1));

        //empty pattern
        Assertions.assertThrows(IllegalArgumentException.class, () -> RabinKarp.patternSearch(s1, emptyString));
        //empty text
        Assertions.assertThrows(IllegalArgumentException.class, () -> RabinKarp.patternSearch(emptyString, s1));
        //empty pattern and text
        Assertions.assertThrows(IllegalArgumentException.class, () -> RabinKarp.patternSearch(emptyString, emptyString));

        //pattern longer than text
        Assertions.assertThrows(IllegalArgumentException.class, () -> RabinKarp.patternSearch("text", "Longer Pattern"));
    }
    @Test
    public void patternSearchKRTimeTest(){
        int textTests = 100;
        int patternTests = 1000;
        long startTime, endTime, result;
        String text,shortPattern,longPattern;

        List<Long> list = new ArrayList<>(textTests*patternTests);
        //short pattern
        for(int i = 0; i < textTests; i++) {
            text = PatternTools.generateRandomString(1000);
            for(int j = 0; j < patternTests; j++) {
                shortPattern = PatternTools.generateRandomString(4);
                startTime = System.nanoTime();
                RabinKarp.patternSearch(text,shortPattern);
                endTime = System.nanoTime();
                result = endTime - startTime;
                list.add(result);
            }
        }
        System.out.println("Short pattern Karp-Rabin search algorithm");
    //    System.out.println(list);
        OptionalDouble avg = list.stream().mapToDouble(a -> a).average();
        System.out.println("Average of short pattern: " + (avg.isPresent() ? avg.getAsDouble() : 0));

       list = new ArrayList<>(textTests*patternTests);
        //long pattern
        for(int i = 0; i < textTests; i++) {
            text = PatternTools.generateRandomString(1000);
            for(int j = 0; j < patternTests; j++) {
                longPattern = PatternTools.generateRandomString(100);
                startTime = System.nanoTime();
                RabinKarp.patternSearch(text,longPattern);
                endTime = System.nanoTime();
                result = endTime - startTime;
                list.add(result);
            }
        }
        System.out.println("Long pattern Karp-Rabin search algorithm");
    //    System.out.println(list);
        avg = list.stream().mapToDouble(a -> a).average();
        System.out.println("Average of long pattern: " + (avg.isPresent() ? avg.getAsDouble() : 0));
    }


}
