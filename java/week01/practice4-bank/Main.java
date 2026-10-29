import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SavingsAccount aisha = new SavingsAccount("Aisha", 500, 0.02);
        CheckingAccount kofi = new CheckingAccount("Kofi", 200, 300);
        Account lena = new Account("Lena", 50);

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(aisha);
        accounts.add(kofi);
        accounts.add(lena);

        System.out.println(aisha.withdraw(450));
        System.out.println(aisha.withdraw(300));
        System.out.println(kofi.withdraw(400));
        System.out.println(kofi.withdraw(200));
        System.out.println(lena.withdraw(80));

        lena.deposit(-5);

        double total = 0;
        int negative = 0;
        for (Account account : accounts) {
            account.monthEnd();
            System.out.println(account);
            total += account.getBalance();
            if (account.getBalance() < 0) {
                negative++;
            }
        }

        System.out.printf("Total: $%.2f%n", total);
        System.out.println("Negative: " + negative);
    }
}
