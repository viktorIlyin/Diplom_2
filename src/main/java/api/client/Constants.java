package api.client;

public class Constants {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";
    public static final String USER_CREATE_PATH = "auth/register";
    public static final String USER_DATA_PATH = "auth/user";
    public static final String USER_LOGIN_PATH = "auth/login";
    public static final String ORDERS_PATH = "orders";
    public static final String INGREDIENTS_MISSING_ERROR_MESSAGE = "Ingredient ids must be provided";
    public static final String INCORRECT_LOGIN_DATA_ERROR_MESSAGE = "email or password are incorrect";
    public static final String UNAUTHORIZED_ERROR_MESSAGE = "You should be authorised";
    public static final String EXISTS_EMAIL_ERROR_MESSAGE = "User with such email already exists";
    public static final String REQUIRED_FIELDS_MISSING_ERROR_MESSAGE = "Email, password and name are required fields";
    public static final String EXISTS_USER_ERROR_MESSAGE = "User already exists";
}
