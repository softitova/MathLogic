import javafx.util.Pair;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;


/**
 * Created by Sofia228 on 15.10.16.
 */

public class Checker {

    private static ArrayList<String> proof = new ArrayList();
    public static ArrayList<Node> axiomsRoots = new ArrayList<>();
    private static Node curRoot;


    static {
        curRoot = new Node();
        for (int i = 0; i < 10; i++) {
            axiomsRoots.add(ExpressionParser.parse(Axioms.getAxiomsArrElement(i)));
        }
    }

    public static HashMap<String, ArrayList<Pair<String, Integer>>> leftRightPartToMP = new HashMap<>();
    public static HashMap<String, Integer> trueLines = new HashMap<>();
    public static HashMap<String, Integer> hypotesisMap = new HashMap<>();


    public static void addToMap(int count) {
        if (curRoot.leftChild != null) {
            if (!leftRightPartToMP.containsKey(curRoot.leftChild.toString())) {
                ArrayList<Pair<String, Integer>> t = new ArrayList<>();
                if (curRoot.rightChild != null) {
                    t.add(new Pair<>(curRoot.rightChild.toString(), count));
                    leftRightPartToMP.put(curRoot.leftChild.toString(), t);
                }
            } else {
                leftRightPartToMP.get(curRoot.leftChild.toString()).
                        add(new Pair<>(curRoot.rightChild.toString(), count));
            }
        }
        if (leftRightPartToMP.containsKey(curRoot.toString())) {   
            ArrayList<Pair<String, Integer>> temp = leftRightPartToMP.get(curRoot.toString());
            for (Pair<String, Integer> p : temp) {
                trueLines.put(p.getKey(), p.getValue());
            }
        }
        if (curRoot.leftChild != null && curRoot.rightChild != null) {
            if (trueLines.containsKey(curRoot.leftChild.toString())) {

                trueLines.put(curRoot.rightChild.toString(), count);

            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("good4.in"));
        String h = in.readLine();
        h = h.replaceAll(" ", "");
        String h1 = "";
        String toProve = "";
        boolean flag = false;
        for (int i = 0; i < h.length() - 1; i++) {
            if (!(h.charAt(i) == '|' && h.charAt(i + 1) == '-') && !flag) {
                h1 += h.charAt(i);
            } else {
                if (!flag) {
                    i += 2;
                    flag = true;
                }
                toProve += h.charAt(i);
            }
        }

        StringTokenizer st = new StringTokenizer(h1, ",", false);
        for (int j = 0; j < st.countTokens(); j++) {
            String hyp = st.nextToken();
            hypotesisMap.put(hyp, j);
            trueLines.put(hyp, j);
        }

        String s = in.readLine();
        s = s.replaceAll(" ", "");
        int count = 0;
        while (s != null) {
            s = s.replaceAll(" ", "");
            proof.add(s);
            s = in.readLine();
        }

        for (String line : proof) {
            curRoot = ExpressionParser.parse(line);
            if (hypotesisMap.containsKey(line)) {
                System.out.println("Предп. " + (hypotesisMap.get(line) + 1));
            } else if (!Axioms.checkAxiom(curRoot, count)) {
                ModusPonens.MP(count, curRoot);
            }
            count++;
        }
//        if(trueLines.containsKey(toProve))  {
//            System.out.println("correct");
//            return;
//        }
//        System.out.println("OOPs");
        System.out.println("end");
    }
}

