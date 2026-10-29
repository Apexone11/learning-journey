# Java Week 1 Review

Both assignments compile and run. Fix the items marked **Fix**, push again, and tell Claude "Java week 1 fixes pushed."

---

## Assignment 1: People. Grade: **88 / 100**

### What you did well

* Clean inheritance: both subclasses call `super(name, age)` first.
* `@Override` on every overridden method.
* Fields are `private final`. That wasn't required, and it's a good habit: the values can't change after the object is
  built.
* `describe()` in the subclasses calls `super.describe()` and adds to it instead of repeating code. That's exactly what
  Assignment 2 teaches, and you did it on your own.
* `equals` and `hashCode` use the same fields. Correct.
* The polymorphic loop works: one `ArrayList<Person>`, four different sentences.

### Fix (points lost)

1. **Your "not equal" test isn't actually testing anything (−6).**
   `person3` and `person6` are both `Employee("Charlie", 30, ...)`, so `equals` correctly returns **true**, but your
   comment says `// should be false`. Your output prints `person3 equals person6: true`, and nothing caught it.
   Make a pair that really differs (same name, different age, for example) and check the output matches your comment.
   **Always read your own output against what you expected.** That habit catches most bugs.

2. **`getClass()` vs `instanceof` in `equals` (−4).**
   You used `getClass() != o.getClass()`. That means `new Student("Ana", 20, ...)` and `new Employee("Ana", 20, ...)`
   are **not** equal, even though the task said "two people with the same name and age are equal."
   Both styles are real and used in industry:

* `getClass()` = only equal if they are the **exact same class**
* `instanceof Person p` = equal if they are both **some kind of Person**

The task asked for the second. Try it with a Student and an Employee who share a name and age, and see the
difference. Then switch to `instanceof`.

3. **Indentation in `Main.java` (−2).** Lines 19 to 29 aren't indented to match the code around them, so the `}` at the
   end looks like it closes the wrong thing. In VS Code, press **Shift + Alt + F** to auto format the file. Also, the
   comment on line 11 says "Test equals () method," but that block builds the list.

### Nice to know (no points lost)

* Salaries print as `$80000.0`. For money, use `String.format("$%,.2f", salary)` to get `$80,000.00`.
* `Person.toString()` has odd spacing: `name= ` and `age =`. Small, but consistent output looks professional.

---

## Assignment 2: Pet Clinic. Grade: **62 / 100**

### What you did well

* `protected` fields, `private static` counter, and `getPetCount()` all correct. The count prints 4.
* Both subclasses use `super.visitCost()` instead of hard coding 50. That's the main lesson, and you got it.
* Overloaded `feed()` / `feed(int)` correct.
* `final tag()` with a comment explaining what `final` does. Good.
* Pattern matching `p instanceof Dog d` used correctly; only dogs fetch.
* Plain `Pet[]` array as asked.

### Fix (points lost)

1. **The visit prices don't follow the spec (−10).** Read the spec again carefully:

   | | Spec | Your code |
            |---|---|---|
   | Dog | 50 **+ 20 always**, + 15 more if grooming | 50, + 30 if grooming |
   | Cat | 50, + **10** if outdoor | 50, + 20 if outdoor |

   That's why your total is 250 instead of the expected 265. In a real job, the spec is the contract: if the client says
   outdoor cats cost $10 extra, $20 is a bug, even if the code runs fine.

2. **`totalVisitCost` is an `int` (−10). This one is important.**
   ```java
   int totalVisitCost = 0;
   totalVisitCost += p.visitCost();   // visitCost() returns a double!
   ```
   Normally Java refuses to put a `double` into an `int`. But `+=` **secretly casts** the result to `int`, which **chops
   off the decimals with no warning**. If a visit cost $85.50, your total would silently lose the 50 cents. Bugs like
   this cause real money errors in production.
   Make the total a `double`. (Try `totalVisitCost = totalVisitCost + p.visitCost();` with the `int` and see the
   compiler refuse. That shows you what `+=` was hiding.)

3. **`ANSWERS.md` is empty (−12).** The 5 questions are where you show you understand *why*, not just *how*. Answer them
   in your own words.

4. **"Break it on purpose" steps (−4).** Overriding `tag()` in `Dog` and removing `super(...)` from `Cat` are how you
   get the compiler errors you need for questions 1 and 2. Do them, write down the errors, then undo.

### Nice to know (no points lost)

* `tag()` should return `name.toUpperCase() + "-" + ageYears` (like `BUDDY-3`). Yours returns a different format. Small
  spec miss.
* You call `p.visitCost()` twice per loop. Store it once: `double cost = p.visitCost();` then use `cost`.
* Your constructors have no `public` (package private). They work here because everything is in the same folder, but if
  `Main` were in another package it couldn't create pets. Make them `public`.

---

## Overall

**Week 1 average: 75 / 100.** Your Java fundamentals are clearly coming back. Inheritance, `super`, overriding, and
polymorphism all work. The points you lost are mostly about **precision**: following the spec exactly, testing that your
output matches what you expected, and watching types. Those are exactly the habits that separate junior from senior
developers, so they're worth the attention.

**Your fix list:**

- [ ] A1: real "not equal" test
- [ ] A1: switch `equals` to `instanceof Person p`
- [ ] A1: format `Main.java` (Shift + Alt + F)
- [ ] A2: prices match the spec (total should be 265.0 with the spec's pets)
- [ ] A2: `totalVisitCost` as `double`
- [ ] A2: do the two break it steps
- [ ] A2: answer all 5 questions in `ANSWERS.md`

---

## Assignment 2 Regrade (Oct 19). New grade: **84 / 100** (was 62)

### Fixed ✓

* `totalVisitCost` is now a `double`. (+10)
* `ANSWERS.md` mostly filled in, errors recorded. (+12 of a possible 16)

### Still open

1. **Prices still don't match the spec (−10).** Dog should be `+20` always and `+15` more for grooming (you have `+30`
   for grooming only). Outdoor cat should be `+10` (you have `+20`). Your total prints 250.0; with your same four pets
   it should print **265.0**.
2. **Answer 5 is empty.** Hint: the loop variable is a `Pet`, but `Woof!` prints. Look at section 12 of your chapter
   file.

### Feedback on your answers

1. **Mostly right.** Correct error. More precise: `final` on a method means **subclasses can't override it**. (The
   method still exists and works, it just can't be replaced.) *Why it exists:* to lock in behavior that must be
   identical for every subclass, like how an ID or tag is formatted.
2. **Error right, reason needs fixing.** It isn't about inheriting attributes. Here's what really happens: when you
   leave out `super(...)`, Java silently adds `super();` with **no arguments**. But `Pet`'s only constructor needs
   `(String, int)`, so there is no matching constructor. That's what "found: no arguments" means. Java forces a parent
   constructor call because **the parent part of every object must be built first**.
3. **Overriding is right. Overloading needs one correction.** In your code it's **methods**, not constructors: `feed()`
   and `feed(int grams)` have the same name but **different parameters**, in the same class. (Constructors *can* be
   overloaded too, but that's not what happened here.) *How to tell them apart:* same parameters = overriding, different
   parameters = overloading.
4. **Close.** `name` is `protected`, not public. `protected` means subclasses can use it directly. If it were `private`,
   **even `Dog` couldn't touch it**, not just other files. `Dog` would have to call a getter like `getName()`.
5. Missing.

---

## Practice 3: Shapes. Grade: **98 / 100**

Output matches **exactly**. Clean and correct:

* `Rectangle`'s public constructor chains to the protected one with `this(...)`. Exactly right.
* `Square` has **zero** extra code and still works. That's inheritance doing its job.
* One loop handles describe, total, largest, and the `instanceof` count.

**Small stuff (−2):** delete the leftover `// TODO` comments now that they're done. In a real
code review, stale TODOs make reviewers think work is unfinished. Also, `area()` is called up to
three times per shape in the loop; store it once in a variable.

## Practice 4: Bank Accounts. Grade: **97 / 100**

Output matches **exactly**. This one had real design decisions and you got them all right:

* `SavingsAccount.withdraw` **adds a rule then calls `super.withdraw`**. Correct reuse.
* `CheckingAccount.withdraw` **replaces** the rule completely, because the parent's
  "no negative balance" rule would block overdrafts. You understood why `super` couldn't be used.
* `monthEnd` uses `deposit(...)` for interest, so it goes through the same validation.
* Static account number generator works.

**Small stuff (−3):** `Account.toString()` is missing `@Override`. Also `$-225.00` reads oddly;
real banking apps show `-$225.00`. (Bonus challenge: make it print that way.)

## Week 1 Summary

| Work                     | Grade                              |
|--------------------------|------------------------------------|
| Assignment 1: People     | 88                                 |
| Assignment 2: Pet Clinic | 84 (fix prices + answer 5 for 100) |
| Practice 3: Shapes       | 98                                 |
| Practice 4: Bank         | 97                                 |

Big jump from the assignments to the practices. Following the spec exactly and matching the
expected output: that's the habit that matters most, and you've got it now.
