package gui;

import controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Login GUI:
 * - Handles username + password input
 * - Prevents empty input from counting as attempts
 * - Terminates app on lock conditions
 */
public class LoginFrame extends JFrame {

    private final AuthController controller;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    public LoginFrame(AuthController controller) {
        this.controller = controller;

        setTitle("University of Bradford MFA System - Login");
        setPreferredSize(new Dimension(800, 300));
        setMinimumSize(new Dimension(800, 300));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();

        pack();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        messageLabel = new JLabel("", SwingConstants.CENTER);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(messageLabel);

        add(panel);

        loginButton.addActionListener(new LoginHandler());
    }

    private class LoginHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            // -------- EMPTY INPUT VALIDATION --------
            if (username.isEmpty() && password.isEmpty()) {
                messageLabel.setText("Username and password are required.");
                return;
            }

            if (username.isEmpty()) {
                messageLabel.setText("Username is required.");
                return;
            }

            if (password.isEmpty()) {
                messageLabel.setText("Password is required.");
                return;
            }

            // -------- USERNAME VALIDATION --------
            if (controller.getCurrentUser() == null) {

                boolean validUser = controller.validateUsername(username);

                if (!validUser) {

                    if (controller.isUsernameLocked()) {
                        messageLabel.setText("Username not found in our system. Access denied.");
                        exitAfterDelay();
                    } else {
                        messageLabel.setText(
                                "Invalid username. Attempts left: "
                                        + controller.getRemainingUsernameAttempts()
                        );
                    }

                    return;
                }
            }

            // -------- PASSWORD VALIDATION --------
            boolean validPassword = controller.validatePassword(password);

            if (!validPassword) {

                if (controller.isPasswordLocked()) {
                    messageLabel.setText("Password limit reached. Access denied.");
                    exitAfterDelay();
                } else {
                    messageLabel.setText(
                            "Incorrect password. Attempts left: "
                                    + controller.getRemainingPasswordAttempts()
                    );
                }

                return;
            }

            // -------- SUCCESS → MOVE TO MFA --------
            messageLabel.setText("Password verified. Proceeding to MFA...");

            SwingUtilities.invokeLater(() -> new MFAFrame(controller));

            dispose();
        }
    }

    // -------- EXIT HELPER --------
    private void exitAfterDelay() {
        Timer timer = new Timer(5000, evt -> System.exit(0));
        timer.setRepeats(false);
        timer.start();
    }
}