package classes.problem2;

import java.util.*;

public class Huffman {
    public static Node createTree(String text) {
        int n = PatternTools.getLettersInAlphabet();
        int index;
        PriorityQueue<Node> q = new PriorityQueue<>(n, Comparator.comparingInt(Node::getFreq));
        List<Integer> freqList = new ArrayList<>(Collections.nCopies(n, 0)); //initialise with 0s

        //count letters
        for (int i = 0; i < text.length(); i++) {
            index = text.charAt(i) - PatternTools.getFirstLetter();
            freqList.set(index, freqList.get(index) + 1);
        }

        //create nodes
        for (int i = 0; i < n; i++) {
            if (freqList.get(i) != 0) {
                Node node = new Node();
                node.setLetter((char) (PatternTools.getFirstLetter() + i));
                node.setFreq(freqList.get(i));
                q.add(node);
            }
        }

        Node root = null;
        while (q.size() > 1) {
            Node x = q.peek();
            q.poll();
            Node y = q.peek();
            q.poll();

            Node f = new Node();
            f.setFreq(x.getFreq() + y.getFreq());
            f.setLetter('-');
            f.setLeftChild(x);
            f.setRightChild(y);
            root = f;
            q.add(f);
        }
        return root;
    }

    public static LinkedHashMap<Character, String> createHashMap(Node root) {
        LinkedHashMap<Character, String> codes = new LinkedHashMap<>();

        Stack<Node> stack = new Stack<>();
        Node node = root;
        Stack<StringBuilder> codeStack = new Stack<>();
        StringBuilder currCode = new StringBuilder();
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.add(node);
                codeStack.add(new StringBuilder(currCode));  // ensure independent path code
                currCode.append('0');
                node = node.getLeftChild();
            }
            node = stack.pop();
            currCode = codeStack.pop();

            if (node.getLeftChild() == null && node.getRightChild() == null && Character.isLetter(node.getLetter())) {
                // System.out.println("- " + node.getLetter() + " " + node.getFreq() + " " + currCode);
                codes.put(node.getLetter(), currCode.toString());
            }

            node = node.getRightChild();
            currCode.append('1');
        }

        return codes;
    }

    public static String encodeText(String text) {
        Node root = createTree(text);
        return null;
    }
}
