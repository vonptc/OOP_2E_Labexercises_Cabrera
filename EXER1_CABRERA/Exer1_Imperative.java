import java.util.Arrays;
import java.util.List;

public class Exer1_Imperative {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // Imperative style: step by step with explicit instructions
        int sumOfSquares = 0;
        for (int n : numbers) {
            if (n % 2 == 0) {    // check even
                sumOfSquares += n * n; // square and add
            }
        }

        System.out.println("Sum of squares of even numbers (Imperative): " + sumOfSquares);
    }
}
