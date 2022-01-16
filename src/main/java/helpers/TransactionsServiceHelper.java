package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.transactions.TransactionsData;
import java.util.Collections;

public class TransactionsServiceHelper {

    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"", "");
    private Response response;
    private TransactionsData transactionsData = new TransactionsData();

    public TransactionsServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createTransaction(String userId, String userToken, String nftId, String recipientId) {
        transactionsData = createTransactionBody(userId, nftId, recipientId);

        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(transactionsData)
                .log().all()
                .post(EndPoints.CREATE_TRANSACTION)
                .andReturn();
        response.prettyPrint();
        return response;
    }

    public Response getUserTransactions(String userId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_TRANSACTIONS_OF_AN_USER.replace("{userId}", userId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getTransaction(String userToken, String transactionId) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_TRANSACTION.replace("{transactionId}", transactionId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getNftTransactions(String nftId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_TRANSACTIONS_OF_NFT.replace("{nftId}", nftId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response updateTransaction(TransactionsData transactionsData, String userToken, String transactionId) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(transactionsData)
                .put(EndPoints.UPDATE_TRANSACTION.replace("{transactionId}", transactionId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response deleteTransaction(String userToken, String transactionId) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .delete(EndPoints.DELETE_TRANSACTION.replace("{transactionId}",transactionId))
                .andReturn();

        return response;
    }

    public TransactionsData createTransactionBody(String userId, String nftId, String recipientId) {
        transactionsData.setSenderId(userId);
        transactionsData.setRecipientId(Collections.singletonList(recipientId));
        transactionsData.setTransactionItemId(nftId);
        transactionsData.setTransactionValue("99 USD");
        transactionsData.setType("gift");

        return transactionsData;
    }
}
