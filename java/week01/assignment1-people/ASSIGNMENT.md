# Assignment 1: People

**Skills:** `extends`, `super(...)`, overriding, `ArrayList` of a parent type, `equals()`

The starter files already exist with `TODO` comments. You write all the code.

1. **Person.java:** private `name` (String) and `age` (int), a constructor that sets both,
   getters for both, and a `toString()` that returns something like `Person[name=Aisha, age=30]`.
2. **Student.java:** extends `Person`. Adds private `major` (String) and `gpa` (double).
   Its constructor takes all four values and calls `super(name, age)` first.
   Override `toString()`.
3. **Employee.java:** extends `Person`. Adds private `title` (String) and `salary` (double).
   Same idea as Student.
4. **describe():** add `public String describe()` to `Person` that returns a sentence.
   Override it in `Student` and `Employee` so each returns its own sentence.
   Example: `Kofi is a Computer Science student with a 3.6 GPA.`
5. **Main.java:** create 2 Students and 2 Employees, put all 4 in **one** `ArrayList<Person>`,
   loop through it, and print `describe()` for each.
6. **equals():** override `equals(Object o)` in `Person` so two people with the same name and
   age are equal. In `Main`, test it with one pair that should be equal and one that should not.
   Also override `hashCode()` (one line: `return Objects.hash(name, age);`).

**Check yourself:** your loop prints 4 different sentences even though the list type is `Person`.
That is polymorphism.

---

## How to run

**VS Code terminal** (open it with Ctrl + backtick, the key under Esc):

```powershell
cd java\week01\assignment1-people
javac *.java
java Main
```

**VS Code Run button:** File > Open Folder > pick `assignment1-people` itself, open `Main.java`, click **Run** above `main`.

Stuck on one thing for more than 15 minutes? Check `Java Ch 11 OOP Inheritance and Polymorphism.md` one folder up, then ask Claude.
