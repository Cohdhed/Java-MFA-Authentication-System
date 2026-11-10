package authentication;

import model.User;
import java.util.Random;

/*
 * Strategy implementation responsible for
 * generating and validating Email OTP codes.
 * The email sending is simulated for prototype purposes.
 */
public class EmailOTPAuthentication implements AuthenticationMethod {

    private String generatedOTP;

    // Generates a random 6-digit OTP
    private void generateOTP() {
        Random random = new Random();
        generatedOTP = String.valueOf(100000 + random.nextInt(900000));
    }

    @Override
    public boolean authenticate(User user, String input) {

        // First call generates OTP
        if (generatedOTP == null) {

            generateOTP();

            System.out.println("Email OTP sent to: " + user.getEmail());
            System.out.println("Simulated Email OTP: " + generatedOTP);

            return false;
        }

        // Second call validates user input
        return generatedOTP.equals(input);
    }
}