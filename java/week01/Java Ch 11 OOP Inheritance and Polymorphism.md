# Java Chapter 11: OOP, Inheritance and Polymorphism

Your refresher for IS 247 Chapter 11. Read it before you code, and come back to it whenever
you get stuck. Most examples use **vehicles and animals**, so they teach the ideas without
giving away your assignment answers.

**Contents**

1. Object oriented thinking
2. Classes and objects
3. The four pillars of OOP (APIE)
4. Encapsulation
5. Using one class from another class
6. Packages and imports
7. Composition (has a)
8. Inheritance (is a) and `extends`
9. `super`: constructors and methods
10. Overriding
11. Overloading vs overriding
12. Polymorphism and dynamic binding
13. `instanceof` and casting
14. The `Object` class: `toString`, `equals`, `hashCode`
15. Access modifiers
16. `final` and `static`
17. Abstraction (preview of Chapter 13)
18. `StringBuilder` and `StringBuffer`
19. Arrays and `ArrayList` reminder
20. Common mistakes checklist
21. Self check
22. Definitions gallery
23. Sources

---

## 1. Object oriented thinking

**Object oriented programming (OOP)** means organizing a program around **objects** (things
that hold data and can do actions) instead of one long list of instructions.

### Don't put everything in one class

A beginner program often has one giant method that validates input, does math, and prints
results all at once. That works, but it is hard to read, test, and change.

**Refactoring** means changing the structure of code **without changing what it does**,
so it is easier to read and maintain.

**Single Responsibility Principle (SRP):** each class and each method should have **one job**.

```java
// Bad: one method does everything
void processOrder() {
    // check the input
    // calculate the total
    // apply tax
    // print a receipt
    // send an email
}

// Better: one job per method
boolean validateOrder() { ... }
double calculateTotal() { ... }
double applyTax(double amount) { ... }
void printReceipt() { ... }
void sendConfirmation() { ... }
```

If you can't describe what a method does without the word "and", it is probably doing too much.

---

## 2. Classes and objects

* A **class** is a **blueprint**. It describes what data (fields) and actions (methods) things
  of that type have.
* An **object** is **one real thing built from the blueprint**. It is also called an
  **instance** of the class.
* Every object has **state** (the current values of its fields) and **behavior** (its methods).

```java
public class Car {
    // fields (state)
    private String make;
    private String model;
    private int year;
    private double speed;

    // constructor: runs when you write `new Car(...)`
    public Car(String make, String model, int year) {
        this.make = make;      // this.make = the field, make = the parameter
        this.model = model;
        this.year = year;
        this.speed = 0;
    }

    // methods (behavior)
    public void accelerate(double amount) { speed += amount; }
    public void brake() { speed = 0; }

    // getters
    public String getMake() { return make; }
    public double getSpeed() { return speed; }
}
```

```java
Car a = new Car("Toyota", "Camry", 2020);   // object 1
Car b = new Car("Honda", "Civic", 2022);    // object 2, completely separate
a.accelerate(30);
System.out.println(a.getSpeed());   // 30.0
System.out.println(b.getSpeed());   // 0.0  (b was not touched)
```

### Constructor facts

* Same name as the class, **no return type** (not even `void`).
* If you write **no** constructor, Java gives you a free empty one: `public Car() {}`.
* The moment you write **any** constructor, that free one disappears.
* You can have several constructors with different parameters (that is overloading, section 11).
* One constructor can call another with `this(...)`:

```java
public Car(String make, String model) {
    this(make, model, 2024);   // calls the 3 parameter constructor
}
```

### The `this` keyword

`this` means "the current object." Use it to tell a field apart from a parameter with the
same name (`this.make = make;`).

---

## 3. The four pillars of OOP (APIE)

| Pillar | One line meaning | Java tool |
|---|---|---|
| **A**bstraction | Show what something does, hide how | abstract classes, interfaces |
| **P**olymorphism | One variable, many possible forms | overriding, overloading |
| **I**nheritance | A new class reuses an existing one | `extends` |
| **E**ncapsulation | Hide data, control access to it | `private` + getters and setters |

Remember **APIE** ("a pie"). Each one has its own section below.

---

## 4. Encapsulation (data hiding)

Wrap the data and the methods that use it into one class, and **hide the data** from
everyone else.

```java
public class BankAccount {
    private double balance;           // nobody outside can touch this directly

    public double getBalance() {      // getter: read access
        return balance;
    }

    public void deposit(double amount) {   // controlled write access
        if (amount <= 0) {
            System.out.println("Deposit must be positive.");
            return;
        }
        balance += amount;
    }
}
```

**Why bother?** If `balance` were `public`, any code could write `account.balance = -5000;`.
With encapsulation, the class protects its own rules.

**Getter and setter naming:** `getName()`, `setName(String name)`, and for booleans
`isActive()`.

---

## 5. Using one class from another class

Java programs are made of many classes that work together. There are three common ways
one class uses another.

### a) Create an object of the other class and call its methods

```java
// Engine.java
public class Engine {
    public void start() {
        System.out.println("Engine running.");
    }
}

// Main.java  (same folder)
public class Main {
    public static void main(String[] args) {
        Engine e = new Engine();   // make an object from the other class
        e.start();                 // call its method with a dot
    }
}
```

### b) Call a `static` method on the class name (no object needed)

```java
// MathHelper.java
public class MathHelper {
    public static double average(double a, double b) {
        return (a + b) / 2;
    }
}

// Main.java
double avg = MathHelper.average(80, 90);   // ClassName.method()
```

`Math.max(...)` and `Integer.parseInt(...)` work the same way.

### c) Pass an object into another object's method

```java
public class Mechanic {
    public void inspect(Car c) {
        System.out.println("Inspecting a " + c.getMake());
    }
}

Mechanic m = new Mechanic();
Car car = new Car("Ford", "Focus", 2018);
m.inspect(car);
```

### The rules that make it work

* **One `public` class per file**, and the **file name must match** the class name exactly
  (`Engine` lives in `Engine.java`, case matters).
* Classes in the **same folder with no package line** can see each other automatically. That
  is how your assignments work.
* You can only call methods the other class makes visible (`public`, or package access when
  in the same package). You **cannot** call its `private` methods.
* Compile them together: `javac *.java`, then run the one with `main`: `java Main`.

---

## 6. Packages and imports

### Packages

A **package** is a named group of related classes, like a folder for code. It prevents name
clashes (two classes called `Logger` in different packages are fine) and keeps big projects
organized.

The package name **must match the folder path**:

```
src/
└── com/
    └── fornah/
        └── garage/
            ├── Car.java        <- first line: package com.fornah.garage;
            └── Engine.java     <- first line: package com.fornah.garage;
```

```java
package com.fornah.garage;   // must be the very first line of the file

public class Car { ... }
```

* Naming convention: all lowercase, often your domain reversed (`com.fornah...`).
* Classes in the **same package** can use each other without importing.
* If a file has **no** `package` line, it is in the **default package** (fine for small
  assignments like yours, not for real projects).

### Imports

To use a class from a **different** package, import it:

```java
import java.util.ArrayList;     // one class
import java.util.Objects;
import java.util.*;             // every class in java.util (works, but explicit is clearer)
import com.fornah.garage.Car;   // your own class from another package
```

* `java.lang` is imported automatically. That is why `String`, `Math`, `System`, and
  `Integer` need no import.
* Imports go **after** the `package` line and **before** the class.
* Without an import you can still use the full name: `java.util.ArrayList<String> list = ...`

### Order inside a .java file

```java
package com.fornah.garage;          // 1. package (optional)

import java.util.ArrayList;         // 2. imports

public class Garage { ... }         // 3. the class
```

### Compiling and running with packages

```powershell
# from the src folder
javac com\fornah\garage\*.java
java com.fornah.garage.Main          # run using the full name with dots
```

---

## 7. Composition (has a)

**Composition** means an object is **built from other objects**. You put an object of another
class **inside** as a field.

* A Car **has an** Engine.
* A Student **has an** Address.

```java
public class Address {
    private String street;
    private String state;

    public Address(String street, String state) {
        this.street = street;
        this.state = state;
    }

    public String getState() { return state; }
}

public class Customer {
    private String name;
    private Address address;    // composition: Customer HAS AN Address

    public Customer(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getState() {
        return address.getState();   // ask the inner object
    }
}
```

**Aggregation** is a looser form: the inner object can exist on its own (a Team has Players,
but players exist without the team).

### "Is a" or "has a"? Test before you use `extends`

| Sentence | Relationship | Use |
|---|---|---|
| A Truck **is a** Vehicle | is a | `extends` |
| A Truck **has an** Engine | has a | a field |
| A Manager **is an** Employee | is a | `extends` |
| A Library **has** Books | has a | a field (often a list) |

**Rule of thumb:** prefer composition. Only use inheritance when "is a" is truly true.

---

## 8. Inheritance (is a) and `extends`

**Inheritance** lets a new class reuse the fields and methods of an existing class.

```java
public class Vehicle {
    private String make;
    private int year;

    public Vehicle(String make, int year) {
        this.make = make;
        this.year = year;
    }

    public String getMake() { return make; }
    public int getYear() { return year; }
}

public class Truck extends Vehicle {
    private int towingCapacity;   // only trucks have this

    public Truck(String make, int year, int towingCapacity) {
        super(make, year);
        this.towingCapacity = towingCapacity;
    }

    public int getTowingCapacity() { return towingCapacity; }
}
```

* `Vehicle` is the **superclass** (also called parent or **supertype**).
* `Truck` is the **subclass** (also called child or **subtype**).
* `Truck` automatically has `getMake()` and `getYear()`, without writing them again.
* A class can extend **only one** class. `class Truck extends Vehicle, Machine` is not allowed.
* Every class without `extends` secretly extends **`Object`** (section 14).
* Inheritance can go many levels: `Object` → `Vehicle` → `Truck` → `FireTruck`.

### What the child gets

| Parent member | Inherited and usable in the child? |
|---|---|
| `public` fields and methods | Yes |
| `protected` fields and methods | Yes |
| `private` fields and methods | **No direct access.** They exist inside the object, but the child must use the parent's public getters. |
| Constructors | **Not inherited.** The child calls them with `super(...)`. |

---

## 9. `super`: constructors and methods

`super` means "my parent." It has two uses.

### a) `super(...)` calls the parent constructor

```java
public Truck(String make, int year, int towingCapacity) {
    super(make, year);                 // build the Vehicle part first
    this.towingCapacity = towingCapacity;
}
```

**Rules:**

* It **must be the first line** of the constructor.
* If you leave it out, Java secretly inserts `super();` (no arguments). If the parent has **no**
  no argument constructor, you get a compile error.
* The child can't do `this.make = make;` because `make` is `private` in the parent. That is
  exactly why you pass it up with `super(...)`.

### Constructor chaining

Constructors run **top down**, parent first:

```java
class A {
    A() { System.out.println("A constructor"); }
}
class B extends A {
    B() { System.out.println("B constructor"); }
}

new B();
// A constructor
// B constructor
```

### b) `super.method()` calls the parent's version of a method

```java
// in Vehicle
public double insuranceCost() { return 1000; }

// in Truck
@Override
public double insuranceCost() {
    return super.insuranceCost() + 400;   // parent's amount, plus extra
}
```

Use this to **add to** the parent's behavior instead of copying its numbers. If the parent's
price changes later, the child updates automatically.

---

## 10. Overriding

A child **overrides** a parent method by writing a method with the **exact same name, same
parameters, and same (or compatible) return type**.

```java
// Vehicle
public String describe() {
    return getYear() + " " + getMake();
}

// Truck
@Override
public String describe() {
    return getYear() + " " + getMake() + " truck that tows " + towingCapacity + " lbs";
}
```

**Rules:**

* Put **`@Override`** above it, always. If you misspell the name or change the parameters by
  accident, the compiler tells you instead of silently making a brand new method.
* You **can't** make it less visible (`public` in the parent must stay `public`).
* You **can't** override a `final` method or a `static` method.
* `private` methods are not inherited, so they can't be overridden.

---

## 11. Overloading vs overriding

People mix these up constantly.

| | **Overloading** | **Overriding** |
|---|---|---|
| Where | Same class | Child replaces parent's method |
| Method name | Same | Same |
| Parameters | **Different** (number or types) | **Exactly the same** |
| Return type | Can differ | Same or a subtype |
| Decided | At **compile time** (static binding) | At **run time** (dynamic binding) |
| Kind of polymorphism | Compile time | Run time |

```java
// Overloading: same name, different parameters, both exist side by side
public void honk() {
    System.out.println("Beep");
}
public void honk(int times) {
    for (int i = 0; i < times; i++) System.out.println("Beep");
}
```

Changing **only** the return type is **not** overloading. It is a compile error.

**Memory trick:** over**load** = **load** in more versions. over**ride** = **ride** over the old one.

---

## 12. Polymorphism and dynamic binding

**Polymorphism** means "many forms." A variable of the **parent type** can point to an object of
**any child type**.

```java
public class Animal {
    public String sound() { return "..."; }
}
public class Dog extends Animal {
    @Override public String sound() { return "Woof"; }
}
public class Cow extends Animal {
    @Override public String sound() { return "Moo"; }
}
```

```java
Animal a1 = new Dog();
Animal a2 = new Cow();

Animal[] farm = { a1, a2, new Animal() };
for (Animal a : farm) {
    System.out.println(a.sound());
}
// Woof
// Moo
// ...
```

The variable type is `Animal` every time, but Java checks the **actual object** while the
program runs and calls **that object's** version. This is called **dynamic binding** (also
late binding).

### Declared type vs actual type

```java
Animal a = new Dog();
// ^ declared type   ^ actual type
```

* The **declared type** decides which methods you are **allowed to call**. The compiler checks this.
* The **actual type** decides which **version runs**. Java picks this at run time.

So if `Dog` has a `fetch()` method that `Animal` doesn't have:

```java
a.sound();   // OK, Animal has sound(), Dog's version runs
a.fetch();   // COMPILE ERROR, Animal has no fetch()
```

### Static vs dynamic binding

* **Static binding (early):** the compiler decides. Used for overloaded, `static`, `private`,
  and `final` methods.
* **Dynamic binding (late):** decided at run time. Used for overridden methods.

### Why this matters

You can write **one** loop that works for every animal, including animal types you add next
year, without changing the loop. That is the main payoff of polymorphism.

---

## 13. `instanceof` and casting

To call a child only method on a parent type variable, check the type first, then cast.

**Upcasting** (child to parent) is automatic and always safe:

```java
Animal a = new Dog();     // no cast needed
```

**Downcasting** (parent to child) needs a check:

**Old style:**

```java
if (a instanceof Dog) {
    Dog d = (Dog) a;
    d.fetch();
}
```

**Modern style (Java 16+), use this:**

```java
if (a instanceof Dog d) {
    d.fetch();
}
```

Casting without checking is dangerous. If `a` is really a `Cow`, `(Dog) a` crashes with a
**`ClassCastException`**.

---

## 14. The `Object` class: `toString`, `equals`, `hashCode`

Every class in Java extends `Object`, so every object has these methods. You usually override them.

### `toString()`

Called automatically when you print an object or join it to a String.

```java
Car c = new Car("Kia", "Soul", 2021);
System.out.println(c);   // without toString: Car@1b6d3586   (useless)
```

```java
@Override
public String toString() {
    return "Car{make='" + make + "', model='" + model + "', year=" + year + "}";
}
// Car{make='Kia', model='Soul', year=2021}
```

### `equals()`

* `==` on objects asks: **are these the exact same object in memory?**
* `equals()` should ask: **do these hold the same content?**
* By default, `equals()` behaves just like `==`. Override it to compare content.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;                    // same object
    if (!(o instanceof Car other)) return false;   // null or a different type
    return year == other.year
        && make.equals(other.make)
        && model.equals(other.model);
}
```

**Watch out:**

* The parameter **must be `Object`**. Writing `equals(Car o)` is **overloading**, not overriding,
  and Java's built in tools like `ArrayList.contains()` will ignore it. `@Override` catches this.
* Compare **Strings** with `.equals()`, never `==`.
* Compare **primitives** (`int`, `double`, `boolean`) with `==`.

### `hashCode()`

**Rule:** if you override `equals`, you **must** override `hashCode`. Equal objects must return
equal hash codes, or `HashMap` and `HashSet` stop working correctly.

```java
import java.util.Objects;

@Override
public int hashCode() {
    return Objects.hash(make, model, year);   // same fields you used in equals
}
```

---

## 15. Access modifiers

| Modifier | Same class | Same package | Subclass (other package) | Everyone |
|---|:-:|:-:|:-:|:-:|
| `private` | ✓ | | | |
| *(none)* "package private" | ✓ | ✓ | | |
| `protected` | ✓ | ✓ | ✓ | |
| `public` | ✓ | ✓ | ✓ | ✓ |

* **Fields:** make them `private` by default.
* **`protected`** lets subclasses use a field directly (`name` instead of `getName()`). Handy,
  but any subclass can also change it, so it weakens encapsulation a little.
* **Methods** other classes need: `public`. Helper methods only this class uses: `private`.

---

## 16. `final` and `static`

### `final`

| On a... | Meaning |
|---|---|
| variable | can't be reassigned (a constant) |
| method | subclasses **can't override** it |
| class | nobody can **extend** it (`String` is a final class) |

```java
public static final double TAX_RATE = 0.06;   // a constant, named in ALL_CAPS
```

Make a method `final` when its behavior must be identical for every subclass.

### `static`

A `static` field or method belongs to the **class itself**, not to each object. All objects
share **one copy**.

```java
public class Vehicle {
    private static int totalBuilt = 0;   // one shared counter

    public Vehicle(String make, int year) {
        this.make = make;
        this.year = year;
        totalBuilt++;                    // every new vehicle (and every subclass) adds 1
    }

    public static int getTotalBuilt() {
        return totalBuilt;
    }
}

new Truck("Ram", 2020, 9000);
new Truck("Ford", 2021, 8000);
System.out.println(Vehicle.getTotalBuilt());   // 2
```

Creating a `Truck` counts because its constructor calls `super(...)`, which runs `Vehicle`'s
constructor.

**Static rules:**

* Call it on the class name: `Vehicle.getTotalBuilt()`.
* A `static` method **can't** use `this` or non static fields (there is no specific object).
* `main` is `static` because Java runs it before any object exists.

---

## 17. Abstraction (preview of Chapter 13)

**Abstraction** means hiding **how** something works and showing only **what** it does. You
press a car's brake pedal without knowing the hydraulics.

An **abstract class** is a class you **can't create objects of directly**. It exists to be extended.

```java
public abstract class Shape {
    public abstract double area();          // no body: every child MUST write one

    public void printArea() {               // normal method, shared by all children
        System.out.println("Area: " + area());
    }
}

public class Circle extends Shape {
    private double r;
    public Circle(double r) { this.r = r; }

    @Override
    public double area() { return Math.PI * r * r; }
}

Shape s = new Circle(2);   // OK
Shape x = new Shape();     // COMPILE ERROR: Shape is abstract
```

You will go deep on this (and interfaces) in Chapter 13. For now, just know the idea.

---

## 18. `StringBuilder` and `StringBuffer`

A `String` is **immutable**: it can never change. Every `+` creates a brand new String. In a
big loop, that wastes memory and time.

`StringBuilder` is a **mutable** string you can keep changing:

```java
StringBuilder sb = new StringBuilder("Report");
sb.append(": ");
sb.append(42);
sb.insert(0, ">> ");
sb.reverse();          // can reverse too
String result = sb.toString();
```

| | `String` | `StringBuilder` | `StringBuffer` |
|---|---|---|---|
| Can change? | No | Yes | Yes |
| Thread safe? | Yes (can't change) | No | Yes |
| Speed | Slow for repeated edits | **Fastest** | A bit slower |
| Use when | Normal text | Building text in a loop (most cases) | Many threads share it |

---

## 19. Arrays and `ArrayList` reminder

```java
// Array: fixed size
Vehicle[] lot = new Vehicle[3];
lot[0] = new Truck("Ram", 2020, 9000);
Animal[] zoo = { new Dog(), new Cow() };   // create and fill at once
int n = zoo.length;                        // no () on arrays

// ArrayList: grows as you add
import java.util.ArrayList;

ArrayList<Vehicle> list = new ArrayList<>();
list.add(new Truck("Ford", 2021, 8000));
list.size();         // how many (with ())
list.get(0);         // first item
list.remove(0);      // remove first item
list.contains(x);    // uses equals()!
```

**Enhanced for loop** works for both:

```java
for (Vehicle v : list) {
    System.out.println(v.describe());
}
```

ArrayLists can't hold primitives directly. Use the wrapper class: `ArrayList<Integer>`,
not `ArrayList<int>`.

---

## 20. Common mistakes checklist

* [ ] Forgot `super(...)`, or it isn't the first line of the constructor
* [ ] Tried to set a parent's `private` field from the child
* [ ] Missing `@Override`, so a typo created a new method by accident
* [ ] Wrote `equals(Car o)` instead of `equals(Object o)`
* [ ] Compared Strings with `==`
* [ ] Overrode `equals` but not `hashCode`
* [ ] Called a child only method on a parent type variable without `instanceof`
* [ ] File name doesn't match the public class name
* [ ] Forgot `import java.util.ArrayList;` or `import java.util.Objects;`
* [ ] Used `extends` for a "has a" relationship
* [ ] Put a return type on a constructor (`public void Car()` is a method, not a constructor!)

---

## 21. Self check

Answer in your head before you code. Answers are below.

1. A `Laptop` and its `Battery`: "is a" or "has a"?
2. What line does Java insert if you leave out `super(...)`?
3. `Animal a = new Dog(); a.fetch();` Why doesn't this compile?
4. What is the difference between `honk()` / `honk(int)` and `Dog.sound()` / `Animal.sound()`?
5. Why must you override `hashCode` when you override `equals`?
6. Which pillar of OOP is `private` fields with getters?
7. How do you use `ArrayList` in a file without writing `java.util.ArrayList` every time?
8. Two classes in the same folder, no package line. Can `Main` create a `Truck` without an import?
9. Can a `static` method use `this`?
10. You need to build a long String inside a loop of 10,000 steps. What should you use?

**Answers:**
1. has a.  2. `super();`  3. The declared type `Animal` has no `fetch()`.
4. The first pair is overloading (compile time), the second is overriding (run time).
5. Equal objects must have equal hash codes, or hash based collections break.
6. Encapsulation.  7. `import java.util.ArrayList;`  8. Yes, same default package.
9. No, there is no current object.  10. `StringBuilder`.

---

## 22. Definitions gallery

| Term | Meaning |
|---|---|
| **Class** | A blueprint describing fields and methods |
| **Object / instance** | One real thing created from a class with `new` |
| **Field** | A variable that belongs to an object (its state) |
| **Method** | A function that belongs to a class (its behavior) |
| **Constructor** | Special method that sets up a new object; same name as the class, no return type |
| **`this`** | The current object |
| **Encapsulation** | Hiding data with `private` and exposing it through methods |
| **Getter / setter** | Methods that read / change a private field |
| **Package** | A named group of classes that matches a folder path |
| **Import** | Lets you use a class from another package by its short name |
| **Composition** | "Has a": one object holds another as a field |
| **Aggregation** | A looser "has a" where the inner object can exist on its own |
| **Inheritance** | "Is a": a class reuses another class with `extends` |
| **Superclass / supertype / parent** | The class being extended |
| **Subclass / subtype / child** | The class doing the extending |
| **`super(...)`** | Calls the parent constructor; must be the first line |
| **`super.method()`** | Calls the parent's version of a method |
| **Constructor chaining** | Parent constructors run before child constructors |
| **Overriding** | Child replaces a parent method with the same signature |
| **Overloading** | Same method name, different parameters |
| **Method signature** | A method's name plus its parameter types |
| **Polymorphism** | One parent type variable can refer to many child types |
| **Dynamic (late) binding** | The method version is chosen at run time from the actual object |
| **Static (early) binding** | The method is chosen at compile time |
| **Declared type** | The type written on the variable; controls what you can call |
| **Actual type** | The type of the real object; controls which version runs |
| **Upcasting** | Child to parent; automatic and safe |
| **Downcasting** | Parent to child; needs a cast and should be checked with `instanceof` |
| **`instanceof`** | Checks whether an object is a certain type |
| **`ClassCastException`** | Crash from casting an object to a type it isn't |
| **`Object`** | The root superclass of every Java class |
| **`toString()`** | Returns a text version of an object |
| **`equals()`** | Compares two objects by content (once overridden) |
| **`hashCode()`** | Number used by hash collections; must agree with `equals` |
| **Access modifier** | `private`, package private, `protected`, `public` |
| **`final`** | Can't be reassigned, overridden, or extended (depends where it is used) |
| **`static`** | Belongs to the class, shared by all objects |
| **Abstract class** | Class you can't instantiate; may contain abstract methods |
| **Abstract method** | Method with no body that subclasses must implement |
| **Immutable** | Can't change after it is created (like `String`) |
| **`StringBuilder`** | Fast, changeable string for building text |
| **Refactoring** | Improving code structure without changing its behavior |
| **Single Responsibility Principle** | Each class or method should have one job |

---

## 23. Sources

* Professor's Chapter 11 slides: https://shivaprogramming.com/IS247/slides/chap11.html
* Oracle, OOP concepts (object, class, inheritance, interface, package): https://docs.oracle.com/javase/tutorial/java/concepts/
* Oracle, Inheritance: https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html
* Oracle, Overriding and hiding methods: https://docs.oracle.com/javase/tutorial/java/IandI/override.html
* Oracle, Polymorphism: https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html
* Oracle, Object as a superclass: https://docs.oracle.com/javase/tutorial/java/IandI/objectclass.html
* Oracle, Packages: https://docs.oracle.com/javase/tutorial/java/package/packages.html
* Oracle, Controlling access: https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html
* Dev.java (newer official tutorials): https://dev.java/learn/
