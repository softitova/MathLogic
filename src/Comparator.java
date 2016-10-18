import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by macbook on 15.10.16.
 */
public class Comparator {
    public static HashMap<String, Node> roots;
    private static String vars[] = new String[]{"a", "b", "c"};


    private static boolean containsVar(String s) {

        for (String x : vars) {
            if (x.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compare(Node curRoot, Node axiomsRoot) {

        while (!containsVar(axiomsRoot.current)) {
            if (!curRoot.current.equals(axiomsRoot.current)) {
                return false;
            } else {
                boolean res = true;
                if (!containsVar(axiomsRoot.leftChild.current)) {
                    res = compare(curRoot.leftChild, axiomsRoot.leftChild);
                } else {

                    if (roots.containsKey(axiomsRoot.leftChild.current)) {
                        if (!simpleCompare(roots.get(axiomsRoot.leftChild.current), curRoot.leftChild)) {
                            return false;
                        }
                    }
                    roots.put(axiomsRoot.leftChild.current, curRoot.leftChild);
                }
                if (!containsVar(axiomsRoot.rightChild.current)) {

                    res &= compare(curRoot.rightChild, axiomsRoot.rightChild);
                } else {
                    if (roots.containsKey(axiomsRoot.rightChild.current)) {
                        if (!simpleCompare(roots.get(axiomsRoot.rightChild.current), curRoot.rightChild)) {
                            return false;
                        }
                    }
                    roots.put(axiomsRoot.rightChild.current, curRoot.rightChild);
                }
                return res;
            }
        }


        return true;
    }

    private static boolean simpleCompare(Node first, Node second) {
        if ((first.current == null && second.current != null) || (second.current == null && first.current != null)) {
            return false;
        }
        while (first.current != null) {
            if (!first.current.equals(second.current)) {
                return false;
            } else {
                boolean res = true;
                if (first.leftChild != null) {
                    res = simpleCompare(first.leftChild, second.leftChild);
                }
                if (first.rightChild != null) {
                    res &= simpleCompare(first.rightChild, second.rightChild);
                }
                return res;
            }
        }
        return true;
    }
}
