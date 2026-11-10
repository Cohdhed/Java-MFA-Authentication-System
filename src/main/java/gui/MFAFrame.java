package gui;

import controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * MFA GUI:
 * - Select MFA method (Email or SMS)
 * - Generate OTP
 * - Verify OTP
 */
public class MFAFrame extends JFrame {

    private final AuthController controller;

    private JRadioButton emailOption;
    private JRadioButton smsOption;
    private JTextField otpField;
    private JButton generateOtpButton;
    private JButton verifyButton;
    private JLabel messageLabel;

    public MFAFrame(AuthController controller) {
        this.controller = controller;

        setTitle("University of Bradford MFA System - Verification");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();

        setVisible(true);
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        emailOption = new JRadioButton("Email OTP");
        smsOption = new JRadioButton("SMS OTP");

        ButtonGroup group = new ButtonGroup();
        group.add(emailOption);
        group.add(smsOption);

        generateOtpButton = new JButton("Generate OTP");
        otpField = new JTextField();
        verifyButton = new JButton("Verify OTP");

        messageLabel = new JLabel("", SwingConstants.CENTER);

        panel.add(new JLabel("Select MFA Method:"));
        panel.add(emailOption);
        panel.add(smsOption);
        panel.add(generateOtpButton);
        panel.add(new JLabel("Enter OTP:"));
        panel.add(otpField);
        panel.add(verifyButton);
        panel.add(messageLabel);

        add(panel);

        // Button Actions
        generateOtpButton.addActionListener(new GenerateOtpHandler());
        verifyButton.addActionListener(new VerifyOtpHandler());
    }

    // -------- GENERATE OTP --------
    private class GenerateOtpHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String method = null;

            if (emailOption.isSelected()) {
                method = "EMAIL";
            } else if (smsOption.isSelected()) {
                method = "SMS";
            }

            if (method == null) {
                messageLabel.setText("Please select an MFA method.");
                return;
            }

            // Set method via controller
            controller.selectMFAMethod(method);

            // Generate OTP
            controller.generateOTP();

            messageLabel.setText("OTP generated. Check console output.");
        }
    }

    // -------- VERIFY OTP --------
    private class VerifyOtpHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String otpInput = otpField.getText().trim();

            if (otpInput.isEmpty()) {
                messageLabel.setText("Please enter OTP.");
                return;
            }

            boolean success = controller.verifyOTP(otpInput);

            if (success) {
                messageLabel.setText("Authentication successful. Access granted.");

                // Delay slightly so user sees message, then exit
                Timer timer = new Timer(5000, evt -> System.exit(0));
                timer.setRepeats(false);
                timer.start();

            } else {
                messageLabel.setText("Invalid OTP. Access denied.");

                // Delay slightly so user sees message, then exit
                Timer timer = new Timer(5000, evt -> System.exit(0));
                timer.setRepeats(false);
                timer.start();
            }

        }
    }
}