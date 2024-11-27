package site.stellarburger.user;

import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class UserCheck {

    @Step("Проверка успешного создания уникального пользователя")
    public String userCreated(ValidatableResponse response) {
        String accessToken = response
                .assertThat()
                .statusCode(200)
                .extract()
                .path("accessToken");

        assertNotNull("Access token должен быть получен", accessToken);
        return accessToken;
    }

    @Step("Проверка неудачного создания уже зарегистрированного пользователя")
    public void userAlreadyExist(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"))
                .body("success", equalTo(false));
    }

    @Step("Проверка неудачного создания пользователя без обязательных полей")
    public void userFieldsMissed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"))
                .body("success", equalTo(false));
    }

    @Step("Проверка успешной авторизации пользователя")
    public void loggedIn(ValidatableResponse loginResponse) {
        loginResponse.assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user", notNullValue());
    }

    @Step("Проверка ошибки при авторизации с неверным логином и паролем")
    public void incorrectAuth(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"))
                .body("success", equalTo(false));
    }

    @Step("Проверка успешного редактирования пользователя с авторизацией")
    public void updatedUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user", notNullValue());
    }

    @Step("Проверка ошибки редактирования без авторизации")
    public void updatedUserWithoutAuth(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"))
                .body("success", equalTo(false));
    }

    @Step("Проверка успешного удаления пользователя")
    public void userDeleted(ValidatableResponse response) {
        response.assertThat()
                .statusCode(202)
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));
    }
}