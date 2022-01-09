package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.users.User;

public class UserServiceHelper {
    //fetch the data from the endpoints
    // GET / POST / PATCH / DELETE
    // we need to read config properties

    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"", "");
    private Response response;

    public UserServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public User getUser(String userId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_USER.replace("{userId}", userId)).andReturn();

        User actualUser = response.as(User.class);
        response.prettyPrint();
        return actualUser;
    }

    public Response createUser(User user) {
        response = RestAssured
                .given().body(user.getData())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post(EndPoints.CREATE_USER).andReturn();

        return response;
    }

    public Response updateUser(User user, String userId,String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .body(user.getData())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .put(EndPoints.UPDATE_USER.replace("{userId}",userId)).andReturn();

        return response;
    }

    public Response deleteUser(String userId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .when()
                .delete(EndPoints.DELETE_USER.replace("{userId}",userId)).andReturn();

        return response;
    }
    public int getUserStatusCode() {
        return response.getStatusCode();
    }

}
