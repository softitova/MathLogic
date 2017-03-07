package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Or implements Expression {

    Expression secondExpression;
    Expression firstExpression;
    public Or(Expression e1, Expression e2) {
        this.secondExpression = e1;
        this.firstExpression = e2;
    }

    public String exToStr() {
        return "("
                + secondExpression.exToStr()
                + ") | ("
                + firstExpression.exToStr()
                + ")";
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof Or))
            return false;
        Or or = (Or)expression;
        return secondExpression.eqT(or.secondExpression)
                && firstExpression.eqT(or.firstExpression);
    }
    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (!(expression instanceof Or))
            return false;
        Or or = (Or)expression;
        return secondExpression.eqTT(or.secondExpression, stringExpressionMap)
                && firstExpression.eqTT(or.firstExpression, stringExpressionMap);
    }

    public boolean freeE(Variable variable) {
        return secondExpression.freeE(variable)
                | firstExpression.freeE(variable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Or or = (Or) o;

        if (secondExpression != null ? !secondExpression.equals(or.secondExpression) : or.secondExpression != null)
            return false;
        return firstExpression != null ? firstExpression.equals(or.firstExpression) : or.firstExpression == null;

    }

    @Override
    public int hashCode() {
        int result = secondExpression != null ? secondExpression.hashCode() : 0;
        result = 31 * result + (firstExpression != null ? firstExpression.hashCode() : 0);
        return result;
    }

    public Expression getSecondExpression() {

        return secondExpression;
    }

    public void setSecondExpression(Expression secondExpression) {
        this.secondExpression = secondExpression;
    }

    public Expression getFirstExpression() {
        return firstExpression;
    }

    public void setFirstExpression(Expression firstExpression) {
        this.firstExpression = firstExpression;
    }

    public Expression replV(Variable variable, Expression expression) {
        return new Or(secondExpression.replV(variable, expression),
                firstExpression.replV(variable, expression));
    }
    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> stringExpressionHashMap = new HashMap<>();
        if (!(expression instanceof Or))
            return stringExpressionHashMap;
        Or expression1 = (Or)expression;
        stringExpressionHashMap.putAll(secondExpression.getV(expression1.secondExpression));
        stringExpressionHashMap.putAll(firstExpression.getV(expression1.firstExpression));
        return stringExpressionHashMap;
    }

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        return secondExpression.haveBE(variable, stringSet, stringList)
                | firstExpression.haveBE(variable, stringSet, stringList);
    }

    @Override
    public Class<? extends Expression> gType() {
        return Or.class;
    }
}
