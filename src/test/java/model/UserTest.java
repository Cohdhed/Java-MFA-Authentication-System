package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserFields() {
        User user = new User("john", "pass", "email", "123");

        assertEquals("john", user.getUsername());
        assertEquals("pass", user.getPassword());
        assertEquals("email", user.getEmail());
        assertEquals("123", user.getPhoneNumber());
    }
}