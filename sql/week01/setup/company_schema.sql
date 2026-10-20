-- =============================================================
-- Company practice database (Week 1)
-- Written for Oracle. Run this whole file once as a script.
-- The first time you run it, the two DROP TABLE lines will error
-- because the tables don't exist yet. That is normal, ignore it.
-- =============================================================

DROP TABLE employees;
DROP TABLE departments;

CREATE TABLE departments (
    dept_id    NUMBER(4)     PRIMARY KEY,
    dept_name  VARCHAR2(30)  NOT NULL,
    location   VARCHAR2(30)
);

CREATE TABLE employees (
    emp_id          NUMBER(6)     PRIMARY KEY,
    first_name      VARCHAR2(20)  NOT NULL,
    last_name       VARCHAR2(25)  NOT NULL,
    email           VARCHAR2(40)  UNIQUE,
    hire_date       DATE          NOT NULL,
    job_title       VARCHAR2(35)  NOT NULL,
    salary          NUMBER(8,2)   NOT NULL,
    commission_pct  NUMBER(3,2),
    manager_id      NUMBER(6),
    dept_id         NUMBER(4)     REFERENCES departments(dept_id)
);

INSERT INTO departments VALUES (10, 'Executive',   'Baltimore');
INSERT INTO departments VALUES (20, 'IT',          'Baltimore');
INSERT INTO departments VALUES (30, 'Sales',       'Arlington');
INSERT INTO departments VALUES (40, 'Finance',     'Columbia');
INSERT INTO departments VALUES (50, 'Security',    'Hanover');
INSERT INTO departments VALUES (60, 'Research',    NULL);

INSERT INTO employees VALUES (100, 'Grace',   'Mensah',   'gmensah',   DATE '2015-03-01', 'CEO',                    185000, NULL, NULL, 10);
INSERT INTO employees VALUES (101, 'Daniel',  'Okafor',   'dokafor',   DATE '2016-07-15', 'IT Director',            142000, NULL, 100,  20);
INSERT INTO employees VALUES (102, 'Sofia',   'Ramirez',  'sramirez',  DATE '2017-01-09', 'Sales Manager',          118000, 0.10, 100,  30);
INSERT INTO employees VALUES (103, 'Kwame',   'Asante',   'kasante',   DATE '2018-11-20', 'Software Engineer',       98000, NULL, 101,  20);
INSERT INTO employees VALUES (104, 'Emily',   'Chen',     'echen',     DATE '2019-04-02', 'Database Administrator', 104000, NULL, 101,  20);
INSERT INTO employees VALUES (105, 'Marcus',  'Johnson',  'mjohnson',  DATE '2020-06-30', 'Help Desk Technician',    52000, NULL, 101,  20);
INSERT INTO employees VALUES (106, 'Aisha',   'Bello',    'abello',    DATE '2021-02-14', 'Security Analyst',        88000, NULL, 100,  50);
INSERT INTO employees VALUES (107, 'Liam',    'O''Brien', 'lobrien',   DATE '2021-09-01', 'Sales Representative',    61000, 0.15, 102,  30);
INSERT INTO employees VALUES (108, 'Fatima',  'Diallo',   'fdiallo',   DATE '2022-01-10', 'Sales Representative',    58000, 0.20, 102,  30);
INSERT INTO employees VALUES (109, 'Noah',    'Smith',    NULL,        DATE '2022-08-22', 'Accountant',              72000, NULL, 100,  40);
INSERT INTO employees VALUES (110, 'Chloe',   'Nguyen',   'cnguyen',   DATE '2023-03-13', 'Software Engineer',       91000, NULL, 101,  20);
INSERT INTO employees VALUES (111, 'Samuel',  'Adeyemi',  'sadeyemi',  DATE '2023-10-02', 'Security Analyst',        79000, NULL, 106,  50);
INSERT INTO employees VALUES (112, 'Olivia',  'Brown',    'obrown',    DATE '2024-05-20', 'Financial Analyst',       67000, NULL, 109,  40);
INSERT INTO employees VALUES (113, 'Yusuf',   'Kamara',   'ykamara',   DATE '2025-01-06', 'Help Desk Technician',    49000, NULL, 105,  20);
INSERT INTO employees VALUES (114, 'Hannah',  'Lee',      'hlee',      DATE '2025-07-14', 'Intern',                  32000, NULL, 103,  NULL);

COMMIT;
