package HW2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sofia228 on 02.01.17.
 */


class Binary implements Expression {
    Expression e1;
    Expression e2;
    private String s;

    Class<? extends Expression> type;

    Binary(Expression e1, Expression e2, String s) {
        this.e1 = e1;
        this.e2 = e2;
        this.s = s;
    }

    public Class<? extends Expression> gType() {
        return this.type;
    }

    public boolean eqT(Expression curExpression) {
        if (!(curExpression.gType().equals(type)))
            // if (!(curExpression instanceof Binary))
            return false;
        Binary c = (Binary) curExpression;
        return e1.eqT(c.e1) && e2.eqT(c.e2);
    }



    public boolean eqTT(Expression curExpression, Map<String, Expression> mapOfExpressions) {
        if (!(curExpression.gType().equals(type)))
            // if (!(curExpression instanceof Binary))
            return false;
        Binary c = (Binary) curExpression;
        return e1.eqTT(c.e1, mapOfExpressions) && e2.eqTT(c.e2, mapOfExpressions);
    }

    public String exToStr() {
        return "(" + e1.exToStr() + ") " + s + " (" + e2.exToStr() + ")";
    }

    public boolean freeE(Variable xx) {
        return e1.freeE(xx) | e2.freeE(xx);
    }

    public Expression replV(Variable fromVariable, Expression toExpression) {
        return new Binary(e1.replV(fromVariable, toExpression), e2.replV(fromVariable, toExpression), s);
    }

    public Map<String, Expression> getV(Expression curExpression) {
        Map<String, Expression> res = new HashMap<>();
        if (!(curExpression.gType().equals(type)))
            //if (!(curExpression instanceof Binary))
            return res;
        Binary c = (Binary) curExpression;
        res.putAll(e1.getV(c.e1));
        res.putAll(e2.getV(c.e2));
        return res;
    }

    public boolean haveBE(Variable xx, Set<String> setOfVariables, List<String> listOfQuantors) {
        return e1.haveBE(xx, setOfVariables, listOfQuantors) | e2.haveBE(xx, setOfVariables, listOfQuantors);
    }
}
