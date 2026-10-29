# Java Week 1, Practice 3: Shapes

Code only, no written answers. Starter files have `TODO`s.

**New skills:** multi level inheritance (`Square` → `Rectangle` → `Shape`), calling another constructor with
`this(...)`, a `protected` constructor, finding a max with polymorphism.

1. **Shape.java**

* private `name` (String), constructor, `getName()`
* `public double area()` returns `0.0`
* `public String describe()` returns `"<name> with area <area>"` with the area rounded to **2 decimals**.
  Use `String.format("%s with area %.2f", name, area())`. **Only Shape has `describe()`.** Don't override it anywhere.

2. **Circle.java:** extends Shape. private `radius`. Constructor takes only the radius and passes `"Circle"` up to
   `super`. Override `area()` (use `Math.PI`).
3. **Rectangle.java:** extends Shape. private `width` and `height`. **Two** constructors:

* `public Rectangle(double width, double height)`: calls the other constructor with `this("Rectangle", width, height)`
* `protected Rectangle(String name, double width, double height)`: calls `super(name)` and sets the fields
* Override `area()`.

4. **Square.java:** extends **Rectangle** (not Shape). Constructor takes only `side` and uses the protected Rectangle
   constructor to pass `"Square"`, `side`, `side`. **No other code** in Square. It gets `area()` for free.
5. **Main.java**

* `Shape[]` with: `Circle(1)`, `Rectangle(3, 4)`, `Square(5)`, `Circle(2.5)`, in that order
* One loop that:
  * prints each `describe()`
  * adds up the total area
  * keeps track of the shape with the **largest** area
  * counts how many shapes are `instanceof Rectangle`
* After the loop print the largest, the total (2 decimals), and the count

## Expected output (match it exactly)

```
Circle with area 3.14
Rectangle with area 12.00
Square with area 25.00
Circle with area 19.63
Largest: Square with area 25.00
Total area: 59.78
Rectangles: 2
```

Surprised the rectangle count is 2 and not 1? That's the point. A Square **is a** Rectangle.
