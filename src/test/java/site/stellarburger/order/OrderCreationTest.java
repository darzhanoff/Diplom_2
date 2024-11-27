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

public class OrderCreationTest {
    private UserClient userClient = new UserClient();
    private UserCheck userCheck = new UserCheck();
    private OrderClient orderClient = new OrderClient();
    private OrderCheck orderCheck = new OrderCheck();
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
    @DisplayName("Создание заказа авторизованного пользователя с ингредиентами")
    @Description ("Проверка: Заказ создается с авторизированным пользователем и с ингредиентами")
    public void createOrderWithAuthAndIngred() {
        ValidatableResponse response1 = orderClient.listOfIngredients();
        var ids = orderCheck.getIngredientsHash(response1);
        Order order = new Order(new String[]{ids.get(0), ids.get(1)});
        ValidatableResponse response2 = orderClient.authUserWithIngrOrder(accessToken, order);
        orderCheck.orderCreated(response2);
    }

    @Test
    @DisplayName("Создание заказа авторизованного пользователя без ингредиентов")
    @Description ("Проверка: Заказ не создается с авторизированным пользователем без ингредиентов")
    public void createOrderWithoutIngred() {
        ValidatableResponse response = orderClient.authUserWithoutIngrOrder(accessToken);
        orderCheck.orderIngredientsMissed(response);
    }

    @Test
    @DisplayName("Создание заказа неавторизованного пользователя с ингредиентами")
    @Description ("Проверка: Заказ не создается неавторизированным пользователем с ингредиентами")
    public void createOrderNotAuthWithIngred() {
        ValidatableResponse response1 = orderClient.listOfIngredients();
        var ids = orderCheck.getIngredientsHash(response1);
        Order order = new Order(new String[]{ids.get(0), ids.get(1)});
        ValidatableResponse response2 = orderClient.notAuthUserWithIngrOrder(order);
        orderCheck.notAuthCreatedOrder(response2);
    }

    @Test
    @DisplayName("Создание заказа неавторизованного пользователя без ингредиентов")
    @Description ("Проверка: Заказ не создается неавторизированным пользователем без ингредиентов")
    public void createOrderNotAuthWithoutIngred() {
        ValidatableResponse response = orderClient.notAuthUserWithoutIngrOrder();
        orderCheck.notAuthCreatedOrder(response);
    }

    @Test
    @DisplayName("Создание заказа с неправильным хэшом ингредиентов")
    @Description ("Проверка: Заказ не создается с авторизированным пользователем и неправильным хэшем ингредиентов")
    public void createOrderWithWrongHashIngred() {
        Order order = OrderRandomizer.randomOrder();
        ValidatableResponse response = orderClient.authUserWithWrongIngrOrder(accessToken, order);
        orderCheck.orderWrongHashIngredients(response);
    }
}
