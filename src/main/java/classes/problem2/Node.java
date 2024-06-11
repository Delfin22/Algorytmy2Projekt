package classes.problem2;

public class Node {
    private int freq;
    private  char letter;
    private Node leftChild;
    private Node rightChild;

    public Node() {
        leftChild = null;
        rightChild = null;
    }

    public Node(int freq, char letter) {
        this.freq = freq;
        this.letter = letter;
        leftChild = null;
        rightChild = null;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}
