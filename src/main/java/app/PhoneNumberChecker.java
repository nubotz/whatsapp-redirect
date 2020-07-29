package app;

import java.util.regex.Pattern;

public class PhoneNumberChecker {
    private static final Pattern PATTERN = Pattern.compile("^[\\d-\\s()]{6,20}$");

    public static boolean isValidPhoneNumber(String text) {
        return PATTERN.matcher(text).matches();
    }
}
