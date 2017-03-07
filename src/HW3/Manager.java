package HW3;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Sofia228 on 17.12.16.
 */
public class Manager {

    static PrintWriter out;
    private static String A, B, C;
    private static String aa, bb, cc;
    private static Scanner in;

    private void run() throws IOException {
        int a, b, c;
        out = new PrintWriter(new File("58New.in"));
        StringTokenizer st = new StringTokenizer("0 8");
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        c = b - a;

        if (c >= 0) {
            String A = "0";
            String B = "0";
            String C = "0";
            for (int i = 0; i < b; i++) {
                if (i < c)
                    C += "'";
                if (i < a)
                    A += "'";
                B += "'";
            }
            String s = "";
            out.println("|-?p(" + A + "+p)=0" + B);
            in = new Scanner(new File("Base2"));
            while (in.hasNextLine()) {
                out.println(in.nextLine());
            }


            out.println("@a(a+0=a)->" + A + "+0=" + A);
            out.println(A + "+0=" + A);
            String prooff = "";
            in = new Scanner(new File("Proof1"));
            while (in.hasNextLine()) {
                s = in.nextLine();
                prooff += s.replaceAll("A", A) + "\n";
            }
            String sh = "";

            for (int i = 0; i < c; i++) {
                out.print(prooff.replaceAll("#", sh));
                sh += "'";
            }
            out.println("(" + A + "+" + C + ")=" + B + "->?p(" + A + "+p)=" + B + "\n" + "?p(" + A + "+p)=" + B);


        } else {

            in = new Scanner(new File("simpleProof2"));
            while (in.hasNextLine()) {
                out.println(in.nextLine());
            }
            c = a - b;
            String C = "";
            String B = "";
            for (int i = 0; i < c - 1; i++) {
                if (i < b)
                    B += "'";
                C += "'";
            }
            if (c - 1 < b) {
                for (int i = c - 1; i < b; i++) {
                    B += "'";
                }
            }
            in = new Scanner(new File("ProofSecondPart"));
            while (in.hasNextLine()) {
                out.println(in.nextLine().replaceAll("C", C).replaceAll("B", B).replaceAll(" ", ""));
            }
        }

        out.close();
    }
    public static void main(String[] args) throws IOException {
        new Manager().run();
    }
}
