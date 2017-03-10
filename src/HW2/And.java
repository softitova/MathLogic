package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class And implements Expression {

    Expression firstExpression;
    Expression secondexpression;

    And(Expression e1, Expression e2) {
        this.firstExpression = e1;
        this.secondexpression = e2;
    }

    public String exToStr() {
            return "(" + firstExpression.exToStr() + ") & (" + secondexpression.exToStr() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        And and = (And) o;

        return firstExpression != null ? firstExpression.equals(and.firstExpression) : and.firstExpression == null && (secondexpression != null ? secondexpression.equals(and.secondexpression) : and.secondexpression == null);

    }

    @Override
    public int hashCode() {
        int result = firstExpression != null ? firstExpression.hashCode() : 0;
        result = 31 * result + (secondexpression != null ? secondexpression.hashCode() : 0);
        return result;
    }

    public Expression getSecondexpression() {

        return secondexpression;
    }

    public void setSecondexpression(Expression secondexpression) {
        this.secondexpression = secondexpression;
    }

    public Expression getFirstExpression() {

        return firstExpression;
    }

    public void setFirstExpression(Expression firstExpression) {
        this.firstExpression = firstExpression;
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof And))
            return false;
        And and = (And) expression;
        return firstExpression.eqT(and.firstExpression)
                && secondexpression.eqT(and.secondexpression);
    }

    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (!(expression instanceof And))
            return false;
        And expression1 = (And) expression;
        return firstExpression.eqTT(expression1.firstExpression, stringExpressionMap)
                && secondexpression.eqTT(expression1.secondexpression, stringExpressionMap);
    }

    public boolean freeE(Variable variable) {
        return firstExpression.freeE(variable) | secondexpression.freeE(variable);
    }

    public Expression replV(Variable variable, Expression expression) {
        return new And(firstExpression.replV(variable, expression), secondexpression.replV(variable, expression));
    }

    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> res = new HashMap<>();
        if (!(expression instanceof And))
            return res;
        And b1 = (And) expression;
        res.putAll(firstExpression.getV(b1.firstExpression));
        res.putAll(secondexpression.getV(b1.secondexpression));
        return res;
    }

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        return firstExpression.haveBE(variable, stringSet, stringList)
                | secondexpression.haveBE(variable, stringSet, stringList);
    }

    @Override
    public Class<? extends Expression> gType() {
        return And.class;
    }
}