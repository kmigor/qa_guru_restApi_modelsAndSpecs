package tests;

import models.UserListResponseModel;
import models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class GetTests extends TestBase {

    @Test
    @DisplayName("Проверка получения списка пользователей")
    void getUsersListTest() {

        UserListResponseModel userListResponseModel =

        given()

        .when()
                .log().uri()
                .get("/users")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(UserListResponseModel.class);

        assertThat(userListResponseModel.getTotal())
                .isEqualTo(12);
        assertThat(userListResponseModel.getData().get(0).getEmail())
                .isEqualTo("george.bluth@reqres.in");
    }

    @Test
    @DisplayName("Проверка получения пользователя по id")
    void getUserByIdSuccessTest() {
        UserModel expectedModel = new UserModel();
        expectedModel.getData().setId(1);
        expectedModel.getData().setEmail("george.bluth@reqres.in");
        expectedModel.getData().setFirst_name("George");
        expectedModel.getData().setLast_name("Bluth");
        expectedModel.getData().setAvatar("https://reqres.in/img/faces/1-image.jpg");
        expectedModel.getSupport().setUrl("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral");
        expectedModel.getSupport().setText("Tired of writing endless social media content? Let Content Caddy generate it for you.");

        UserModel actualModel =
        given()

        .when()
                .log().uri()
                .get("/users/" + 1)

        .then()
                .log().status()
                .log().body()
                .extract().as(UserModel.class);

        assertThat(actualModel.getData().getId())
                .isEqualTo(expectedModel.getData().getId());
        assertThat(actualModel.getData().getEmail())
                .isEqualTo(expectedModel.getData().getEmail());
        assertThat(actualModel.getData().getFirst_name())
                .isEqualTo(expectedModel.getData().getFirst_name());
        assertThat(actualModel.getData().getLast_name())
                .isEqualTo(expectedModel.getData().getLast_name());
        assertThat(actualModel.getData().getAvatar())
                .isEqualTo(expectedModel.getData().getAvatar());
    }

    @Test
    @DisplayName("Проверка получения несуществующего пользователя")
    void getUserByIdNotFoundTest() {
        given()

        .when()
                .log().uri()
                .get("/users/" + 0)

        .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}