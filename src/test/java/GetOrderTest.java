import api.client.Constants;
import api.client.OrderClient;
import api.client.UserClient;
import api.data.UserGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetOrderTest {
    String authToken;
    UserClient userClient;
    OrderClient orderClient;
    UserGenerator user;
    String correctIngredients = "{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa75\",\"61c0c5a71d1f82001bdaaa70\"]\n}";

    @Before
    public void setUp() {
        user = UserGenerator.generateUser();
        orderClient = new OrderClient();
        userClient = new UserClient();

        Response createUserResponse = userClient.createNewUser(user);
        authToken = createUserResponse.path("accessToken");
        orderClient.createOrderWithToken(authToken.substring(7), correctIngredients);
    }

    @After
    public void cleanUp() {
        if (authToken != null) {
            userClient.deleteUser(authToken.substring(7));
        }
    }

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void GetOrdersFromUserWithAuth() {
        Response response = orderClient.getUserOrdersWithToken(authToken.substring(7));
        assertThat("Ответа не содержит параметра success со значением true", response.path("success"), equalTo(true));
        assertThat("Вернулся код ответа, отличный от ожидаемого 200 success", response.statusCode(), equalTo(SC_OK));
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void GetOrdersFromUserWithoutAuth() {
        Response response = orderClient.getUserOrdersWithoutToken();
        assertThat("Ответ содержит номер заказа", response.path("order.number"), nullValue());
        assertThat("Ответ не содержит параметра success со значением false", response.path("success"), equalTo(false));
        assertThat("Вернулся код ответа, отличный от ожидаемого 401 unauthorized", response.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat("Вернулось сообщение, не соответствующее ожидаемому", response.path("message"), equalTo(Constants.UNAUTHORIZED_ERROR_MESSAGE));
    }
}

