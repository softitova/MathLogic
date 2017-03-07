package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */

import java.util.ArrayList;
import java.util.List;


class Correctness extends MakeExpr{
    Correctness(List<Expression> s, List<Expression> hyp, Expression alpha) {
        statements = new ArrayList<Expression>(hyp);
        if (alpha != null) {
            statements.add(alpha);
        }
        statements.addAll(s);
        this.allStr = alpha;
        this.hyp = new ArrayList<Expression>(hyp);
        if (alpha != null) {
            this.hyp.add(alpha);
        }
    }
    private List<String> errors = new ArrayList<>();
    private List<String> proof = new ArrayList<>();
    private List<Expression> hyp;

    private int compWithHyp(Expression exp) {
        for (int i = 0; i < hyp.size(); i++) {
            if (equalT(hyp.get(i), exp)) return i;
        }
        return -1;
    }

    private boolean goCheck() {

        for (int i = 0; i < statements.size(); i++) {
            Expression exp = statements.get(i);
            int ax = compWithAx(exp);
            int hx = compWithHyp(exp);
            if (ax != -1 || hx != -1) {
                if (ax != -1) {
                    proof.add("(" + (i + 1) + ") " + exp.exToStr() + " (сх.акс. " + (ax + 1) + ")");
                } else {
                    proof.add("(" + (i + 1) + ") " + exp.exToStr() + " (hyp)");
                }
            } else {
                List<Integer> mp = modusPonens(i, exp, statements);
                if (mp != null) {
                    proof.add("(" + (i + 1) + ") " + exp.exToStr() + " (M.P. "
                            + (mp.get(0) + 1) + ", " + (mp.get(1) + 1) + ")");
                } else {
                    int fa = forAllRule(exp, statements);
                    if (fa != -1) {
                        proof.add("(" + (i + 1) + ") " + exp.exToStr() + " (FORALL " + (fa + 1) + ")");
                    } else {
                        int ex = existsRule(exp, statements);
                        if (ex != -1) {
                            proof.add("(" + (i + 1) + ") " + exp.exToStr() + " (EXISTS " + (ex + 1) + ")");
                        } else {
                            statements.set(i, null);
                            proof.add("(" + (i + 1) + ") " + exp.exToStr() + " Не доказано");
                            errors.add("Вывод не корректен начиная с формулы номер " + (i + 1));
                            if (!curError.isEmpty()) {
                                errors.add(": " + curError);
                                curError = "";
                            }
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    List<String> getStatements() {
        return proof;
    }

    boolean isCorrect() {
        return goCheck();
    }

    List<String> getErrors() {
        return errors;
    }
}