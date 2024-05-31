package problem2Test;

import classes.problem2.PatternTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatternToolsTest {
    @Test
    public void changePatternTest(){
        String text = "polimorfizmopolippolippopoli";
        String wrongPattern = "poli";
        String goodPattern = "boli";
        String expectedOutput = "bolimorfizmobolipbolippoboli";
        Assertions.assertEquals(expectedOutput, PatternTools.changePattern(text,wrongPattern,goodPattern));
    }
}
