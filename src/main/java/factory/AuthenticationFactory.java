package factory;

import authentication.*;

/*
 * Factory class responsible for creating
 * authentication strategy objects.
 */
public class AuthenticationFactory {

    public static AuthenticationMethod createAuthenticationMethod(String type) {

        switch (type.toUpperCase()) {

            case "PASSWORD":
                return new PasswordAuthentication();

            case "EMAIL":
                return new EmailOTPAuthentication();

            case "SMS":
                return new SMSOTPAuthentication();

            default:
                throw new IllegalArgumentException("Invalid authentication type.");
        }
    }
}