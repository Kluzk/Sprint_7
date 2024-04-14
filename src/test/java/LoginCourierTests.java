import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests extends BaseTest {

    private String courierId;

    @Before
    public void setup() {
        super.setup();
        createCourier(LOGIN, PASSWORD, FIRST_NAME);
    }

    @Test
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
    public void checkLoginCourierWithoutLogin() {
        loginCourier(null,PASSWORD)
                .then().statusCode(400);
    }

    @Test
    public void checkLoginCourierWithoutPassword() {
        loginCourier(LOGIN,null)
                .then().statusCode(504);
    }

    @Test
    public void checkLoginCourierNonExisting() {
        loginCourier("another_courier","1234")
                .then().statusCode(404);
    }

    @Test
    public void checkLoginCourierWrongPassword() {
        loginCourier(LOGIN,"1234")
                .then().statusCode(404);
    }

}
