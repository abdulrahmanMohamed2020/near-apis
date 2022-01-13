package tests.usersTests;

import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.users.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestUserIntegration {

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

        user = userServiceHelper.createUserBody();

        Response response = userServiceHelper.createUser(user);
        assertFalse(response.asString().contains("already exists"));
        assertFalse(response.asString().contains("is needed"));

        userToken = userServiceHelper.getTokenOfUser();
        userId = userServiceHelper.getUserIdOfUser();

        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
    }

    @Test(priority=1,dependsOnMethods = {"testCreateUser"})
    public void testGetUser() {
        User actualUser = userServiceHelper.getUser(userId, userToken);

        assertEquals(userServiceHelper.getUserStatusCode(), 200, "The status code should be 200");
        assertNotNull(actualUser, "The user data is empty");
        assertNotEquals(actualUser.getUserData().getFullName(),"","The Full Name is empty");
        assertEquals(actualUser.getUserData().getUserId(),userId, "The user data is empty");
    }

    @Test(priority=2,dependsOnMethods = {"testCreateUser"})
    public void testUpdateUser() {
        String updatedFullName = userServiceHelper.generateRandomStrings();
        user.getUserData().setFullName(updatedFullName);

        Response response = userServiceHelper.updateUser(user,userId,userToken);
        assertEquals(response.getStatusCode(), 200, "The status code should be 200");

        response.prettyPrint();
        user = userServiceHelper.getUser(userId,userToken);
        assertEquals(user.getUserData().getFullName(),updatedFullName,"The full name doesn't get updated");
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
        assertTrue(actualUser.getUserData().getStatus().equals("deleted"));
    }
}