package tests.usersTests;

import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.users.User;
import model.users.UserData;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class TestUserIntegration {

    private UserData userData = new UserData();
    private UserServiceHelper userServiceHelper = new UserServiceHelper();
    private Response response;
    private User actualUser;
    private String userToken;
    private String userId;

    @BeforeMethod()
    public void setUpCreateUser() {
        response = userServiceHelper.createUser();
        actualUser = response.as(User.class);

        userToken = actualUser.getJwtAccessToken();
        userId = actualUser.getUserData().getUserId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");
    }

    @Test()
    public void testGetUser() {
        response = userServiceHelper.getUser(userId, userToken);
        actualUser = response.as(User.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(actualUser.getMessage(), "User retrieved successfully!");
        assertNotNull(actualUser, "The user data is empty");
        assertTrue(actualUser.getUserData().getWalletName().contains(".near"),
                "The Wallet Name doesn't end with .near");
    }

    @Test()
    public void testUserAttributes() {
        response = userServiceHelper.getUser(userId, userToken);
        actualUser = response.as(User.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
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

    @Test()
    public void testUpdateUser() {
        String updatedFullName = userServiceHelper.generateRandomStrings();
        userData.setFullName(updatedFullName);

        response = userServiceHelper.updateUser(userData,userId,userToken);

        User user = userServiceHelper.getUser(userId,userToken).as(User.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(user.getUserData().getFullName(),updatedFullName,
                "The full name doesn't get updated");
    }

    @Test()
    public void testGetUnauthorizedUser() {
        response = userServiceHelper.getWrongUser("userId", "");

        assertEquals(response.statusCode(),401);
        assertEquals(response.jsonPath().get("message"), "Unauthorized");
    }

    @Test()
    public void testGetWrongUser() {
        response = userServiceHelper.getWrongUser("aabcd123zxzx12", userToken);

        assertEquals(response.statusCode(),200);
        assertEquals(response.jsonPath().get("message"), "User not found!");
    }

    @Test()
    public void testGetUserWithProvidingEmptyUserId() {
        response = userServiceHelper.getWrongUser("", userToken);

        assertEquals(response.statusCode(),500);
        assertEquals(response.jsonPath().get("message"), "Unable to get user details!");
        assertTrue(response.jsonPath().get("data").toString()
                .contains("The AttributeValue for a key attribute cannot contain an empty string value"));
    }

    @AfterMethod()
    public void tearDownAndDeleteUser() {
        response = userServiceHelper.deleteUser(userId,userToken);

        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
        actualUser = userServiceHelper.getUser(userId, userToken).as(User.class);
        assertEquals(actualUser.getUserData().getStatus(),
                "deleted",
                "The user status should be deleted");
    }
}