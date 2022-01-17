package by.kosolobov.barbershop.util.validator;

public class UserValidator {
    private static final String PASSWORD_PATTERN = "^\\w{8,20}$";
    private static final String USERNAME_PATTERN = "^\\w{8,20}$";
    private static final String FIRST_NAME_PATTERN = "^[a-zA-Z]{3,12}$";
    private static final String SECOND_NAME_PATTERN = "^[a-zA-Z]{3,16}$";
    private static final String SUR_NAME_PATTERN = "^[a-zA-Z]{3,16}$";
    private static final String PHONE_PATTERN = "^[+]?\\d{1,3}([(]\\d{2}[)]|\\d{2})\\d{3}[-]?\\d{2}[-]?\\d{2}$";

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    public static boolean validateUsername(String username) {
        return username.matches(USERNAME_PATTERN);
    }

    public static boolean validateFirstName(String name) {
        return name.matches(FIRST_NAME_PATTERN);
    }

    public static boolean validateSecondName(String name) {
        return name.matches(SECOND_NAME_PATTERN);
    }

    public static boolean validateSurName(String name) {
        return name.matches(SUR_NAME_PATTERN);
    }

    public static boolean validatePhone(String phone) {
        return phone.matches(PHONE_PATTERN);
    }
}
