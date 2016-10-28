import javafx.util.Pair;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by macbook on 15.10.16.
 */
class ModusPonens {
    protected static void MP(int count, Node curRoot, PrintStream out) {
        if (!Checker.trueLines.containsKey(curRoot.toString())) {
           out.println("(" + (count + 2) +") "+Checker.proof.get(count) +"(" +" Не доказано"+")");
            //System.out.println("Не доказано");
        } else {
            Checker.addToMap(count);
            //System.out.println("M.P. " + (Checker.trueLines.get(curRoot.toString())+1) + ", " +(count+1) );
            out.println("(" + (count+2) +") "+Checker.proof.get(count) +" (M.P. " + (Checker.trueLines.get(curRoot.toString())+1) + ", " +
                    (count+1) + ")");
        }
    }

}
