package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.users.UserData;
import model.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

public class UserServiceHelper {

    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"", "");
    private Response response;
    UserData userData = new UserData();

    public UserServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public User getUser(String userId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_USER.replace("{userId}", userId)).then().assertThat().statusCode(200).extract().response().andReturn();

        User actualUser = response.as(User.class);
        response.prettyPrint();
        return actualUser;
    }

    public Response createUser() {
        createUserData();
        response = RestAssured
                .given().body(userData)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post(EndPoints.CREATE_USER).then().assertThat().statusCode(200).extract().response().andReturn();
        return response;
    }

    public Response updateUser(UserData userData, String userId,String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .body(userData)
                .contentType(ContentType.JSON)
                .when()
                .put(EndPoints.UPDATE_USER.replace("{userId}",userId)).then().assertThat().statusCode(200).extract().response().andReturn();

        return response;
    }

    public Response deleteUser(String userId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .when()
                .delete(EndPoints.DELETE_USER.replace("{userId}",userId)).then().assertThat().statusCode(200).extract().response().andReturn();

        response.then().assertThat().statusCode(200);
        return response;
    }

    public String getTokenOfUser() {
        JSONObject jsonObjectRes= new JSONObject(response.asPrettyString());
        return jsonObjectRes.getString("jwt_access_token");
    }

    public String getUserIdOfUser() {
        JSONObject jsonObjectRes= new JSONObject(response.asPrettyString());
        return jsonObjectRes.getJSONObject("user_info").getString("user_id");
    }

    public void createUserData() {
        String phone = RandomStringUtils.random(7,false,true);
        userData.setFullName("Abdo "+generateRandomStrings());
        userData.setWalletId(generateRandomStrings()+".near");
        userData.setEmail(generateRandomStrings()+"@test.com");
        userData.setPhone("001"+phone);
    }

    public String generateRandomStrings() {
        return RandomStringUtils.random(10,97,122,true,true);
    }

    public int getUserStatusCode() {
        return response.getStatusCode();
    }

}
