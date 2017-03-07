package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Implication implements Expression{

    Expression firstExpression;
    Expression secondExpression;

    Implication(Expression e1, Expression e2) {
//        super(firstExpression, secondexpression, "->");
//        this.type = Implication.class;
        this.firstExpression = e1;
        this.secondExpression = e2;
    }

    Expression leftArg() {
        return firstExpression;
    }

    Expression rightArg() {
        return secondExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Implication that = (Implication) o;

        if (firstExpression != null ? !firstExpression.equals(that.firstExpression) : that.firstExpression != null)
            return false;
        return secondExpression != null ? secondExpression.equals(that.secondExpression) : that.secondExpression == null;

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

    public String exToStr() {

        return "("
                + firstExpression.exToStr()
                + ") -> ("
                + secondExpression.exToStr()
                + ")";
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof Implication))
            return false;
        Implication expression1 = (Implication) expression;
        return firstExpression.eqT(expression1.firstExpression)
                && secondExpression.eqT(expression1.secondExpression);
    }

    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (!(expression instanceof Implication))
            return false;
        Implication implication = (Implication) expression;
        return firstExpression.eqTT(implication.firstExpression, stringExpressionMap)
                && secondExpression.eqTT(implication.secondExpression, stringExpressionMap);
    }

    public boolean freeE(Variable variable) {
        return firstExpression.freeE(variable) | secondExpression.freeE(variable);
    }

    public Expression replV(Variable variable, Expression expression) {
        return new Implication(firstExpression.replV(variable, expression),
                secondExpression.replV(variable, expression));
    }

    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> stringExpressionMap = new HashMap<>();
        if (!(expression instanceof Implication))
            return stringExpressionMap;
        Implication c = (Implication) expression;
        stringExpressionMap.putAll(firstExpression.getV(c.firstExpression));
        stringExpressionMap.putAll(secondExpression.getV(c.secondExpression));
        return stringExpressionMap;
    }

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        return firstExpression.haveBE(variable, stringSet, stringList)
                | secondExpression.haveBE(variable, stringSet, stringList);
    }

    @Override
    public Class<? extends Expression> gType() {
        return Implication.class;
    }
}