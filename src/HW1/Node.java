package HW1;

class Node {
    
    String current = "";
    Node leftChild = null;
    Node rightChild = null;
    String d = "";

    @Override
    public String toString() {
        return d;
    }
    @Override
    public int hashCode() {
        return d.hashCode();
    }

    Node() {}

    Node(String value) {
        if (value != null)
            d = current = value;
    }

    Node(String value, Node l, Node r) {
        current = value;
        leftChild = l;
        rightChild = r;
        if (leftChild != null)
            d += "(" + leftChild.toString() + ")";
        d += current;
        if (rightChild != null)
            d += "(" + rightChild.toString() + ")";
    }

}
