import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class ListOrdersTests extends BaseTest {

    @Test
    @DisplayName("Проверка списка заказов")
    @Description("Проверка списка заказов курьера")
    public void checkGetListOrders() {
        Response response = getOrders();
        response.then().assertThat().body("orders", notNullValue())
                .and().statusCode(200);
    }
}
