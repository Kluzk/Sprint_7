import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {

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

    @After
    @Step("Отмена заказа")
    public void teardown() {
        if(track != null) cancelOrder(track);
    }


    @Test
    @DisplayName("Проверка создания заказа")
    @Description("Проверка создания заказов с различными цветами")
    @Parameterized.Parameters
    public void checkCreateOrders() {
        Response response = createOrder(color);

        response.then().assertThat().body("track", notNullValue())
                .and().statusCode(201);



        JsonPath json = new JsonPath(response.then().extract().asString());
        track = json.getString("track");
    }

}
