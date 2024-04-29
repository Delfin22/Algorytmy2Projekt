import classes.PatternTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatternToolsTest {
    String pattern = "boli";

    @Test
    public void patternSearchTest(){
       String s1 = "kjjn bolimodsarfizm asmlkd boli assa boli sad";
       String s2 = new String();
       Assertions.assertEquals(true, PatternTools.searchPattern(s1,pattern));

       Assertions.assertEquals(true, PatternTools.searchPattern(s1,s1));

       Assertions.assertEquals(true, PatternTools.searchPattern(s1,s2));

       Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.searchPattern(s2,pattern));
       Assertions.assertThrows(IllegalArgumentException.class, () -> PatternTools.searchPattern(s2,s2));
    }


}
