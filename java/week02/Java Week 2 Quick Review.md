# Java Week 2 Quick Review

5 minute refresher. Details are in `Java Ch 12 Exceptions and Text IO.md`.

## The full shape

```java
try {
    // risky code
} catch (SpecificException e) {        // child types first
    System.out.println(e.getMessage());
} catch (OtherException | ThirdException e) {   // multi catch
    // same handling for both
} finally {
    // always runs
}
```

## Throwing

```java
if (bad) {
    throw new IllegalArgumentException("Helpful message: " + value);
}
```

```java
public void load() throws FileNotFoundException { ... }   // checked: declare or catch
```

**throw** = do it now. **throws** = this method might.

## Checked or unchecked?

* **Checked** (must catch or declare): `IOException`, `FileNotFoundException`, anything extending `Exception` but not `RuntimeException`.
* **Unchecked**: `ArithmeticException`, `NullPointerException`, `ArrayIndexOutOfBoundsException`, `NumberFormatException`, `IllegalArgumentException`.

## Custom exception

```java
public class MyProblemException extends Exception {      // extends Exception = checked
    public MyProblemException(String message) { super(message); }
    public MyProblemException(String message, Throwable cause) { super(message, cause); }
}
```

## Files

```java
// WRITE
try (PrintWriter out = new PrintWriter("out.txt")) {
    out.println("hello");
} catch (FileNotFoundException e) { ... }

// READ
try (Scanner in = new Scanner(new File("in.txt"))) {      // new File(...)! not just the name
    while (in.hasNextLine()) {
        String[] parts = in.nextLine().split(",");
    }
} catch (FileNotFoundException e) { ... }
```

Imports: `java.io.File`, `java.io.PrintWriter`, `java.io.FileNotFoundException`, `java.util.Scanner`.

**File not found but it's right there?** Run from the folder that contains it.

## Before you say "done"

- [ ] No empty catch blocks
- [ ] Specific catches before general ones
- [ ] Every file opened with try with resources
- [ ] Output matches the expected output exactly
