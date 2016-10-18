import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.util.Pair;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


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

    static HashMap<String, Integer> trueMPLines = new HashMap<>();
    static HashMap<String, ArrayList<Pair<String, Integer>>> rightPartToMP = new HashMap<>();


    private static void addToMap(int count) {
        if (!rightPartToMP.containsKey(curRoot.rightChild.toString())) {
            ArrayList<Pair<String, Integer>> t = new ArrayList<>();
            t.add(new Pair<>(curRoot.leftChild.toString(), count));
            rightPartToMP.put(curRoot.rightChild.toString(), t);
        } else {
            rightPartToMP.get(curRoot.rightChild.toString()).add(new Pair<>(curRoot.rightChild.toString(), count));
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        String s = in.readLine();
        int count = 0;
        while (s != null) {
            proof.add(s);
            s = in.readLine();
        }
        for (String line : proof) {
            boolean axiomFound = false;
            curRoot = ExpressionParser.parse(line);
            for (int i = 0; i < axiomsRoots.size(); i++) {
                Comparator.roots = new HashMap<>();
                if (Comparator.compare(curRoot, axiomsRoots.get(i))) {
                    System.out.println("Сх. акс. " + (i + 1));
                    axiomFound = true;
                    trueMPLines.put(curRoot.toString(), count);
                    if (trueMPLines.containsKey(curRoot.leftChild.toString())) {
                        addToMap(count);
                    }
                    break;
                }
            }
            if (!axiomFound) {
                Pair<Integer, Integer> resNumberF = ModusPonens.MP(curRoot, count);
                if (resNumberF.getKey() == -1) {
                    System.out.println("Wrong proof");
                } else {
                    addToMap(count);
                    System.out.println("M.P. " + resNumberF.getKey() + ", " +
                            resNumberF.getValue());
                }
            }
            count++;
        }
    }
}

