import java.util.Arrays;
import java.util.List;

public class Exer1_Functional {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // Functional style: using streams, lambdas, and method references
        int sumOfSquares = numbers.stream()
                                  .filter(n -> n % 2 == 0) // keep even numbers
                                  .map(n -> n * n)         // square them
                                  .reduce(0, Integer::sum); // sum them up

        System.out.println("Sum of squares of even numbers (Functional): " + sumOfSquares);
    }
}
