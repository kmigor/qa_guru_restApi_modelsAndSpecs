import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PostTests extends TestBase {


    @Test
    @DisplayName("Проверка залогиниться несуществующим пользователем.")
    void postLoginErrorTest() {
        given()
                .body("{\"username\": \"Rick\",\"email\": \"rickrolled@example.com\",\"password\": \"Astley\"}")
                .contentType(JSON)

                .when()
                .log().uri()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("user not found"));
    }

    @Test
    @DisplayName("Проверка регистрации")
    void postRegistrationTest() {
        given()
                .body("{\"email\": \"emma.wong@reqres.in\",\"password\": \"Astley\"}")
                .contentType(JSON)

                .when()
                .log().uri()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());
    }
}