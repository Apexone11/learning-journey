
public class Ex1 {
    // See PRACTICE.md, Ex1
    public static void divide(int a, int b) {
        try {
            int result = a / b;
            System.out.println(a + " / " + b + " = " + result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide " + b + " by zero.");
        }
    }

    public static void main(String[] args) {
        divide(10, 2);
        divide(7, 0);
        divide(9, 3);
    }
}
