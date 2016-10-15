/**
 * Created by macbook on 14.10.16.
 */
class Node {

    String current = "";
    Node leftChild = null;
    Node rightChild = null;

    Node() {}

    Node(String value) {
        current = value;
    }

    Node(String value, Node l, Node r) {
        current = value;
        leftChild = l;
        rightChild = r;
    }

}
