import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by macbook on 15.10.16.
 */
public class Axioms {
    private static String axiomsArr[] = new String[]{
            "a->b->a",
            "(a->b)->(a->b->c)->(a->c)",
            "a->b->a&b",
            "a&b->a",
            "a&b->b",
            "a->a|b",
            "b->a|b",
            "(a->c)->(b->c)->(a|b->c)",
            "(a->b)->(a->!b)->!a",
            "!!a->a"};


    protected static String getAxiomsArrElement(int i) {
        return axiomsArr[i];
    }

    protected static boolean checkAxiom(Node curRoot, int count, PrintStream out) {
        for (int i = 0; i < Checker.axiomsRoots.size(); i++) {
            Comparator.roots = new HashMap<>();
            if (Comparator.compare(curRoot, Checker.axiomsRoots.get(i))) {
                //System.out.println("Сх. акс. " + (i + 1));
                out.println("(" + (count + 1) +") " +Checker.proof.get(count) + " (Сх. акс. " + (i + 1)+")");
                Checker.trueLines.put(curRoot.toString(), count);
                Checker.addToMap(count);
                return true;
            }
        }
        return false;
    }

}

