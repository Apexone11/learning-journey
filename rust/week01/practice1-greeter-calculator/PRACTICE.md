# Rust Practice 1: Greeter and Calculator (easy)

Code only. Uses Rust Book chapters 1 and 2 only.

**Skills:** `let`, `let mut`, `String::new()`, `read_line`, `trim`, `parse`, `expect`, shadowing, `println!` with `{}`.

Write it in `src/main.rs`. Run it from this folder:

```powershell
cd rust\week01\practice1-greeter-calculator
cargo run
```

## Steps

1. Ask `What is your name?`, read the answer, trim it, and print `Hello, <name>!`
2. Ask `How old are you?`, parse the answer into a `u32`, and print `Next year you will be <age + 1>.`
   Use **shadowing**: read into `let mut age = String::new();` then later `let age: u32 = ...`
3. Ask `Enter the first whole number:` and `Enter the second whole number:`. Parse both into `i32`.
4. Print the sum, difference, product, and the whole number division with remainder.

If someone types something that isn't a number, it's fine for the program to crash with your `expect` message. (Practice 2 handles that properly.)

## Expected run

What you type is on the lines after each question.

```
What is your name?
Abdul
Hello, Abdul!
How old are you?
25
Next year you will be 26.
Enter the first whole number:
17
Enter the second whole number:
5
17 + 5 = 22
17 - 5 = 12
17 * 5 = 85
17 / 5 = 3 remainder 2
```

**Try after it works:**
* Type `abc` for the age. Read the crash message. Where did that text come from?
* Enter `0` as the second number. What happens, and why?
* Remove `.trim()` from the age line. What breaks? (Hint: the Enter key.)
