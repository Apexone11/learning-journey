public class Student extends Person {
    private final String major;
    private final double gpa;

    public Student(String name, int age, String major, double gpa) {
        super(name, age);
        this.major = major;
        this.gpa = gpa;
    }

    @Override
    public String describe() {
        return super.describe() + " I am studying " + major + " and my GPA is " + gpa + ".";
    }

    @Override
    public String toString() {
        return "Student[name='" + getName() + "', age=" + getAge() + ", major='" + major + "', gpa=" + gpa + ']';
    }

}
