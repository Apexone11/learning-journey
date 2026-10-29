# Java Week 2 Practice: 5 Exercises

Code only. Each exercise is its own file with its own `main`. Easy to harder.

Run one from this folder:

```powershell
cd java\week02\practice
javac Ex1.java
java Ex1
```

Match each expected output **exactly**.

---

## Ex1: Safe Divide (try/catch)

Write `static void divide(int a, int b)` that prints `a / b = result`. If `b` is 0, catch the
`ArithmeticException` and print `Cannot divide <a> by zero.` In `main`, call
`divide(10, 2)`, `divide(7, 0)`, `divide(9, 3)`, then print `Done.`

```
10 / 2 = 5
Cannot divide 7 by zero.
9 / 3 = 3
Done.
```

The program must keep going after the zero. That's the whole point.

---

## Ex2: Parse Everything (NumberFormatException in a loop)

Given `String[] inputs = {"42", "3.5", "abc", "-7", ""};` add up every value that
`Integer.parseInt` accepts. For each one that fails, print `Skipped: "<value>"` (with the quotes).
Then print the sum and how many were skipped.

```
Skipped: "3.5"
Skipped: "abc"
Skipped: ""
Sum: 35
Skipped count: 3
```

---

## Ex3: Safe Lookup (multi catch + finally)

Write `static void show(String[] arr, int index)` that prints `Item: ` + the item in UPPERCASE.
Use **one** multi catch for `ArrayIndexOutOfBoundsException | NullPointerException` that prints
`Problem: ` + the exception's simple class name (`e.getClass().getSimpleName()`). Add a `finally`
that prints `Lookup finished.`

In `main`, with `String[] fruits = {"apple", "banana", null};` call:
`show(fruits, 1)`, `show(fruits, 5)`, `show(fruits, 2)`, `show(null, 0)`

```
Item: BANANA
Lookup finished.
Problem: ArrayIndexOutOfBoundsException
Lookup finished.
Problem: NullPointerException
Lookup finished.
Problem: NullPointerException
Lookup finished.
```

Think: why does `show(fruits, 2)` give a NullPointerException and not an index problem?

---

## Ex4: Age Validator (throw)

Make `Ex4` a class with a private `int age`, `getAge()`, and `setAge(int age)` that **throws**
`IllegalArgumentException("Invalid age: <age>")` if age is below 0 or above 150. A bad value
must **not** change the stored age.

In `main`, create one `Ex4` object and try setting `25`, `-3`, `200`, `40` in a loop. Print
`Age set to <age>` on success, `Error: <message>` on failure, then the final age.

```
Age set to 25
Error: Invalid age: -3
Error: Invalid age: 200
Age set to 40
Final age: 40
```

---

## Ex5: Write, Then Read a File (PrintWriter, Scanner, try with resources)

1. Using try with resources and `PrintWriter`, write these 3 lines to `notes.txt`:
   ```
   Java exceptions are objects
   try catch finally
   Always close your files
   ```
2. Write `static void count(String fileName)` that opens the file with try with resources and a
   `Scanner`, counts **lines** and **words**, and prints `<fileName>: <lines> lines, <words> words`.
   If the file doesn't exist, catch `FileNotFoundException` and print `File not found: <fileName>`.
   (Hint for words: `line.trim().split("\\s+").length`)
3. Call `count("notes.txt")` and then `count("missing.txt")`.

```
notes.txt: 3 lines, 11 words
File not found: missing.txt
```

---

When all 5 are done: push, then tell Claude "Java week 2 practice pushed."
