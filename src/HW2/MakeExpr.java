package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */

import java.util.*;

public class MakeExpr {
    public static final String[] AXIOMS = {

            "A->(B->A)"
            , "(A->B)->((A->B->C)->(A->C))"
            , "A->(B->(A&B))"
            , "A&B->A"
            , "A&B->B"
            , "A->(A|B)"
            , "B->(A|B)"
            , "(A->C)->((B->C)->((A|B)->C))"
            , "(A->B)->((A->!B)->!A)"
            , "!!A ->A"
            , "@aP(a)->P(a)"
            , "Q(a)->?aQ(a)" // 12
            , "a=b->a'=b'"
            , "a=b->a=c->b=c"
            , "a'=b'->a=b"
            , "!a'=0"
            , "a+b'=(a+b)'"
            , "a+0=a"
            , "a*0=0"
            , "a*b'=a*b+a"};
    List<Expression> statements = new ArrayList<>();
    String curError = "";
    Expression allStr;

    static List<Expression> aToA(Expression a) {
        List<Expression> res = new ArrayList<>();
        res.add(new Implication(a, new Implication(a, a)));
        res.add(new Implication(new Implication(a, new Implication(a, a)),
                new Implication(new Implication(a, new Implication(new Implication(a, a), a)), new Implication(a, a))));
        res.add(new Implication(new Implication(a, new Implication(new Implication(a, a), a)), new Implication(a, a)));
        res.add(new Implication(a, new Implication(new Implication(a, a), a)));
        res.add(new Implication(a, a));
        return res;
    }

    static boolean equalT(Expression a, Expression b) {
        return !(a == null || b == null) && a.eqT(b);
    }

    private boolean almostEqualT(Expression a, Expression b) {
        if (a == null || b == null)
            return false;
        Map<String, Expression> list = new HashMap<>();
        return a.eqTT(b, list);
    }

    int compWithAx(Expression a) {
        for (int i = 0; i < AXIOMS.length; i++) {
            //System.err.println(AXIOMS[i]);
            // System.err.println(a.exToStr());
            if (almostEqualT(ExpressionParser.parse(AXIOMS[i]), a)) {
                return i;
            }
        }
        // F(0)&@expression(F(expression)->F(expression'))->F(expression)
        if (a instanceof Implication) {
            Expression left = ((Implication) a).firstExpression;
            Expression F = ((Implication) a).secondExpression;
            if (left instanceof And) {
                Expression right = ((And) left).secondexpression;
                if (right instanceof Quant) {
                    Variable x = (Variable) (((Quant) right).expression);

                    Expression axiom9 = new Implication(new And(F.replV(x, new Const(0)),
                            new Quant(Quant.FORALL, x, new Implication(F, F.replV(x, new Const(x))))), F);
                    if (a.eqT(axiom9)) return 99;
                }
            }
        }
        //@xF(expression)->F(expression = y)
        if (a instanceof Implication) {
            Implication b = (Implication) a;
            if (b.leftArg() instanceof Quant) {
                Quant q = (Quant) b.leftArg();
                if (q.getQuant() == Quant.FORALL) {
                    Expression arg2 = b.rightArg();
                    Variable var = q.getVar();
                    if (allStr != null && allStr.freeE(var)) {
                        if (curError.isEmpty())
                            curError = " используется правило с квантором по переменной " + var.exToStr() +
                                    " входящей свободно в допущение " + allStr.exToStr();
                    } else {
                        Expression arg1 = q.getExpr();
                        Expression arg12 = arg1.replV(var, new Variable("QQ99"));
                        if (almostEqualT(arg12, arg2)) {
                            Map<String, Expression> comp = arg12.getV(arg2);
                            boolean ok = true;
                            Set<String> keys = comp.keySet();
                            for (String key : keys) {
                                if (!key.equals("QQ99")) {
                                    ok &= key.equals(comp.get(key).exToStr());
                                }
                            }
                            if (ok) {
                                Expression replace = comp.get("QQ99");
                                if (replace == null) return 10;
                                Set<String> vars = replace.getV(replace).keySet();
                                if (!arg12.haveBE(new Variable("QQ99"), vars, new ArrayList<>())) {
                                    Expression arg13 = arg12.replV(new Variable("QQ99"), replace);
                                    if (equalT(arg13, arg2)) return 10;
                                } else {
                                    if (curError.isEmpty()) curError = "терм " + replace.exToStr() +
                                            " не свободен для подстановки в формулу " + arg1.exToStr() +
                                            " вместо переменной " + var.exToStr();
                                }
                            }
                        }
                    }
                }
            }
            //F(expression = y)->?xF(expression)
            if (b.rightArg() instanceof Quant) {
                Quant q = (Quant) b.rightArg();
                if (q.getQuant() == Quant.EXISTS) {
                    Expression arg2 = b.leftArg();
                    Variable var = q.getVar();
                    if (allStr != null && allStr.freeE(var)) {
                        if (curError.isEmpty())
                            curError = "используется правило с кванторомпо переменной " + var.exToStr() +
                                    " входящей в свободно в допущение " + allStr.exToStr();
                    } else {
                        Expression arg1 = q.getExpr();
                        Expression arg12 = arg1.replV(var, new Variable("QQ99"));
                        if (almostEqualT(arg12, arg2)) {
                            Map<String, Expression> comp = arg12.getV(arg2);
                            boolean ok = true;
                            Set<String> keys = comp.keySet();
                            for (String key : keys) {
                                if (!key.equals("QQ99")) {
                                    ok &= key.equals(comp.get(key).exToStr());
                                }
                            }
                            if (ok) {
                                Expression replace = comp.get("QQ99");
                                if (replace == null) return 11;
                                Set<String> vars = replace.getV(replace).keySet();
                                if (!arg12.haveBE(new Variable("QQ99"), vars, new ArrayList<>())) {
                                    Expression arg13 = arg12.replV(new Variable("QQ99"), replace);
                                    if (equalT(arg13, arg2)) return 11;
                                } else {
                                    if (curError.isEmpty()) curError = "терм " + replace.exToStr() +
                                            " не свободен для подстановки в формулу " + arg1.exToStr() +
                                            " вместо переменной" + var.exToStr();
                                }
                            }
                        }
                    }
                }
            }

        }
        return -1;
    }

    List<Integer> modusPonens(int pos, Expression a, List<Expression> expr) {
        if (a == null)
            return null;
        for (int i = 0; i < pos; i++) {
            Expression cur = expr.get(i);
            if (cur instanceof Implication) {
                if (equalT(((Implication) cur).rightArg(), a)) {
                    for (int j = 0; j < pos; j++) {
                        if (equalT(((Implication) cur).leftArg(), expr.get(j))) {
                            List<Integer> pair = new ArrayList<>();
                            pair.add(j);
                            pair.add(i);
                            return pair;
                        }
                    }
                }
            }
        }
        return null;
    }

    int forAllRule(Expression a, List<Expression> expr) {
        if (a == null)
            return -1;
        if (!(a instanceof Implication))
            return -1;
        Implication st = (Implication) a;
        if (!(st.rightArg() instanceof Quant))
            return -1;
        if (!(((Quant) st.rightArg()).getQuant() == Quant.FORALL))
            return -1;
        if (allStr != null && allStr.freeE(((Quant) st.rightArg()).getVar())) {
            curError = "используется правилос квантором по переменной "
                    + ((Quant) st.rightArg()).getVar().exToStr() +
                    " входящей свободно в допущение " + allStr.exToStr();
            return -1;
        }
        Expression rightArg = ((Quant) st.rightArg()).getExpr();
        if (st.leftArg().freeE(((Quant) st.rightArg()).getVar())) {
            if (curError.isEmpty())
                if (st.leftArg() instanceof And) {
                    curError = "переменная " + ((Quant) st.rightArg()).getVar().exToStr()
                            + "свободно входит в формулу " + st.leftArg().exToStr();
                } else {
                    curError = "переменная " + ((Quant) st.leftArg()).getVar().exToStr()
                            + "свободно входит в формулу " + st.rightArg().exToStr();
                }
            return -1;
        }
        Expression findExp = new Implication(st.leftArg(), rightArg);
        for (int i = 0; i < expr.size(); i++) {
            Expression cur = expr.get(i);
            if (equalT(findExp, cur)) {
                return i;
            }
        }
        return -1;
    }

    int existsRule(Expression a, List<Expression> expr) {
        if (a == null)
            return -1;
        if (!(a instanceof Implication))
            return -1;
        Implication st = (Implication) a;
        if (!(st.leftArg() instanceof Quant))
            return -1;
        if (!(((Quant) st.leftArg()).getQuant() == Quant.EXISTS))
            return -1;
        if (allStr != null && allStr.freeE(((Quant) st.leftArg()).getVar())) {
            if (curError.isEmpty()) curError = "используется правилос квантором по переменной "
                    + ((Quant) st.leftArg()).getVar().exToStr() +
                    " входящей свободно в допущение " + allStr.exToStr();
        }
        Expression leftArg = ((Quant) st.leftArg()).getExpr();
        if (st.rightArg().freeE(((Quant) st.leftArg()).getVar())) {
            if (curError.isEmpty())
                curError = "переменная " + ((Quant) st.leftArg()).getVar().exToStr()
                        + "свободно входит в формулу " + st.rightArg().exToStr();
            return -1;
        }
        Expression findExp = new Implication(leftArg, st.rightArg());
        for (int i = 0; i < expr.size(); i++) {
            Expression cur = expr.get(i);
            if (equalT(findExp, cur)) {
                return i;
            }
        }
        return -1;
    }

    public String printExp(Expression a) {
        if (a == null)
            return null;
        return a.exToStr();
    }
}
