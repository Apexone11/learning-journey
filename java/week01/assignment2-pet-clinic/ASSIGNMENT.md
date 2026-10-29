# Assignment 2: Pet Clinic

**Skills:** `protected`, calling `super.method()`, overriding vs overloading, `final`,
`static` counters, `instanceof`, arrays of a parent type

The starter files already exist with `TODO` comments. You write all the code.

1. **Pet.java**

* `protected` fields: `name` (String) and `ageYears` (int)
* a `private static int petCount` that goes up by 1 every time any pet is created
* constructor that sets name and age and increments `petCount`
* `public static int getPetCount()`
* `public double visitCost()` returns `50.0`
* `public String sound()` returns `"..."`
* **two** `feed` methods (overloading):
  * `feed()` prints `Feeding Max the normal amount.`
  * `feed(int grams)` prints `Feeding Max 200 grams.`
* `public final String tag()` returns `name.toUpperCase() + "-" + ageYears`
2. **Dog.java:** extends `Pet`. Adds `boolean needsGrooming`.

* `visitCost()` returns `super.visitCost() + 20`, plus another `15` if it needs grooming.
  You **must** use `super.visitCost()`, not the number 50.
* `sound()` returns `"Woof"`
* a Dog only method `fetch()` that prints `Max runs to get the ball.`
3. **Cat.java:** extends `Pet`. Adds `boolean indoor`.

* `visitCost()` returns `super.visitCost()`, plus `10` if the cat is NOT indoor
* `sound()` returns `"Meow"`
4. **Main.java**

* Make a `Pet[]` array (a plain array this time, not ArrayList) with 2 Dogs and 2 Cats
* Loop through it and print each pet's `tag()`, `sound()`, and `visitCost()`
* Add up the total cost of all visits and print it
* Inside the loop, if the pet is a Dog, call `fetch()` on it.
  Use pattern matching: `if (p instanceof Dog d) { d.fetch(); }`
* Call both versions of `feed` on one pet
* Print `Pet.getPetCount()` at the end
5. **Break it on purpose (then undo it):**

* Try to override `tag()` in `Dog`. Read the compile error, then delete your override.
* In `Cat`, remove the `super(...)` call from the constructor. Read the error, then put it back.

## Questions (answer in `ANSWERS.md` in your own words)

1. What was the compile error when you overrode `tag()`, and why does `final` exist?
2. What was the error when you removed `super(...)`? Why does Java force it here?
3. In your own code, what is the difference between how `feed` and `visitCost` are reused? (One is overloading, one is
   overriding. Which is which, and how can you tell?)
4. Why could `Dog` read `name` directly in `fetch()`? What would change if `name` were `private`?
5. Your loop variable is a `Pet`, yet Dogs print `Woof`. Which method gets called is decided
   when the program runs, not when it compiles. What is that called?

## Check your math

| Pet          | Details        | Cost      |
|--------------|----------------|-----------|
| Dog Max, 3   | needs grooming | 85.0      |
| Dog Bella, 5 | no grooming    | 70.0      |
| Cat Luna, 2  | indoor         | 50.0      |
| Cat Simba, 4 | outdoor        | 60.0      |
| **Total**    |                | **265.0** |

---

## How to run

**VS Code terminal** (open it with Ctrl + backtick, the key under Esc):

```powershell
cd java\week01\assignment2-pet-clinic
javac *.java
java Main
```

**VS Code Run button:** File > Open Folder > pick `assignment2-pet-clinic` itself, open `Main.java`, click **Run** above
`main`.

Stuck on one thing for more than 15 minutes? Check `Java Ch 11 OOP Inheritance and Polymorphism.md` one folder up, then
ask Claude.

## When both assignments are done

```powershell
git add .
git commit -m "Java week 1: inheritance and pet clinic"
git push
```

Then tell Claude "Java week 1 pushed" for a review.
