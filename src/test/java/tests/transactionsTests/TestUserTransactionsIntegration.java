package tests.transactionsTests;

import helpers.NftServiceHelper;
import helpers.TransactionsServiceHelper;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.nfts.Nft;
import model.transactions.Transactions;
import model.transactions.TransactionsData;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestUserTransactionsIntegration {

    private TransactionsServiceHelper transactionsServiceHelper;
    private UserServiceHelper userServiceHelper;
    private NftServiceHelper nftServiceHelper;
    private String userToken;
    private String senderId;
    private String recipientId;
    private Nft nft = new Nft();
    private String nftId;

    @BeforeClass
    public void init() {
        userServiceHelper = new UserServiceHelper();
        userServiceHelper.createUser();
        userToken = userServiceHelper.getTokenOfUser();
        senderId = userServiceHelper.getUserIdOfUser();
        userServiceHelper.createUser();
        recipientId = userServiceHelper.getUserIdOfUser();
        nftServiceHelper = new NftServiceHelper();
        nft = nftServiceHelper.createNftOnUser(senderId,userToken);
        nftId = nft.getNftData().get(0).getNftId();
        transactionsServiceHelper = new TransactionsServiceHelper();
    }

    @Test(priority = 0)
    public void testCreateTransaction() {
        Transactions transaction = transactionsServiceHelper.createTransaction(senderId, userToken, nftId , recipientId);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transaction created successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test(priority = 1, dependsOnMethods = {"testCreateTransaction"})
    public void testGetUserTransactions() {
        Transactions userTransactions = transactionsServiceHelper.getUserTransactions(senderId, userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(userTransactions.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(userTransactions, "The user transactions are empty");
    }

    @Test(priority = 3, dependsOnMethods = {"testCreateTransaction"})
    public void testGetNftTransactions() {
        Transactions nftTransactions = transactionsServiceHelper.getNftTransactions(nftId, userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(nftTransactions.getMessage(),"Transactions for NFT "+nftId+" retrieved successfully!");
        assertNotNull(nftTransactions, "The user transactions are empty");
    }

    @Test(priority = 4, dependsOnMethods = {"testCreateTransaction"})
    public void testUpdateTransaction() {
        TransactionsData transactionsData = new TransactionsData();

        transactionsData.setTransactionValue("15 USD");
        transactionsData.setType("regular");

        Response response = transactionsServiceHelper.updateTransaction(transactionsData, userToken);

        assertEquals(response.statusCode(), 200, "The status code should be 200");

    }

    @Test(priority = 5, dependsOnMethods = {"testCreateTransaction"})
    public void testDeleteTransaction() {
        Response response = transactionsServiceHelper.deleteTransaction(userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(response.jsonPath().get("message"), "Transaction deleted successfully!");
    }

    @Test(priority = 6, dependsOnMethods = {"testCreateTransaction"})
    public void verifyTheTransactionStatusAfterDeleting() {
        Transactions transactions = transactionsServiceHelper.getTransaction(userToken);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertTrue(transactions.getData().get(0).getStatus().equals("cancelled"));
    }

    @AfterClass
    public void tearDown() {
        userServiceHelper.deleteUser(senderId,userToken);
        userServiceHelper.deleteUser(recipientId,userToken);
        nftServiceHelper.deleteNftDetails(nftId,userToken);
        System.out.println("User and Nft are Deleted Successfully!!");
    }
}
