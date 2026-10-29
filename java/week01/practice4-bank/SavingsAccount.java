public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String owner, double openingDeposit, double interestRate) {
        super(owner, openingDeposit);
        this.interestRate = interestRate;
    }

    @Override
    public boolean withdraw(double amount) {
        if (getBalance() - amount < 100) {
            System.out.println("Savings must keep a $100 minimum.");
            return false;
        }
        return super.withdraw(amount);
    }

    @Override
    public void monthEnd() {
        deposit(getBalance() * interestRate);
    }
}
