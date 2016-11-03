import javafx.util.Pair;


import java.io.*;
import java.util.*;


/**
 * Last Version Created By Sofia
 */

public class Checker {

    public static ArrayList<String> proof = new ArrayList();
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
    public static HashMap<String, Integer> assumptionMap = new HashMap<>();
    public static HashMap<String, String> trueRightLeft = new HashMap<>();


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
                if(!trueLines.containsKey(p.getKey())) {
                    trueLines.put(p.getKey(), null);
                } // можно записывать нул как номер чтобы понимать что это только возможно доказано / но что если мы эту строеку уже ранее писали и теперь ее нул никто не перепишет?
                trueRightLeft.put(p.getKey().toString(),curRoot.toString());
            }
        }
        if (curRoot.leftChild != null && curRoot.rightChild != null) {
            if (trueLines.containsKey(curRoot.leftChild.toString())) {
                trueLines.put(curRoot.rightChild.toString(), count);
                trueRightLeft.put(curRoot.rightChild.toString(), curRoot.leftChild.toString());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("good6.in"));
        PrintStream out = new PrintStream(new File("output.txt"));
        String h = in.readLine();

        h = h.replaceAll(" ", "");
        out.println(h);
        String h1 = "";
        String toProve = "";
        boolean flag = false;
        int asCount=0;
        for (int i = 0; i < h.length() - 1; i++) {
            if (!(h.charAt(i) == '|' && h.charAt(i + 1) == '-') && !flag) {
                if(h.charAt(i)!=',') {
                    h1 += h.charAt(i);
                } else {
                    //i++;
                    curRoot = ExpressionParser.parse(h1);
                    assumptionMap.put(curRoot.toString(), asCount);
                    trueLines.put(curRoot.toString(), null);
                    addToMap(asCount);
                    asCount++;
                    h1="";
                }
            } else {
                curRoot = ExpressionParser.parse(h1);
                assumptionMap.put(curRoot.toString(), asCount);
                trueLines.put(curRoot.toString(), null);
                addToMap(asCount);
                asCount++;
                if (!flag) {
                    i += 2;
                    flag = true;
                }
                toProve += h.charAt(i);
            }
        }


//        for (int j = 0; j < st.countTokens(); j++) {
//            curRoot = ExpressionParser.parse(st.nextToken());
//            assumptionMap.put(curRoot.toString(), j);
//            trueLines.put(curRoot.toString(), null);
//            addToMap(j);
//        }

        String s = in.readLine();
        s.trim();
        s = s.replaceAll(" ", "");
        int count = 0;
        while (s != null) {
            s = s.replaceAll(" ", "");
            proof.add(s);
            s = in.readLine();
        }

        for (String line : proof) {
            curRoot = ExpressionParser.parse(line);
            if (assumptionMap.containsKey(curRoot.toString())) {
                out.println("(" + (count + 1) + ") " + line + " (Предп. " + (assumptionMap.get(curRoot.toString()) + 1) + ")");
//                curRoot = ExpressionParser.parse(proof.get(count));
//                trueLines.put(curRoot.toString(), count);
//                addToMap(count);
            } else if (!Axioms.checkAxiom(curRoot, count, out)) {
                ModusPonens.MP(count, curRoot, out);
            }
            count++;
        }
        
        out.close();
    }
}

