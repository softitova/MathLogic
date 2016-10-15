import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by macbook on 15.10.16.
 */
class ModusPonens {

    private static int pos = 2;

    static Pair MP(String line, Node curRoot, int curNumber) {
        //getPos(curRoot.leftChild);
        long hashCodeRight = getHashCode(line, 0, line.length());
        long hashCodeLeft;
        if (Checker.rightPartToMP.containsKey(hashCodeRight)) {
            ArrayList hashCodeLeftArr = Checker.rightPartToMP.get(hashCodeRight);
            for (int i=0;i<hashCodeLeftArr.size();i++) {
                int numderOfLineS =-1;
                Pair p = (Pair)hashCodeLeftArr.get(i);
                if (Checker.trueMPLines.containsKey(p.getKey())) {
                    int numderOfLineF = Checker.trueMPLines.get(p.getKey());
                    numderOfLineS =(int) p.getValue();
                    Checker.trueMPLines.put(hashCodeRight, curNumber);
                    return new Pair(numderOfLineF, numderOfLineS);
                }
            }
        }
        return new Pair(-1, -1);

    }

    static long getHashCode(String line, int begin, int end) {
        final int p = 31;
        long hash = 0;
        long pPow = 1;
        for (int i = begin; i < end; i++) { // which position
            hash += (line.charAt(i) - 'a' + 1) * pPow;
            pPow *= p;
        }
        return hash;
    }

}
