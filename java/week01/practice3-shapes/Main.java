public class Main {
    public static void main(String[] args) {
        // TODO: Shape[] shapes = { ... };
        Shape[] shapes = {
            new Circle(1),
            new Rectangle(3, 4),
            new Square(5),
            new Circle(2.5)
        };

        // TODO: one loop: print describe(), add to total, track largest, count instanceof Rectangle
        double totalArea = 0;
        Shape largestShape = shapes[0];
        int rectangleCount = 0;

        for (Shape shape : shapes) {
            System.out.println(shape.describe());
            totalArea += shape.area();
            if (shape.area() > largestShape.area()) {
                largestShape = shape;
            }
            if (shape instanceof Rectangle) {
                rectangleCount++;
            }
        }

        // TODO: print Largest, Total area (2 decimals), Rectangles count
        System.out.println("Largest: " + largestShape.describe());
        System.out.printf("Total area: %.2f%n", totalArea);
        System.out.println("Rectangles: " + rectangleCount);
    }
}
