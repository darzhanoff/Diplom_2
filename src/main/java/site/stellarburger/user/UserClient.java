package site.stellarburger.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import site.stellarburger.BaseClient;
import site.stellarburger.BaseSpec;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    @Step("Создание уникального пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .body(user)
                .when()
                .post(USER_PATH + "register")
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse logIn(UserAuthInfo info) {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .body(info)
                .when()
                .post(USER_PATH + "login")
                .then();
    }

    @Step("Изменения данных пользователя")
    public ValidatableResponse update(String accessToken, String email, String password,String name) {
        User updatedUser = new User(email, password, name);
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .header("Authorization", accessToken)
                .body(updatedUser)
                .when()
                .patch(USER_PATH + "user")
                .then();
    }

    @Step("Изменения данных пользователя без авторизации")
    public ValidatableResponse updateNotAuth( String email, String password,String name) {
        User updatedUser = new User(email, password, name);
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .body(updatedUser)
                .when()
                .patch(USER_PATH + "user")
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(BaseSpec.getBaseSpec(true))
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH + "user")
                .then();
    }
}
