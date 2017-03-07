package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class Deduction extends MakeExpr {

    private List<Expression> hypothesis = new ArrayList<>();
    private List<Expression> expressions = new ArrayList<>();
    private Expression alpha;
    private Expression state;

    private void hyParser(String s) {
        int pos = 0;
        s = s.replaceAll("\\s", "");
        if (!s.contains("|-")) {
            alpha = null;
            state = null;
            return;
        }
        int balance = 0;
        while (!s.substring(pos, pos + 2).equals("|-")) {
            String exp = "";
            while ((s.charAt(pos) != ',' || balance > 0) && !s.substring(pos, pos + 2).equals("|-")) {
                exp += s.charAt(pos);
                if (s.charAt(pos) == '(') balance++;
                if (s.charAt(pos) == ')') balance--;
                pos++;
            }
            if (exp.isEmpty()) {
                alpha = null;
                break;
            }
            if (s.charAt(pos) == ',') {
                hypothesis.add(ExpressionParser.parse(exp));
                pos++;
            } else {
                alpha = ExpressionParser.parse(exp);
            }
            expressions.add(ExpressionParser.parse(exp));
        }
        state = ExpressionParser.parse(s.substring(pos + 2));
    }

    public int compWithHyp(Expression a) {
        for (int i = 0; i < hypothesis.size(); i++) {
            if (equalT(hypothesis.get(i), a)) {
                return i;
            }
        }
        return -1;
    }

    public void makeDeduction() {
        for (int i = 0; i < expressions.size(); i++) {
            Expression exp = expressions.get(i);
            int ax = compWithAx(exp);
            int hyp = compWithHyp(exp);
            if (ax != -1) {
                statements.add(exp);
                statements.add(new Implication(exp, new Implication(alpha, exp)));
                statements.add(new Implication(alpha, exp));
            } else if (hyp != -1) {
                statements.add(new Implication(exp, new Implication(alpha, exp)));
                statements.add(new Implication(alpha, exp));
            } else if (equalT(alpha, exp)) {
                statements.addAll(aToA(alpha));
            } else {
                List<Integer> mp = modusPonens(i, exp, expressions);
                if (mp != null) {
                    Expression gj;
                    if (expressions.get(mp.get(0)).eqT(new Implication(expressions.get(mp.get(1)), exp))) {
                        gj = expressions.get(mp.get(1));
                    } else {
                        gj = expressions.get(mp.get(0));
                    }
                    Expression tmp = new Implication(new Implication(alpha, new Implication(gj, exp)), new Implication(alpha, exp));
                    statements.add((new Implication(new Implication(alpha, gj), tmp)));
                    statements.add(tmp);
                    statements.add(new Implication(alpha, exp));
                } else {
                    int forAll = forAllRule(exp, expressions);
                    if (forAll != -1) {
                        Implication exp2 = (Implication) exp;
                        Quant q = (Quant) exp2.rightArg();
                        Expression a = alpha;
                        Expression c = q.getExpr();
                        Expression b = exp2.leftArg();
                        Variable d = q.getVar();
                        try {
                            BufferedReader in = new BufferedReader(new FileReader("quantors/forall.txt"));
                            String myStr = "";
                            int ii = 0;
                            while ((myStr = in.readLine()) != null) {
                                myStr = myStr.replaceAll("A", "QQ99");
                                myStr = myStr.replaceAll("B", "WW88");
                                myStr = myStr.replaceAll("C", "XX77");
                                myStr = myStr.replaceAll("d", "ZZ66");
                                myStr = myStr.replaceAll("QQ99", "(" + a.exToStr() + ")");
                                myStr = myStr.replaceAll("WW88", "(" + b.exToStr() + ")");
                                myStr = myStr.replaceAll("XX77", "(" + c.exToStr() + ")");
                                myStr = myStr.replaceAll("ZZ66", d.exToStr());
                                statements.add(ExpressionParser.parse(myStr));

                            }
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        int exists = existsRule(exp, expressions);
                        if (exists != -1) {
                            Implication exp2 = (Implication) exp;
                            Quant q = (Quant) exp2.leftArg();
                            Expression a = alpha;
                            Expression b = q.getExpr();
                            Expression c = exp2.rightArg();
                            Variable d = q.getVar();
                            try {
                                BufferedReader in = new BufferedReader(new FileReader("quantors/exists.txt"));
                                String myStr = "";
                                int ii = 0;
                                while (in.ready()) {
                                    myStr = in.readLine();
                                    myStr = myStr.replaceAll("A", "QQ99");
                                    myStr = myStr.replaceAll("B", "WW88");
                                    myStr = myStr.replaceAll("C", "XX77");
                                    myStr = myStr.replaceAll("d", "ZZ66");
                                    myStr = myStr.replaceAll("QQ99", "(" + a.exToStr() + ")");
                                    myStr = myStr.replaceAll("WW88", "(" + b.exToStr() + ")");
                                    myStr = myStr.replaceAll("XX77", "(" + c.exToStr() + ")");
                                    myStr = myStr.replaceAll("ZZ66", d.exToStr());
                                    statements.add(ExpressionParser.parse(myStr));
                                    ii++;
                                }
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("null" + alpha.exToStr() + "==" + exp.exToStr());
                            System.out.println("BEGIN========");
                            System.out.println(alpha.exToStr() + "|-" + state.exToStr());
                            for (int h = 0; h < expressions.size(); h++) {
                                System.out.println(expressions.get(h).exToStr());
                            }
                            System.out.println("END========");
                        }
                    }
                }
            }
        }
    }


    Deduction(String s, List<Expression> pr) {
        hyParser(s);
        expressions.addAll(pr);
    }

    Expression getAlpha() {
        return alpha;
    }

    List<Expression> getHyp() {
        return hypothesis;
    }

    List<Expression> getDeduction() {
        makeDeduction();
        return statements;
    }

}