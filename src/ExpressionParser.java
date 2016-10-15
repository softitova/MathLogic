import java.util.ArrayList;

/**
 * Created by macbook on 04.10.16.
 */
public class ExpressionParser extends Node {

    /*public static void main(String[] args) {
        String r = "a|b";

        Node resRoot = parse(r);
        System.out.println(resRoot.current.toString());
        System.out.println(resRoot.leftChild.current.toString());
        System.out.println(resRoot.rightChild.current.toString());
        System.out.println(resRoot.leftChild.leftChild.current.toString());
        System.out.println(resRoot.leftChild.rightChild.current.toString());

    }*/



    public static ArrayList<Node> tree = new ArrayList<>();


    private static String expr = "";
    private static Node root;
    private static int index = 0;

    private ExpressionParser(String str) {
        index = 0;
        expr = str;
        root = makeExpr();
    }

    public static Node parse(String str) {
        return (new ExpressionParser(str.toLowerCase()).root);
    }

    private static Node makeExpr() {
        Node curRoot = disjuction();
        while (index < expr.length() && expr.charAt(index) == '-') {
            index += 2;
            curRoot = new Node("-", curRoot, makeExpr());
            tree.add(curRoot);
        }
        return curRoot;
    }

    private static Node disjuction() {
        Node curRoot = conjuction();
        while (index < expr.length() && expr.charAt(index) == '|') {
            index++;
            curRoot = new Node("|", curRoot, conjuction());
            tree.add(curRoot);
        }
        return curRoot;
    }

    private static Node conjuction() {
        Node curRoot = negation();
        while (index < expr.length() && expr.charAt(index) == '&') {
            index++;
            curRoot = new Node("&", curRoot, negation());
            tree.add(curRoot);
        }
        return curRoot;
    }

    private static Node negation() {
        Node curRoot;
        char sumbol = expr.charAt(index);
        if (sumbol <= 'z' && sumbol >= 'a') {
            curRoot = new Node(expr.charAt(index) + "");
            index++;
        } else if (sumbol == '!') {
            index++;
            curRoot = new Node(makeExpr().current,new Node("!"), new Node()); // too strange !a|b
        } else if (sumbol == '(') {
            index++;// ?????? crazy brackets
            curRoot = makeExpr();
            index++;
        } else {
            curRoot = null;
            index++;
        }
        tree.add(curRoot);
        return curRoot;
    }


}
