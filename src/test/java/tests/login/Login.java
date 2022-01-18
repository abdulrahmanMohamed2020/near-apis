package tests.login;

import controllers.LoginServiceControllers;
import io.restassured.response.Response;
import model.users.User;
import model.users.UserException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class Login {
    // This is test class for permanent user
    // Just to check the login endpoints

    private LoginServiceControllers loginServiceControllers;
    Response response;

    @BeforeClass
    public void init() {
        loginServiceControllers = new LoginServiceControllers();
    }

    @Test()
    public void testCreateLoginOtp() {
        response = loginServiceControllers.createLoginOtp();
        UserException user = response.as(UserException.class);

        assertEquals(user.getMessage(),"Code sent on email!");
    }

    @Test()
    public void testVerifyLoginOtp() {
        response = loginServiceControllers.verifyLoginOtp();
        User user = response.as(User.class);

        assertNotNull(user.getJwtAccessToken());
        assertNotNull(user.getJwtIdToken());
        assertNotNull(user.getJwtRefreshToken());
        assertEquals(user.getUser_info().getUserId(),"ZA8n8ZASUj6IouGQTPIZZ");
        assertEquals(user.getUser_info().getWalletName(),"permanentuser.near");
        assertEquals(user.getUser_info().getEmail(),"permanentuser.near@test.com");
    }
}
