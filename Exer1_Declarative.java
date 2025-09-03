import java.util.Arrays;
import java.util.List;

public class Exer1_Declarative {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Anna", "Bob", "Charlie", "David");

        // Declarative: state what we want (filter names starting with 'A')
        names.stream()
             .filter(name -> name.startsWith("A"))
             .forEach(name -> System.out.println("Name starting with A: " + name));
    }
}
