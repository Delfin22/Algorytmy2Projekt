package org.algorithmProject;

import classes.problem2.Huffman;
import classes.problem2.Node;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Node node = Huffman.createTree("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbccccccccccccddddddddddddddddfffffeeeeeeeee");
        node.print("");
        System.out.println();

        Map<Character, String> letter_codes = Huffman.createHashMap(node);
        letter_codes.forEach(((character, code) -> System.out.printf("%c %s\n", character, code)));
    }
}