package authentication;

import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMSOTPAuthenticationTest {

    @Test
    void testOTPGenerationForSMS() {
        SMSOTPAuthentication auth = new SMSOTPAuthentication();
        User user = new User("john", "password123", "email", "123");

        boolean result = auth.authenticate(user, "");
        assertFalse(result);
    }

    // NEW — empty OTP case
    @Test
    void testEmptyOTPForSMSFails() {
        SMSOTPAuthentication auth = new SMSOTPAuthentication();
        User user = new User("john", "password123", "email", "123");

        auth.authenticate(user, "");
        assertFalse(auth.authenticate(user, ""));
    }

    // NEW — wrong OTP
    @Test
    void testWrongSMSOTP() {
        SMSOTPAuthentication auth = new SMSOTPAuthentication();
        User user = new User("john", "password123", "email", "123");

        auth.authenticate(user, "");
        assertFalse(auth.authenticate(user, "999999"));
    }
}