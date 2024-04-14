import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class CreateCourierTests extends BaseTest {

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание нового курьера")
    public void checkCreateCourier() {
        createCourier(LOGIN, PASSWORD, FIRST_NAME)
            .then()
            .statusCode(201);
    }

    @Test
    @DisplayName("Попытка создания существующего курьера")
    @Description("Попытка создания курьера с уже существующим логином")
    public void checkTryCreateDoubleLoginCourier() {
        createCourier(LOGIN, PASSWORD, FIRST_NAME)
                .then()
                .statusCode(201);
        createCourier(LOGIN, PASSWORD, FIRST_NAME)
                .then()
                .statusCode(409);
    }

    @Test
    @DisplayName("Попытка создания курьера без \"login\"")
    @Description("Попытка создания курьера без обязательного поля \"login\"")
    public void checkTryCreateCourierWithoutLogin() {
        createCourier(null, PASSWORD, FIRST_NAME)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Попытка создания курьера без \"password\"")
    @Description("Попытка создания курьера без обязательного поля \"password\"")
    public void checkTryCreateCourierWithoutPassword() {
        createCourier(LOGIN, null, FIRST_NAME)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Попытка создания курьера без \"firstName\"")
    @Description("Попытка создания курьера без поля \"firstName\"")
    public void checkTryCreateCourierWithoutFirstName() {
        createCourier(LOGIN, PASSWORD, null)
                .then()
                .statusCode(201);
    }
}
