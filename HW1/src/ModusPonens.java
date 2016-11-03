import java.io.PrintStream;

/**
 * Created by macbook on 15.10.16.
 */
class ModusPonens {
    protected static void MP(int count, Node curRoot, PrintStream out) {
        if (!Checker.trueLines.containsKey(curRoot.toString()) && Checker.trueLines.get(curRoot.toString())!=null) {
            out.println("(" + (count + 1) + ") " + Checker.proof.get(count) + " (" + "Не доказано" + ")");
        } else {
            Checker.addToMap(count);
            String lp = Checker.trueRightLeft.get(curRoot.toString());
            Checker.trueLines.put(curRoot.toString(), count);
            if(!Checker.trueLines.containsKey(lp) || Checker.trueLines.get(lp)==null ||
                    (Checker.trueLines.get("(" + lp + ")-(" + curRoot.toString() + ")")==null)||
                    (!Checker.trueLines.containsKey("(" + lp + ")-(" + curRoot.toString() + ")"))  ) {
                out.println(("(" + (count + 1) + ") " + Checker.proof.get(count)+" (Не доказано)"));
            } else {
                int ind1 = Checker.trueLines.get(lp) + 1;
                int ind2 = Checker.trueLines.get("(" + lp + ")-(" + curRoot.toString() + ")") + 1;
                out.println("(" + (count + 1) + ") " + Checker.proof.get(count)+" (M.P. " + ind1 + ", " +
                        ind2 + ")");
            }
        }
    }
}