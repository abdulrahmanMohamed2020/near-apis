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

        assertNotNull(actualUser, "The user data is empty");
        assertNotEquals(actualUser.getUserData().getFullName(),"","The Full Name is empty");
    }

    @Test(priority=2,dependsOnMethods = {"testCreateUser"})
    public void testUpdateUser() {
        String updatedFullName = userServiceHelper.generateRandomStrings();
        userData.setFullName(updatedFullName);

        userServiceHelper.updateUser(userData,userId,userToken);

        User user = userServiceHelper.getUser(userId,userToken).as(User.class);
        assertEquals(user.getUserData().getFullName(),updatedFullName,"The full name doesn't get updated");
    }

    @Test(priority=3,dependsOnMethods = {"testCreateUser"})
    public void testDeleteUser() {
        response = userServiceHelper.deleteUser(userId,userToken);

        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
    }

    @Test(priority=4,dependsOnMethods = {"testCreateUser"})
    public void verifyTheUserStatusAfterDeleting() {
        User actualUser = userServiceHelper.getUser(userId, userToken).as(User.class);

        assertEquals(actualUser.getUserData().getStatus(),
                "deleted",
                "The user status should be deleted");
    }
}