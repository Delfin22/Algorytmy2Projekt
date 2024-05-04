package classes.problem2;

public class Node {
    private boolean value;  //interface uses integers
    private Node left;
    private Node right;

    public Node() {
    }

    public Node(int value) throws IllegalArgumentException{
        if(value < 0 || value > 1){
            throw new IllegalArgumentException();
        }
        this.value = (value != 0);
        right = null;
        left = null;
    }


    public int getValue() {
        return value ? 1 : 0;
    }

    public void setValue(int value) {
        this.value = (value != 0);
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
