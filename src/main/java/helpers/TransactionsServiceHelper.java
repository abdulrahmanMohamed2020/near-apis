package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

    public Transactions getTransaction(String transactionId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_TRANSACTION.replace("{transactionId}", transactionId)).andReturn();

        Transactions transaction = response.as(Transactions.class);
        response.prettyPrint();
        return transaction;
    }

    public Transactions getNftTransactions(String nftId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_TRANSACTIONS_OF_NFT.replace("{nftId}", nftId)).andReturn();

        Transactions nftTransactions = response.as(Transactions.class);
        response.prettyPrint();
        return nftTransactions;
    }

    public Response updateTransaction(Transactions transaction,String transactionId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(transaction.getData().get(0))
                .log().all()
                .put(EndPoints.UPDATE_TRANSACTION.replace("{transactionId}", transactionId)).andReturn();

        response.prettyPrint();
        return response;
    }

    public Response deleteTransaction(String transactionId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .when()
                .delete(EndPoints.DELETE_TRANSACTION.replace("{transactionId}",transactionId)).andReturn();

        return response;
    }

    public int getTransactionStatusCode() {
        return response.getStatusCode();
    }
}
