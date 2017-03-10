package HW2; /**
 * Created by Sofia228 on 24.12.16.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Homework2 {

    static void debug() {

//        String s = "((!0'''+p=0)->(!0'''+p'=0))";
//        String d = "((!0'''+p=0)->(!0'''+p'=0))->(0=0->0=0->0=0)->((!0'''+p=0)->(!0'''+p'=0))";
//        System.out.println(ExpressionParser.parse(s).exToStr());;
//        System.exit(0);
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(new File("tests/mytest")); // mytest - не парсит вывод с 3 дз :(
       // Scanner in = new Scanner(new File("tests/HW4/correct12.in"));
        System.setOut(new PrintStream("output.out"));
        String state = in.nextLine();

        debug();

        //System.err.println("state " + ExpressionParser.parse(state).exToStr());
        List<Expression> exprs = new ArrayList<>();
        if (!state.contains("|-")) {
            exprs.add(ExpressionParser.parse(state));
        }


        int temp = 0;
        while (in.hasNext()) {
            String s = in.nextLine();
            exprs.add(ExpressionParser.parse(s));
            ++temp;
        }
        Deduction deduction = new Deduction(state, exprs);
        Expression alpha = deduction.getAlpha();
        List<Expression> res = new ArrayList<>();
        List<Expression> hyp = deduction.getHyp();
        Correctness correct = new Correctness(exprs, hyp, alpha);
        if (correct.isCorrect()) {
            if (alpha == null) {
                res = exprs;
            } else {
                res = deduction.getDeduction();
            }
        } else {
            List<String> errs = correct.getErrors();
            errs.forEach(System.out::print);
        }
        List<String> proof = correct.getStatements();
        proof.forEach(System.err::println);
        for (Expression re : res) {
            System.out.println(re.exToStr());
        }
        in.close();
    }

}
