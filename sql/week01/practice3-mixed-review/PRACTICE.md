# SQL Week 1, Practice 3: Mixed Review

Code only, no written answers. Same tables (`../setup/company_schema.sql`).
Write each query under its number in `answers.sql`. Easy at the top, harder at the bottom.
Expected row counts are there so you can check yourself.

1. Show the ID, first name, and last name of every employee, sorted by last name A to Z. *(15)*
2. Show the names of the departments located in Baltimore. *(2)*
3. Show each department ID that has at least one employee, once each. Don't show NULL. *(5)*
4. Show every column for employees earning **under** 60,000, lowest salary first. *(4)*
5. Show everyone whose job title **contains** the word `Analyst`. *(3)*
6. Show everyone in department 30 **or** 40 who earns **65,000 or more**. *(3)*
7. Show everyone whose manager is employee `101`. *(4)*
8. Show the employee who has **no manager**. *(1)*
9. Show the first name and hire date of everyone hired during **2020, 2021, or 2022**. Use `BETWEEN` with date literals. *(5)*
10. Show everyone whose email contains the letter `e`. *(9)*
11. In department 20, show everyone who is a Software Engineer **or** earns under 55,000. Watch your parentheses. *(4)*
12. Of the people who earn commission, show the **one** who earns the **biggest commission amount** (salary × commission percent). *(1)*
13. Show everyone whose salary is **not** between 50,000 and 100,000. *(6)*
14. Show the first name and hire date of the **5 most recently hired** employees. *(5)*
15. Show everyone who is **not** in department 20. People with **no** department count as "not in 20," so they must show up too. *(9)*

When done: push, then tell Claude "SQL practice 3 pushed."
