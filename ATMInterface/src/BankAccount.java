import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private double balance;
    private List<String> transactionHistory;
    private String accountNumber;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account Created", initialBalance);
    }
    
    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("Withdrawal", amount);
            return true;
        }
        return false;
    }
    
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposit", amount);
            return true;
        }
        return false;
    }
    
    public boolean transfer(double amount, String recipientName) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("Transfer to " + recipientName, amount);
            return true;
        }
        return false;
    }
    
    private void addTransaction(String type, double amount) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timestamp = now.format(formatter);
        String transaction = timestamp + " | " + type + " | Rs. " + amount + " | Balance: Rs. " + balance;
        transactionHistory.add(transaction);
    }
    
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}
