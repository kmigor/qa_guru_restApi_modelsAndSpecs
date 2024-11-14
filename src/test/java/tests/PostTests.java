package tests;

import models.LoginErrorResponseModel;
import models.LoginRequestModel;
import models.RegisterRequestModel;
import models.RegisterResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PostTests extends TestBase {


    @Test
    @DisplayName("Проверка залогиниться несуществующим пользователем.")
    void postLoginErrorTest() {

        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setEmail("rickrolled@example.com");
        loginRequestModel.setUsername("Rick");
        loginRequestModel.setPassword("Astley");

        LoginErrorResponseModel loginErrorResponseModel =

        given()
                .body(loginRequestModel)
                .contentType(JSON)
        .when()
                .log().uri()
                .log().body()
                .post("/login")

        .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(LoginErrorResponseModel.class);

        assertThat(loginErrorResponseModel.getError())
                .isEqualTo("user not found");
    }

    @Test
    @DisplayName("Проверка регистрации")
    void postRegistrationTest() {

        RegisterRequestModel registerRequestModel = new RegisterRequestModel();
        registerRequestModel.setEmail("emma.wong@reqres.in");
        registerRequestModel.setPassword("Astley");

        RegisterResponseModel registerResponseModel =

        given()
                .body(registerRequestModel)
                .contentType(JSON)
        .when()
                .log().uri()
                .log().body()
                .post("/register")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseModel.class);

        assertThat(registerResponseModel.getId())
                .asInt();
        assertThat(registerResponseModel.getToken())
                .asString()
                .hasSize(17);
    }
}