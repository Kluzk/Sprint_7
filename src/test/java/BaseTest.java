import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.*;
import org.junit.Before;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;


public class BaseTest {
    private static final String URI = "http://qa-scooter.praktikum-services.ru/";
    private static final String SERVICE_COURIER = "/api/v1/courier";
    private static final String SERVICE_LOGIN_COURIER = SERVICE_COURIER + "/login";
    private static final String SERVICE_ORDER = "/api/v1/orders";

    protected static Faker faker = new Faker();

    protected static final String LOGIN = faker.name().username();
    protected static final String PASSWORD = faker.internet().password();
    protected static final String FIRST_NAME = faker.name().firstName();
    private static final String LAST_NAME = faker.name().lastName();
    private static final String ADDRESS = faker.address().fullAddress();
    private static final int METRO_STATION = faker.number().numberBetween(1, 237);
    private static final String PHONE = faker.phoneNumber().phoneNumber();
    private static final int RENT_TIME = faker.number().numberBetween(1, 254);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final String DELIVERY_DATE = sdf.format(faker.date().future(1,TimeUnit.DAYS));
    private static final String COMMENT = faker.dune().saying();

    @Before
    @DisplayName("Настройка тестовых условий")
    @Description("Настройка Rest-assured")
    public void setup() {
        RestAssured.baseURI = URI;
    }

    @Step("Создание курьера")
    protected Response createCourier(String login, String password, String firstName) {
        CreateCourierRequest createCourier = new CreateCourierRequest(login, password, firstName);

        return given()
                .contentType("application/json")
                .body(createCourier)
                .post(SERVICE_COURIER);
    }

    @Step("Удаление курьера")
    protected Response deleteCourier(String id) {
        DeleteCourierRequest deleteCourier = new DeleteCourierRequest(id);

        return given()
                .contentType("application/json")
                .body(deleteCourier)
                .delete(SERVICE_COURIER + "/" + id);
    }

    @Step("Вход курьера в систему")
    protected Response loginCourier(String login, String password) {
        return given()
                .contentType("application/json")
                .body(new LoginCourierRequest(login, password))
                .post(SERVICE_LOGIN_COURIER);
    }

    @Step("Отмена заказа")
    protected Response cancelOrder(String track) {
        return given()
                .contentType("application/json")
                .body("{\"track\":" + track + "}")
                .put(SERVICE_ORDER + "/cancel");
    }

    @Step("Создание заказа")
    protected Response createOrder(String[] color) {
        return given()
                .contentType("application/json")
                .body(new CreateOrderRequest(FIRST_NAME,
                        LAST_NAME,
                        ADDRESS,
                        METRO_STATION,
                        PHONE,
                        RENT_TIME,
                        DELIVERY_DATE,
                        COMMENT,
                        color))
                .post(SERVICE_ORDER);
    }

    @Step("Получение списка заказов")
    protected Response getOrders() {
        return given()
                .contentType("application/json")
                .get(SERVICE_ORDER);
    }
}
