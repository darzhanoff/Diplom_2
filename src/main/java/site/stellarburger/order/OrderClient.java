package site.stellarburger.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.stellarburger.BaseClient;
import site.stellarburger.BaseSpec;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {

    @Step("Запрос списка ингредиентов")
    public ValidatableResponse listOfIngredients() {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .when()
                .get(INGREDIENTS_PATH)
                .then();
    }

    @Step("Создание заказа с авторизацией и ингредиентами")
    public ValidatableResponse authUserWithIngrOrder(String accessToken, Order order) {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Создание заказа с авторизацией без ингредиентов")
    public ValidatableResponse authUserWithoutIngrOrder(String accessToken) {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .header("Authorization", accessToken)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Создание заказа без авторизации с ингредиентами")
    public ValidatableResponse notAuthUserWithIngrOrder(Order order) {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Создание заказа без авторизации и без ингредиентов")
    public ValidatableResponse notAuthUserWithoutIngrOrder() {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Создание заказа с неверными хешами ингредиентов")
    public ValidatableResponse authUserWithWrongIngrOrder (String accessToken, Order order) {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получение списка заказов с авторизацией")
    public ValidatableResponse authUserListOfOrders (String accessToken) {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("Получение списка заказов без авторизации")
    public ValidatableResponse notAuthUserListOfOrders() {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .when()
                .get(ORDER_PATH)
                .then();
    }
}