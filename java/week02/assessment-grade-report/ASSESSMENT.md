# Java Week 2 Assessment: Grade Report

This is graded. It combines everything from Chapter 12 in one program. Code only.

You're given `students.csv`. Some lines are **bad on purpose**. Your program must read the
file, skip bad lines with a clear message (never crash), and write a clean report.

```
name,score
Aisha,92
Kofi,78
Lena,abc
Marcus,105
Chloe,85
,70
Yusuf,64
```

## Requirements

### 1. `InvalidScoreException.java`
* A **checked** custom exception
* Two constructors: `(String message)` and `(String message, Throwable cause)`

### 2. `Main.java`

**`static int parseScore(String text) throws InvalidScoreException`**
* Trims and parses the text into an `int`
* If parsing fails: catch the `NumberFormatException` and throw
  `InvalidScoreException("Not a number: <text>", e)` (keep the cause!)
* If the number is below 0 or above 100: throw `InvalidScoreException("Out of range: <score>")`
* Otherwise return it

**`static char letter(int score)`**: A 90+, B 80+, C 70+, D 60+, F below

**`main`**
* Open `students.csv` with **try with resources** and a `Scanner`
* Skip the header line. Count line numbers starting with the header as line 1.
* For each line:
  * Split on `,`
  * If it has fewer than 2 parts **or** the name is blank: print `Line <n> skipped: missing name` and go to the next line
  * Otherwise call `parseScore`. On `InvalidScoreException` print `Line <n> skipped: <message>`
  * Valid lines: save `name,score,letter` (for example `Aisha,92,A`) and add to a running total
* Write `report.txt` with **try with resources** and a `PrintWriter`: each saved line, then
  `Average: <average to 2 decimals>`
* Print `<count> students written to report.txt`
* If `students.csv` doesn't exist: print `Could not find students.csv`
* **No matter what**, at the very end print `Processing complete.` (use `finally`)

## Expected console output

```
Line 4 skipped: Not a number: abc
Line 5 skipped: Out of range: 105
Line 7 skipped: missing name
4 students written to report.txt
Processing complete.
```

## Expected `report.txt`

```
Aisha,92,A
Kofi,78,C
Chloe,85,B
Yusuf,64,D
Average: 79.75
```

## Test the missing file case

Rename `students.csv` to something else and run again. You should see only:

```
Could not find students.csv
Processing complete.
```

Rename it back when done.

## How to run

Run from **this folder**, or the CSV won't be found:

```powershell
cd java\week02\assessment-grade-report
javac *.java
java Main
```

## Grading (100)

| Area | Points |
|---|---|
| Custom checked exception with both constructors | 15 |
| `parseScore`: throws correctly, keeps the cause | 20 |
| Reading with try with resources, header skip, line numbers | 15 |
| Bad lines skipped with the exact messages, never crashes | 20 |
| `report.txt` exactly right, written with try with resources | 15 |
| Missing file handled, `finally` always prints | 10 |
| Clean code (formatting, names, no empty catches) | 5 |

When done: push, then tell Claude "Java week 2 assessment pushed."
