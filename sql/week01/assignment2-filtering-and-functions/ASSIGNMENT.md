# SQL Week 1, Assignment 2: NULLs, Functions and Oracle Specifics

**Skills:** `IS NULL`, `NVL`, `||`, string functions, `EXTRACT`, `CASE`, `DUAL`, `OFFSET`, NULL logic

Same tables as Assignment 1. Write each answer under its number in `answers.sql`.
These are harder. Read sections 7 to 11 of the chapter file first.

1. Show the first and last name of every employee who has **no email**. *(1 row)*
2. Show the first name, salary, commission percent, and the commission **amount**
   (salary times commission percent) for everyone who **earns** a commission.
   Call the last column `Commission`. *(3 rows)*
3. Show every employee's first name and commission percent, but show **0** instead of
   blank when they have no commission. *(15 rows)*
4. Show every department's name and location. If the location is missing, show the word
   **Remote**. *(6 rows)*
5. Show the first and last name of every employee who is **not assigned to a department**. *(1 row)*
6. For every employee who has an email, show their last name in **ALL CAPS** and a column
   called `Work Email` that looks like `gmensah@company.com`. *(14 rows)*
7. Show each employee's **initials** (like `GM`) in a column called `Initials`, and the
   **number of letters** in their first name plus last name combined (no space) in a column
   called `Name Length`. Sort by `Name Length`, longest first. *(15 rows)*
8. Show the first name and **hire year only** (like `2019`) for everyone hired **before 2020**. *(5 rows)*
9. Show each employee's first name, salary, and a column called `Band`:
   * `Senior` if salary is 100,000 or more
   * `Mid` if salary is 70,000 or more
   * `Junior` for everyone else

   *(15 rows)*
10. Without using any table of ours, show **today's date** and what **15% of 88,000** is,
    in one query. *(1 row)*
11. Show the first name and salary of the employees ranked **4th, 5th and 6th** by salary
    (highest first). This is "page 2" of a list showing 3 per page. *(3 rows)*
12. **The NULL trap.** Show everyone whose commission percent is **not 0.10**, and that must
    **include** people with no commission at all. *(14 rows)*

    Then, as a comment, explain: if you write only `WHERE commission_pct <> 0.10`, you get
    just 2 rows. Why?

**Bonus thinking question** (answer as a comment): In Oracle, what is the difference between
`''` (empty string) and `NULL`? Why does that surprise people coming from other databases?
