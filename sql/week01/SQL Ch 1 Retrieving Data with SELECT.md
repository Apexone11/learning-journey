# SQL Chapter 1: Databases, Oracle, and Retrieving Data with SELECT

Your refresher for Week 1. Every example uses the practice tables from
`setup/company_schema.sql`, so you can paste them straight into FreeSQL and watch them work.

**Contents**

1. Databases, tables, rows, columns
2. SQL vs Oracle Database
3. The five kinds of SQL statements
4. Oracle data types vs standard SQL
5. Our practice tables
6. The SELECT statement
7. Filtering with WHERE
8. NULL: the value that isn't there
9. Sorting with ORDER BY
10. Expressions, aliases and concatenation
11. Single row functions
12. CASE: if/else inside a query
13. DUAL and limiting rows
14. The order SQL actually runs
15. Style rules
16. Common mistakes checklist
17. Self check
18. Definitions gallery
19. Sources

---

## 1. Databases, tables, rows, columns

A **database** is an organized collection of data. A **relational database** stores data in
**tables** that can be linked together.

| Term | Means | Example |
|---|---|---|
| **Table** | One kind of thing | `employees` |
| **Row** (record) | One item of that thing | Grace Mensah |
| **Column** (field) | One fact about every item | `salary` |
| **Primary key** | Column that uniquely identifies each row | `emp_id` |
| **Foreign key** | Column that points to a primary key in another table | `employees.dept_id` → `departments.dept_id` |

A **DBMS** (Database Management System) is the software that stores and runs the database.
An **RDBMS** is a relational one. Oracle, PostgreSQL, MySQL, SQL Server, and SQLite are all RDBMSs.

---

## 2. SQL vs Oracle Database

* **SQL** (Structured Query Language) is the **language** you use to talk to a relational
  database. There is an official standard (ANSI/ISO SQL).
* **Oracle Database** is a **product**, one of the most widely used RDBMSs in big companies,
  banks, and government.
* Every product speaks SQL, but each adds its own extras and quirks, called its **dialect**.
  Oracle's is often called **Oracle SQL** (and its programming add on is **PL/SQL**).

About 90% of what you learn works everywhere. This file points out the Oracle differences with
an **🔶 Oracle** marker so you know which is which. Your software project supports both, so
you need to know both.

---

## 3. The five kinds of SQL statements

| Group | Stands for | Statements | Does what |
|---|---|---|---|
| **DQL** | Data Query Language | `SELECT` | Reads data |
| **DML** | Data Manipulation Language | `INSERT`, `UPDATE`, `DELETE`, `MERGE` | Changes rows |
| **DDL** | Data Definition Language | `CREATE`, `ALTER`, `DROP`, `TRUNCATE` | Changes structure |
| **DCL** | Data Control Language | `GRANT`, `REVOKE` | Permissions |
| **TCL** | Transaction Control Language | `COMMIT`, `ROLLBACK`, `SAVEPOINT` | Saves or undoes changes |

Week 1 is all **DQL** (reading). We'll build and change tables in later weeks.

🔶 **Oracle:** DML changes aren't permanent until you `COMMIT`. DDL statements commit automatically.

---

## 4. Oracle data types vs standard SQL

| Kind of data | 🔶 Oracle | Standard / other databases |
|---|---|---|
| Text, variable length | `VARCHAR2(n)` | `VARCHAR(n)` |
| Text, fixed length | `CHAR(n)` | `CHAR(n)` |
| Whole number | `NUMBER(p)` | `INTEGER`, `INT`, `BIGINT` |
| Decimal | `NUMBER(p,s)` | `DECIMAL(p,s)`, `NUMERIC(p,s)` |
| Date and time | `DATE` (includes time!) | `DATE` (date only), `TIMESTAMP` |
| Precise time | `TIMESTAMP` | `TIMESTAMP` |
| Big text | `CLOB` | `TEXT` |
| True/false | `BOOLEAN` (new in 23ai) | `BOOLEAN` |

`NUMBER(8,2)` means 8 digits total, 2 after the decimal point (max 999999.99).

🔶 **Oracle:** always use `VARCHAR2`, not `VARCHAR`. Oracle's `DATE` stores a **time** too
(it's just midnight if you don't set one).

---

## 5. Our practice tables

```
departments                     employees
-----------                     ---------
dept_id   (PK)  <-------+       emp_id          (PK)
dept_name               |       first_name
location                |       last_name
                        |       email
                        |       hire_date
                        |       job_title
                        |       salary
                        |       commission_pct  (often NULL)
                        |       manager_id      (points to another emp_id)
                        +-----  dept_id         (FK)
```

Some data is missing **on purpose** (an employee with no email, one with no department, a
department with no location) so you can practice handling NULLs.

---

## 6. The SELECT statement

```sql
SELECT column1, column2      -- which columns
FROM   table_name            -- which table
WHERE  condition             -- which rows (optional)
ORDER BY column1;            -- what order (optional)
```

### All columns

```sql
SELECT * FROM departments;
```

`*` means "every column." Fine for exploring, but in real code list the columns you need.

### Specific columns

```sql
SELECT first_name, last_name, salary
FROM   employees;
```

### Remove duplicates with DISTINCT

```sql
SELECT DISTINCT dept_id
FROM   employees;
```

`DISTINCT` applies to the **whole row** you select. `SELECT DISTINCT dept_id, job_title`
removes rows where **both** values repeat.

### Rules

* SQL **keywords aren't case sensitive** (`select` = `SELECT`). Write them in CAPS so they stand out.
* **Text values are case sensitive**: `'IT'` is not `'it'`.
* Text goes in **single quotes**: `'Sales'`. Double quotes mean something else (section 10).
* End each statement with a **semicolon** `;`.
* A single quote inside text is written twice: `'O''Brien'`.

---

## 7. Filtering with WHERE

`WHERE` keeps only the rows where the condition is **true**.

```sql
SELECT first_name, salary
FROM   employees
WHERE  dept_id = 30;
```

### Comparison operators

| Operator | Means |
|---|---|
| `=` | equal (just one `=`, not `==`) |
| `<>` or `!=` | not equal |
| `>`, `<`, `>=`, `<=` | greater, less, and so on |

### Combine conditions: AND, OR, NOT

```sql
SELECT first_name, job_title, salary
FROM   employees
WHERE  dept_id = 20
AND    salary > 60000;
```

**Precedence:** `NOT` runs first, then `AND`, then `OR`. Use **parentheses** whenever you mix
`AND` and `OR`:

```sql
-- IT people OR Sales people, but only the ones over 60k
WHERE (dept_id = 20 OR dept_id = 30)
AND   salary > 60000
```

Without the parentheses, it means "all IT people, plus Sales people over 60k." Very different.

### IN: matches any value in a list

```sql
WHERE dept_id IN (20, 30, 50)
-- same as: dept_id = 20 OR dept_id = 30 OR dept_id = 50
```

`NOT IN (...)` excludes them.

### BETWEEN: a range, both ends included

```sql
WHERE salary BETWEEN 50000 AND 70000
-- same as: salary >= 50000 AND salary <= 70000
```

Low value first. `BETWEEN 70000 AND 50000` returns nothing.

### LIKE: pattern matching on text

| Wildcard | Matches |
|---|---|
| `%` | any number of characters (even zero) |
| `_` | exactly one character |

```sql
WHERE last_name LIKE 'A%'       -- starts with A
WHERE last_name LIKE '%son'     -- ends with son
WHERE email LIKE '%o%'          -- contains o
WHERE first_name LIKE '_a%'     -- second letter is a
```

### Dates

🔶 **Oracle date literal:** `DATE 'YYYY-MM-DD'`

```sql
WHERE hire_date >= DATE '2021-01-01'
```

This is clear and works the same on every Oracle setup. Writing `'01-JAN-21'` depends on
server settings, so avoid it.

---

## 8. NULL: the value that isn't there

**NULL** means **unknown or missing**. It is **not** zero and **not** an empty string.

### You can't use = with NULL

```sql
WHERE email = NULL       -- WRONG: returns nothing, ever
WHERE email IS NULL      -- correct
WHERE email IS NOT NULL  -- correct
```

### Why? Three valued logic

Any comparison with NULL gives **UNKNOWN**, not true or false. `WHERE` only keeps rows that
are **TRUE**, so UNKNOWN rows silently disappear.

| Expression | Result |
|---|---|
| `5 = 5` | TRUE |
| `5 = 6` | FALSE |
| `NULL = 5` | UNKNOWN |
| `NULL = NULL` | UNKNOWN (even this!) |
| `NULL <> 5` | UNKNOWN |

This is the #1 source of "missing rows" bugs. If a column can be NULL and you filter with
`<>`, `NOT IN`, or `>`, ask yourself: **what happens to the NULL rows?**

### Math with NULL gives NULL

```sql
SELECT salary * commission_pct FROM employees;
-- NULL for everyone with no commission
```

### Replace NULL with a value

| Function | Works in | Example |
|---|---|---|
| `NVL(x, replacement)` | 🔶 Oracle only | `NVL(commission_pct, 0)` |
| `COALESCE(x, y, z, ...)` | Everywhere (standard) | `COALESCE(location, 'Unknown')` |
| `NVL2(x, if_not_null, if_null)` | 🔶 Oracle only | `NVL2(email, 'Has email', 'No email')` |

`COALESCE` returns the **first non NULL** value in its list. Prefer it when you want code
that works on any database.

🔶 **Oracle quirk:** Oracle treats an **empty string `''` as NULL**. In most other databases
they are different. Keep that in mind for the bonus question.

---

## 9. Sorting with ORDER BY

```sql
SELECT first_name, salary
FROM   employees
ORDER BY salary DESC;
```

* `ASC` = smallest first (the default). `DESC` = largest first.
* Sort by several columns: the second one breaks ties in the first.

```sql
ORDER BY dept_id ASC, salary DESC
-- group by department, highest paid first inside each one
```

* You can sort by a **column alias** or a column you didn't select.
* Text sorts alphabetically, dates sort oldest to newest.

### Where do NULLs go?

🔶 **Oracle:** NULLs count as the **largest** value, so they go **last** in `ASC` and
**first** in `DESC`. Control it yourself:

```sql
ORDER BY commission_pct DESC NULLS LAST
```

Without an `ORDER BY`, **the order of rows is never guaranteed**, even if it looks sorted.

---

## 10. Expressions, aliases and concatenation

### Math in SELECT

```sql
SELECT first_name, salary, salary * 1.05
FROM   employees;
```

The table isn't changed. The math only affects what you see.

### Column aliases

```sql
SELECT first_name AS name,
       salary * 1.05 AS new_salary,
       salary / 12 AS "Monthly Pay"
FROM   employees;
```

* `AS` is optional (`salary * 1.05 new_salary` also works), but write it. It is clearer.
* **Double quotes** are needed when the alias has **spaces**, special characters, or you
  want **exact capitalization**. Without them, Oracle shows the alias in CAPS.
* **Single quotes are for text values. Double quotes are for names.** Mixing these up is a
  very common error.

### Table aliases

```sql
SELECT e.first_name, e.salary
FROM   employees e;
```

Saves typing now, and becomes essential when we join tables. 🔶 **Oracle:** no `AS` before a
table alias (`FROM employees AS e` is an error in Oracle).

### Concatenation (joining text)

🔶 **Oracle and standard SQL:** `||`

```sql
SELECT first_name || ' ' || last_name AS "Full Name"
FROM   employees;
```

`CONCAT(a, b)` also exists, but in Oracle it only takes **two** arguments, so `||` is easier.
(SQL Server uses `+` instead. Another dialect difference.)

---

## 11. Single row functions

These run once for **each row** and return one value per row.

### Text functions

| Function | Example | Result |
|---|---|---|
| `UPPER(s)` | `UPPER('chen')` | `CHEN` |
| `LOWER(s)` | `LOWER('Chen')` | `chen` |
| `INITCAP(s)` 🔶 | `INITCAP('emily chen')` | `Emily Chen` |
| `LENGTH(s)` | `LENGTH('Chen')` | `4` |
| `SUBSTR(s, start, len)` | `SUBSTR('Baltimore', 1, 4)` | `Balt` |
| `INSTR(s, find)` 🔶 | `INSTR('Baltimore', 'more')` | `6` |
| `TRIM(s)` | `TRIM('  hi  ')` | `hi` |
| `REPLACE(s, old, new)` | `REPLACE('IT Dept', 'Dept', 'Team')` | `IT Team` |
| `LPAD(s, n, c)` / `RPAD` | `LPAD('7', 3, '0')` | `007` |

**Positions start at 1, not 0** (unlike Java). `SUBSTR(s, 1, 1)` is the first letter.

Case insensitive search trick:

```sql
WHERE UPPER(last_name) = 'CHEN'
```

### Number functions

| Function | Example | Result |
|---|---|---|
| `ROUND(n, d)` | `ROUND(1234.567, 2)` | `1234.57` |
| `ROUND(n, -d)` | `ROUND(1234.567, -2)` | `1200` |
| `TRUNC(n, d)` | `TRUNC(1234.567, 1)` | `1234.5` (chops, no rounding) |
| `MOD(a, b)` | `MOD(10, 3)` | `1` (remainder) |
| `ABS(n)` | `ABS(-5)` | `5` |
| `CEIL(n)` / `FLOOR(n)` | `CEIL(4.2)` / `FLOOR(4.8)` | `5` / `4` |

### Date functions (🔶 Oracle)

| Function | Gives |
|---|---|
| `SYSDATE` | current date and time (no parentheses) |
| `CURRENT_DATE` | current date and time in your session's time zone (standard) |
| `EXTRACT(YEAR FROM d)` | the year as a number (also `MONTH`, `DAY`). Standard SQL. |
| `ADD_MONTHS(d, n)` | date plus n months |
| `MONTHS_BETWEEN(d1, d2)` | months between two dates |
| `TRUNC(d)` | removes the time part |
| `TO_CHAR(d, 'YYYY-MM-DD')` | date to formatted text |
| `TO_DATE('2024-05-20', 'YYYY-MM-DD')` | text to date |
| `d + 7` | 7 days later (Oracle date math is in days) |

```sql
SELECT first_name,
       EXTRACT(YEAR FROM hire_date)                       AS hire_year,
       TRUNC(MONTHS_BETWEEN(SYSDATE, hire_date) / 12)     AS years_here
FROM   employees;
```

---

## 12. CASE: if/else inside a query

```sql
SELECT first_name,
       dept_id,
       CASE
           WHEN dept_id = 20 THEN 'Tech'
           WHEN dept_id IN (30, 40) THEN 'Business'
           ELSE 'Other'
       END AS area
FROM   employees;
```

* Checks each `WHEN` **top to bottom** and uses the **first** one that's true.
* So put the **most specific** conditions first.
* No `ELSE` and nothing matches? You get NULL.
* Always ends with `END`.

Short form when you are comparing one column to exact values:

```sql
CASE dept_id
    WHEN 20 THEN 'Tech'
    WHEN 30 THEN 'Sales'
    ELSE 'Other'
END
```

🔶 Oracle also has `DECODE(dept_id, 20, 'Tech', 30, 'Sales', 'Other')`. You'll see it in old
code, but `CASE` is standard and easier to read.

---

## 13. DUAL and limiting rows

### DUAL

🔶 **Oracle:** every `SELECT` needs a `FROM`. When you just want to calculate something,
use the built in one row table **`DUAL`**:

```sql
SELECT SYSDATE FROM dual;
SELECT 250 * 1.06 AS with_tax FROM dual;
SELECT UPPER('hello') FROM dual;
```

(Other databases let you write `SELECT 250 * 1.06;` without a `FROM`. Oracle 23ai allows that
too now, but `FROM dual` works on every version, so use it.)

### Getting only the first N rows

| Database | Syntax |
|---|---|
| 🔶 **Oracle 12c+** and standard SQL | `FETCH FIRST 5 ROWS ONLY` |
| MySQL, PostgreSQL, SQLite | `LIMIT 5` |
| SQL Server | `SELECT TOP 5 ...` |
| 🔶 Old Oracle | `WHERE ROWNUM <= 5` (tricky with ORDER BY, avoid) |

```sql
-- the 5 most recent hires
SELECT first_name, hire_date
FROM   employees
ORDER BY hire_date DESC
FETCH FIRST 5 ROWS ONLY;
```

Always use `ORDER BY` with it, or "first 5" means "any 5."

### Skipping rows (paging)

```sql
-- rows 11 to 20
ORDER BY last_name
OFFSET 10 ROWS FETCH NEXT 10 ROWS ONLY;
```

`OFFSET` = how many to **skip**. Page `p` with `n` per page skips `(p - 1) * n` rows.

---

## 14. The order SQL actually runs

You **write** a query in this order:

```
SELECT → FROM → WHERE → ORDER BY → FETCH
```

But the database **runs** it in this order:

```
1. FROM        pick the table
2. WHERE       keep matching rows
3. SELECT      compute columns and aliases
4. DISTINCT    remove duplicates
5. ORDER BY    sort
6. OFFSET/FETCH  cut down to N rows
```

(`GROUP BY` and `HAVING` slot in after WHERE. That's a later week.)

**Why it matters:** an alias is **created in step 3**, so:

* `WHERE` (step 2) **can't** see a column alias. Repeat the expression instead.
* `ORDER BY` (step 5) **can** use it.

```sql
-- ERROR: invalid identifier
SELECT salary * 12 AS yearly FROM employees WHERE yearly > 100000;

-- Works
SELECT salary * 12 AS yearly FROM employees WHERE salary * 12 > 100000;

-- Works
SELECT salary * 12 AS yearly FROM employees ORDER BY yearly;
```

---

## 15. Style rules

```sql
SELECT e.first_name,
       e.last_name,
       e.salary
FROM   employees e
WHERE  e.dept_id = 20
AND    e.salary > 60000
ORDER BY e.salary DESC;
```

* Keywords in CAPS, names in lowercase.
* One column per line once there are more than two or three.
* Line up the clauses.
* Comments: `-- one line` or `/* several lines */`.

---

## 16. Common mistakes checklist

* [ ] `= NULL` instead of `IS NULL`
* [ ] Forgot NULL rows disappear with `<>`, `NOT IN`, `>`
* [ ] Double quotes around a text value (`WHERE dept_name = "IT"` is wrong)
* [ ] Single quotes around an alias with spaces (`AS 'Full Name'` is wrong in Oracle)
* [ ] Used a column alias in `WHERE`
* [ ] Mixed `AND` and `OR` without parentheses
* [ ] `BETWEEN high AND low` (backwards)
* [ ] Used `LIMIT` in Oracle
* [ ] `FETCH FIRST` without `ORDER BY`
* [ ] Wrong text case: `'it'` when the data says `'IT'`
* [ ] Forgot `FROM dual` for a calculation in Oracle
* [ ] `AS` before a table alias in Oracle

---

## 17. Self check

Answer in your head. Answers below.

1. What's the difference between SQL and Oracle?
2. Which group is `SELECT` in? Which is `CREATE TABLE`?
3. What does `WHERE commission_pct = NULL` return, and why?
4. Can you use a column alias in `WHERE`? In `ORDER BY`?
5. Single quotes vs double quotes: what is each for?
6. What does `NVL(location, 'Remote')` do? What is the standard version?
7. What does `LIKE '_e%'` match?
8. What is `DUAL`?
9. Oracle version of `LIMIT 10`?
10. In an `ASC` sort in Oracle, where do NULLs end up?

**Answers:**
1. SQL is the language, Oracle is a database product that speaks its own dialect of it.
2. DQL. DDL.
3. Zero rows. Comparing anything to NULL gives UNKNOWN, and WHERE only keeps TRUE.
4. No. Yes.
5. Single = text values. Double = names (aliases or identifiers).
6. Shows 'Remote' when location is NULL. `COALESCE(location, 'Remote')`.
7. Text whose second character is `e`.
8. Oracle's built in one row table for selecting values that don't come from a table.
9. `FETCH FIRST 10 ROWS ONLY`.
10. Last.

---

## 18. Definitions gallery

| Term | Meaning |
|---|---|
| **Database** | Organized collection of data |
| **RDBMS** | Software that manages relational databases (Oracle, PostgreSQL, ...) |
| **SQL** | Standard language for relational databases |
| **Dialect** | One product's version of SQL with its own extras |
| **PL/SQL** | Oracle's programming language built on top of SQL |
| **Table** | Set of rows about one kind of thing |
| **Row / record** | One item in a table |
| **Column / field** | One attribute stored for every row |
| **Primary key (PK)** | Column(s) that uniquely identify each row; can't be NULL |
| **Foreign key (FK)** | Column that refers to a primary key in another table |
| **Schema** | A user's collection of tables and other objects |
| **Query** | A statement that reads data (`SELECT`) |
| **Result set** | The rows a query returns |
| **DQL / DML / DDL / DCL / TCL** | The five groups of SQL statements |
| **NULL** | Missing or unknown value |
| **Three valued logic** | TRUE, FALSE, UNKNOWN; any comparison with NULL is UNKNOWN |
| **Predicate** | A condition that's true, false, or unknown (`salary > 50000`) |
| **Wildcard** | `%` (any characters) and `_` (one character) in `LIKE` |
| **Alias** | A temporary name for a column or table in a query |
| **Concatenation** | Joining text together (`||`) |
| **Literal** | A fixed value written in the query (`'Sales'`, `42`, `DATE '2024-01-01'`) |
| **Single row function** | Function that returns one value per row (`UPPER`, `ROUND`) |
| **`NVL` / `COALESCE`** | Replace NULL with another value |
| **`CASE`** | If/else logic inside a query |
| **`DUAL`** | Oracle's one row dummy table |
| **`SYSDATE`** | Oracle's current date and time |
| **Row limiting clause** | `FETCH FIRST n ROWS ONLY`, with optional `OFFSET` |
| **Logical query order** | The order clauses actually run: FROM, WHERE, SELECT, ORDER BY |
| **Commit** | Makes changes permanent |

---

## 19. Sources

* Oracle SQL Language Reference (23ai): https://docs.oracle.com/en/database/oracle/oracle-database/23/sqlrf/
* SELECT statement: https://docs.oracle.com/en/database/oracle/oracle-database/23/sqlrf/SELECT.html
* Oracle data types: https://docs.oracle.com/en/database/oracle/oracle-database/23/sqlrf/Data-Types.html
* Oracle single row functions: https://docs.oracle.com/en/database/oracle/oracle-database/23/sqlrf/Single-Row-Functions.html
* Oracle FreeSQL (run SQL in your browser): https://freesql.com
* Oracle Dev Gym free SQL classes: https://devgym.oracle.com
* Standard SQL (works everywhere) refresher: https://www.sqltutorial.org
