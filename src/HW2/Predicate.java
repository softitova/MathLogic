package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class Predicate implements Expression {
    String value;
    List<Expression> expressionList;

    Predicate(String val, List<Expression> e1) {
        this.value = val;
        this.expressionList = e1;
    }

    public String exToStr() {
        if (value.equals("=")) {
            return "(" + expressionList.get(0).exToStr() + value + expressionList.get(1).exToStr() + ")";
        }
        String res = value;
        if (!expressionList.isEmpty()) {
            res += "(";
        }
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
        if (!(expression instanceof Predicate))
            return false;
        Predicate predicate = (Predicate) expression;
        if (expressionList.size() != predicate.expressionList.size()) return false;
        boolean eq = true;
        for (int i = 0; i < expressionList.size(); i++) {
            eq &= expressionList.get(i).eqT(predicate.expressionList.get(i));
        }
        return value.equals(predicate.value) && eq;
    }

    public boolean eqTT(Expression expression, Map<String, Expression> stringExpressionMap) {
        if (expressionList.isEmpty() && !value.equals("=")) {
            if (!stringExpressionMap.containsKey(value)) {
                stringExpressionMap.put(value, expression);
                return true;
            }
            return expression.eqT(stringExpressionMap.get(value));
        }
        if (!(expression instanceof Predicate)) {
            return false;
        }
        Predicate predicate = (Predicate) expression;
        if (expressionList.size() != predicate.expressionList.size()) {
            return false;
        }
        boolean eq = true;
        for (int i = 0; i < expressionList.size(); i++) {
            eq &= expressionList.get(i).eqTT(predicate.expressionList.get(i), stringExpressionMap);
        }
        return value.equals(predicate.value) && eq;
    }

    public boolean freeE(Variable variable) {
        boolean good = false;
        for (Expression anE1 : expressionList) good |= anE1.freeE(variable);
        return good;
    }

    public Expression replV(Variable variable, Expression expression) {
        List<Expression> expressionList1 = expressionList.stream().map(anE1 -> anE1.replV(variable, expression)).collect(Collectors.toList());
        return new Predicate(value, expressionList1);
    }

    public Map<String, Expression> getV(Expression b) {
        Map<String, Expression> res = new HashMap<>();
        if (!(b instanceof Predicate))
            return res;
        Predicate c = (Predicate) b;
        if (expressionList.size() != c.expressionList.size()) return res;
        for (int i = 0; i < expressionList.size(); i++) {
            res.putAll(expressionList.get(i).getV(c.expressionList.get(i)));
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Predicate predicate = (Predicate) o;

        if (value != null ? !value.equals(predicate.value) : predicate.value != null) return false;
        return expressionList != null ? expressionList.equals(predicate.expressionList) : predicate.expressionList == null;

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

    public boolean haveBE(Variable variable, Set<String> stringSet, List<String> stringList) {
        boolean good = false;
        for (Expression anE1 : expressionList) {
            good |= anE1.haveBE(variable, stringSet, stringList);
        }
        return good;
    }

    @Override
    public Class<? extends Expression> gType() {
        return Predicate.class;
    }
}