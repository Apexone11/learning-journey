# Rust Chapters 1 and 2: Getting Started and the Guessing Game

Your notes for The Rust Programming Language book, chapters 1 and 2. Read this when something
from the book is fuzzy, so you don't have to reopen the book. Java comparisons are marked ☕.

**Contents**

1. What Rust is and why people use it
2. Installing and updating (rustup)
3. Hello, world! without Cargo
4. Cargo: Rust's build tool
5. Anatomy of a Rust program
6. Variables: `let` and `mut`
7. Reading input: `String::new()` and `read_line`
8. References: `&` and `&mut` (first look)
9. `Result` and `expect`
10. Printing with `println!`
11. Adding a crate (dependency)
12. Comparing with `match` and `Ordering`
13. Shadowing and converting text to numbers
14. Loops: `loop`, `break`, `continue`
15. Handling bad input without crashing
16. The full guessing game
17. Common mistakes checklist
18. Self check
19. Definitions gallery
20. Sources

---

## 1. What Rust is and why people use it

Rust is a **compiled systems language** focused on three things:

* **Speed:** compiles to machine code like C and C++. No virtual machine.
* **Memory safety without a garbage collector:** the compiler checks your memory use at
  compile time, so whole classes of bugs (crashes, security holes) can't happen.
* **Fearless concurrency:** the same checks stop many threading bugs.

☕ Java runs on the JVM and cleans memory with a garbage collector. Rust has no JVM and no
garbage collector. The compiler does the safety work up front, which is why it is strict.

**The compiler is strict on purpose.** Its error messages are some of the best of any
language. Read them fully; they usually tell you the exact fix.

---

## 2. Installing and updating (rustup)

**rustup** installs and manages Rust versions.

| Command | Does |
|---|---|
| `rustc --version` | show the compiler version |
| `cargo --version` | show Cargo's version |
| `rustup update` | update to the latest stable Rust |
| `rustup doc` | open the docs offline in your browser |
| `rustup self uninstall` | remove everything |

On Windows, Rust needs the **Visual Studio C++ Build Tools** (the installer offers them).

Your repo's `rust/rust-toolchain.toml` pins the toolchain, so both your machines use the same one.

---

## 3. Hello, world! without Cargo

```rust
fn main() {
    println!("Hello, world!");
}
```

```powershell
rustc main.rs     # compile
.\main.exe        # run (Windows)
```

You will almost never do this. Real projects use Cargo.

---

## 4. Cargo: Rust's build tool

Cargo builds your code, downloads libraries, and runs tests. ☕ It is like **Maven or Gradle**
built into the language.

| Command | Does |
|---|---|
| `cargo new my_app` | make a new project folder |
| `cargo build` | compile (debug, fast to build) |
| `cargo run` | compile if needed, then run |
| `cargo check` | check for errors **without** building. Fastest feedback. |
| `cargo build --release` | optimized build for real use (slower to compile, faster to run) |
| `cargo doc --open` | build docs for your project **and every crate it uses** |
| `cargo update` | update crates within the allowed versions |

### What `cargo new` creates

```
my_app/
├── Cargo.toml     ← project settings and dependencies
├── .gitignore     ← already ignores target/
└── src/
    └── main.rs    ← your code
```

### Cargo.toml

```toml
[package]
name = "my_app"
version = "0.1.0"
edition = "2024"     # which version of the Rust language rules

[dependencies]
rand = "0.10"         # libraries go here
```

The file format is **TOML** (Tom's Obvious, Minimal Language).

### Other things Cargo makes

* `target/` folder: build output. **Never commit it.** (Your `.gitignore` blocks it.)
* `Cargo.lock`: the **exact** versions of every dependency, so builds are repeatable.
  **Commit it** for programs.

---

## 5. Anatomy of a Rust program

```rust
use std::io;          // bring io into scope (like Java's import)

fn main() {           // fn = function. main is where the program starts.
    println!("Hi");   // ! means println is a MACRO, not a normal function
}                     // statements end with ;
```

* `fn` declares a function. ☕ No `public static void`, no class needed.
* Indent with **4 spaces**. `cargo fmt` formats your code automatically.
* A name ending in `!` (`println!`) is a **macro**. Macros write code for you at compile time.
* `std` is the **standard library**. A small part of it (the **prelude**) is available without `use`.
* `//` comments a line. (`///` is a doc comment, later chapters.)

---

## 6. Variables: `let` and `mut`

```rust
let apples = 5;          // IMMUTABLE by default: can't change
let mut bananas = 10;    // mut = mutable: CAN change
bananas = 11;            // OK
apples = 6;              // COMPILE ERROR: cannot assign twice to immutable variable
```

☕ In Java, variables change unless you write `final`. **Rust is the opposite:** everything
is "final" unless you write `mut`. This makes it obvious which values change.

Rust usually **figures out the type** for you (type inference). You can also write it:

```rust
let age: u32 = 30;
```

Common number types: `i32` (default whole number, can be negative), `u32` (no negatives),
`i64`, `u64`, `f64` (decimal). Chapter 3 covers all of them.

---

## 7. Reading input: `String::new()` and `read_line`

```rust
use std::io;

let mut guess = String::new();      // a new, empty, growable String

io::stdin()                         // get a handle to the keyboard input
    .read_line(&mut guess)          // read one line INTO guess
    .expect("Failed to read line"); // crash with this message if reading fails
```

* `String::new()`: the `::` means `new` is an **associated function** of the `String` type.
  ☕ Like a static method: `String.new()` would be the Java feel.
* `read_line` **adds** the text to the end of the String. It does **not** clear it first.
  If you reuse the same String in a loop, it keeps growing. Make a new one each time.
* The text includes the **Enter key** (`\n`, or `\r\n` on Windows). That's why you call `.trim()`.
* Long method chains are split across lines for readability. It's still one statement.

---

## 8. References: `&` and `&mut` (first look)

```rust
.read_line(&mut guess)
```

`&` means **"lend it, don't give it away."** A **reference** lets code use your data without
copying it or taking it over.

* `&guess` = borrow it, **read only**
* `&mut guess` = borrow it **and allow changes** (read_line must add text, so it needs `&mut`)

References are immutable by default too, so `&mut` is required to change through one.
Chapter 4 (ownership) explains this deeply. For now: **if a function needs to change your
variable, pass `&mut`.**

---

## 9. `Result` and `expect`

Many operations can fail. Instead of throwing exceptions (☕ like Java), Rust functions
return a **`Result`**:

| Variant | Means | Holds |
|---|---|---|
| `Ok(value)` | it worked | the value |
| `Err(error)` | it failed | information about the error |

`Result` is an **enum**: a type that is exactly one of a fixed set of **variants**.

You must deal with it. Ways to do that so far:

```rust
.expect("Failed to read line")   // Ok → give me the value. Err → CRASH with this message.
.unwrap()                        // same, but crashes with a generic message
match ... { Ok(x) => ..., Err(_) => ... }   // handle both cases yourself (section 15)
```

If you ignore a `Result` completely, the compiler **warns** you. Rust doesn't let errors
silently slip by.

A crash in Rust is called a **panic**.

---

## 10. Printing with `println!`

```rust
let x = 5;
let y = 10;

println!("x = {x}");                       // variable name inside {}
println!("x + y = {}", x + y);             // empty {} = next argument
println!("x = {x} and y + 2 = {}", y + 2); // mix both
```

* Put a **variable name** right inside `{}`.
* For **expressions** (math, method calls), use empty `{}` and list them after the comma, in order.
* `print!` = same without the newline.

---

## 11. Adding a crate (dependency)

A **crate** is a package of Rust code. **crates.io** is where public crates live (☕ like Maven Central).

* A **binary crate** is a program you run (your projects).
* A **library crate** is code for other programs to use (like `rand`).

Add one in `Cargo.toml`:

```toml
[dependencies]
rand = "0.10"
```

Or let Cargo do it: `cargo add rand`

The version `"0.10"` really means `^0.10`: any version that's **compatible** (0.10.x), not 0.11.
This is **Semantic Versioning (SemVer)**: MAJOR.MINOR.PATCH.

On the next `cargo build`, Cargo downloads it and records the exact version in **Cargo.lock**.
`cargo update` moves to the newest compatible version and updates the lock file.

### Random numbers

The `rand` crate's functions changed between versions. Use whatever matches your version:

```rust
// rand 0.10 (what your guessing_game uses)
let secret_number = rand::random_range(1..=100);

// older rand 0.8 (some older copies of the book)
use rand::Rng;
let secret_number = rand::thread_rng().gen_range(1..=100);
```

`1..=100` is a **range** from 1 to 100, **including** 100. (`1..100` would stop at 99.)

**Can't remember how a crate works?** Run `cargo doc --open` in your project. It builds docs
for every crate you depend on, matching your exact versions.

---

## 12. Comparing with `match` and `Ordering`

```rust
use std::cmp::Ordering;

match guess.cmp(&secret_number) {
    Ordering::Less    => println!("Too small!"),
    Ordering::Greater => println!("Too big!"),
    Ordering::Equal   => println!("You win!"),
}
```

* `cmp` compares two values and returns an **`Ordering`**: an enum with variants `Less`,
  `Greater`, `Equal`.
* `match` checks the value against each **pattern** (arm) **top to bottom** and runs the
  first one that fits.
* An **arm** = `pattern => code`.

☕ Like a supercharged `switch`. One big difference: **`match` must cover every possible
case**, or it won't compile. Forget `Ordering::Equal` and the compiler tells you. No bug slips in.

---

## 13. Shadowing and converting text to numbers

Input arrives as text. To compare it with a number, convert it:

```rust
let mut guess = String::new();
// ... read_line into guess ...

let guess: u32 = guess.trim().parse().expect("Please type a number!");
```

### Shadowing

The second `let guess` makes a **brand new variable** that **hides** the old one. The old
`guess` was a `String`, the new one is a `u32`. Same name, new type.

This is **shadowing**. It's common when converting a value from one type to another, so you
don't need names like `guess_str` and `guess_num`.

☕ Java won't let you declare the same variable name twice in one scope. Rust will, with `let`.

Shadowing vs `mut`:

| | `let x = ...` again (shadowing) | `mut x` then `x = ...` |
|---|---|---|
| Can change the **type**? | **Yes** | No |
| Makes a new variable? | Yes | No, same one |

### `parse`

* `trim()` removes spaces and the Enter key from both ends.
* `parse()` converts a string to a number. It needs to know **which** number type, so you
  give the variable a type (`let guess: u32`), or write `parse::<u32>()` (the "turbofish").
* `parse()` returns a **`Result`**, because the text might not be a number (`"abc"`).

---

## 14. Loops: `loop`, `break`, `continue`

```rust
loop {
    // runs forever until you break
    if done {
        break;      // leave the loop
    }
    if skip_this_one {
        continue;   // jump back to the top for the next round
    }
}
```

☕ `loop { }` is like `while (true) { }`. `break` and `continue` mean the same as in Java.

(`while` and `for` loops are in Chapter 3.)

---

## 15. Handling bad input without crashing

`expect` crashes the whole program if someone types `abc`. Better: **match** on the
`Result` that `parse` returns.

```rust
let guess: u32 = match guess.trim().parse() {
    Ok(num) => num,       // it's a number: use it
    Err(_) => continue,   // not a number: skip and ask again
};
```

* `Ok(num)` **pulls the value out** of the `Ok` and names it `num`. That value becomes the
  result of the whole `match`, which goes into `guess`.
* `_` means "**I don't care what's in here**." It matches anything.
* `match` is an **expression**: it produces a value. ☕ Unlike a Java `switch` statement,
  you can put it on the right side of `=`.

**Moving from `expect` to `match` is how you go from "crash on error" to "handle the error."**
This pattern shows up everywhere in real Rust code.

---

## 16. The full guessing game

```rust
use std::cmp::Ordering;
use std::io;

fn main() {
    println!("Guess the number!");

    let secret_number = rand::random_range(1..=100);

    loop {
        println!("Please input your guess.");

        let mut guess = String::new();            // NEW String each round

        io::stdin()
            .read_line(&mut guess)
            .expect("Failed to read line");

        let guess: u32 = match guess.trim().parse() {
            Ok(num) => num,
            Err(_) => continue,                   // ignore bad input
        };

        println!("You guessed: {guess}");

        match guess.cmp(&secret_number) {
            Ordering::Less => println!("Too small!"),
            Ordering::Greater => println!("Too big!"),
            Ordering::Equal => {
                println!("You win!");
                break;                            // end the game
            }
        }
    }
}
```

The order matters: read → convert (handle bad input) → compare → loop or stop.

---

## 17. Common mistakes checklist

* [ ] Changing a variable without `mut`
* [ ] Forgot `.trim()` before `.parse()` (the Enter key makes parse fail)
* [ ] Passed `&guess` to `read_line` instead of `&mut guess`
* [ ] Reused the same `String` across loop rounds (`read_line` appends)
* [ ] `parse()` without saying the type (`let x: u32` or `parse::<u32>()`)
* [ ] Using `.unwrap()` / `.expect()` on user input you could handle with `match`
* [ ] A `match` missing a case (the compiler will tell you)
* [ ] Committed the `target/` folder
* [ ] Forgot the `!` in `println!`
* [ ] Used `1..100` when you meant `1..=100`

---

## 18. Self check

Answers below. Try first.

1. What's the difference between `cargo build`, `cargo run`, and `cargo check`?
2. Should `Cargo.lock` be committed? `target/`?
3. `let x = 5; x = 6;` What happens and how do you fix it?
4. Why does `read_line` need `&mut guess` and not just `guess`?
5. What are the two variants of `Result`?
6. What does `.expect("msg")` do if it gets an `Err`?
7. Why `.trim()` before `.parse()`?
8. What is shadowing, and how is it different from `mut`?
9. What does `_` mean in `Err(_)`?
10. Why won't the compiler accept a `match` on `Ordering` with only `Less` and `Greater`?

**Answers:**
1. build = compile; run = compile then run; check = only look for errors (fastest).
2. Commit `Cargo.lock` (for programs). Never commit `target/`.
3. Compile error, `x` is immutable. Write `let mut x = 5;`.
4. `read_line` changes the String, so it needs a mutable reference.
5. `Ok` and `Err`.
6. Crashes (panics) and prints "msg".
7. The input ends with the Enter key characters, and parse fails on them.
8. Shadowing makes a brand new variable with the same name (it can even change type). `mut` changes the same variable and keeps its type.
9. "Match anything, I don't care about the value."
10. `match` must cover every possible case, and `Equal` is missing.

---

## 19. Definitions gallery

| Term | Meaning |
|---|---|
| **rustup** | Tool that installs and updates Rust |
| **rustc** | The Rust compiler |
| **Cargo** | Rust's build tool and package manager |
| **Crate** | A package of Rust code (binary = program, library = reusable code) |
| **crates.io** | The public registry of Rust crates |
| **Cargo.toml** | Project settings and dependency list (TOML format) |
| **Cargo.lock** | Exact versions of every dependency; keeps builds repeatable |
| **Edition** | A version of Rust's language rules (2015, 2018, 2021, 2024) |
| **`target/`** | Build output folder; never commit it |
| **Release build** | Optimized build made with `--release` |
| **Macro** | Code that writes code at compile time; name ends in `!` |
| **Prelude** | Standard items available without `use` |
| **`use`** | Brings an item into scope (like Java `import`) |
| **Immutable** | Can't change. Rust variables are immutable by default. |
| **`mut`** | Makes a variable mutable |
| **Type inference** | The compiler figures out the type for you |
| **Associated function** | A function attached to a type, called with `::` (`String::new()`) |
| **Method** | A function called on a value with `.` (`guess.trim()`) |
| **Reference** | `&x`: access data without taking ownership of it |
| **Mutable reference** | `&mut x`: a reference that can change the data |
| **`Result`** | Enum for operations that can fail: `Ok(value)` or `Err(error)` |
| **Enum** | A type that is one of several named variants |
| **Variant** | One possible value of an enum (`Ok`, `Less`, ...) |
| **Panic** | A Rust crash |
| **`expect` / `unwrap`** | Get the value out of `Ok`, or panic on `Err` |
| **`match`** | Compares a value against patterns; must cover every case |
| **Arm** | One `pattern => code` line in a `match` |
| **Pattern** | The shape `match` checks for (`Ok(num)`, `Ordering::Less`, `_`) |
| **`_`** | Wildcard pattern: matches anything |
| **`Ordering`** | Enum returned by `cmp`: `Less`, `Greater`, `Equal` |
| **Shadowing** | Declaring a new variable with the same name using `let` |
| **`parse`** | Converts a string to another type; returns a `Result` |
| **Turbofish** | `::<Type>` syntax, like `parse::<u32>()` |
| **Range** | `1..=100` (inclusive) or `1..100` (stops at 99) |
| **SemVer** | MAJOR.MINOR.PATCH version numbering |
| **`loop`** | Repeats forever until `break` |

---

## 20. Sources

* The Rust Book, Chapter 1: https://doc.rust-lang.org/book/ch01-00-getting-started.html
* The Rust Book, Chapter 2: https://doc.rust-lang.org/book/ch02-00-guessing-game-tutorial.html
* Rust by Example (short runnable examples): https://doc.rust-lang.org/rust-by-example/
* `std::io::Stdin::read_line`: https://doc.rust-lang.org/std/io/struct.Stdin.html#method.read_line
* `Result`: https://doc.rust-lang.org/std/result/enum.Result.html
* The `rand` crate: https://docs.rs/rand
* Rustlings (small practice exercises): https://github.com/rust-lang/rustlings
