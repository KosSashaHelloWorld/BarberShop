package by.kosolobov.barbershop.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlValidator {
    private static final String QUERY_PATTERN = "[^;]++";

    public static String getValidQuery(String invalid) {
        Matcher matcher = Pattern.compile(QUERY_PATTERN).matcher(invalid);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "invalid query";
        }
    }
}
