package controller;

import model.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private AuthController createController() {
        Map<String, User> users = new HashMap<>();
        users.put("john", new User("john", "password123", "john@gmail.com", "07000000001"));
        return new AuthController(users);
    }

    // ---------------- USERNAME TESTS ----------------

    @Test
    void testValidUsername() {
        AuthController controller = createController();
        assertTrue(controller.validateUsername("john"));
    }

    @Test
    void testInvalidUsername() {
        AuthController controller = createController();
        assertFalse(controller.validateUsername("invalid"));
    }

    @Test
    void testUsernameLockAfterThreeAttempts() {
        AuthController controller = createController();

        controller.validateUsername("wrong");
        controller.validateUsername("wrong");
        controller.validateUsername("wrong");

        assertTrue(controller.isUsernameLocked());
        assertEquals(0, controller.getRemainingUsernameAttempts());
    }

    // ---------------- PASSWORD TESTS ----------------

    @Test
    void testPasswordSuccess() {
        AuthController controller = createController();
        controller.validateUsername("john");

        assertTrue(controller.validatePassword("password123"));
    }

    @Test
    void testPasswordFailure() {
        AuthController controller = createController();
        controller.validateUsername("john");

        assertFalse(controller.validatePassword("wrong"));
    }

    @Test
    void testPasswordLockAfterThreeAttempts() {
        AuthController controller = createController();
        controller.validateUsername("john");

        controller.validatePassword("wrong");
        controller.validatePassword("wrong");
        controller.validatePassword("wrong");

        assertTrue(controller.isPasswordLocked());
        assertEquals(0, controller.getRemainingPasswordAttempts());
    }

    // ---------------- OTP TESTS ----------------

    @Test
    void testOTPFlowFailure() {
        AuthController controller = createController();

        controller.validateUsername("john");
        controller.validatePassword("password123");

        controller.selectMFAMethod("EMAIL");
        controller.generateOTP();

        // Wrong OTP should fail
        assertFalse(controller.verifyOTP("wrong"));
    }

    @Test
    void testVerifyOTPWithoutUser() {
        AuthController controller = createController();

        // No user set
        assertFalse(controller.verifyOTP("123456"));
    }

    // ---------------- EDGE CASES ----------------

    @Test
    void testValidatePasswordWithoutUsername() {
        AuthController controller = createController();

        // No username set
        assertFalse(controller.validatePassword("password123"));
    }

    @Test
    void testUsernameAttemptsDecreaseProperly() {
        AuthController controller = createController();

        controller.validateUsername("wrong");
        assertEquals(2, controller.getRemainingUsernameAttempts());
    }

    @Test
    void testPasswordAttemptsDecreaseProperly() {
        AuthController controller = createController();
        controller.validateUsername("john");

        controller.validatePassword("wrong");
        assertEquals(2, controller.getRemainingPasswordAttempts());
    }

    // NEW: Boundary test (username)
    @Test
    void testUsernameExactlyThreeAttemptsBoundary() {
        AuthController controller = createController();

        controller.validateUsername("wrong");
        controller.validateUsername("wrong");

        assertFalse(controller.isUsernameLocked());

        controller.validateUsername("wrong");

        assertTrue(controller.isUsernameLocked());
    }

    // NEW: Boundary test (password)
    @Test
    void testPasswordExactlyThreeAttemptsBoundary() {
        AuthController controller = createController();
        controller.validateUsername("john");

        controller.validatePassword("wrong");
        controller.validatePassword("wrong");

        assertFalse(controller.isPasswordLocked());

        controller.validatePassword("wrong");

        assertTrue(controller.isPasswordLocked());
    }

    // NEW: OTP empty case
    @Test
    void testOTPEmptyFails() {
        AuthController controller = createController();
        controller.validateUsername("john");
        controller.validatePassword("password123");

        controller.selectMFAMethod("EMAIL");
        controller.generateOTP();

        assertFalse(controller.verifyOTP(""));
    }
}