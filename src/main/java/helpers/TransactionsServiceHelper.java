package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.transactions.Transactions;

public class TransactionsServiceHelper {

    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"", "");
    private Response response;

    public TransactionsServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Transactions getUserTransactions(String userId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_TRANSACTIONS_OF_AN_USER.replace("{userId}", userId)).andReturn();

        Transactions userTransactions = response.as(Transactions.class);
        response.prettyPrint();
        return userTransactions;
    }

    public int getUserStatusCode() {
        return response.getStatusCode();
    }
}
