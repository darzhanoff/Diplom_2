package site.stellarburger.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.After;

public class UserCreationTest {
    private UserClient userClient = new UserClient();
    private UserCheck userCheck = new UserCheck();
    String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            ValidatableResponse response = userClient.deleteUser(accessToken);
            userCheck.userDeleted(response);
        }
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description ("Проверка: пользователь создан")
    public void createUser() {
            var user = UserRandomizer.randomUser();
            ValidatableResponse response = userClient.createUser(user);
            accessToken = userCheck.userCreated(response);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description ("Проверка: пользователь который уже зарегистрирован не создан")
    public void createExistingUser() {
        var user = UserRandomizer.randomUser();
        ValidatableResponse response1 = userClient.createUser(user);
        accessToken = userCheck.userCreated(response1);
        var userExistingLogin = user;
        ValidatableResponse response2 = userClient.createUser(userExistingLogin);
        userCheck.userAlreadyExist(response2);
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля Name")
    @Description ("Проверка: пользователь без имени не создан")
    public void createUserWithoutName() {
        var user = UserRandomizer.emptyName();
        ValidatableResponse response = userClient.createUser(user);
        userCheck.userFieldsMissed(response);
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля Email")
    @Description ("Проверка: пользователь без почты не создан")
    public void createUserWithoutEmail() {
        var user = UserRandomizer.emptyEmail();
        ValidatableResponse response = userClient.createUser(user);
        userCheck.userFieldsMissed(response);
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля Password")
    @Description ("Проверка: пользователь без пароля не создан")
    public void createUserWithoutPassword() {
        var user = UserRandomizer.emptyPassword();
        ValidatableResponse response = userClient.createUser(user);
        userCheck.userFieldsMissed(response);
    }

    @Test
    @DisplayName("Создание пользователя без обязательных полей Name, email")
    @Description ("Проверка: пользователь без имени и почты не создан")
    public void createUserWithoutNameAndEmail() {
        var user = UserRandomizer.emptyNameEmail();
        ValidatableResponse response = userClient.createUser(user);
        userCheck.userFieldsMissed(response);
    }

    @Test
    @DisplayName("Создание пользователя без обязательных полей Name, Password")
    @Description ("Проверка: пользователь без имени и пароля не создан")
    public void createUserWithoutNameAndPassword() {
        var user = UserRandomizer.emptyNamePassword();
        ValidatableResponse response = userClient.createUser(user);
        userCheck.userFieldsMissed(response);
    }

    @Test
    @DisplayName("Создание пользователя без обязательных полей Email, Password")
    @Description ("Проверка: пользователь без почты и пароля не создан")
    public void createUserWithoutEmailAndPassword() {
        var user = UserRandomizer.emptyEmailPassword();
        ValidatableResponse response = userClient.createUser(user);
        userCheck.userFieldsMissed(response);
    }

    @Test
    @DisplayName("Создание пользователя без всех обязательных полей")
    @Description ("Проверка: пользователь без всех обязательных полей не создан")
    public void createUserWithoutAllRequiredFields() {
        var user = UserRandomizer.allFieldsEmpty();
        ValidatableResponse response = userClient.createUser(user);
        userCheck.userFieldsMissed(response);
    }
}
