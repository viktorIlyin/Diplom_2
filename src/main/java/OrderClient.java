import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    @Step("Создание заказа с токеном")
    public Response createOrderWithToken(String token, String ingredient) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .body(ingredient)
                .post(Constants.ORDERS_PATH);
    }

    @Step("Получение списка заказов пользователя с токеном")
    public Response getUserOrdersWithToken(String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .get(Constants.ORDERS_PATH);
    }

    @Step("Создание заказа без токена")
    public Response createOrderWithoutToken(String ingredient) {
        return given()
                .spec(getSpec())
                .body(ingredient)
                .post(Constants.ORDERS_PATH);
    }

    @Step("Получение списка заказов пользователя без токена")
    public Response getUserOrdersWithoutToken() {
        return given()
                .spec(getSpec())
                .get(Constants.ORDERS_PATH);
    }
}
