public class Employee extends Person {

    private final String title;
    private final double salary;

    public Employee(String name, int age, String title, double salary) {
        super(name, age);
        this.title = title;
        this.salary = salary;
    }
    
    @Override
    public String describe() {
        return super.describe() + " I work as a " + title + " and my salary is $" + salary + ".";
    }

    @Override
    public String toString() {
        return "Employee[name='" + getName() + "', age=" + getAge() + ", title='" + title + "', salary=" + salary + ']';
    }
}
