import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests extends BaseCourierTests {

    private String courierId;

    @Override
    @Before
    @DisplayName("Настройка тестовых условий")
    @Description("Настройка Rest-assured, создание курьера")
    public void setup() {
        super.setup();
        createCourier(LOGIN, PASSWORD, FIRST_NAME);
    }

    @Test
    @DisplayName("Проверка входа пользователя")
    @Description("Проверка входа пользователя по логину и паролю")
    public void checkLoginCourier() {
        Response response = loginCourier(LOGIN,PASSWORD);

        response
                .then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Попытка входа пользователя без логина")
    @Description("Попытка входа пользователя без логина")
    public void checkLoginCourierWithoutLogin() {
        loginCourier(null,PASSWORD)
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Попытка входа пользователя без пароля")
    @Description("Попытка входа пользователя без пароля")
    public void checkLoginCourierWithoutPassword() {
        loginCourier(LOGIN,null)
                .then().statusCode(504);
    }

    @Test
    @DisplayName("Попытка входа несуществующего пользователя")
    @Description("Попытка входа несуществующего пользователя с случайным логином и паролем")
    public void checkLoginCourierNonExisting() {
        loginCourier(faker.name().username(), faker.internet().password())
                .then().statusCode(404);
    }

    @Test
    @DisplayName("Попытка входа пользователя с неверным паролем")
    @Description("Попытка входа пользователя с случайным паролем")
    public void checkLoginCourierWrongPassword() {
        loginCourier(LOGIN,faker.internet().password())
                .then().statusCode(404);
    }

}
