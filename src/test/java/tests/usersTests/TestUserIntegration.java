package tests.usersTests;

import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.users.User;
import model.users.UserData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class TestUserIntegration {

    private UserData userData = new UserData();
    private UserServiceHelper userServiceHelper;
    private Response response;
    private String userToken;
    private String userId;

    @BeforeClass
    public void init() {
        userServiceHelper = new UserServiceHelper();
    }

    @Test()
    public void testCreateUser() {

        response = userServiceHelper.createUser();
        assertFalse(response.asString().contains("already exists"),"User already exists");
        assertFalse(response.asString().contains("is needed"),"one or more attribute is needed");

        userToken = userServiceHelper.getTokenOfUser();
        userId = userServiceHelper.getUserIdOfUser();
    }

    @Test(priority=1,dependsOnMethods = {"testCreateUser"})
    public void testGetUser() {
        User actualUser = userServiceHelper.getUser(userId, userToken).as(User.class);

        assertEquals(actualUser.getMessage(), "User retrieved successfully!");
        assertNotNull(actualUser, "The user data is empty");
    }

    @Test(priority = 2,dependsOnMethods = {"testCreateUser"})
    public void testUserAttributes() {
        User actualUser = userServiceHelper.getUser(userId, userToken).as(User.class);


        assertEquals(actualUser.getMessage(), "User retrieved successfully!");
        assertNotNull(actualUser, "The user data is empty");
        assertFalse(actualUser.getUserData().getUserId().isEmpty(),"The Full Name is empty");
        assertFalse(actualUser.getUserData().getFullName().isEmpty(),"The Full Name is empty");
        assertFalse(actualUser.getUserData().getUserId().isEmpty(),"The User ID is empty");
        assertFalse(actualUser.getUserData().getWalletName().isEmpty(),"The Wallet Name is empty");
        assertFalse(actualUser.getUserData().getStatus().isEmpty(),"The Status is empty");
        assertFalse(actualUser.getUserData().getWalletStatus().isEmpty(),"The Wallet Status is empty");
        assertEquals(actualUser.getUserData().getStatus(),"active", "The user status should be Active");
    }

    @Test(priority=3,dependsOnMethods = {"testCreateUser"})
    public void testUpdateUser() {
        String updatedFullName = userServiceHelper.generateRandomStrings();
        userData.setFullName(updatedFullName);

        userServiceHelper.updateUser(userData,userId,userToken);

        User user = userServiceHelper.getUser(userId,userToken).as(User.class);
        assertEquals(user.getUserData().getFullName(),updatedFullName,"The full name doesn't get updated");
    }

    @Test(priority=4,dependsOnMethods = {"testCreateUser"})
    public void testDeleteUser() {
        response = userServiceHelper.deleteUser(userId,userToken);

        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
    }

    @Test(priority=5,dependsOnMethods = {"testCreateUser"})
    public void verifyTheUserStatusAfterDeleting() {
        User actualUser = userServiceHelper.getUser(userId, userToken).as(User.class);

        assertEquals(actualUser.getUserData().getStatus(),
                "deleted",
                "The user status should be deleted");
    }

    @Test()
    public void testGetUnauthorizedUser() {
        response = userServiceHelper.getWrongUser("userId", "");

        assertEquals(response.statusCode(),401);
        assertEquals(response.jsonPath().get("message"), "Unauthorized");
    }

    @Test(dependsOnMethods = {"testCreateUser"})
    public void testGetWrongUser() {
        response = userServiceHelper.getWrongUser("aabcd123zxzx12", userToken);

        assertEquals(response.statusCode(),200);
        assertEquals(response.jsonPath().get("message"), "User not found!");
    }

    @Test(dependsOnMethods = {"testCreateUser"})
    public void testGetUserWithProvidingEmptyUserId() {
        response = userServiceHelper.getWrongUser("", userToken);


        assertEquals(response.statusCode(),500);
        assertEquals(response.jsonPath().get("message"), "Unable to get user details!");
        assertTrue(response.jsonPath().get("data").toString()
                .contains("The AttributeValue for a key attribute cannot contain an empty string value"));
    }

    @Test()
    public void testCreateUserWithoutEmailOrPhone() {
        response = userServiceHelper.createWrongUser("email");

        assertEquals(response.statusCode(),400);
        assertEquals(response.jsonPath().get("message"), "Email or phone is needed.");
    }

    @Test()
    public void testCreateUserWithoutWalletName() {
        response = userServiceHelper.createWrongUser("wallet");

        assertEquals(response.statusCode(),400);
        assertTrue(response.jsonPath().get("message").toString().contains("Supplied AttributeValue is empty"));
    }
}