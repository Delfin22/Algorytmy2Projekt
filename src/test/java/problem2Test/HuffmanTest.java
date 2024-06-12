package problem2Test;

import classes.problem2.Huffman;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HuffmanTest {
    @Test
    public void encodeTextTest(){
        //example from presentation
        StringBuilder stringBuilder = new StringBuilder();
        Huffman huffman = new Huffman();

        for(int i = 0; i < 45; i++)
            stringBuilder.append('a');
        for(int i = 0; i < 13; i++)
            stringBuilder.append('b');
        for(int i = 0; i < 12; i++)
            stringBuilder.append('c');
        for(int i = 0; i < 16; i++)
            stringBuilder.append('d');
        for(int i = 0; i < 9; i++)
            stringBuilder.append('e');
        for(int i = 0; i < 5; i++)
            stringBuilder.append('f');

        StringBuilder expectedString = new StringBuilder();
        for(int i = 0; i < 45; i++)
            expectedString.append("0");
        for(int i = 0; i < 13; i++)
            expectedString.append("101");
        for(int i = 0; i < 12; i++)
            expectedString.append("100");
        for(int i = 0; i < 16; i++)
            expectedString.append("111");
        for(int i = 0; i < 9; i++)
            expectedString.append("1101");
        for(int i = 0; i < 5; i++)
            expectedString.append("1100");

       Assertions.assertEquals(expectedString.toString(),huffman.encodeText(stringBuilder.toString()));

       stringBuilder = new StringBuilder();
       expectedString = new StringBuilder();


       //example from internet
        for(int i = 0; i < 12; i++)
            stringBuilder.append('a');
        for(int i = 0; i < 15; i++)
            stringBuilder.append('b');
        for(int i = 0; i < 7; i++)
            stringBuilder.append('c');
        for(int i = 0; i < 13; i++)
            stringBuilder.append('d');
        for(int i = 0; i < 9; i++)
            stringBuilder.append('e');

        for(int i = 0; i < 12; i++)
            expectedString.append("00");
        for(int i = 0; i < 15; i++)
            expectedString.append("10");
        for(int i = 0; i < 7; i++)
            expectedString.append("110");
        for(int i = 0; i < 13; i++)
            expectedString.append("01");
        for(int i = 0; i < 9; i++)
            expectedString.append("111");
        Assertions.assertEquals(expectedString.toString(),huffman.encodeText(stringBuilder.toString()));
    }

}
