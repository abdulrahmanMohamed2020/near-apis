package tests.usersTests;

import generatingData.GenerateUserData;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.users.User;
import model.users.UserException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestUserInvalidScenarios {

    private UserServiceHelper userServiceHelper = new UserServiceHelper();
    private GenerateUserData generateUserData= new GenerateUserData();
    private Response response;
    private User actualUser;
    private UserException userException = new UserException();
    private String userToken;
    private String userId;

    public void setUpCreateUser() {
        response = userServiceHelper.createUser(generateUserData.createFullUserData());
        actualUser = response.as(User.class);

        userToken = actualUser.getJwtAccessToken();
        userId = actualUser.getUserData().getUserId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");
    }

    @Test()
    public void testGetUnauthorizedUser() {
        setUpCreateUser();
        response = userServiceHelper.getWrongUser(userId, "");
        userException = response.as(UserException.class);

        assertEquals(response.statusCode(),401);
        assertEquals(userException.getMessage(), "Unauthorized");
        tearDownAndDeleteUser();
    }

    @Test()
    public void testGetWrongUser() {
        setUpCreateUser();
        response = userServiceHelper.getWrongUser("aabcd123zxzx12", userToken);
        userException = response.as(UserException.class);

        assertEquals(response.statusCode(),200);
        assertEquals(userException.getMessage(), "User not found!");
        tearDownAndDeleteUser();
    }

    @Test()
    public void testGetUserWithProvidingEmptyUserId() {
        setUpCreateUser();
        response = userServiceHelper.getWrongUser("", userToken);
        userException = response.as(UserException.class);

        assertEquals(response.statusCode(),500);
        assertEquals(userException.getMessage(), "Unable to get user details!");
        tearDownAndDeleteUser();
    }

    @Test()
    public void testCreateUserWithoutEmailOrPhone() {
        response =
                userServiceHelper
                        .createWrongUser(generateUserData.createUserDataWithoutEmailAndPhone());
        userException = response.as(UserException.class);

        assertEquals(response.statusCode(),400);
        assertEquals(userException.getMessage(), "Email or phone is needed.");
    }

    @Test()
    public void testCreateUserWithoutWalletName() {
        response =
                userServiceHelper
                        .createWrongUser(generateUserData.createUserDataWithoutWalletName());
        userException = response.as(UserException.class);

        assertEquals(response.statusCode(),400);
        assertTrue(userException.getMessage().contains("Supplied AttributeValue is empty"));
    }

    public void tearDownAndDeleteUser() {
        response = userServiceHelper.deleteUser(userId,userToken);

        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
        actualUser = userServiceHelper.getUser(userId, userToken).as(User.class);
        assertEquals(actualUser.getUserData().getStatus(),
                "deleted",
                "The user status should be deleted");
    }

}
