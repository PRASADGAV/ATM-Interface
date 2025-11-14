import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private ATM atm;
    private JTextField userIdField;
    private JPasswordField pinField;
    private JButton loginButton;
    private JLabel messageLabel;
    private int attemptCount = 0;
    
    public LoginGUI() {
        atm = new ATM();
        
        setTitle("ATM - Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(230, 240, 250));
        
        JLabel titleLabel = new JLabel("ATM LOGIN SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204));
        panel.add(titleLabel);
        
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userIdField = new JTextField();
        userIdField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(userIdLabel);
        panel.add(userIdField);
        
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 12));
        pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 12));
        pinField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
        panel.add(pinLabel);
        panel.add(pinField);
        
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setBackground(new Color(0, 150, 100));
        loginButton.setForeground(Color.WHITE);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());
        panel.add(loginButton);
        
        messageLabel = new JLabel("Test: U001/1234, U002/5678, U003/9999");
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setForeground(new Color(100, 100, 100));
        panel.add(messageLabel);
        
        add(panel);
        setVisible(true);
    }
    
    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String pin = new String(pinField.getPassword());
        
        if (userId.isEmpty() || pin.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please fill all fields!");
            return;
        }
        
        if (atm.authenticate(userId, pin)) {
            dispose();
            new ATMGUI(atm);
        } else {
            attemptCount++;
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Invalid credentials. Attempts: " + attemptCount + "/3");
            pinField.setText("");
            
            if (attemptCount >= 3) {
                JOptionPane.showMessageDialog(this, "Card Blocked!", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }
}
