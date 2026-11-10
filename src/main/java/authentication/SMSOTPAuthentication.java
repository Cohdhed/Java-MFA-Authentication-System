package authentication;

import model.User;
import java.util.Random;

/*
 * Strategy implementation responsible for
 * generating and validating SMS OTP codes.
 * SMS delivery is simulated for this prototype.
 */
public class SMSOTPAuthentication implements AuthenticationMethod {

    private String generatedOTP;

    private void generateOTP() {
        Random random = new Random();
        generatedOTP = String.valueOf(100000 + random.nextInt(900000));
    }

    @Override
    public boolean authenticate(User user, String input) {

        if (generatedOTP == null) {

            generateOTP();

            System.out.println("SMS OTP sent to: " + user.getPhoneNumber());
            System.out.println("Simulated SMS OTP: " + generatedOTP);

            return false;
        }

        return generatedOTP.equals(input);
    }
}