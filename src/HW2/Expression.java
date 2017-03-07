package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Expression {

    boolean eqT(Expression b);

    boolean eqTT(Expression b, Map<String, Expression> list);

    String exToStr();

    boolean freeE(Variable xx);

    Expression replV(Variable from, Expression to);

    Map<String, Expression> getV(Expression b);

    boolean haveBE(Variable xx, Set<String> vars, List<String> quants);

    Class<? extends Expression> gType();


}