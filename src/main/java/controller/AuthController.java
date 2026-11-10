package controller;

import authentication.AuthenticationMethod;
import factory.AuthenticationFactory;
import model.User;
import service.AuthenticationService;
import java.util.Map;

/*
 * Acts as the controller layer between GUI and backend logic.
 * Handles:
 * - Username validation
 * - Password authentication (with attempts)
 * - MFA selection
 * - OTP generation and verification
 */
public class AuthController {

    private final Map<String, User> users;
    private final AuthenticationService authService;

    private User currentUser;

    private int usernameAttempts = 0;
    private int passwordAttempts = 0;

    private static final int MAX_ATTEMPTS = 3;

    public AuthController(Map<String, User> users) {
        this.users = users;
        this.authService = new AuthenticationService();
    }

    // ---------------- USERNAME VALIDATION ----------------
    public boolean validateUsername(String username) {

        if (usernameAttempts >= MAX_ATTEMPTS) {
            return false;
        }

        User user = users.get(username);

        if (user != null) {
            currentUser = user;
            usernameAttempts = 0; // reset on success
            return true;
        }

        usernameAttempts++;
        return false;
    }

    public int getRemainingUsernameAttempts() {
        return MAX_ATTEMPTS - usernameAttempts;
    }

    public boolean isUsernameLocked() {
        return usernameAttempts >= MAX_ATTEMPTS;
    }

    // ---------------- PASSWORD AUTHENTICATION ----------------
    public boolean validatePassword(String password) {

        if (currentUser == null || passwordAttempts >= MAX_ATTEMPTS) {
            return false;
        }

        AuthenticationMethod passwordAuth =
                AuthenticationFactory.createAuthenticationMethod("PASSWORD");

        authService.setAuthenticationMethod(passwordAuth);

        boolean success = authService.authenticate(currentUser, password);

        if (success) {
            passwordAttempts = 0; // reset on success
            return true;
        }

        passwordAttempts++;
        return false;
    }

    public int getRemainingPasswordAttempts() {
        return MAX_ATTEMPTS - passwordAttempts;
    }

    public boolean isPasswordLocked() {
        return passwordAttempts >= MAX_ATTEMPTS;
    }

    // ---------------- MFA SELECTION ----------------
    public void selectMFAMethod(String methodType) {

        AuthenticationMethod method =
                AuthenticationFactory.createAuthenticationMethod(methodType);

        authService.setAuthenticationMethod(method);
    }

    // ---------------- OTP GENERATION ----------------
    public void generateOTP() {

        if (currentUser != null) {
            // Empty input triggers OTP generation (based on your CW1 logic)
            authService.authenticate(currentUser, "");
        }
    }

    // ---------------- OTP VERIFICATION ----------------
    public boolean verifyOTP(String otpInput) {

        if (currentUser == null) {
            return false;
        }

        return authService.authenticate(currentUser, otpInput);
    }

    // ---------------- GET CURRENT USER ----------------
    public User getCurrentUser() {
        return currentUser;
    }
}