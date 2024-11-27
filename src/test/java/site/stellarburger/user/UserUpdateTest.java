package site.stellarburger.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class UserUpdateTest {
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
    @DisplayName("Изменение данных авторизованного пользователя")
    @Description("Проверка: данные обновились")
    public void updateLogInInfo() {
        String newEmail = "updated.email@gmail.com";
        String newPassword = "updPass123";
        String newName = "Updated Name";
        ValidatableResponse response = userClient.update(accessToken, newEmail, newPassword, newName);
        userCheck.updatedUser(response);
    }

    @Test
    @DisplayName("Изменение данных неавторизованного пользователя")
    @Description("Проверка: данные не обновились")
    public void updateLogInInfoWithoutAuth() {
        String newEmail = "updated.email@gmail.com";
        String newPassword = "updPass123";
        String newName = "Updated Name";
        ValidatableResponse response = userClient.updateNotAuth(newEmail, newPassword, newName);
        userCheck.updatedUserWithoutAuth(response);
    }
}
