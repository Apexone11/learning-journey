# SQL Week 1 Quick Review

Read this in 5 minutes before you start. Need more detail? Open `SQL Ch 1 Retrieving Data with SELECT.md`.

## The shape of a query

```sql
SELECT   columns            -- what you see
FROM     table              -- where it comes from
WHERE    condition          -- which rows
ORDER BY column [ASC|DESC]  -- sort
FETCH FIRST n ROWS ONLY;    -- 🔶 Oracle row limit
```

**Runs in this order:** FROM → WHERE → SELECT → ORDER BY → FETCH.
That's why an alias **can't** be used in WHERE but **can** be used in ORDER BY.

## WHERE cheat sheet

| Want | Write |
|---|---|
| equal / not equal | `= ` / `<>` |
| any of a list | `dept_id IN (20, 30)` |
| a range, ends included | `salary BETWEEN 50000 AND 70000` |
| starts with A | `last_name LIKE 'A%'` |
| contains "son" | `last_name LIKE '%son%'` |
| 2nd letter is a | `first_name LIKE '_a%'` |
| missing value | `email IS NULL` |
| has a value | `email IS NOT NULL` |
| date | `hire_date >= DATE '2022-01-01'` |

Mixing AND and OR? **Use parentheses.**

## NULL rules

* `= NULL` never works. Use `IS NULL`.
* Any comparison with NULL is UNKNOWN, and WHERE drops those rows.
* `x <> 5` does **not** return rows where x is NULL. Add `OR x IS NULL` if you want them.
* Math with NULL gives NULL.
* Replace NULL: `NVL(x, 0)` 🔶 or `COALESCE(x, 0)` (works everywhere).

## Quotes

* `'single'` = text values: `WHERE dept_name = 'IT'`
* `"double"` = names with spaces: `AS "Full Name"`
* Apostrophe inside text: `'O''Brien'`

## Handy functions

| Function | Does |
|---|---|
| `a || ' ' || b` | join text |
| `UPPER`, `LOWER`, `INITCAP` | change case |
| `LENGTH(s)` | number of characters |
| `SUBSTR(s, start, len)` | piece of text (starts at **1**) |
| `ROUND(n, 2)` | round to 2 decimals |
| `EXTRACT(YEAR FROM d)` | year of a date |
| `SYSDATE` 🔶 | now |
| `CASE WHEN ... THEN ... ELSE ... END` | if/else |

## 🔶 Oracle vs others

| Oracle | Others |
|---|---|
| `FETCH FIRST 3 ROWS ONLY` | `LIMIT 3` |
| `OFFSET 3 ROWS FETCH NEXT 3 ROWS ONLY` | `LIMIT 3 OFFSET 3` |
| `SELECT 2 + 2 FROM dual` | `SELECT 2 + 2` |
| `VARCHAR2` | `VARCHAR` |
| `''` is NULL | `''` is an empty string |
| `FROM employees e` (no AS) | `FROM employees AS e` |
