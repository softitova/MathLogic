package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Quant implements Expression {
    static final char FORALL = '@';
    static final char EXISTS = '?';
    private char quant;
    Expression expression;
    Expression firstExpression;
    public Quant(char quant, Expression expression, Expression expression1) {
        this.quant = quant;
        this.expression = expression;
        this.firstExpression = expression1;
    }

    public String exToStr() {
//		return "(" + quant + expression.exToStr() + firstExpression.exToStr() + ")";
        return "(" + quant + expression.exToStr() + "(" + firstExpression.exToStr() + "))";
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof Quant))
            return false;
        Quant quant = (Quant)expression;
        return this.quant == quant.quant &&
                this.expression.eqT(quant.expression)
                && firstExpression.eqT(quant.firstExpression);
    }

    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (!(expression instanceof Quant))
            return false;
        Quant quant = (Quant)expression;
        return this.quant == quant.quant
                && this.expression.eqTT(quant.expression, stringExpressionMap)
                && firstExpression.eqTT(quant.firstExpression, stringExpressionMap);
    }

    public boolean freeE(Variable variable) {
        Variable cur = (Variable) expression;
        return !cur.currentSymbol.equals(variable.currentSymbol) && firstExpression.freeE(variable);
    }

    Expression getExpr() {
        return firstExpression;
    }
    char getQuant() {
        return quant;
    }
    Variable getVar() {
        return (Variable) expression;
    }

    public Expression replV(Variable variable, Expression expression) {
        Variable variable1 = (Variable) this.expression;
        if (!variable1.currentSymbol.equals(variable.currentSymbol)) {
            return new Quant(quant, this.expression, firstExpression.replV(variable, expression));
        }
        return new Quant(quant, this.expression, firstExpression);
    }


    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> stringExpressionMap = new HashMap<>();
        if (!(expression instanceof Quant))
            return stringExpressionMap;
        Quant c = (Quant)expression;
        stringExpressionMap.putAll(this.expression.getV(c.expression));
        stringExpressionMap.putAll(firstExpression.getV(c.firstExpression));
        return stringExpressionMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quant quant1 = (Quant) o;

        if (quant != quant1.quant) return false;
        if (expression != null ? !expression.equals(quant1.expression) : quant1.expression != null) return false;
        return firstExpression != null ? firstExpression.equals(quant1.firstExpression) : quant1.firstExpression == null;

    }

    @Override
    public int hashCode() {
        int result = (int) quant;
        result = 31 * result + (expression != null ? expression.hashCode() : 0);
        result = 31 * result + (firstExpression != null ? firstExpression.hashCode() : 0);
        return result;
    }

    public static char getFORALL() {

        return FORALL;
    }

    public static char getEXISTS() {
        return EXISTS;
    }

    public void setQuant(char quant) {
        this.quant = quant;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getFirstExpression() {
        return firstExpression;
    }

    public void setFirstExpression(Expression firstExpression) {
        this.firstExpression = firstExpression;
    }

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        stringList.add(expression.exToStr());
        boolean haveBE = firstExpression.haveBE(variable, stringSet, stringList);
        stringList.remove(expression.exToStr());
        return haveBE;
    }

    @Override
    public Class<? extends Expression> gType() {
        return Quant.class;
    }


}
