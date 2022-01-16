package tests.transactionsTests;

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
    private UserServiceHelper userServiceHelper = new UserServiceHelper();
    private User sender;
    private User recipient;
    private NftServiceHelper nftServiceHelper = new NftServiceHelper();
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
        sender = userServiceHelper.createUser().as(User.class);
        userToken = sender.getJwtAccessToken();
        senderId = sender.getUserData().getUserId();

        recipient = userServiceHelper.createUser().as(User.class);
        recipientId = recipient.getUserData().getUserId();

        nft = nftServiceHelper.createNftOnUser(senderId, userToken).as(Nft.class);
        nftId = nft.getNftData().get(0).getNftId();

        transaction =
                transactionsServiceHelper
                        .createTransaction(senderId, userToken, nftId , recipientId).as(Transactions.class);

        assertEquals(transaction.getMessage(),"Transaction created successfully!");
        assertNotNull(transaction, "The user transactions are empty");

        // this because the transaction id doesn't return in the create response
        transaction =
                transactionsServiceHelper
                        .getUserTransactions(senderId, userToken).as(Transactions.class);
        transactionId = transaction.getData().get(0).getTransactionId();

        assertEquals(transaction.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test()
    public void testGetUserTransactions() {
        transaction =
                transactionsServiceHelper
                        .getUserTransactions(senderId, userToken).as(Transactions.class);

        transactionId = transaction.getData().get(0).getTransactionId();

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test()
    public void testGetNftTransactions() {
        transaction =
                transactionsServiceHelper
                        .getNftTransactions(nftId, userToken).as(Transactions.class);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transactions for NFT "+nftId+" retrieved successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test()
    public void testUpdateTransaction() {
        transactionsData = new TransactionsData();

        transactionsData.setTransactionValue("15 USD");
        transactionsData.setType("regular");

        Response response = transactionsServiceHelper.updateTransaction(transactionsData, userToken, transactionId);

        assertEquals(response.statusCode(), 200, "The status code should be 200");

    }

    @AfterMethod
    public void tearDown() {
        userServiceHelper.deleteUser(senderId,userToken);
        userServiceHelper.deleteUser(recipientId,userToken);
        nftServiceHelper.deleteNftDetails(nftId,userToken);
        System.out.println("Users and Nft are Deleted Successfully!!");

        transaction =
                transactionsServiceHelper
                        .deleteTransaction(userToken, transactionId).as(Transactions.class);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(), "Transaction deleted successfully!");

        transaction =
                transactionsServiceHelper
                        .getTransaction(userToken, transactionId).as(Transactions.class);

        assertEquals(transactionsServiceHelper.getTransactionStatusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getData().get(0).getStatus(), "cancelled");
        System.out.println("Transaction Deleted Successfully!!");

    }
}
