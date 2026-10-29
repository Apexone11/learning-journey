# Java Chapter 12: Exception Handling and Text I/O

Your refresher for IS 247 Chapter 12. Read it before you code; come back whenever you're stuck.

**Contents**

1. Three kinds of errors
2. What an exception is
3. Reading a stack trace
4. The exception hierarchy
5. Checked vs unchecked
6. `try` and `catch`
7. Several catches and multi catch
8. `finally`
9. `throw`: raising an exception yourself
10. `throws`: warning callers
11. How exceptions travel up the call stack
12. Exception objects: constructors and methods
13. Custom exceptions
14. Common exceptions you'll meet
15. The `File` class
16. Writing text files with `PrintWriter`
17. Reading text files with `Scanner`
18. Try with resources
19. Splitting lines (CSV basics)
20. Best practices
21. Common mistakes checklist
22. Self check
23. Definitions gallery
24. Sources

---

## 1. Three kinds of errors

| Kind | When you find out | Example | Who catches it |
|---|---|---|---|
| **Compile time (syntax)** | Before the program runs | Undeclared variable, missing `;`, wrong type | The compiler |
| **Runtime** | While it runs, it crashes | Dividing an `int` by zero | You, with exception handling |
| **Logic** | Never, unless you test! It runs and gives the wrong answer | `(byte) 1000` silently becomes `-24` | Only testing |

Exception handling is about **runtime errors**. Logic errors are why you check your output
against the expected output.

---

## 2. What an exception is

An **exception** is an **unplanned event that interrupts the normal flow** of a program.
Things go wrong that you can't fully control:

* A file the user asked for doesn't exist
* The network or database is down
* The user types `abc` where you need a number

Without handling, the program **crashes**, shows an ugly message, and may leave files or
connections open.

**Goals of exception handling:**

1. Give the program an **alternative path** instead of crashing
2. Show the **user** a friendly message
3. Give **developers** enough detail to debug

In Java, an exception is an **object**. When something goes wrong, Java creates an exception
object and **throws** it. Your code can **catch** it.

---

## 3. Reading a stack trace

```java
public class Demo {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(nums[5]);
    }
}
```

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 3
    at Demo.main(Demo.java:4)
```

| Part | Tells you |
|---|---|
| `ArrayIndexOutOfBoundsException` | **What** went wrong (the exception class) |
| `Index 5 out of bounds for length 3` | The **message**: details |
| `at Demo.main(Demo.java:4)` | **Where**: class, method, file, **line number** |

With several `at` lines, the **top** one is where it happened, and each line below it is
who called that. **Start reading at the top line that is in your own code.**

---

## 4. The exception hierarchy

Exceptions are classes, so they use inheritance (Chapter 11!):

```
                    Throwable
                   /         \
              Error           Exception
       (don't catch)          /        \
   OutOfMemoryError    IOException    RuntimeException
   StackOverflowError      |               |
                  FileNotFoundException    ├── ArithmeticException
                                           ├── NullPointerException
                                           ├── ArrayIndexOutOfBoundsException
                                           ├── IllegalArgumentException
                                           │       └── NumberFormatException
                                           └── ClassCastException
```

* **`Throwable`**: the root. Only Throwables can be thrown.
* **`Error`**: serious **system** problems (out of memory, stack overflow). Usually
  unrecoverable. **Don't catch these.**
* **`Exception`**: problems your program **can** reasonably handle.
* **`RuntimeException`**: usually **bugs in your code** (null, bad index, bad cast).

Because it's inheritance, `catch (Exception e)` catches **every** subclass too, just like a
`Pet` variable can hold any Dog.

---

## 5. Checked vs unchecked

| | **Checked** | **Unchecked** |
|---|---|---|
| Which ones | `Exception` and its subclasses, **except** RuntimeException | `RuntimeException`, `Error`, and their subclasses |
| Compiler forces you to handle it? | **Yes**: catch it or declare `throws` | No |
| Usually caused by | Things outside your control (files, network) | Bugs in your code |
| Examples | `IOException`, `FileNotFoundException` | `NullPointerException`, `ArithmeticException`, `NumberFormatException` |

```java
// Won't compile: FileNotFoundException is CHECKED
Scanner sc = new Scanner(new File("data.txt"));
// error: unreported exception FileNotFoundException; must be caught or declared to be thrown
```

**Rule of thumb:**
* **Checked** = "this can fail even if your code is perfect, so plan for it."
* **Unchecked** = "fix your code so it doesn't happen."

Either kind, if **nobody** catches it, crashes the program.

---

## 6. `try` and `catch`

```java
try {
    // code that might fail
    int result = 10 / 0;
    System.out.println("This line never runs");
} catch (ArithmeticException e) {
    // runs only if that exception happened
    System.out.println("Can't divide by zero.");
}
System.out.println("Program keeps going.");
```

How it flows:

1. Java runs the `try` block line by line.
2. When a line throws, Java **jumps immediately** to a matching `catch`. The rest of `try` is **skipped**.
3. If nothing goes wrong, every `catch` is skipped.
4. Either way, the program continues **after** the try/catch.

`e` is the exception object. Useful methods: `e.getMessage()`, `e.printStackTrace()`.

---

## 7. Several catches and multi catch

### Several `catch` blocks

```java
try {
    String text = args[0];
    int n = Integer.parseInt(text);
    System.out.println(100 / n);
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("No input given.");
} catch (NumberFormatException e) {
    System.out.println("Not a number.");
} catch (ArithmeticException e) {
    System.out.println("Can't use zero.");
}
```

Java checks the catches **top to bottom** and runs **only the first** one that matches.

**Order rule:** put **specific (child) exceptions before general (parent) ones**:

```java
catch (Exception e) { ... }
catch (NumberFormatException e) { ... }   // COMPILE ERROR: already caught above
```

### Multi catch (one block, several types)

When the handling is the same, join them with `|`:

```java
catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
    System.out.println("Bad lookup: " + e.getClass().getSimpleName());
}
```

`e.getClass().getSimpleName()` gives the exception's class name, like `NullPointerException`.

### What if no catch matches?

The exception isn't handled there. It keeps flying up to the caller (section 11).
That's the "no matching exception" lab from class.

---

## 8. `finally`

```java
try {
    System.out.println("Opening connection");
    int x = 5 / 0;
} catch (ArithmeticException e) {
    System.out.println("Math error");
} finally {
    System.out.println("Closing connection");   // ALWAYS runs
}
```

`finally` runs **no matter what**:

* the try succeeded
* an exception was caught
* an exception was **not** caught (finally runs, then the exception keeps going)
* even if the `try` or `catch` has a `return`

Use it for **cleanup**: closing files, connections, printing "done." (Try with resources,
section 18, is usually better for closing things.)

Legal shapes: `try/catch`, `try/finally`, `try/catch/finally`. A `try` alone is not allowed.

---

## 9. `throw`: raising an exception yourself

Your code can **throw** an exception when it detects something wrong:

```java
public void setPrice(double price) {
    if (price < 0) {
        throw new IllegalArgumentException("Price can't be negative: " + price);
    }
    this.price = price;
}
```

Rules:

* `throw` needs an **exception object**: `throw new SomethingException(...)`.
* You can only throw **Throwables**. `throw "oops";` won't compile.
* Throwing `null` causes a `NullPointerException`.
* **No code can follow** `throw` in the same block (it's unreachable, compile error).

**Why throw instead of printing an error?** A print inside `setPrice` doesn't stop anything,
and the caller never knows. Throwing **forces** the caller to deal with it.

`IllegalArgumentException` is the standard choice for "you passed me a bad value."
`IllegalStateException` is for "you called this at the wrong time."

---

## 10. `throws`: warning callers

If a method can throw a **checked** exception and doesn't catch it itself, it **must** say so:

```java
public static String readFirstLine(String fileName) throws FileNotFoundException {
    Scanner sc = new Scanner(new File(fileName));
    return sc.nextLine();
}
```

Now **whoever calls** `readFirstLine` must catch it or declare `throws` too.

| | `throw` | `throws` |
|---|---|---|
| What | Actually **throws** an exception, now | **Declares** a method **might** throw one |
| Where | Inside a method body | In the method header |
| Followed by | One exception **object** | One or more exception **class names** |
| Example | `throw new IOException("x");` | `void load() throws IOException` |

Memory trick: **throw** = do it. **throw*s*** = it **says** it might.

You **can** list unchecked exceptions in `throws`, but you don't have to.

---

## 11. How exceptions travel up the call stack

```java
static void c() { int x = 1 / 0; }         // throws here
static void b() { c(); }
static void a() {
    try {
        b();
    } catch (ArithmeticException e) {
        System.out.println("Caught in a()");
    }
}
```

1. `c()` throws. It has no catch, so `c` stops immediately.
2. The exception goes up to `b()`. No catch, so `b` stops too.
3. It reaches `a()`, which catches it. Program continues normally from there.
4. If it had reached `main` with no catch, the **program crashes** and prints the stack trace.

This is called **propagation**. It lets you handle problems at the level that knows what to
do about them (often the UI level, which can show the user a message).

---

## 12. Exception objects: constructors and methods

Exceptions are normal classes. Most have **four constructors**:

```java
new Exception();                          // no details
new Exception("Message");                 // a message
new Exception(cause);                     // wraps another exception
new Exception("Message", cause);          // both
```

Useful methods (inherited from `Throwable`):

| Method | Gives |
|---|---|
| `getMessage()` | The message text |
| `toString()` | Class name + message |
| `printStackTrace()` | Prints the full stack trace (great for debugging) |
| `getCause()` | The wrapped exception, if any |

### Wrapping (chaining) exceptions

Turn a low level exception into one that makes sense for your program, but **keep the
original** as the cause so nothing is lost for debugging:

```java
try {
    qty = Integer.parseInt(text);
} catch (NumberFormatException e) {
    throw new InvalidOrderException("Quantity must be a number: " + text, e);
}
```

---

## 13. Custom exceptions

When no built in exception describes your problem, make your own by **extending** one:

```java
// Checked: callers MUST handle it
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

Using it:

```java
public void removeStock(int qty) throws InsufficientStockException {
    if (qty > stock) {
        throw new InsufficientStockException("Only " + stock + " left, asked for " + qty);
    }
    stock -= qty;
}
```

* Extend **`Exception`** for a **checked** exception (the caller should plan for it).
* Extend **`RuntimeException`** for an **unchecked** one (it means a programming mistake).
* Name it ending in **`Exception`**.
* **Prefer built in exceptions** when they fit (`IllegalArgumentException`, etc.). Only make
  your own when it adds meaning.

---

## 14. Common exceptions you'll meet

| Exception | Checked? | Typical cause |
|---|---|---|
| `ArithmeticException` | No | Integer divided by zero |
| `NullPointerException` | No | Calling a method on `null` (`String s = null; s.length();`) |
| `ArrayIndexOutOfBoundsException` | No | Index below 0 or ≥ length |
| `NumberFormatException` | No | `Integer.parseInt("abc")` or `parseInt("3.5")` |
| `ClassCastException` | No | Casting to a type the object isn't |
| `IllegalArgumentException` | No | A method got a bad argument |
| `InputMismatchException` | No | `Scanner.nextInt()` read text |
| `FileNotFoundException` | **Yes** | File doesn't exist / can't be opened |
| `IOException` | **Yes** | General input/output failure |

Note: `10.0 / 0` does **not** throw. Doubles give `Infinity`. Only **integer** division by zero throws.

---

## 15. The `File` class

`java.io.File` represents a **path** to a file or folder. Creating one does **not** create
or open the file.

```java
import java.io.File;

File f = new File("data.txt");
f.exists();          // true or false
f.getName();         // "data.txt"
f.getAbsolutePath(); // full path on disk
f.length();          // size in bytes
f.delete();          // deletes it
```

### Relative paths

`"data.txt"` is relative to the **working directory**: the folder you were in when you ran
`java`. If your file "can't be found" but it's right there, you probably ran the program
from a different folder. Run from the folder that has the file:

```powershell
cd java\week02\assessment
javac *.java
java Main
```

---

## 16. Writing text files with `PrintWriter`

```java
import java.io.PrintWriter;
import java.io.FileNotFoundException;

try (PrintWriter out = new PrintWriter("scores.txt")) {
    out.println("Aisha,92");
    out.println("Kofi,78");
    out.printf("Average: %.2f%n", 85.0);
} catch (FileNotFoundException e) {
    System.out.println("Couldn't create scores.txt");
}
```

* Same methods as `System.out`: `print`, `println`, `printf`.
* **Creates** the file if it doesn't exist. **Erases** it if it does.
* Throws the **checked** `FileNotFoundException` (for example, a folder that doesn't exist).
* **Must be closed**, or the text may never be saved to disk. Try with resources does it for you.

To **add** to a file instead of erasing it:

```java
new PrintWriter(new FileWriter("log.txt", true))   // true = append; FileWriter throws IOException
```

---

## 17. Reading text files with `Scanner`

```java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

try (Scanner in = new Scanner(new File("scores.txt"))) {
    while (in.hasNextLine()) {
        String line = in.nextLine();
        System.out.println("Read: " + line);
    }
} catch (FileNotFoundException e) {
    System.out.println("scores.txt not found");
}
```

* `new Scanner(new File(...))` reads a **file**. `new Scanner(System.in)` reads the **keyboard**.
  Same methods either way.
* `new Scanner("scores.txt")` (no `File`) scans the **text** "scores.txt" itself. Classic bug!
* Loop with `hasNextLine()` / `nextLine()` for whole lines, or `hasNext()` / `next()` for words,
  or `hasNextInt()` / `nextInt()` for numbers.
* **Mixing `nextInt()` and `nextLine()`** is a trap: `nextInt()` leaves the Enter key behind,
  so the next `nextLine()` returns an empty string. Read lines and parse them instead.

---

## 18. Try with resources

The old way, closing manually:

```java
Scanner in = null;
try {
    in = new Scanner(new File("data.txt"));
    // read...
} catch (FileNotFoundException e) {
    System.out.println("Not found");
} finally {
    if (in != null) {
        in.close();
    }
}
```

The modern way:

```java
try (Scanner in = new Scanner(new File("data.txt"))) {
    // read...
} catch (FileNotFoundException e) {
    System.out.println("Not found");
}
// in is closed automatically here, even if an exception happened
```

* Put the resource in **parentheses** after `try`.
* Java **closes it automatically** when the block ends, success or failure.
* Works with anything that implements `AutoCloseable` (Scanner, PrintWriter, files, database connections).
* **Several resources**: separate with `;`. They close in **reverse** order.

```java
try (Scanner in = new Scanner(new File("in.txt"));
     PrintWriter out = new PrintWriter("out.txt")) {
    while (in.hasNextLine()) {
        out.println(in.nextLine().toUpperCase());
    }
} catch (FileNotFoundException e) {
    System.out.println("File problem: " + e.getMessage());
}
```

**Use try with resources for every file you open.** It's shorter and you can't forget to close.

---

## 19. Splitting lines (CSV basics)

A **CSV** (comma separated values) file stores a table as text:

```
name,score
Aisha,92
Kofi,78
```

```java
String line = "Aisha,92";
String[] parts = line.split(",");     // ["Aisha", "92"]
String name = parts[0].trim();
int score = Integer.parseInt(parts[1].trim());
```

Things that go wrong with real data, and how to guard:

| Problem | Example line | Guard |
|---|---|---|
| Missing field | `Aisha` | check `parts.length` before using `parts[1]` |
| Empty field | `,70` | `parts[0].isBlank()` |
| Not a number | `Lena,abc` | catch `NumberFormatException` |
| Extra spaces | `Kofi , 78` | `.trim()` |
| Header row | `name,score` | read and skip the first line |

`split` takes a **regular expression**: `split("\\s+")` splits on any run of spaces.

---

## 20. Best practices

* **Catch specific exceptions**, not `Exception`, unless you really mean "anything."
* **Never swallow an exception** with an empty catch: `catch (Exception e) { }` hides bugs forever.
  At least print a message.
* **Don't use exceptions for normal flow.** If you can check first (`if (b != 0)`), do that.
* **Fail early:** validate inputs at the start of a method and `throw` right away.
* **Clean up with try with resources**, not by hand.
* **Keep the cause** when wrapping (`new MyException(msg, e)`).
* **Messages should help:** `"Out of range: 105"` beats `"Error"`.

---

## 21. Common mistakes checklist

* [ ] Catching a parent before a child (compile error)
* [ ] Empty catch block
* [ ] `new Scanner("file.txt")` instead of `new Scanner(new File("file.txt"))`
* [ ] Forgot to close a `PrintWriter`, so the file is empty
* [ ] Running from the wrong folder, so the file "isn't found"
* [ ] Code after `throw` in the same block
* [ ] Checked exception neither caught nor declared
* [ ] Confusing `throw` and `throws`
* [ ] Expecting `10.0 / 0` to throw (it gives Infinity)
* [ ] Using `parts[1]` without checking `parts.length`
* [ ] Mixing `nextInt()` and `nextLine()`

---

## 22. Self check

1. Name the three kinds of errors. Which one does exception handling deal with?
2. In a stack trace, which line do you read first?
3. Is `NumberFormatException` checked or unchecked? `FileNotFoundException`?
4. When does `finally` run?
5. `catch (Exception e)` then `catch (IOException e)`: what happens?
6. Difference between `throw` and `throws`?
7. To make your own **checked** exception, which class do you extend?
8. What's wrong with `new Scanner("data.txt")`?
9. Why use try with resources?
10. What does `10 / 0` do? `10.0 / 0`?

**Answers:**
1. Compile time, runtime, logic. Runtime.
2. The top line that's in your own code.
3. Unchecked. Checked.
4. Always: after success, after a catch, or before an uncaught exception continues upward.
5. Compile error, the second catch is unreachable.
6. `throw` actually throws an exception object; `throws` in a method header declares it might.
7. `Exception` (not `RuntimeException`).
8. It scans the text "data.txt" instead of opening the file. Use `new File("data.txt")`.
9. It closes the resource automatically, even when an exception happens.
10. Throws `ArithmeticException`. Gives `Infinity`, no exception.

---

## 23. Definitions gallery

| Term | Meaning |
|---|---|
| **Exception** | An object representing an unplanned event that interrupts normal flow |
| **Throw** | Create and raise an exception |
| **Catch** | Handle a thrown exception |
| **Stack trace** | Printout showing the exception and the chain of method calls that led to it |
| **Call stack** | The list of methods currently running, each called by the one below it |
| **Propagation** | An unhandled exception moving up to the calling method |
| **`Throwable`** | Root of everything that can be thrown |
| **`Error`** | Serious system problem; don't catch |
| **`Exception`** | Problem a program can handle |
| **`RuntimeException`** | Unchecked exceptions, usually programming bugs |
| **Checked exception** | Compiler forces you to catch or declare it |
| **Unchecked exception** | Compiler doesn't force handling |
| **`try`** | Block of code that might throw |
| **`catch`** | Block that handles a specific exception type |
| **Multi catch** | One catch for several types: `catch (A \| B e)` |
| **`finally`** | Block that always runs, for cleanup |
| **`throw`** | Statement that throws an exception object |
| **`throws`** | Method header clause declaring possible exceptions |
| **Custom exception** | Your own exception class, extending `Exception` or `RuntimeException` |
| **Cause / chaining** | Wrapping one exception inside another without losing it |
| **`File`** | Object representing a file path |
| **Working directory** | The folder relative paths start from |
| **`PrintWriter`** | Writes text to a file |
| **`Scanner`** | Reads text from the keyboard, a file, or a String |
| **Try with resources** | `try (resource) { }`: closes the resource automatically |
| **`AutoCloseable`** | Interface for things try with resources can close |
| **CSV** | Comma separated values: a table stored as text |
| **`split`** | Breaks a String into an array at a separator |

---

## 24. Sources

* Professor's Chapter 12 slides: https://shivaprogramming.com/IS247/slides/chap12.html
* Course code samples (`src/Exceptions`): https://github.com/shivasharma/IS247
* Oracle, Exceptions lesson: https://docs.oracle.com/javase/tutorial/essential/exceptions/
* Oracle, Try with resources: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
* Oracle, Scanning: https://docs.oracle.com/javase/tutorial/essential/io/scanning.html
* Dev.java exceptions: https://dev.java/learn/exceptions/
