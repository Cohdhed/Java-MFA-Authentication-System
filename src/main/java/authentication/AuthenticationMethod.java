package authentication;

import model.User;

public interface AuthenticationMethod {

    boolean authenticate(User user, String input);
}