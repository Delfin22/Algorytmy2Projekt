package org.algorithmProject;
import classes.problem2.Huffman;
import classes.problem2.Node;

public class Main {
    public static void main(String[] args) {
        Node node = Huffman.createTree("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbccccccccccccddddddddddddddddfffffeeeeeeeee");
        node.print("");
        System.out.println();
        Huffman.createHashMap(node);

    }
}