package authentication;

import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailOTPAuthenticationTest {

    @Test
    void testOTPGenerationDoesNotAuthenticateImmediately() {
        EmailOTPAuthentication auth = new EmailOTPAuthentication();
        User user = new User("john", "password123", "email", "123");

        boolean result = auth.authenticate(user, "");
        assertFalse(result);
    }

    // NEW — empty OTP edge case
    @Test
    void testOTPEmptyInputFails() {
        EmailOTPAuthentication auth = new EmailOTPAuthentication();
        User user = new User("john", "password123", "email", "123");

        auth.authenticate(user, ""); // generate OTP
        assertFalse(auth.authenticate(user, ""));
    }

    // NEW — wrong OTP must fail
    @Test
    void testWrongOTPAlwaysFails() {
        EmailOTPAuthentication auth = new EmailOTPAuthentication();
        User user = new User("john", "password123", "email", "123");

        auth.authenticate(user, ""); // generate OTP
        assertFalse(auth.authenticate(user, "wrong"));
    }
}