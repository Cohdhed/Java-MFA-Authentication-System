package service;

import authentication.AuthenticationMethod;
import model.User;

/*
 * Context class in the Strategy pattern.
 * Delegates authentication logic to the selected strategy.
 */
public class AuthenticationService {

    private AuthenticationMethod authenticationMethod;

    // Allows runtime switching of authentication strategy
    public void setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    // Delegates authentication task to the selected strategy
    public boolean authenticate(User user, String input) {

        if (authenticationMethod == null) {
            throw new IllegalStateException("Authentication method not set.");
        }

        return authenticationMethod.authenticate(user, input);
    }
}