public class Circle extends Shape {
    // TODO: private double radius
    private double radius;

    // TODO: constructor(radius) -> super("Circle")
    public Circle(double radius) {
        super("Circle");
        this.radius = radius;
    }

    // TODO: override area()
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
