package authentication;

import model.User;

/*
 * Strategy implementation responsible for validating
 * the user's password during the first authentication step.
 */
public class PasswordAuthentication implements AuthenticationMethod {

    @Override
    public boolean authenticate(User user, String input) {

        // Basic null validation
        if (user == null || input == null) {
            return false;
        }

        // Compare stored password with user input
        return user.getPassword().equals(input);
    }
}


