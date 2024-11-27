package site.stellarburger;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class BaseClient {
    protected static final String ORDER_PATH = "/api/orders/";
    protected static final String INGREDIENTS_PATH = "/api/ingredients/";
    protected static final String USER_PATH = "/api/auth/";
}
