package site.stellarburger.order;

import java.util.List;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCheck {

    @Step("получение id ингредиентов")
    public  List<String> getIngredientsHash(ValidatableResponse response) {
        return response
                .assertThat()
                .statusCode(200)
                .extract()
                .path("data._id");
    }

    @Step("Проверка успешного создания заказа")
    public void orderCreated(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue())
                .body("name", notNullValue());
    }

    @Step("Проверка создание заказа без ингредиентов")
    public void orderIngredientsMissed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"))
                .body("success", equalTo(false));
    }

    @Step("Проверка создание заказа неавторизированным пользователем")
    public void notAuthCreatedOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", equalTo(false));
    }

    @Step("Проверка создание заказа с неверным хешем ингредиентов")
    public void orderWrongHashIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(500);
        String body = response.extract().body().asString();
        assertTrue("Ответ сервера не содержит ожидаемого сообщения: " + body,
                body.contains("Internal Server Error"));
    }

    @Step("Проверка списка заказов пользователя с авторизацией")
    public void listOfOrdersWithAuth(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("orders", notNullValue())
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }

    @Step("Проверка списка заказов пользователя без авторизации")
    public void listOfOrdersWithoutAuth(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"))
                .body("success", equalTo(false));
    }
}