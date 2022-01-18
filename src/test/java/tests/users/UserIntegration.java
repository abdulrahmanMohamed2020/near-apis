package tests.users;

import data_generation.UserDataGeneration;
import controllers.UserServiceControllers;
import io.restassured.response.Response;
import model.users.User;
import model.users.UserData;
import org.testng.annotations.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static org.testng.Assert.*;

public class UserIntegration {

    private UserData userData = new UserData();
    private UserServiceControllers userServiceControllers = new UserServiceControllers();
    private UserDataGeneration userDataGeneration = new UserDataGeneration();
    private Response response;
    private User actualUser;
    private String userToken;
    private String userId;

    @BeforeMethod()
    public void setUpAndCreateUser() {
        userData = userDataGeneration.createFullUserData();
        response = userServiceControllers.createUser(userData);
        actualUser = response.as(User.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");

        userToken = actualUser.getJwtAccessToken();
        userId = actualUser.getUser_info().getUserId();

        //assertEquals(actualUser.getUserData().getFullName(),userData.getFullName(),"The full name created wrong");
        assertEquals(actualUser.getUser_info().getWalletName(),userData.getWalletId(),"The wallet name created wrong");
        assertEquals(actualUser.getUser_info().getEmail(),userData.getEmail(),"The email created wrong");
        assertEquals(actualUser.getUser_info().getPhone(),userData.getPhone(),"The phone created wrong");
    }

    @Test()
    public void testGetUser() {
        response = userServiceControllers.getUser(userId, userToken);
        actualUser = response.as(User.class);

        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getUser-schema.json"));

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(actualUser.getMessage(), "User retrieved successfully!");
        assertNotNull(actualUser, "The user data is empty");
        assertFalse(actualUser.getUserData().getUserId().isEmpty(),"The User ID is empty");
        //assertEquals(actualUser.getUserData().getFullName(),userData.getFullName(),"The full name returned wrong");
        assertEquals(actualUser.getUserData().getWalletName(),userData.getWalletId(),"The wallet name returned wrong");
        assertEquals(actualUser.getUserData().getEmail(),userData.getEmail(),"The email returned wrong");
        assertEquals(actualUser.getUserData().getPhone(),userData.getPhone(),"The phone returned wrong");
        assertEquals(actualUser.getUserData().getStatus(),"active", "The user status should be Active");
        assertTrue(actualUser.getUserData().getWalletName().contains(".near"),
                "The Wallet Name doesn't end with .near");

    }

    @Test()
    public void testUpdateUser() {
        String updatedFullName = userDataGeneration.createFullName();
        userData.setFullName(updatedFullName);

        response = userServiceControllers.updateUser(userData,userId,userToken);

        User user = userServiceControllers.getUser(userId,userToken).as(User.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(user.getUserData().getFullName(),updatedFullName,
                "The full name doesn't get updated");
    }

    @AfterMethod()
    public void tearDownAndDeleteUser() {
        response = userServiceControllers.deleteUser(userId,userToken);

        assertEquals(response.getStatusCode(), 200, "The status code should be 200");
        actualUser = userServiceControllers.getUser(userId, userToken).as(User.class);
        assertEquals(actualUser.getUserData().getStatus(),
                "deleted",
                "The user status should be deleted");
    }
}