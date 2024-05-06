import classes.problem2.PatternTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class PatternToolsTestBM {
    @Test
    public void patternSearchBMTest(){
        String pattern = "boli";
        String s1 = "kjjnbo limodsarfizmasmlkd boliassabolisad";
        String emptyString = "";

        List<Integer> list = new ArrayList<>(3);
        list.add(26);
        list.add(34);
        //Searching "boli". Expected at indexes above.
        Assertions.assertEquals(list, PatternTools.patternSearchBM(s1, pattern));

        List<Integer> list1 = new ArrayList<>(1);
        list1.add(0);
        //Searching text in text - expected at index 0
        Assertions.assertEquals(list1, PatternTools.patternSearchBM(s1, s1));

        //empty pattern
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.patternSearchBM(s1, emptyString));
        //empty text
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.patternSearchBM(emptyString, s1));
        //empty pattern and text
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.patternSearchBM(emptyString, emptyString));

        //pattern longer than text
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.patternSearchBM("text", "Longer Pattern"));
    }
    @Test
    public void patternSearchBMTimeTest(){
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
                PatternTools.patternSearchBM(text,shortPattern);
                endTime = System.nanoTime();
                result = endTime - startTime;
                list.add(result);
            }
        }
        System.out.println("Short pattern Bayer-Moore search algorithm");
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
                PatternTools.patternSearchBM(text,longPattern);
                endTime = System.nanoTime();
                result = endTime - startTime;
                list.add(result);
            }
        }
        System.out.println("Long pattern Bayer-Moore search algorithm");
        //    System.out.println(list);
        avg = list.stream().mapToDouble(a -> a).average();
        System.out.println("Average of long pattern: " + (avg.isPresent() ? avg.getAsDouble() : 0));
    }
}
