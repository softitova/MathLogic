/**
 * Created by macbook on 15.10.16.
 */
public class Axioms {
    private static String axiomsArr[] = new String[]{ "a->b->a","(a->b)->(a->b->c)->(a->c)", "(a->b)->a&b",  "a&b->a",
            "a&b->b", "a->a|b", "b->a|b", "(a->c)->(b->c)->(a|b->c)", "(a->b)->(a->!b)->!a", "!!a->a"};

    public static String getAxiomsArrElement(int i) {
        return axiomsArr[i];
    }

}
