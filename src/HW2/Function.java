package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class Function implements Expression {
    private String value;
    List<Expression> expressionList;
    Function(String val, List<Expression> e1) {
        this.value = val;
        this.expressionList = e1;
    }

    public String exToStr() {
        String res =  value + "(";
        for (int i = 0; i < expressionList.size(); i++) {
            res += expressionList.get(i).exToStr();
            if (i + 1 < expressionList.size()) {
                res += ", ";
            } else {
                res += ")";
            }
        }
        return res;
    }

    public boolean eqT(Expression expression) {
        if (!(expression instanceof Function))
            return false;
        Function function = (Function)expression;
        if (expressionList.size() != function.expressionList.size()) return false;
        boolean eq = true;
        for (int i = 0; i < expressionList.size(); i++) {
            eq &= expressionList.get(i).eqT(function.expressionList.get(i));
        }
        return value.equals(function.value) && eq;
    }

    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (!(expression instanceof Function))
            return false;
        Function function = (Function)expression;
        if (expressionList.size() != function.expressionList.size()) return false;
        boolean eq = true;
        for (int i = 0; i < expressionList.size(); i++) {
            eq &= expressionList.get(i).eqTT(function.expressionList.get(i), stringExpressionMap);
        }
        return value.equals(function.value) && eq;
    }
    public boolean freeE(Variable xx) {
        boolean good
                = false;
        for (Expression anE1 : expressionList) {
            good |= anE1.freeE(xx);
        }
        return good;
    }

    public Expression replV(Variable variable, Expression expression) {
        List<Expression> e2 = expressionList.stream().map(anE1 -> anE1.replV(variable, expression)).collect(Collectors.toList());
        return new Function(value, e2);
    }
    public Map<String, Expression> getV(Expression expression) {
        Map<String, Expression> res = new HashMap<>();
        if (!(expression instanceof Function))
            return res;
        Function function = (Function)expression;
        if (expressionList.size() != function.expressionList.size()) return res;
        for (int i = 0; i < expressionList.size(); i++) {
            res.putAll(expressionList.get(i).getV(function.expressionList.get(i)));
        }
        return res;
    }

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        boolean good = false;
        for (Expression anE1 : expressionList) {
            good |= anE1.haveBE(variable, stringSet, stringList);
        }
        return good;
    }

    @Override
    public Class<? extends Expression> gType() {
        return Function.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Function function = (Function) o;

        if (value != null ? !value.equals(function.value) : function.value != null) return false;
        return expressionList != null ? expressionList.equals(function.expressionList) : function.expressionList == null;

    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (expressionList != null ? expressionList.hashCode() : 0);
        return result;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }
}
