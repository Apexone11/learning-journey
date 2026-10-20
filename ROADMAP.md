# My Roadmap

The one plan I follow. If any teacher (Claude, Gemini, a book) suggests a change, it goes here first.

## Big goals

1. **First or second job at $100,000+**
2. **Long term: $400,000 a year** as a deeply technical architect (data, cloud, and security)
3. **Certifications:** Security+, Network+, AWS Solutions Architect Associate, then A+ and beyond
4. **Programming:** Java back to full strength, Rust and TypeScript to the same level
5. **Data and automation:** strong SQL, PostgreSQL performance tuning, scripting, infrastructure as code
6. **Build my professional software project** and a GitHub portfolio that proves all of the above

## The path to the money (honest version)

* **$100k first or second job:** in the Baltimore and DC area, the fastest route is cleared or DoD contractor work. Security+ is the baseline cert those jobs require. Add cloud (AWS) and real SQL skills on top and I stand out.
* **$400k later:** the most common route is **Staff or Principal Engineer / Architect** at a large tech, finance, or defense company, where pay is base + bonus + stock. It takes roughly 8 to 12 years of shipping real systems. Certs open doors early; after that, **proof of what I built** is what gets paid.
* Being honest with myself: my first job might be help desk or a junior role. That is fine if I keep building. The portfolio is what skips steps.

## Certification timeline

One cert at a time. Finish one before opening the next book.

| When | Exam | Why |
|---|---|---|
| Now to Feb 2027 | **Security+** (SY0-801) | Baseline for cleared and DoD jobs. Main book is the 801 version. My 701 books are backup practice only. |
| Mar to Jun 2027 | **Network+** (N10-009) | Every architect has to understand how traffic actually moves. |
| Jul to Dec 2027 | **AWS Solutions Architect Associate** | Cloud is the biggest lever toward $100k+. |
| 2028 | **A+** (220-1201, 220-1202) | Completes the Trifecta. Optional for the architect path, keep it if I still want it. |
| 2028 | **Terraform Associate** or **CySA+** | Terraform for the architect path, CySA+ for the SOC path. |
| Later | **CISSP** | Needs 5 years of paid security work experience, so this comes with time. |

**Rule:** before booking any exam, check the exam code is still current on the official site (comptia.org, aws.amazon.com, hashicorp.com).

### How I study each cert

* Read one chapter of the physical book
* Watch the matching Professor Messer videos (free) for CompTIA
* Practice questions from my practice book and our HTML practice site
* PBQ practice: subnetting, port matching, firewall rules, troubleshooting order
* Hands on labs: Cisco Packet Tracer (Network+), OverTheWire Bandit (Linux), my homelab (everything)

## Skill tracks

### Programming

| Phase | Java | Rust | TypeScript |
|---|---|---|---|
| 1. Basics and OOP | IS 247 Ch 11 to 13 | Book Ch 1 to 6 | Types, interfaces, classes |
| 2. Errors, files, collections | Ch 12, 14, 16 | Ch 7 to 9 | Arrays, maps, file I/O, async |
| 3. Generics and recursion | Ch 15, 17, 18 | Ch 10 to 11 | Generics, utility types |
| 4. Algorithms and Big O | Ch 19, 20 | Ch 13 | Same sorts in TS |
| 5. Real project | | Tauri backend | Tauri screens |

### SQL and databases

| Level | What I learn | How I practice |
|---|---|---|
| 1. Solid SQL | Joins, subqueries, grouping, constraints, normalization | My IS 410 and IS 320 classes, plus weekly SQL drills |
| 2. Real PostgreSQL | Install it in Docker on my homelab, roles and permissions, backups | Run my own database server |
| 3. Performance | Indexes (B Tree, GIN), reading `EXPLAIN ANALYZE`, fixing slow queries | Load 1 million+ fake rows and make queries fast |
| 4. Correctness at scale | Transactions, isolation levels, locking, deadlocks | Break it on purpose with two sessions at once |
| 5. Architecture | Replication, failover, backup and restore drills, schema design for big systems | Primary and replica on the homelab |

My software project (Oracle and standard SQL) gives me real reasons to use every level.

### Automation

| Level | Tool | First real use |
|---|---|---|
| 1 | **PowerShell** | My check-setup script, Windows admin tasks |
| 2 | **Bash** | Homelab maintenance scripts |
| 3 | **Git + GitHub Actions** | Auto test my learning-journey code on every push |
| 4 | **Docker** | Rebuild my homelab services by hand with compose files, then my PostgreSQL server |
| 5 | **Python** | Scripts that talk to APIs and databases (comes after my Java/Rust/TS foundation) |
| 6 | **Terraform** | Build my AWS practice environment from code, tear it down, rebuild it |

## The plan in time blocks

Not everything at once. Each block adds a little.

| Block | Certs | Code | SQL and automation |
|---|---|---|---|
| **Now to Feb 2027** | Security+ | Phases 1 and 2 | SQL Level 1 (classes), PowerShell, Docker basics on the KAMRUI (Saturday lab) |
| **Mar to Jun 2027** | Network+ | Phases 3 and 4 | PostgreSQL Levels 2 and 3 (in Docker), Bash, GitHub Actions |
| **Jul to Dec 2027** | AWS SAA | Phase 5: start the real project | PostgreSQL Level 4, Python basics, Terraform basics |
| **2028** | A+ or Terraform or CySA+ | Ship project v1 | PostgreSQL Level 5, Terraform on AWS, job hunt with portfolio |

## Weekly rhythm

| Day | Focus |
|---|---|
| Mon | Java assignment |
| Tue | Cert reading (one chapter) |
| Wed | Rust book plus Rust assignment |
| Thu | Cert practice questions and labs |
| Fri | TypeScript lesson plus assignment |
| Sat | **SQL week assignments** (or Docker lab), shared problem, push to GitHub, code review |
| Sun | **Off** |

60 to 90 minutes a day. Missing a day is fine. Skipping the rest day is not. When a new track starts, something else gets lighter. I do not just stack more on top.

## Where everything connects

* Security+ crypto (hashing, signatures, PKI) = my app's signed updates
* Network+ segmentation and firewalls = isolating my homelab update server
* SQL and PostgreSQL = the core of my software project
* Docker and Terraform = how my homelab and AWS practice get rebuilt from code
* GitHub = the proof that I can do all of it

## Portfolio (what employers will see)

* `learning-journey`: every assignment in Java, Rust, TypeScript, SQL
* Homelab repo: Docker configs and scripts (no secrets ever committed)
* PostgreSQL tuning write ups: before and after query times with explanations
* AWS + Terraform project: a small multi tier app built entirely from code
* My professional software project

## Progress log

| Date | What I finished |
|---|---|
| 2026-10-19 | Set up learning-journey repo on laptop and main computer |
