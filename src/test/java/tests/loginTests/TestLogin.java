package tests.loginTests;

import helpers.LoginServiceHelper;
import io.restassured.response.Response;
import model.users.User;
import model.users.UserException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestLogin {
    // This is test class for permanent user
    // Just to check the login endpoints

    private LoginServiceHelper loginServiceHelper;
    Response response;

    @BeforeClass
    public void init() {
        loginServiceHelper = new LoginServiceHelper();
    }

    @Test()
    public void testCreateLoginOtp() {
        response = loginServiceHelper.createLoginOtp();
        UserException user = response.as(UserException.class);

        assertEquals(user.getMessage(),"Code sent on email!");
    }

    @Test()
    public void testVerifyLoginOtp() {
        response = loginServiceHelper.verifyLoginOtp();
        User user = response.as(User.class);

        assertNotNull(user.getJwtAccessToken());
        assertNotNull(user.getJwtIdToken());
        assertNotNull(user.getJwtRefreshToken());
        assertEquals(user.getUserData().getUserId(),"ZA8n8ZASUj6IouGQTPIZZ");
        assertEquals(user.getUserData().getWalletName(),"permanentuser.near");
        assertEquals(user.getUserData().getEmail(),"permanentuser.near@test.com");
    }
}
