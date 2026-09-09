package bank;

public class BankAccount {
    String accountNumber;
    String holderName;
    double balance;

    void deposit (double cash) {
        balance = cash + balance;
    }

    ;

    void withdraw(double cash) {
        if (balance > cash) balance = balance - cash;

    }

    ;

    //alt+insert插入构造方法
    public BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public BankAccount() {
    }

    //alt+insert 插入+修改
    @Override
    public String toString() {
        return "BankAccount{" +
                "账号accountNumber='" + accountNumber + '\'' +
                ",户主姓名 holderName='" + holderName + '\'' +
                ",余额 balance=" + balance +
                '}';
    }

    public static void main(String[] args) {
        BankAccount bank = new BankAccount("12344", "ljy", 6789.0);
        bank.withdraw(1322);
        System.out.println(bank.balance  );
        bank.deposit(676);
        System.out.println(bank.balance);
    }
}
