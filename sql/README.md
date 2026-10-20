# SQL and Oracle Database

One folder per week (`week01`, `week02`, ...). Each week has:

* a chapter file (`SQL Ch X Topic.md`) that teaches the concepts
* a `setup` folder with the script that builds the practice tables
* two assignment folders, each with `ASSIGNMENT.md` (the questions) and `answers.sql` (where you write your queries)

Questions go from easy to hard inside each week, and each week builds on the last.

## Where to run your SQL (free, nothing to install)

**Oracle FreeSQL: https://freesql.com**

1. Open it in your browser. Make a free Oracle account so your tables and scripts are saved.
2. Open the SQL worksheet.
3. Paste the whole `setup/company_schema.sql` file and run it as a **script**
   (the "Run Script" button, or F5). The first time, the two `DROP TABLE` lines will
   show errors because the tables don't exist yet. That is normal.
4. Now run your queries one at a time (select one query, then "Run Statement" or Ctrl+Enter).

Can't run something? That's fine. Write your best answer in `answers.sql` and Claude will check it.

Later on we will run a real Oracle Database in Docker on your homelab.

## Workflow

1. Read the chapter file.
2. Run the setup script.
3. Do assignment 1, then assignment 2. Write every answer in that folder's `answers.sql`.
4. Push, then tell Claude "SQL week 1 pushed" for grading.
