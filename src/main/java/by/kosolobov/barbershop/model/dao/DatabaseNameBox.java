package by.kosolobov.barbershop.model.dao;

public final class DatabaseNameBox {
    //--------------------------- TABLES ---------------------------//
    public static final String TABLE_USER = "users";
    public static final String TABLE_SERVICE = "services";
    public static final String TABLE_BOOK = "books";
    public static final String TABLE_CERTIFICATE = "certificates";
    public static final String TABLE_BARBER_HAS_SERVICE = "barbers_has_services";

    //--------------------------- COLUMNS ---------------------------//


    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_ROLE = "user_role";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_SECOND_NAME = "second_name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_EMAIL = "user_email";
    public static final String COLUMN_PHONE = "user_phone";
    public static final String COLUMN_DESC = "user_desc";

    public static final String COLUMN_CLIENT_USERNAME = "client_username";
    public static final String COLUMN_BARBER_USERNAME = "barber_username";
    public static final String COLUMN_SERVICE_NAME = "service_name";
    public static final String COLUMN_BOOK_DATE = "book_date";
    public static final String COLUMN_BOOK_TIME = "book_time";
    public static final String COLUMN_BOOK_STATE = "book_state";

    public static final String COLUMN_PRICE = "service_price";

    public static final String COLUMN_CERT_FILE_NAME = "certificate_file_name";
    public static final String COLUMN_CERT_STATE = "certificate_state";

    public static final String[] COLUMNS_USER = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_USER_ROLE,
            COLUMN_FIRST_NAME, COLUMN_SECOND_NAME, COLUMN_SURNAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_DESC};
    public static final String[] COLUMNS_USER_MIN = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_USER_ROLE};
    public static final String[] COLUMNS_SERVICE = {COLUMN_SERVICE_NAME};
    public static final String[] COLUMNS_BOOK = {COLUMN_CLIENT_USERNAME, COLUMN_BARBER_USERNAME, COLUMN_SERVICE_NAME,
            COLUMN_BOOK_DATE, COLUMN_BOOK_TIME, COLUMN_BOOK_STATE};
    public static final String[] COLUMNS_CERTIFICATE = {COLUMN_BARBER_USERNAME, COLUMN_CERT_FILE_NAME, COLUMN_CERT_STATE};
    public static final String[] COLUMNS_BARBER_HAS_SERVICE = {COLUMN_BARBER_USERNAME, COLUMN_SERVICE_NAME, COLUMN_PRICE};


    //--------------------------- ANOTHER STRINGS ---------------------------//


    private DatabaseNameBox() {
    }
}