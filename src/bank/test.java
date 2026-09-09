package bank;

public class test {
    public static void main(String[] args) {
        BankAccount bk1=new BankAccount("12232","lll",12345.1);
        bk1.withdraw(123) ;
        bk1.deposit(456) ;
        System.out.println(bk1);
    }
}
