package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import model.users.UserData;
import org.apache.commons.lang3.RandomStringUtils;

public class UserServiceHelper {

    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"", "");
    private Response response;
    private UserData userData = new UserData();

    public UserServiceHelper() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.registerParser("text/plain", Parser.JSON);
    }

    public Response createUser(UserData userData) {
        response = RestAssured
                .given().body(userData)
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post(EndPoints.CREATE_USER)
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getUser(String userId, String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_USER.replace("{userId}", userId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response updateUser(UserData userData, String userId,String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .body(userData)
                .contentType(ContentType.JSON)
                .put(EndPoints.UPDATE_USER.replace("{userId}",userId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response deleteUser(String userId, String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .when()
                .delete(EndPoints.DELETE_USER.replace("{userId}",userId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getWrongUser(String userId, String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_USER.replace("{userId}", userId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response createWrongUser(UserData userData) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .body(userData)
                .contentType(ContentType.JSON)
                .log().all()
                .post(EndPoints.CREATE_USER)
                .andReturn();

        response.prettyPrint();
        return response;
    }
}
