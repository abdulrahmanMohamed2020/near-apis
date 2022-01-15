package tests;

import helpers.LoginServiceHelper;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestLogin {
    // This is test class for permanent user
    // Just to check the login endpoints

    private LoginServiceHelper loginServiceHelper;

    @BeforeClass
    public void init() {
        loginServiceHelper = new LoginServiceHelper();
    }

    @Test()
    public void createLoginOtp() {
        Response response = loginServiceHelper.createLoginOtp();

        assertEquals(response.jsonPath().get("message"),"Code sent on email!");
        assertEquals(response.jsonPath().get("type"),"email");
    }

    @Test()
    public void verifyLoginOtp() {
        Response response = loginServiceHelper.verifyLoginOtp();

        JSONObject verifyLoginBody = new JSONObject(response.asPrettyString());

        assertNotNull(response.jsonPath().get("jwt_access_token"));
        assertNotNull(response.jsonPath().get("jwt_id_token"));
        assertNotNull(response.jsonPath().get("jwt_refresh_token"));
        assertEquals(verifyLoginBody.getJSONObject("user_info").get("user_id"),"ZA8n8ZASUj6IouGQTPIZZ");
        assertEquals(verifyLoginBody.getJSONObject("user_info").get("wallet_id"),"permanentuser.near");
        assertEquals(verifyLoginBody.getJSONObject("user_info").get("email"),"permanentuser.near@test.com");
    }
}
