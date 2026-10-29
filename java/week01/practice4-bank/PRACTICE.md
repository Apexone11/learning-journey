# Java Week 1, Practice 4: Bank Accounts

Code only, no written answers. Starter files have `TODO`s.

**New skills:** a static ID generator, overriding to **add a rule** then calling `super`, overriding to **replace** a
rule completely, a method that returns `boolean`, a "do nothing" parent method that children fill in.

1. **Account.java**
  * `private static int nextNumber = 1001`
  * `private final String accountNumber`, `private String owner`, `protected double balance`
  * constructor `(String owner, double openingDeposit)`: sets `accountNumber` to `"ACC-" + nextNumber`, then adds 1 to
    `nextNumber`
  * `deposit(double amount)`: if amount is 0 or less, print `Deposit must be positive.` and do nothing. Otherwise add
    it.
  * `boolean withdraw(double amount)`: if amount is more than the balance, print `Insufficient funds.` and return
    `false`. Otherwise subtract it and return `true`.
  * `getBalance()`
  * `monthEnd()`: does **nothing** in Account (empty body). Children will override it.
  * `toString()` returns like `ACC-1001 Aisha: $204.00` (use `String.format` with `%.2f`)
2. **SavingsAccount.java:** extends Account. Adds `private double interestRate`.
  * `withdraw`: if the withdrawal would leave **less than $100**, print `Savings must keep a $100 minimum.` and return
    `false`. Otherwise **return `super.withdraw(amount)`**.
  * `monthEnd`: add interest (`balance * interestRate`) **using `deposit(...)`**
3. **CheckingAccount.java:** extends Account. Adds `private double overdraftLimit`.
  * `withdraw`: the balance may go **negative**, down to minus the overdraft limit. If it would go past that, print
    `Over the overdraft limit.` and return `false`. Otherwise subtract and return `true`. (You can't use
    `super.withdraw` here. Why not? Think about it.)
  * `monthEnd`: if the balance is negative, take a **$25** fee
4. **Main.java** (create in exactly this order so the numbers match)
  * `SavingsAccount("Aisha", 500, 0.02)`
  * `CheckingAccount("Kofi", 200, 300)`
  * `Account("Lena", 50)`
  * Put all three in one `ArrayList<Account>`
  * Print the result of each of these, in order:
    * Aisha withdraws 450
    * Aisha withdraws 300
    * Kofi withdraws 400
    * Kofi withdraws 200
    * Lena withdraws 80
  * Lena deposits -5
  * One loop over the list: call `monthEnd()`, print the account, add to a total, count negative balances
  * Print the total (2 decimals) and the negative count

## Expected output (match it exactly)

```
Savings must keep a $100 minimum.
false
true
true
Over the overdraft limit.
false
Insufficient funds.
false
Deposit must be positive.
ACC-1001 Aisha: $204.00
ACC-1002 Kofi: $-225.00
ACC-1003 Lena: $50.00
Total: $29.00
Negative: 1
```
