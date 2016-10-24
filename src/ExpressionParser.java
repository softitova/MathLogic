import java.util.ArrayList;

/**
 * Created by macbook on 04.10.16.
 */
public class ExpressionParser extends Node {

    /*public static void main(String[] args) {
        String r = "aa";

        Node resRoot = parse(r);

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

     static Node parse(String str) {
        index = 0;
        expr = str.toLowerCase();
        root = makeExpr();
        return root;
       // return (new ExpressionParser(str.toLowerCase()).root);
    }

    private static Node makeExpr() {
        Node curRoot = disjunction();
        if (index < expr.length() && expr.charAt(index) == '-') {
            index += 2;
            curRoot = new Node("-", curRoot, makeExpr());
        }
        return curRoot;
    }

    private static Node disjunction() {
        Node curRoot = conjunction();
        if (index < expr.length() && expr.charAt(index) == '|') {
            index++;
            curRoot = new Node("|", curRoot, conjunction());
        }
        return curRoot;
    }

    private static Node conjunction() {
        Node curRoot = negation();
        if (index < expr.length() && expr.charAt(index) == '&') {
            index++;
            curRoot = new Node("&", curRoot, negation());
        }
        return curRoot;
    }

    private static Node negation() {
        Node curRoot;
        if (expr.charAt(index) == '!') {
            index++;
            curRoot = new Node("!", null, negation()); // too strange !a|b
        } else if (expr.charAt(index) == '(') {
            index++;// ?????? crazy brackets
            curRoot = makeExpr();
            index++;
        } else {
            StringBuilder val = new StringBuilder();
            while (index < expr.length() && Character.isLetterOrDigit(expr.charAt(index))) {
                val.append(expr.charAt(index));
                index++;
            }
            curRoot = new Node(val.toString());
        }
        return curRoot;
    }


}
