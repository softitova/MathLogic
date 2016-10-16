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

    public static Node parse(String str) {
        return (new ExpressionParser(str.toLowerCase()).root);
    }

    private static Node makeExpr() {
        Node curRoot = disjunction();
        while (index < expr.length() && expr.charAt(index) == '-') {
            index += 2;
            curRoot = new Node("-", curRoot, makeExpr());
        }
        return curRoot;
    }

    private static Node disjunction() {
        Node curRoot = conjunction();
        while (index < expr.length() && expr.charAt(index) == '|') {
            index++;
            curRoot = new Node("|", curRoot, conjunction());
        }
        return curRoot;
    }

    private static Node conjunction() {
        Node curRoot = negation();
        while (index < expr.length() && expr.charAt(index) == '&') {
            index++;
            curRoot = new Node("&", curRoot, negation());
        }
        return curRoot;
    }

    private static Node negation() {
        Node curRoot;
        char sumbol = expr.charAt(index);
        if (sumbol == '!') {
            index++;
            curRoot = new Node("!", null, negation()); // too strange !a|b
        } else if (sumbol == '(') {
            index++;// ?????? crazy brackets
            curRoot = makeExpr();
            index++;
        } else {
            String val = "";
            char cur = expr.charAt(index);
            while ((cur<='z' && cur>='a') ||
                    (cur<='9' && cur>='0')) {
                val+=cur;
                index++;
                if(index==expr.length()){
                    break;
                }
                cur = expr.charAt(index);
            }
            curRoot = new Node(val + "");
           // index++;
        }
        return curRoot;
    }


}
