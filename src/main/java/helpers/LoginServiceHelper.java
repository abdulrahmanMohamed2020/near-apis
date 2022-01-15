package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class LoginServiceHelper {
    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;

    public LoginServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createLoginOtp() {

        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\"walletName\": \"permanentuser.near\"}")
                .log().all()
                .post(EndPoints.CREATE_TRIGGER_LOGIN_OTP)
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response verifyLoginOtp() {

        response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"walletName\": \"permanentuser.near\",\n" +
                        "    \"nonce\": \"123456\"\n" +
                        "}")
                .log().all()
                .post(EndPoints.VERIFY_LOGIN_OTP)
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }
}
