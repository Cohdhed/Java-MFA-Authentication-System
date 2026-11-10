package app;

import controller.AuthController;
import gui.LoginFrame;
import model.User;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/*
 * Entry point of the MFA authentication system (GUI version).
 * Responsibility:
 * - Initialize user data
 * - Initialize controller
 * - Launch GUI
 */
public class Main {

    public static void main(String[] args) {

        // Hard-coded users for prototype purposes
        Map<String, User> users = new HashMap<>();

        users.put("john", new User(
                "john",
                "password123",
                "john@gmail.com",
                "07000000001"
        ));

        users.put("mary", new User(
                "mary",
                "securepass",
                "mary@gmail.com",
                "07000000002"
        ));

        users.put("nath", new User(
                "nath",
                "nathpass9",
                "nath@gmail.com",
                "07000000003"
        ));

        users.put("smith", new User(
                "smith",
                "passbysmith",
                "smith@gmail.com",
                "07000000004"
        ));

        users.put("angel", new User(
                "angel",
                "securedangel",
                "angel@gmail.com",
                "07000000005"
        ));

        // Initialize controller with user data
        AuthController controller = new AuthController(users);

        // Launch GUI on Event Dispatch Thread (best practice for Swing)
        SwingUtilities.invokeLater(() -> new LoginFrame(controller));
    }
}