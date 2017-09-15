import java.util.Scanner;

/**
 * A simple class that calculates what one needs to get on the final based on hw
 * and exam scores previously
 *
 * @author Jonathan Buchanan
 *
 */

public class FinalTarget {
    public static void main(String[] args) {
        double hw;
        double exams;
        double target;
        double finalExam;
        int x = 1;
        x = x++;
        System.out.print(x);
        Scanner input = new Scanner(System.in);

        System.out.println("What is your homework average?");
        hw = input.nextDouble();

        System.out.println("What is your exam average?");
        exams = input.nextDouble();
        System.out.println("What grade do you want to get in the class?");
        target = input.nextDouble();

        // target = hw*.2 + exams*.6 + finalExam*.2;
        finalExam = (target - hw * .2 - exams * .6) / .2;
        if (finalExam > 100) {
            System.out
                    .println("You'd have to get over 100% on the final exam.  "
                            + "You already screwed it up");
        } else {
            System.out.format(
                    "you need to get a %d on the final exam"
                            + " to get a %d in cs1331.",
                    Math.round(finalExam), Math.round(target));
        }

        input.close();
    }

    /**
     * A test method that essentially does nothing
     *
     * @param i
     *            a random integer
     */

    public static void test(int i) {
        System.out.format("%d", i);
    }

}
