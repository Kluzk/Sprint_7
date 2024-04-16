import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;

public class BaseCourierTests extends BaseTest {

    @After
    @DisplayName("Удаление тестовых данных")
    @Description("Удаление курьера после тестов")
    public void teardown() {
        Response response = loginCourier(LOGIN, PASSWORD);
        JsonPath json = new JsonPath(response.then().extract().asString());
        String id = json.getString("id");
        if(id != null) deleteCourier(id).statusCode();
    }

}
