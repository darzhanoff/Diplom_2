package site.stellarburger.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.stellarburger.user.User;
import site.stellarburger.user.UserCheck;
import site.stellarburger.user.UserClient;
import site.stellarburger.user.UserRandomizer;

public class GetUserOrdersTest {
    private UserClient client = new UserClient();
    private UserCheck check = new UserCheck();
    private OrderClient orderClient = new OrderClient();
    private OrderCheck orderCheck = new OrderCheck();
    User user = UserRandomizer.randomUser();
    String accessToken;

    @Before
    public void createTestUser() {
        ValidatableResponse response = client.createUser(user);
        accessToken = check.userCreated(response);
    }

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            ValidatableResponse response = client.deleteUser(accessToken);
            check.userDeleted(response);
        }
    }

    @Test
    @DisplayName("Получение списка заказов авторизованного пользователя")
    @Description ("Проверка: Список ингредиентов получен если пользователь авторизирован")
    public void getListOfOrderWithAuth() {
        ValidatableResponse response1 = orderClient.listOfIngredients();
        var ids = orderCheck.getIngredientsHash(response1);
        Order order = new Order(new String[]{ids.get(0), ids.get(1)});
        orderClient.authUserWithIngrOrder(accessToken, order);
        ValidatableResponse response2 = orderClient.authUserListOfOrders(accessToken);
        orderCheck.listOfOrdersWithAuth(response2);
    }

    @Test
    @DisplayName("Получение списка заказов неавторизованного пользователя")
    @Description ("Проверка: Список ингредиентов не получен если пользователь неавторизирован")
    public void getListOfOrderNotAuth() {
        ValidatableResponse response = orderClient.notAuthUserListOfOrders();
        orderCheck.listOfOrdersWithoutAuth(response);
    }
}
