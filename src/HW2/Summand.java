package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Summand implements Expression {
    Expression firstExpression;
    Expression secondExpression;
    Summand(Expression e1, Expression e2) {
        this.firstExpression = e1;
        this.secondExpression = e2;
    }

    public String exToStr() {
        return "("
                + firstExpression.exToStr()
                + "+"
                + secondExpression.exToStr()
                + ")";
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof Summand))
            return false;
        Summand summand = (Summand)expression;
        return firstExpression.eqT(summand.firstExpression)
                && secondExpression.eqT(summand.secondExpression);
    }

    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (!(expression instanceof Summand))
            return false;
        Summand summand = (Summand)expression;
        return firstExpression.eqTT(summand.firstExpression, stringExpressionMap)
                && secondExpression.eqTT(summand.secondExpression, stringExpressionMap);
    }

    public boolean freeE(Variable variable) {
        return firstExpression.freeE(variable)
                | secondExpression.freeE(variable);
    }
    public Expression replV(Variable variable, Expression expression) {
        return new Summand(firstExpression.replV(variable, expression),
                secondExpression.replV(variable, expression));
    }
    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> stringExpressionMap = new HashMap<>();
        if (!(expression instanceof Summand))
            return stringExpressionMap;
        Summand summand = (Summand)expression;
        stringExpressionMap.putAll(firstExpression.getV(summand.firstExpression));
        stringExpressionMap.putAll(secondExpression.getV(summand.secondExpression));
        return stringExpressionMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Summand summand = (Summand) o;

        if (firstExpression != null ? !firstExpression.equals(summand.firstExpression) : summand.firstExpression != null)
            return false;
        return secondExpression != null ? secondExpression.equals(summand.secondExpression) : summand.secondExpression == null;

    }

    @Override
    public int hashCode() {
        int result = firstExpression != null ? firstExpression.hashCode() : 0;
        result = 31 * result + (secondExpression != null ? secondExpression.hashCode() : 0);
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

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        return firstExpression.haveBE(variable, stringSet, stringList)
                | secondExpression.haveBE(variable, stringSet, stringList);
    }

    @Override
    public Class<? extends Expression> gType() {
        return Summand.class;
    }
}
