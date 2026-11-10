package authentication;

import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordAuthenticationTest {

    @Test
    void testCorrectPassword() {
        User user = new User("john", "password123", "", "");
        PasswordAuthentication auth = new PasswordAuthentication();

        assertTrue(auth.authenticate(user, "password123"));
    }

    @Test
    void testWrongPassword() {
        User user = new User("john", "password123", "", "");
        PasswordAuthentication auth = new PasswordAuthentication();

        assertFalse(auth.authenticate(user, "wrong"));
    }

    @Test
    void testNullPassword() {
        User user = new User("john", "password123", "", "");
        PasswordAuthentication auth = new PasswordAuthentication();

        assertFalse(auth.authenticate(user, null));
    }

    // NEW — kills string comparison mutants
    @Test
    void testPasswordStrictMatch() {
        User user = new User("john", "password123", "", "");
        PasswordAuthentication auth = new PasswordAuthentication();

        assertFalse(auth.authenticate(user, "password123 "));
        assertFalse(auth.authenticate(user, " PASSWORD123"));
    }
}