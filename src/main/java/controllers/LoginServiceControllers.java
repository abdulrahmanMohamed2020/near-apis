package controllers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class LoginServiceControllers {
    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;

    public LoginServiceControllers() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

    public Response createLoginOtp() {

        response = RestAssured
                .given().filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body("{\"walletName\": \"permanentuser.near\"}")
                .log().all()
                .post(EndPoints.CREATE_TRIGGER_LOGIN_OTP)
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response verifyLoginOtp() {

        response = RestAssured
                .given().filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"walletName\": \"permanentuser.near\",\n" +
                        "    \"nonce\": \"123456\"\n" +
                        "}")
                .log().all()
                .post(EndPoints.VERIFY_LOGIN_OTP)
                .andReturn();

        response.prettyPrint();
        return response;
    }
}
