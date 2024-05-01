import classes.PatternTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PatternToolsTest {

    @Test
    public void patternSearchTest(){
        String pattern = "boli";
        String s1 = "kjjn bolimodsarfizm asmlkd boli assa boli sad";
        String emptyString = "";

        List<Integer> list = new ArrayList<>(3);
        list.add(5);
        list.add(27);
        list.add(37);
        //Searching "boli". Expected at indexes above.
        Assertions.assertEquals(list, PatternTools.searchPattern(s1, pattern));

        List<Integer> list1 = new ArrayList<>(1);
        list1.add(0);
        //Searching text in text - expected at index 0
        Assertions.assertEquals(list1, PatternTools.searchPattern(s1, s1));

        //empty pattern
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.searchPattern(s1, emptyString));
        //empty text
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.searchPattern(emptyString, s1));
        //empty pattern and text
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.searchPattern(emptyString, emptyString));

        //pattern longer than text
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.searchPattern("text", "Longer Pattern"));

    }
}
