import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.CreateOrderRequest;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {

    private static final String SERVICE_ORDER = "/api/v1/orders";
    private static final String FIRST_NAME = "Klara";
    private static final String LAST_NAME = "Popkin";
    private static final String ADDRESS = "Gusarovo, 17";
    private static final int METRO_STATION = 4;
    private static final String PHONE = "+7 123 321 11 22";
    private static final int RENT_TIME = 4;
    private static final String DELIVERY_DATE = "2024-05-15";
    private static final String COMMENT = "Go home";
    private String[] color;

    private String track;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Collection colors() {
        return Arrays.asList( new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}}
        });
    }

    @Override
    @After
    public void teardown() {
        if(track != null) cancelOrder(track);
    }

    @Step
    private Response cancelOrder(String track) {
        return given()
                .contentType("application/json")
                .body("{\"track\":" + track + "}")
                .put(SERVICE_ORDER + "/cancel");
    }

    @Step
    private Response createOrder(String[] color) {
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

    @Test
    @Parameterized.Parameters
    public void checkCreateOrders() {
        Response response = createOrder(color);
        response.then().assertThat().body("track", notNullValue())
                .and().statusCode(201);

        JsonPath json = new JsonPath(response.then().extract().asString());
        track = json.getString("track");
    }

}
