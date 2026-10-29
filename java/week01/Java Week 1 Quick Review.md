# Java Week 1 Quick Review

Read this in 5 minutes before you code. Need more detail? Open `Java Ch 11 OOP Inheritance and Polymorphism.md`.

## Inheritance skeleton

```java
public class Parent {
    private String name;                       // private: child can't touch directly
    protected int level;                       // protected: child CAN use directly

    public Parent(String name) { this.name = name; }
    public String getName() { return name; }
    public String info() { return "Parent " + name; }
}

public class Child extends Parent {
    private int extra;

    public Child(String name, int extra) {
        super(name);                           // FIRST line, builds the parent part
        this.extra = extra;
    }

    @Override                                  // always write it
    public String info() {
        return super.info() + " with " + extra;   // reuse the parent's version
    }
}
```

## Rules to remember

* `super(...)` must be the **first line**. Leave it out and Java adds `super();` with no arguments.
* One constructor can call another in the **same** class with `this(...)` (also first line).
* **Override** = same name, **same** parameters, in a child. Decided at **run time**.
* **Overload** = same name, **different** parameters. Decided at **compile time**.
* `final` method = can't be overridden. `final` class = can't be extended.
* `static` = one shared copy for the whole class. Call it with `ClassName.method()`.

## Polymorphism

```java
Parent p = new Child("Ana", 3);   // parent type variable, child object
p.info();                          // runs Child's info()  (dynamic binding)
p.childOnlyMethod();               // COMPILE ERROR, Parent doesn't have it

if (p instanceof Child c) {        // check, then use as Child
    c.childOnlyMethod();
}
```

* **Declared type** (left side) = what you're **allowed** to call.
* **Actual type** (the object) = which **version** runs.
* `x instanceof Parent` is **true** for children too. A Child **is a** Parent.

## equals / hashCode / toString

```java
@Override
public boolean equals(Object o) {              // parameter MUST be Object
    if (this == o) return true;
    if (!(o instanceof Parent other)) return false;
    return level == other.level && getName().equals(other.getName());
}

@Override
public int hashCode() { return Objects.hash(getName(), level); }   // same fields as equals

@Override
public String toString() { return "Parent[name=" + getName() + "]"; }
```

## Watch your types

* `int total = 0; total += 2.75;` compiles and **silently chops** to 2. Use `double` for money and decimals.
* Format money: `String.format("$%.2f", amount)` or `System.out.printf("%.2f%n", amount);`

## Before you say "done"

- [ ] Does the output match the expected output **exactly**?
- [ ] Did every override get `@Override`?
- [ ] Did I follow every number in the spec?
- [ ] Formatted? (VS Code: **Shift + Alt + F**)
