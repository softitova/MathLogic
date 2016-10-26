import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by macbook on 15.10.16.
 */
class ModusPonens {
    protected static void MP(int count, Node curRoot) {
        if (!Checker.trueLines.containsKey(curRoot.toString())) {
            System.out.println("Не доказано");
        } else {
            Checker.addToMap(count);
            System.out.println("M.P. " + (Checker.trueLines.get(curRoot.toString())+1) + ", " +
                    (count+1) );
        }
    }

}
