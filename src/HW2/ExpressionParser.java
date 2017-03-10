package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */

import java.util.ArrayList;
import java.util.List;


class ExpressionParser {

    private String lex;
    private int nP;
    Expression result;
    private List<String> variables = new ArrayList<String>();

    private Expression multiplier() {
        Expression expression;
        String val = "";
        if (lex.charAt(nP) >= 'a' && lex.charAt(nP) <= 'z') {
            val += lex.charAt(nP);
            nP++;
            while (lex.charAt(nP) >= '0' && lex.charAt(nP) <= '9') {
                val += lex.charAt(nP);
                nP++;
            }
            if (lex.charAt(nP) == '(') {
                List<Expression> terms = new ArrayList<>();
                nP++;
                terms.add(term());
                while (lex.charAt(nP) == ',') {
                    nP++;
                    terms.add(term());
                }
                nP++;
                expression = new Function(val, terms);
            } else {
                expression = new Variable(val);
                if (!variables.contains(val)) variables.add(val);
            }
        } else if (lex.charAt(nP) == '(') {
            nP++;
            expression = term();
            nP++;
        } else if (lex.charAt(nP) == '0') {
            expression = new Const(0);
            nP++;
        } else {
            expression = null;
        }
        while (lex.charAt(nP) == '\'') {
            expression = new Const(expression);
            //a'
            nP++;
        }
        return expression;
    }

    private Expression summand() {
        Expression a = multiplier();
        while (lex.charAt(nP) == '*') {
            nP++;
            a = new Multiplier(a, multiplier());
        }
        return a;
    }

    private Expression term() {
        Expression expression = summand();
        while (lex.charAt(nP) == '+') {
            nP++;
            expression = new Summand(expression, summand());
        }
        return expression;
    }

    private Expression predicate() {
        Expression expression;
        String val = "";
        List<Expression> terms = new ArrayList<>();
        if (lex.charAt(nP) >= 'A'
                && lex.charAt(nP) <= 'Z') {
            val += lex.charAt(nP);
            nP++;
            while (lex.charAt(nP) >= '0'
                    && lex.charAt(nP) <= '9') {
                val += lex.charAt(nP);
                nP++;
            }
            if (lex.charAt(nP) == '(') {
                nP++;
                terms.add(term());
                while (lex.charAt(nP) == ',') {
                    nP++;
                    terms.add(term());
                }
                nP++;
            }
        } else {
            val = "=";
            terms.add(term());
            if (lex.charAt(nP) != '=') {
                return null;
            }
            nP++;
            terms.add(term());
        }
        expression = new Predicate(val, terms);
        return expression;
    }

    private Expression variable() {
        Expression expression;
        String val = "";
        val += lex.charAt(nP);
        nP++;
        while (nP < lex.length() && Character.isDigit(lex.charAt(nP))) {
            val += lex.charAt(nP);
            nP++;
        }
        expression = new Variable(val);
        if (!variables.contains(val)) variables.add(val);
        return expression;
    }

    private Expression negation() {
        Expression expression;
        if (lex.charAt(nP) == '(') {
            int level = 1, cp = nP + 1, isExpression = 0;
            while (level != 0 && isExpression == 0) {
                switch (lex.charAt(cp)) {
                    case '(':
                        ++level;
                        break;
                    case ')':
                        --level;
                        break;
                    case '>':
                    case '&':
                    case '|':
                    case '!':
                    case '@':
                    case '?':
                    case '=':
                        isExpression = 1;
                        break;
                    default:
                        if (Character.isUpperCase(lex.charAt(cp)))
                            isExpression = 1;
                }
                ++cp;
            }
//                System.out.println("it's expression? " + isExpression);
            if (isExpression == 1) {
                nP++;
//                    System.out.println("\t" + pointer);
                Expression result = expr();
                assert lex.charAt(nP) == ')';
                nP++;
                return result;
            } else {
                return predicate();
            }
//            expression = predicate();
//            if (expression == null) {
//                nP = step;
//                nP++;
//                expression = expr();
//                nP++;
//            }
        } else if (lex.charAt(nP) == '!') {
            nP++;
            expression = new Not(negation());
        } else if (lex.charAt(nP) == '@'
                || lex.charAt(nP) == '?') {
            char quantor = lex.charAt(nP);
            nP++;
            expression = new Quant(quantor, variable(), negation());
        } else {
            expression = predicate();
        }
        return expression;
    }

    private Expression conjunction() {
        Expression expression = negation();
        while (nP < lex.length() && lex.charAt(nP) == '&') {
            nP++;
            expression = new And(expression, negation());
        }
        return expression;
    }

    private Expression disjunction() {
        Expression expression = conjunction();
        while (nP < lex.length() && lex.charAt(nP) == '|') {
            nP++;
            expression = new Or(expression, conjunction());
        }
        return expression;
    }

    private Expression expr() {
        Expression disjunction = disjunction();
        while (nP < lex.length() && lex.charAt(nP) == '-') {
            nP += 2;
            disjunction = new Implication(disjunction, expr());
        }
        return disjunction;
    }

    ExpressionParser(String a) {
        nP = 0;
       // lex =lex.replaceAll(" ", "");
        lex = a.replaceAll(" ", "").concat(".");
        result = expr();
    }

    public List<String> getVariables() {
        return variables;
    }

    public static Expression parse(String a) {
        return (new ExpressionParser(a)).result;
    }

}
