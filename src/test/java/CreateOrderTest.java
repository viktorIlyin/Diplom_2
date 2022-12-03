import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateOrderTest {
    String authToken;
    UserGenerator user;
    OrderClient orderClient;
    UserClient userClient;
    String correctIngredients = "{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa75\",\"61c0c5a71d1f82001bdaaa70\"]\n}";
    String emptyIngredients = "{\n\"ingredients\": []\n}";
    String wrongIngredients = "{\n\"ingredients\": [\"bluhbluhbluh\",\"bluhbluhbluhbluh\"]\n}";


    @Before
    @DisplayName("Создаём пользователя")
    public void setUp() {
        user = UserGenerator.generateUser();
        orderClient = new OrderClient();
        userClient = new UserClient();
        Response createUserResponse = userClient.createNewUser(user);
        authToken = createUserResponse.path("accessToken");
    }

    @After
    @DisplayName("Удаляем созданного пользователя")
    public void cleanUp() {
        if (authToken != null) {
            userClient.deleteUser(authToken.substring(7));
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и списком ингредиентов")
    public void CreateOrderWithAuthAndWithIngredientsTest() {
        Response response = orderClient.createOrderWithToken(authToken.substring(7), correctIngredients);
        assertThat("Ответ не содержит номер заказа", response.path("order.number"), notNullValue());
        assertThat("Ответ не содержит параметра success со значением true", response.path("success"), equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и со списком ингредиентов")
    public void CreateOrderWithoutAuthAndWithIngredientsTest() {
        Response response = orderClient.createOrderWithoutToken(correctIngredients);
        assertThat("Ответа не содержит номер заказа", response.path("order.number"), notNullValue());
        assertThat("Ответа не содержит параметра success со значением true", response.path("success"), equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и без списка ингредиентов")
    public void CreateOrderWithAuthAndWithoutIngredientsTest() {
        Response response = orderClient.createOrderWithToken(authToken.substring(7), emptyIngredients);
        assertThat("Вернулся код ответа, отличный от ожидаемого 400 bad request", response.statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ответ не содержит параметра success со значением false", response.path("success"), equalTo(false));
        assertThat("Вернулось сообщение, не соответствующее ожидаемому", response.path("message"), equalTo(Constants.INGREDIENTS_MISSING_ERROR_MESSAGE));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и без списка ингредиентов")
    public void CreateOrderWithoutAuthAndWithoutIngredientsTest() {
        Response response = orderClient.createOrderWithoutToken(emptyIngredients);
        assertThat("Вернулся код ответа, отличный от ожидаемого 400 bad request", response.statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("Ответ не содержит параметра success со значением false", response.path("success"), equalTo(false));
        assertThat("Вернулось сообщение, не соответствующее ожидаемому", response.path("message"), equalTo(Constants.INGREDIENTS_MISSING_ERROR_MESSAGE));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и с неверным хэшем ингредиентов")
    public void CreateOrderWithAuthAndWrongIngredientsTest() {
        Response response = orderClient.createOrderWithToken(authToken.substring(7), wrongIngredients);
        assertThat("Вернулся код ответа, отличный от ожидаемого 500 internal server error", response.statusCode(), equalTo(SC_INTERNAL_SERVER_ERROR));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и с неверным хэшем ингредиентов")
    public void CreateOrderWithoutAuthAndWrongIngredientsTest() {
        Response response = orderClient.createOrderWithoutToken(wrongIngredients);
        assertThat("Вернулся код ответа, отличный от ожидаемого 500 internal server error", response.statusCode(), equalTo(SC_INTERNAL_SERVER_ERROR));
    }

}
