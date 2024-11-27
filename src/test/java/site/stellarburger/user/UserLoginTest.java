package site.stellarburger.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;


public class UserLoginTest {
    private UserClient userClient = new UserClient();
    private UserCheck userCheck = new UserCheck();
    User user = UserRandomizer.randomUser();
    String accessToken;

    @Before
    public void createTestUser() {
        ValidatableResponse response = userClient.createUser(user);
        accessToken = userCheck.userCreated(response);
    }

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            ValidatableResponse response = userClient.deleteUser(accessToken);
            userCheck.userDeleted(response);
        }
    }

    @Test
    @DisplayName("Вход в систему под существующим пользователем")
    @Description ("Проверка: пользователь залогинился")
    public void userLogIn() {
            var info = UserAuthInfo.fromUser(user);
            ValidatableResponse response = userClient.logIn(info);
            userCheck.loggedIn(response);
    }

    @Test
    @DisplayName("Вход в систему с неверным логином и паролем")
    @Description ("Проверка: пользователь не залогинился")
    public void userLogInWrongEmailAndPassword() {
        var userFailed = UserRandomizer.emptyEmailPassword();
        var info = UserAuthInfo.fromUser(userFailed);
        ValidatableResponse response = userClient.logIn(info);
        userCheck.incorrectAuth(response);
    }
}
