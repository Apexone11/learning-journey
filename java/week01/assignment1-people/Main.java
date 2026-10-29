import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        testPeople();
        testSwitches();
    }

    private static void testPeople() {
        // Create instances of Student and Employee
        Person person1 = new Student("Alice", 20, "Computer Science", 3.8);
        Person person2 = new Student("Bob", 22, "Mathematics", 3.5);
        Person person3 = new Employee("Charlie", 30, "Software Engineer", 80000);
        Person person4 = new Employee("Diana", 28, "Data Analyst", 70000);

        ArrayList<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);
        people.add(person4);

        // Loop through the list and print describe() for each person
        for (Person person : people) {
            System.out.println(person.describe());
        }

        // Test equals() method
        Person person5 = new Student("Alice", 20, "Computer Science", 3.8);
        Person person6 = new Employee("Charlie", 30, "Software Engineer", 80000);

        System.out.println("person1 equals person5: " + person1.equals(person5)); // should be true
        System.out.println("person3 equals person6: " + person3.equals(person6)); // should be true (same fields)
    }

    private static void testSwitches() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a day number (1-7): ");
            System.out.flush(); // make sure the prompt shows before waiting for input
            int day = scanner.nextInt();

            Switches.exampleSwitch(day);
            Switches.exampleSwitchExpression(day);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer between 1 and 7.");
        }
    }
}
