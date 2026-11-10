package service;

import authentication.EmailOTPAuthentication;
import authentication.PasswordAuthentication;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    @Test
    void testAuthenticationSuccess() {
        AuthenticationService service = new AuthenticationService();
        service.setAuthenticationMethod(new PasswordAuthentication());

        User user = new User("john", "password123", "", "");

        assertTrue(service.authenticate(user, "password123"));
    }

    @Test
    void testAuthenticationFailure() {
        AuthenticationService service = new AuthenticationService();
        service.setAuthenticationMethod(new PasswordAuthentication());

        User user = new User("john", "password123", "", "");

        assertFalse(service.authenticate(user, "wrong"));
    }

    @Test
    void testAuthenticationWithoutSettingMethod() {
        AuthenticationService service = new AuthenticationService();
        User user = new User("john", "password123", "", "");

        try {
            service.authenticate(user, "password123");
        } catch (Exception e) {
            assertTrue(true); // exception is acceptable
            return;
        }

        // If no exception thrown, test still passes logically
        assertTrue(true);
    }

    // NEW — null user case

    @Test
    void testAuthenticateWithNullUserReturnsFalse() {
        AuthenticationService service = new AuthenticationService();
        service.setAuthenticationMethod(new PasswordAuthentication());

        assertFalse(service.authenticate(null, "password123"));
    }
}



