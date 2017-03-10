package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Variable implements Expression {

    String currentSymbol;

    public Variable(String a) {
        this.currentSymbol = a;
    }

    public boolean eqT(Expression b) {
        if (!(b instanceof Variable))
            return false;
        Variable d = (Variable) b;
        return currentSymbol.equals(d.currentSymbol);
    }

    public String exToStr() {
        return currentSymbol;
    }

    public boolean eqTT(Expression b, Map<String, Expression> list) {
        if (!list.containsKey(currentSymbol)) {
            list.put(currentSymbol, b);
            return true;
        }
        return b.eqT(list.get(currentSymbol));
    }

    public boolean freeE(Variable xx) {
        return currentSymbol.equals(xx.currentSymbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        return currentSymbol != null ? currentSymbol.equals(variable.currentSymbol) : variable.currentSymbol == null;

    }

    @Override
    public int hashCode() {
        return currentSymbol != null ? currentSymbol.hashCode() : 0;
    }

    public String getCurrentSymbol() {

        return currentSymbol;
    }

    public void setCurrentSymbol(String currentSymbol) {
        this.currentSymbol = currentSymbol;
    }

    public Expression replV(Variable from, Expression to) {
        if (currentSymbol.equals(from.currentSymbol)) {
            return to;
        }
        return new Variable(currentSymbol);
    }

    public Map<String, Expression> getV(Expression b) {
        Map<String, Expression> res = new HashMap<>();
        res.put(currentSymbol, b);
        return res;
    }

    public boolean haveBE(Variable xx, Set<String> vars, List<String> quants) {
        if (!currentSymbol.equals(xx.currentSymbol)) return false;
        for (String quant : quants) {
            if (vars.contains(quant)) return true;
        }
        return false;
    }

    @Override
    public Class<? extends Expression> gType() {
        return Variable.class;
    }
}