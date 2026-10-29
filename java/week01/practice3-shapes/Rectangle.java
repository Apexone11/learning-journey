public class Rectangle extends Shape {
    // TODO: private double width, height
    private double width, height;

    // TODO: public Rectangle(double width, double height) -> this("Rectangle", width, height)
    public Rectangle(double width, double height) {
        this("Rectangle", width, height);
    }

    // TODO: protected Rectangle(String name, double width, double height) -> super(name)
    protected Rectangle(String name, double width, double height) {
        super(name);
        this.width = width;
        this.height = height;
    }

    // TODO: override area()
    @Override
    public double area() {
        return width * height;
    }
}
