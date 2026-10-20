# SQL Week 1, Assignment 1: SELECT Basics

**Skills:** `SELECT`, `FROM`, `WHERE`, `ORDER BY`, `DISTINCT`, aliases, `IN`, `BETWEEN`, `LIKE`, `FETCH FIRST`

Run `../setup/company_schema.sql` first. Write each answer under its number in `answers.sql`.
The **expected row count** lets you check yourself.

Questions go from easy to harder.

1. Show every column and every row of the `departments` table. *(6 rows)*
2. Show the first name, last name, and job title of every employee. *(15 rows)*
3. Show each job title **only once**, in alphabetical order. *(11 rows)*
4. Show the first name and salary of everyone in department `20`. *(6 rows)*
5. Show the first name and salary of everyone earning **more than** 90,000, highest salary first. *(6 rows)*
6. Show each employee's full name as **one column** called `Full Name` (first name, a space,
   last name) and their monthly pay (salary divided by 12, rounded to 2 decimals) in a column
   called `Monthly Pay`. *(15 rows)*
7. Show the first name and hire date of everyone hired on or after **January 1, 2022**,
   oldest hire first. Use an Oracle date literal. *(7 rows)*
8. Show the name and job title of every **Sales Representative** or **Security Analyst**.
   Use `IN`, not `OR`. *(4 rows)*
9. Show the last name and salary of everyone earning **from 60,000 to 90,000** (both
   included), sorted by salary, then by last name. Use `BETWEEN`. *(5 rows)*
10. Show everyone whose last name **starts with B**. *(2 rows)*
11. Show the last name and job title of everyone in department `20` who is **not** a Software
    Engineer, sorted by last name. *(4 rows)*
12. Show the first name and salary of the **3 highest paid** employees. Use Oracle's row
    limiting clause, not `LIMIT`. *(3 rows)*

**Bonus thinking question** (answer as a comment in `answers.sql`):
Why does Q6 fail if you try `WHERE "Monthly Pay" > 5000`? Explain using the order SQL runs
each clause.
