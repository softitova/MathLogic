package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Const implements Expression {
    Expression expression;
    boolean zero;

    public Const(int a) {
        if (a == 0) {
            zero = true;
            expression = null;
        }
    }

    public Const(Expression e1) {
        this.expression = e1;
        zero = false;
    }

    public String exToStr() {
        if (zero) {
            return "0";
        }
        try {
            return expression.exToStr() + "'";
        } catch (Exception e) {
            //System.out.println("HERERERERE");
            //System.exit(0);
        }
        return "0";
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof Const))
            return false;
        Const aConst = (Const) expression;
        return zero == aConst.zero && (zero || this.expression.eqT(aConst.expression));
    }

    public boolean eqTT(Expression expression1, Map<String, Expression> stringExpressionMap) {
        if (!(expression1 instanceof Const))
            return false;
        Const aConst = (Const) expression1;
        return zero == aConst.zero && (zero || expression.eqTT(aConst.expression, stringExpressionMap));
    }

    public boolean freeE(Variable variable) {
        return !zero && expression.freeE(variable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Const aConst = (Const) o;

        if (zero != aConst.zero) return false;
        return expression != null ? expression.equals(aConst.expression) : aConst.expression == null;

    }

    @Override
    public int hashCode() {
        int result = expression != null ? expression.hashCode() : 0;
        result = 31 * result + (zero ? 1 : 0);
        return result;
    }

    public Expression getExpression() {

        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }

    public Expression replV(Variable variable, Expression expression) {
        if (zero) return new Const(0);
        return new Const(this.expression.replV(variable, expression));
    }

    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> res = new HashMap<>();
        if (!(expression instanceof Const))
            return res;
        Const c = (Const) expression;
        if (zero || c.zero) return res;
        res.putAll(this.expression.getV(c.expression));
        return res;
    }

    public boolean haveBE(Variable variablex, Set<String> stringSet, List<String> stringList) {
        return !zero && expression.haveBE(variablex, stringSet, stringList);
    }

    @Override
    public Class<? extends Expression> gType() {
        return Const.class;
    }
}
