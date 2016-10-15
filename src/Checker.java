import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Created by macbook on 15.10.16.
 */

public class Checker {

    private static ArrayList<String> proof = new ArrayList();
    private static ArrayList<Node> axiomsRoots = new ArrayList<>();

    private static Node curRoot;

    static {
        curRoot = new Node();
        for (int i = 0; i < 10; i++) {
            axiomsRoots.add(ExpressionParser.parse(Axioms.getAxiomsArrElement(i)));
        }
    }

    static HashMap<Long, Integer> trueMPLines = new HashMap<>();
    static HashMap<Long, ArrayList<Pair<Long, Integer>>> rightPartToMP = new HashMap<>();


    private static Pair<String, Integer> restoreLine(Node curRoot) {
        String restoredLine = "";
        if (curRoot.leftChild != null) {
            restoredLine = "(" + restoreLine(curRoot.leftChild).getKey();
        }
        restoredLine += curRoot.current;
        int index = restoredLine.length();

        if (curRoot.rightChild != null) {
            restoredLine += restoreLine(curRoot.rightChild).getKey() + ")";
        }
        return new Pair(restoredLine, index);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = 0;
        /*while (in.hasNextLine()) {
            proof.add(in.nextLine());
        }*/
        proof.add("(a->a->a)");  //   учитывать скобки в парсере, отрицание в парсер
        proof.add("(a->(a->a))->(a->((a->a)->a))->(a->a)"); //
        proof.add("(a->((a->a)->a))->(a->a)");
        proof.add("a->((a->a)->a)");
        proof.add("a->a");
        // proof.add("a->b->a->a");
        for (String line : proof) {
            boolean axiomFound = false;
            curRoot = ExpressionParser.parse(line);
            //restoredLine="";
            line = restoreLine(curRoot).getKey();
            int position = restoreLine(curRoot).getValue() - 1;
            System.out.println(line);
            for (int i = 0; i < axiomsRoots.size(); i++) {
                Comparator.roots = new HashMap<>();
                if (Comparator.compare(curRoot, axiomsRoots.get(i))) {
                    System.out.println("Сх. акс. " + (i + 1));
                    axiomFound = true;
                    trueMPLines.put(ModusPonens.getHashCode(line, 0, line.length()), count);
                    long leftHash = ModusPonens.getHashCode(line, 1, position);
                    long rightHash = ModusPonens.getHashCode(line, position + 1, line.length() - 1);
                    if (trueMPLines.containsKey(leftHash)) {
                        if (!rightPartToMP.containsKey(rightHash)) {
                            ArrayList t = new ArrayList();
                            t.add(new Pair(leftHash, count));
                            rightPartToMP.put(rightHash, t);
                        } else {
                            ArrayList t = rightPartToMP.get(rightHash);
                            t.add(new Pair(leftHash, count));
                            rightPartToMP.replace(rightHash, t);
                        }
                    }
                    break;
                }
            }
            if (!axiomFound) {
                int resNumberF = (int) ModusPonens.MP(line, curRoot, count).getKey();
                if (resNumberF == -1) {
                    System.out.print("Wrong proof");
                } else {
                    System.out.println("M.P. " + (resNumberF + 1) + ", " +
                            ((int) ModusPonens.MP(line, curRoot, count).getValue() + 1));
                }
            }
            count++;
        }
    }


}

