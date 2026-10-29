public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String owner, double openingDeposit, double overdraftLimit) {
        super(owner, openingDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount < -overdraftLimit) {
            System.out.println("Over the overdraft limit.");
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public void monthEnd() {
        if (balance < 0) {
            balance -= 25;
        }
    }
}
