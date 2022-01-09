package tests;

import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.users.Data;
import model.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.json.JSONObject;

import static org.testng.Assert.*;

public class TestGETUser {

    private User user;
    private UserServiceHelper userServiceHelper;
    private String userToken;
    private String userId;

    @BeforeClass
    public void init() {
        userServiceHelper = new UserServiceHelper();
    }

    @Test(priority=0)
    public void testCreateUser() {
        String phone = RandomStringUtils.random(7,false,true);
        Data body = new Data();
        body.setFullName("Abdo "+generateRandomStrings());
        body.setWalletId(generateRandomStrings()+".near");
        body.setEmail(generateRandomStrings()+"@test.com");
        body.setPhone("001"+phone);

        user = new User();
        user.setData(body);

        Response response = userServiceHelper.createUser(user);
        assertFalse(response.asString().contains("already exists"));
        assertFalse(response.asString().contains("is needed"));

        JSONObject jsonObjectRes= new JSONObject(response.asPrettyString());
        System.out.println(jsonObjectRes);
        userToken = jsonObjectRes.getString("jwt_access_token");
        userId = jsonObjectRes.getJSONObject("user_info").getString("user_id");

        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
    }

    @Test(priority=1,dependsOnMethods = {"testCreateUser"})
    public void testGetUser() {
        User actualUser = userServiceHelper.getUser(userId, userToken);

        assertEquals(userServiceHelper.getUserStatusCode(), 200, "The status code should be 200");
        assertNotNull(actualUser, "The user data is empty");
        assertEquals(actualUser.getData().getUserId(),userId, "The user data is empty");
    }

    @Test(priority=2,dependsOnMethods = {"testCreateUser"})
    public void testUpdateUser() {
        String updatedFullName = generateRandomStrings();
        user.getData().setFullName(updatedFullName);

        Response response = userServiceHelper.updateUser(user,userId,userToken);

        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
        assertEquals(user.getData().getFullName(),updatedFullName,"The full name doesn't get updated");
    }

    @Test(priority=3,dependsOnMethods = {"testCreateUser"})
    public void testDeleteUser() {
        Response response = userServiceHelper.deleteUser(userId,userToken);

        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
    }

    @Test(priority=4,dependsOnMethods = {"testCreateUser"})
    public void verifyTheUserStatusAfterDeleting() {
        User actualUser = userServiceHelper.getUser(userId, userToken);

        assertEquals(userServiceHelper.getUserStatusCode(), 200, "The status code should be 200");
        assertTrue(actualUser.getData().getStatus().equals("deleted"));
    }

    private String generateRandomStrings() {
        return RandomStringUtils.random(7,97,122,true,true);
    }
}