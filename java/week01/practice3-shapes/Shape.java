public class Shape {
    // TODO: private String name
    private String name;

    // TODO: constructor(name)
    public Shape(String name) {
        this.name = name;
    }

    // TODO: getName()
    public String getName() {
        return name;
    }

    // TODO: public double area() returns 0.0
    public double area() {
        return 0.0;
    }

    // TODO: public String describe()  -> String.format("%s with area %.2f", name, area())
    public String describe() {
        return String.format("%s with area %.2f", name, area());
    }
}
