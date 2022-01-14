package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.transactions.Transactions;
import model.transactions.TransactionsData;

import java.util.Collections;

public class TransactionsServiceHelper {

    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"", "");
    private Response response;
    private TransactionsData transactionsData = new TransactionsData();
    private String transactionId;

    public TransactionsServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Transactions createTransaction(String userId, String userToken, String nftId, String recipientId) {
        Transactions transaction;
        transactionsData = createTransactionBody(userId, nftId, recipientId);

        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(transactionsData)
                .log().all()
                .post(EndPoints.CREATE_TRANSACTION).then().assertThat().statusCode(200).extract().response().andReturn();

        transaction = response.as(Transactions.class);
        transactionId = transaction.getData().get(0).getTransactionId();
        response.prettyPrint();
        return transaction;
    }

    public Transactions getUserTransactions(String userId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_TRANSACTIONS_OF_AN_USER.replace("{userId}", userId)).then().assertThat().statusCode(200).extract().response().andReturn();

        Transactions userTransactions = response.as(Transactions.class);
        transactionId = userTransactions.getData().get(0).getTransactionId();
        response.prettyPrint();
        return userTransactions;
    }

    public Transactions getTransaction(String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_TRANSACTION.replace("{transactionId}", transactionId)).then().assertThat().statusCode(200).extract().response().andReturn();

        Transactions transaction = response.as(Transactions.class);
        response.prettyPrint();
        return transaction;
    }

    public Transactions getNftTransactions(String nftId, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_TRANSACTIONS_OF_NFT.replace("{nftId}", nftId)).then().assertThat().statusCode(200).extract().response().andReturn();

        Transactions nftTransactions = response.as(Transactions.class);
        response.prettyPrint();
        return nftTransactions;
    }

    public Response updateTransaction(TransactionsData transactionsData, String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(transactionsData)
                .put(EndPoints.UPDATE_TRANSACTION.replace("{transactionId}", transactionId)).then().assertThat().statusCode(200).extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response deleteTransaction(String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .when()
                .delete(EndPoints.DELETE_TRANSACTION.replace("{transactionId}",transactionId)).then().assertThat().statusCode(200).extract().response().andReturn();

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

    public int getTransactionStatusCode() {
        return response.getStatusCode();
    }
}
