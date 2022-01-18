package tests.transactionsTests;

import generatingData.GenerateNftData;
import generatingData.GenerateUserData;
import helpers.NftServiceHelper;
import helpers.TransactionsServiceHelper;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.nfts.Nft;
import model.transactions.Transactions;
import model.transactions.TransactionsData;
import model.users.User;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class TestUserTransactionsIntegration {

    private TransactionsServiceHelper transactionsServiceHelper = new TransactionsServiceHelper();
    private Response response;
    private UserServiceHelper userServiceHelper = new UserServiceHelper();
    private User sender;
    private User recipient;
    private NftServiceHelper nftServiceHelper = new NftServiceHelper();
    private GenerateUserData generateUserData= new GenerateUserData();
    private GenerateNftData generateNftData= new GenerateNftData();
    private Nft nft;
    private Transactions transaction;
    private TransactionsData transactionsData;
    private String transactionId;
    private String userToken;
    private String senderId;
    private String recipientId;
    private String nftId;

    @BeforeMethod
    public void setUp() {
        // create sender
        response = userServiceHelper.createUser(generateUserData.createFullUserData());
        sender = response.as(User.class);
        userToken = sender.getJwtAccessToken();
        senderId = sender.getUserData().getUserId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");

        // create recipient
        response = userServiceHelper.createUser(generateUserData.createFullUserData());
        recipient = response.as(User.class);
        recipientId = recipient.getUserData().getUserId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");

        // create Nft on sender
        response = nftServiceHelper.createNftOnUser(generateNftData.createNftData(senderId), userToken);
        nft = response.as(Nft.class);
        nftId = nft.getNftData().get(0).getNftId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");

        // create transaction
        response = transactionsServiceHelper
                .createTransaction(senderId, userToken, nftId , recipientId);
        transaction =response.as(Transactions.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transaction created successfully!");
        assertNotNull(transaction, "The user transactions are empty");

        // this because the transaction id doesn't return in the create response
        response = transactionsServiceHelper
                .getUserTransactions(senderId, userToken);
        transaction =response.as(Transactions.class);
        transactionId = transaction.getData().get(0).getTransactionId();

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test()
    public void testGetUserTransactions() {
        response = transactionsServiceHelper
                .getUserTransactions(senderId, userToken);
        transaction =response.as(Transactions.class);

        transactionId = transaction.getData().get(0).getTransactionId();

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test()
    public void testGetNftTransactions() {
        response = transactionsServiceHelper
                .getNftTransactions(nftId, userToken);
        transaction = response.as(Transactions.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transactions for NFT "+nftId+" retrieved successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test()
    public void testUpdateTransaction() {
        transactionsData = new TransactionsData();

        transactionsData.setTransactionValue("15 USD");
        transactionsData.setType("regular");

        response = transactionsServiceHelper.updateTransaction(transactionsData, userToken, transactionId);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
    }

    @AfterMethod
    public void tearDown() {
        userServiceHelper.deleteUser(senderId,userToken);
        userServiceHelper.deleteUser(recipientId,userToken);
        nftServiceHelper.deleteNftDetails(nftId,userToken);
        System.out.println("Users and Nft are Deleted Successfully!!");

        response = transactionsServiceHelper
                .deleteTransaction(userToken, transactionId);
        transaction = response.as(Transactions.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(), "Transaction deleted successfully!");

        transaction =
                transactionsServiceHelper
                        .getTransaction(userToken, transactionId).as(Transactions.class);

        assertEquals(transaction.getData().get(0).getStatus(), "cancelled");
        System.out.println("Transaction Deleted Successfully!!");

    }
}
