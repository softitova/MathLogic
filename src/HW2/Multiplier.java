package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Multiplier implements Expression {
    private Expression firstExpression;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Multiplier that = (Multiplier) o;

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

    private Expression secondExpression;
    Multiplier(Expression e1, Expression e2) {
        this.firstExpression = e1;
        this.secondExpression = e2;
    }

    public String exToStr() {
        return "("
                + firstExpression.exToStr()
                + "*"
                + secondExpression.exToStr()
                + ")";
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof Multiplier))
            return false;
        Multiplier multiplier = (Multiplier)expression;
        return firstExpression.eqT(multiplier.firstExpression)
                && secondExpression.eqT(multiplier.secondExpression);
    }

    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (!(expression instanceof Multiplier))
            return false;
        Multiplier multiplier = (Multiplier)expression;
        return firstExpression.eqTT(multiplier.firstExpression, stringExpressionMap)
                && secondExpression.eqTT(multiplier.secondExpression, stringExpressionMap);
    }
    public boolean freeE(Variable variable) {
        return firstExpression.freeE(variable) | secondExpression.freeE(variable);
    }
    public Expression replV(Variable variable, Expression expression) {
        return new Multiplier(firstExpression.replV(variable, expression),
                secondExpression.replV(variable, expression));
    }
    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> stringExpressionHashMap = new HashMap<>();
        if (!(expression instanceof Multiplier))
            return stringExpressionHashMap;
        Multiplier multiplier = (Multiplier)expression;
        stringExpressionHashMap.putAll(firstExpression.getV(multiplier.firstExpression));
        stringExpressionHashMap.putAll(secondExpression.getV(multiplier.secondExpression));
        return stringExpressionHashMap;
    }

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        return firstExpression.haveBE(variable, stringSet, stringList)
                | secondExpression.haveBE(variable, stringSet, stringList);
    }

    @Override
    public Class<? extends Expression> gType() {
        return Multiplier.class;
    }
}

