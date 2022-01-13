package tests.transactionsTests;

import helpers.TransactionsServiceHelper;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.transactions.Transactions;
import model.transactions.TransactionsData;
import model.users.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class TestUserTransactionsIntegration {

    private TransactionsServiceHelper transactionsServiceHelper;
    private UserServiceHelper userServiceHelper;
    private User user;
    private String userToken;
    private String userId;
    private String nftId="Iphqq5J5JHnVHOeYTyxyU";
    private String transactionId="HqmwCqVADQY6DzfASX1Tj";

    @BeforeClass
    public void init() {
        userServiceHelper = new UserServiceHelper();
        user = userServiceHelper.createUserBody();
        userServiceHelper.createUser(user);
        userToken = userServiceHelper.getTokenOfUser();
        userId = userServiceHelper.getUserIdOfUser();
        transactionsServiceHelper = new TransactionsServiceHelper();
    }

    @Test
    public void testCreateTransaction() {
        Transactions transaction = transactionsServiceHelper.createTransaction(userId, userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transaction created successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test
    public void testGetUserTransactions() {
        Transactions userTransactions = transactionsServiceHelper.getUserTransactions(userId, userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(userTransactions.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(userTransactions, "The user transactions are empty");
    }

    @Test
    public void testGetNftTransactions() {
        Transactions nftTransactions = transactionsServiceHelper.getNftTransactions(nftId, userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(nftTransactions.getMessage(),"Transactions for NFT "+nftId+" retrieved successfully!");
        assertNotNull(nftTransactions, "The user transactions are empty");
        assertFalse(nftTransactions.getData().isEmpty(), "The user transactions are empty");
    }

    @Test
    public void testUpdateTransaction() {
        Transactions transactionBody = new Transactions();
        TransactionsData transactionsData = new TransactionsData();
        List<TransactionsData> data = new ArrayList<>();

        transactionsData.setTransactionValue("88 USD");
        transactionsData.setType("regular");

        data.add(transactionsData);

        transactionBody.setData(data);
        Response response = transactionsServiceHelper.updateTransaction(transactionBody,transactionId, userToken);

        assertEquals(response.statusCode(), 200, "The status code should be 200");

    }

    @Test
    public void testDeleteTransaction() {
        Response response = transactionsServiceHelper.deleteTransaction("JMHVKGBNbX1FYZ_1XjZCA",userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(response.jsonPath().get("message"), "Transaction deleted successfully!");
    }

    @Test
    public void verifyTheTransactionStatusAfterDeleting() {
        Transactions transactions = transactionsServiceHelper.getTransaction("JMHVKGBNbX1FYZ_1XjZCA", userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertTrue(transactions.getData().get(0).getStatus().equals("cancelled"));
    }

    @AfterClass
    public void tearDown() {
        userServiceHelper.deleteUser(userId,userToken);
        System.out.println("User is Deleted Successfully!!");
    }
}
