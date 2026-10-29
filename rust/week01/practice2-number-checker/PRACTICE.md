# Rust Practice 2: Number Checker (easy to medium)

Code only. Uses Rust Book chapters 1 and 2 only.

**Skills:** `loop`, `break`, `continue`, `match` on `Ok`/`Err` (no crashing!), `match` with `cmp` and `Ordering`, a mutable counter, `%`.

Write it in `src/main.rs`. Run it from this folder:

```powershell
cd rust\week01\practice2-number-checker
cargo run
```

## Steps

1. Make a mutable counter starting at 0.
2. Start a `loop` that:
   1. Prints `Enter a whole number (or q to quit):`
   2. Reads a line into a **new** `String` each time around the loop
   3. If the trimmed input is `q`, prints `Goodbye! You checked <count> numbers.` and **breaks**
   4. Parses the input into an `i32` using `match`:
      * `Ok(num)` → use the number
      * `Err(_)` → print `That's not a whole number. Try again.` and `continue`
   5. Adds 1 to the counter
   6. Uses `match n.cmp(&0)` to print `<n> is negative`, `<n> is zero`, or `<n> is positive`
   7. Prints `<n> is even` or `<n> is odd` (use `%`)

## Expected run

Typing `7`, `-4`, `hello`, `0`, then `q`:

```
Enter a whole number (or q to quit):
7
7 is positive
7 is odd
Enter a whole number (or q to quit):
-4
-4 is negative
-4 is even
Enter a whole number (or q to quit):
hello
That's not a whole number. Try again.
Enter a whole number (or q to quit):
0
0 is zero
0 is even
Enter a whole number (or q to quit):
q
Goodbye! You checked 3 numbers.
```

Notice `hello` didn't count. The counter only goes up for real numbers.

**Try after it works:** move `let mut input = String::new();` to **before** the loop. Type `5`, then `q`. What goes wrong? (`read_line` **adds** to the String, it doesn't replace it.)
