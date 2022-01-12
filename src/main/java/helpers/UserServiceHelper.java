package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.users.Data;
import model.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

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

    public String getTokenOfUser() {
        JSONObject jsonObjectRes= new JSONObject(response.asPrettyString());
        return jsonObjectRes.getString("jwt_access_token");
    }

    public String getUserIdOfUser() {
        JSONObject jsonObjectRes= new JSONObject(response.asPrettyString());
        return jsonObjectRes.getJSONObject("user_info").getString("user_id");
    }

    public User createUserBody() {
        String phone = RandomStringUtils.random(7,false,true);
        Data body = new Data();
        body.setFullName("Abdo "+generateRandomStrings());
        body.setWalletId(generateRandomStrings()+".near");
        body.setEmail(generateRandomStrings()+"@test.com");
        body.setPhone("001"+phone);

        User userBody = new User();
        userBody.setData(body);
        return userBody;
    }

    public String generateRandomStrings() {
        return RandomStringUtils.random(10,97,122,true,true);
    }

    public int getUserStatusCode() {
        return response.getStatusCode();
    }

}
