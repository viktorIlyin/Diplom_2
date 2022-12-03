import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    @Step("Создание нового пользователя")
    public Response createNewUser(UserGenerator user) {
        return given()
                .spec(getSpec())
                .body(user)
                .post(Constants.USER_CREATE_PATH);
    }

    @Step("Вход пользователя")
    public Response loginUser(UserGenerator user) {
        return given()
                .spec(getSpec())
                .body(user)
                .post(Constants.USER_LOGIN_PATH);
    }

    @Step("Изменение данных о пользователе с токеном")
    public Response changeUserDataWithToken(String token, UserGenerator user) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .body(user)
                .patch(Constants.USER_DATA_PATH);
    }

    @Step("Изменение данных о пользователе без токена")
    public Response changeUserDataWithoutToken(UserGenerator user) {
        return given()
                .spec(getSpec())
                .body(user)
                .patch(Constants.USER_DATA_PATH);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .delete(Constants.USER_DATA_PATH);
    }
}
