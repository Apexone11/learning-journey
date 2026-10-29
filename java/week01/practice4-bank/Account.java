public class Account {
    private static int nextNumber = 1001;
    private final String accountNumber;
    private String owner;
    protected double balance;

    public Account(String owner, double openingDeposit) {
        this.owner = owner;
        this.balance = openingDeposit;
        this.accountNumber = "ACC-" + nextNumber;
        nextNumber++;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit must be positive.");
            return;
        }
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return false;
        }
        balance -= amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public void monthEnd() {
    }

    public String toString() {
        return String.format("%s %s: $%.2f", accountNumber, owner, balance);
    }
}
