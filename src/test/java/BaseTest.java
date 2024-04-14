import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.CreateCourierRequest;
import models.DeleteCourierRequest;
import models.LoginCourierRequest;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class BaseTest {
    private static final String URI = "http://qa-scooter.praktikum-services.ru/";
    private static final String SERVICE_COURIER = "/api/v1/courier";
    private static final String SERVICE_LOGIN_COURIER = SERVICE_COURIER + "/login";
    protected static final String LOGIN = "courierVorok";
    protected static final String PASSWORD = "qwerty";
    protected static final String FIRST_NAME = "Vorok";

    @Before
    public void setup() {
        RestAssured.baseURI = URI;
    }

    @After
    public void teardown() {
        Response response = loginCourier(LOGIN, PASSWORD);
        JsonPath json = new JsonPath(response.then().extract().asString());
        String id = json.getString("id");
        if(id != null) deleteCourier(id).statusCode();
    }

    @Step
    protected Response createCourier(String login, String password, String firstName) {
        CreateCourierRequest createCourier = new CreateCourierRequest(login, password, firstName);

        return given()
                .contentType("application/json")
                .body(createCourier)
                .post(SERVICE_COURIER);
    }

    @Step
    protected Response deleteCourier(String id) {
        DeleteCourierRequest deleteCourier = new DeleteCourierRequest(id);

        return given()
                .contentType("application/json")
                .body(deleteCourier)
                .delete(SERVICE_COURIER + "/" + id);
    }

    @Step
    public Response loginCourier(String login, String password) {
        return given()
                .contentType("application/json")
                .body(new LoginCourierRequest(login, password))
                .post(SERVICE_LOGIN_COURIER);
    }
}
