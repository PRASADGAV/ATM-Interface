import java.util.HashMap;
import java.util.Map;

public class ATM {
    private Map<String, User> users;
    private Map<String, BankAccount> accounts;
    private User currentUser;
    private BankAccount currentAccount;
    
    public ATM() {
        this.users = new HashMap<>();
        this.accounts = new HashMap<>();
        initializeUsers();
    }
    
    private void initializeUsers() {
        User user1 = new User("U001", "1234", "Prasad Gavali");
        users.put("U001", user1);
        accounts.put("U001", new BankAccount("ACC001", 10000));
        
        User user2 = new User("U002", "5678", "John Doe");
        users.put("U002", user2);
        accounts.put("U002", new BankAccount("ACC002", 5000));
        
        User user3 = new User("U003", "9999", "Jane Smith");
        users.put("U003", user3);
        accounts.put("U003", new BankAccount("ACC003", 15000));
    }
    
    public boolean authenticate(String userId, String pin) {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            if (user.authenticate(userId, pin)) {
                currentUser = user;
                currentAccount = accounts.get(userId);
                return true;
            }
        }
        return false;
    }
    
    public User getCurrentUser() { return currentUser; }
    public BankAccount getCurrentAccount() { return currentAccount; }
    
    public void logout() {
        currentUser = null;
        currentAccount = null;
    }
}
