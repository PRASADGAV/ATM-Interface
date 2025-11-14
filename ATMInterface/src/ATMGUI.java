import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMGUI extends JFrame {
    private ATM atm;
    private JPanel mainPanel;
    private JLabel balanceLabel;
    private JLabel userLabel;
    
    public ATMGUI(ATM atm) {
        this.atm = atm;
        
        setTitle("ATM - Main Menu");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(230, 240, 250));
        
        // Top Panel - User Info
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1, 5, 5));
        topPanel.setBackground(new Color(230, 240, 250));
        
        JLabel titleLabel = new JLabel("WELCOME TO ATM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204));
        
        userLabel = new JLabel("User: " + atm.getCurrentUser().getName() + " | Account: " + 
                               atm.getCurrentAccount().getAccountNumber());
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        
        topPanel.add(titleLabel);
        topPanel.add(userLabel);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center Panel - Balance and Menu
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 15, 15));
        centerPanel.setBackground(new Color(230, 240, 250));
        
        // Balance Display
        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new BorderLayout());
        balancePanel.setBackground(new Color(220, 240, 255));
        balancePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        
        balanceLabel = new JLabel("Current Balance: Rs. " + atm.getCurrentAccount().getBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setForeground(new Color(0, 150, 0));
        balanceLabel.setHorizontalAlignment(JLabel.CENTER);
        balancePanel.add(balanceLabel, BorderLayout.CENTER);
        
        centerPanel.add(balancePanel);
        
        // Buttons Panel 1
        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(1, 2, 15, 0));
        buttonPanel1.setBackground(new Color(230, 240, 250));
        
        JButton withdrawButton = createButton("WITHDRAW", new Color(200, 0, 0), e -> handleWithdraw());
        JButton depositButton = createButton("DEPOSIT", new Color(0, 150, 0), e -> handleDeposit());
        
        buttonPanel1.add(withdrawButton);
        buttonPanel1.add(depositButton);
        
        centerPanel.add(buttonPanel1);
        
        // Buttons Panel 2
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(1, 3, 10, 0));
        buttonPanel2.setBackground(new Color(230, 240, 250));
        
        JButton transferButton = createButton("TRANSFER", new Color(255, 165, 0), e -> handleTransfer());
        JButton historyButton = createButton("HISTORY", new Color(100, 150, 200), e -> handleHistory());
        JButton logoutButton = createButton("LOGOUT", new Color(150, 0, 150), e -> handleLogout());
        
        buttonPanel2.add(transferButton);
        buttonPanel2.add(historyButton);
        buttonPanel2.add(logoutButton);
        
        centerPanel.add(buttonPanel2);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JButton createButton(String text, Color bgColor, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void handleWithdraw() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to withdraw:", "Withdraw", JOptionPane.PLAIN_MESSAGE);
        
        if (amountStr != null && !amountStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (atm.getCurrentAccount().withdraw(amount)) {
                    updateBalance();
                    JOptionPane.showMessageDialog(this, "Withdrawal successful!\nAmount: Rs. " + amount + 
                                                "\nNew Balance: Rs. " + atm.getCurrentAccount().getBalance(), 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleDeposit() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to deposit:", "Deposit", JOptionPane.PLAIN_MESSAGE);
        
        if (amountStr != null && !amountStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (atm.getCurrentAccount().deposit(amount)) {
                    updateBalance();
                    JOptionPane.showMessageDialog(this, "Deposit successful!\nAmount: Rs. " + amount + 
                                                "\nNew Balance: Rs. " + atm.getCurrentAccount().getBalance(), 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleTransfer() {
        String recipientName = JOptionPane.showInputDialog(this, "Enter recipient name:", "Transfer", JOptionPane.PLAIN_MESSAGE);
        
        if (recipientName != null && !recipientName.isEmpty()) {
            String amountStr = JOptionPane.showInputDialog(this, "Enter amount to transfer:", "Transfer Amount", JOptionPane.PLAIN_MESSAGE);
            
            if (amountStr != null && !amountStr.isEmpty()) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(this, "Amount must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (atm.getCurrentAccount().transfer(amount, recipientName)) {
                        updateBalance();
                        JOptionPane.showMessageDialog(this, "Transfer successful!\nRecipient: " + recipientName + 
                                                    "\nAmount: Rs. " + amount + 
                                                    "\nNew Balance: Rs. " + atm.getCurrentAccount().getBalance(), 
                                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void handleHistory() {
        JFrame historyFrame = new JFrame("Transaction History");
        historyFrame.setSize(600, 400);
        historyFrame.setLocationRelativeTo(this);
        
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        textArea.setEditable(false);
        textArea.setBackground(new Color(255, 255, 255));
        
        for (String transaction : atm.getCurrentAccount().getTransactionHistory()) {
            textArea.append(transaction + "\n");
        }
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        historyFrame.add(scrollPane);
        historyFrame.setVisible(true);
    }
    
    private void handleLogout() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            atm.logout();
            dispose();
            new LoginGUI();
        }
    }
    
    private void updateBalance() {
        balanceLabel.setText("Current Balance: Rs. " + atm.getCurrentAccount().getBalance());
    }
}
