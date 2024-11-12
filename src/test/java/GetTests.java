import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GetTests extends TestBase {

    String existentId = "1";
    String nonExistentId = "0";

    @Test
    @DisplayName("Проверка получения списка пользователей")
    void getUsersListTest() {
        given()

        .when()
                .log().uri()
                .get("/users")

        .then()
                .log().status()
                .log().body()
                .body("page", is(1))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data[0].id", is(1))
                .body("data[0].email", is("george.bluth@reqres.in"))
                .body("data[0].first_name", is("George"))
                .body("data[0].last_name", is("Bluth"))
                .body("data[0].avatar", is("https://reqres.in/img/faces/1-image.jpg"));
    }

    @Test
    @DisplayName("Проверка получения пользователя по id")
    void getUserByIdSuccessTest() {
        given()

        .when()
                .log().uri()
                .get("/users/" + existentId)

        .then()
                .log().status()
                .log().body()
                .body("data.id", is(1))
                .body("data.email", is("george.bluth@reqres.in"))
                .body("data.first_name", is("George"))
                .body("data.last_name", is("Bluth"))
                .body("data.avatar", is("https://reqres.in/img/faces/1-image.jpg"));
    }

    @Test
    @DisplayName("Проверка получения несуществующего пользователя")
    void getUserByIdNotFoundTest() {
        given()

        .when()
                .log().uri()
                .get("/users/" + nonExistentId)

        .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}