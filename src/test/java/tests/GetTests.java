package tests;

import models.UserListResponseModel;
import models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.Specs.*;

public class GetTests extends TestBase {

    @Test
    @DisplayName("Проверка получения списка пользователей")
    void getUsersListTest() {

        UserListResponseModel userListResponseModel = step("Делаем запрос получения списка пользователей", () ->

        given(getRequestSpec)
                .get("/users")

        .then()
                .spec(getSuccessResponseSpec)
                .extract().as(UserListResponseModel.class));

        step("Проверяем, что в ответе пришел список из 12 человек, и почту первого в списке", ()-> {
            assertThat(userListResponseModel.getTotal())
                .isEqualTo(12);
            assertThat(userListResponseModel.getData().get(0).getEmail())
                .isEqualTo("george.bluth@reqres.in");
        });
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

        UserModel actualModel = step("Делаем запрос на получения первого в списке человека", () ->
        given(getRequestSpec)
                .get("/users/" + 1)

        .then()
                .spec(getSuccessResponseSpec)
                .extract().as(UserModel.class));

        step("Проверяем каждое поле первого человека", ()-> {
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
        });
    }

    @Test
    @DisplayName("Проверка получения несуществующего пользователя")
    void getUserByIdNotFoundTest() {
        UserModel actualUser = step("Делаем запрос человека с несуществующим id", () ->
        given(getRequestSpec)
                .get("/users/" + 0)

        .then()
                .spec(getUnsuccessResponseSpec)
                .extract().as(UserModel.class));

        step("Проверяем ответ, что ответ пустой (поля == null, id = 0)", ()-> {
            assertThat(actualUser.getData().getEmail())
                    .isNull();
            assertThat(actualUser.getData().getId())
                    .isEqualTo(0);
            assertThat(actualUser.getData().getFirst_name())
                    .isNull();
            assertThat(actualUser.getData().getLast_name())
                    .isNull();
            assertThat(actualUser.getData().getAvatar())
                    .isNull();
        });
    }
}