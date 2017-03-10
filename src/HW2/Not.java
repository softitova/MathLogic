package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Not implements Expression {

    private Expression firstExpression;

    Not(Expression e1) {
        this.firstExpression = e1;
    }

    public String exToStr() {
        return "!"
                + "("
                + firstExpression.exToStr()
                + ")";
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof Not))
            return false;
        Not not = (Not) expression;
        return firstExpression.eqT(not.firstExpression);
    }

    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (!(expression instanceof Not))
            return false;
        Not not = (Not) expression;
        return firstExpression.eqTT(not.firstExpression, stringExpressionMap);
    }

    public boolean freeE(Variable variable) {
        return firstExpression.freeE(variable);
    }

    public Expression replV(Variable variable, Expression expression) {
        return new Not(firstExpression.replV(variable, expression));
    }

    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> stringExpressionMap = new HashMap<>();
        if (!(expression instanceof Not))
            return stringExpressionMap;
        Not not = (Not) expression;
        stringExpressionMap.putAll(firstExpression.getV(not.firstExpression));
        return stringExpressionMap;
    }

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        return firstExpression.haveBE(variable, stringSet, stringList);
    }

    @Override
    public Class<? extends Expression> gType() {
        return Not.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Not not = (Not) o;

        return firstExpression != null ? firstExpression.equals(not.firstExpression) : not.firstExpression == null;

    }

    @Override
    public int hashCode() {
        return firstExpression != null ? firstExpression.hashCode() : 0;
    }

    public Expression getFirstExpression() {

        return firstExpression;
    }

    public void setFirstExpression(Expression firstExpression) {
        this.firstExpression = firstExpression;
    }
}
