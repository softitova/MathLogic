import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by macbook on 15.10.16.
 */
class ModusPonens {

    static Pair<Integer, Integer> MP(Node curRoot, int curNumber) {
        if (Checker.rightPartToMP.containsKey(curRoot.toString())) {
            ArrayList<Pair<String, Integer>> hashCodeLeftArr = Checker.rightPartToMP.get(curRoot.toString());
            for (Pair<String, Integer> p : hashCodeLeftArr) {
                if (Checker.trueMPLines.containsKey(p.getKey())) {
                    Checker.trueMPLines.put(curRoot.toString(), curNumber);
                    return new Pair<>(Checker.trueMPLines.get(p.getKey()) + 1, 1 + p.getValue());
                }
            }
        }
        return new Pair<>(-1, -1);
    }
}
