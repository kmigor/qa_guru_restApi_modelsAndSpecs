package tests;

import models.LoginErrorResponseModel;
import models.LoginRequestModel;
import models.RegisterRequestModel;
import models.RegisterResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.Specs.*;

public class PostTests extends TestBase {


    @Test
    @DisplayName("Проверка залогиниться несуществующим пользователем.")
    void postLoginErrorTest() {

        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setEmail("rickrolled@example.com");
        loginRequestModel.setUsername("Rick");
        loginRequestModel.setPassword("Astley");

        LoginErrorResponseModel loginErrorResponseModel = step("Делаем запрос на авторизацию несуществующим пользователем", () ->
                given(postRequestSpec)
                        .body(loginRequestModel)
                        .post("/login")
                        .then()
                        .spec(postLoginResponseSpec)
                        .extract().as(LoginErrorResponseModel.class));

        step("Проверяем ответ, что пользователь не найден", () -> {
            assertThat(loginErrorResponseModel.getError()).isEqualTo("user not found");
        });
    }

    @Test
    @DisplayName("Проверка регистрации")
    void postRegistrationTest() {

        RegisterRequestModel registerRequestModel = new RegisterRequestModel();
        registerRequestModel.setEmail("emma.wong@reqres.in");
        registerRequestModel.setPassword("Astley");

        RegisterResponseModel registerResponseModel = step("Делаем запрос на регистрацию", () ->
                given(postRequestSpec).body(registerRequestModel)
                        .post("/register")
                        .then()
                        .spec(postRegisterResponseSpec)
                        .extract().as(RegisterResponseModel.class));

        step("Проверяем валидность ответа (id имеет тип int, Токен является строкой из 17 символов)", () -> {
            assertThat(registerResponseModel.getId()).asInt();
            assertThat(registerResponseModel.getToken()).asString().hasSize(17);
        });
    }
}