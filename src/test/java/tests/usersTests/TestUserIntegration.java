package tests.usersTests;

import generatingData.GenerateUserData;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.users.User;
import model.users.UserData;
import org.testng.annotations.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static org.testng.Assert.*;

public class TestUserIntegration {

    private UserData userData = new UserData();
    private UserServiceHelper userServiceHelper = new UserServiceHelper();
    private GenerateUserData generateUserData= new GenerateUserData();
    private Response response;
    private User actualUser;
    private String userToken;
    private String userId;

    @BeforeMethod()
    public void setUpCreateUser() {
        response = userServiceHelper.createUser(generateUserData.createFullUserData());
        actualUser = response.as(User.class);

        userToken = actualUser.getJwtAccessToken();
        userId = actualUser.getUserData().getUserId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");
    }

    @Test()
    public void testGetUser() {
        response = userServiceHelper.getUser(userId, userToken);
        actualUser = response.as(User.class);

        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));

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

        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));

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