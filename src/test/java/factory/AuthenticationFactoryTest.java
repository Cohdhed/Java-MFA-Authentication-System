package factory;

import authentication.AuthenticationMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationFactoryTest {

    @Test
    void testCreatePassword() {
        AuthenticationMethod method =
                AuthenticationFactory.createAuthenticationMethod("PASSWORD");

        assertNotNull(method);
    }

    @Test
    void testCreateEmail() {
        AuthenticationMethod method =
                AuthenticationFactory.createAuthenticationMethod("EMAIL");

        assertNotNull(method);
    }

    @Test
    void testCreateSMS() {
        AuthenticationMethod method =
                AuthenticationFactory.createAuthenticationMethod("SMS");

        assertNotNull(method);
    }

    @Test
    void testInvalidType() {
        assertThrows(IllegalArgumentException.class, () ->
                AuthenticationFactory.createAuthenticationMethod("INVALID"));
    }

    // NEW — kills case mutation

    @Test
    void testCaseSensitivityHandled() {
        AuthenticationMethod method =
                AuthenticationFactory.createAuthenticationMethod("email");

        assertNotNull(method);
    }
}