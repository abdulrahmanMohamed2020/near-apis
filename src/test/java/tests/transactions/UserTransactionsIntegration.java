package tests.transactions;

import data_generation.NftDataGeneration;
import data_generation.UserDataGeneration;
import controllers.NftServiceControllers;
import controllers.TransactionsServiceControllers;
import controllers.UserServiceControllers;
import io.restassured.response.Response;
import model.nfts.Nft;
import model.transactions.Transactions;
import model.transactions.TransactionsData;
import model.users.User;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public class UserTransactionsIntegration {

    private TransactionsServiceControllers transactionsServiceControllers = new TransactionsServiceControllers();
    private Response response;
    private UserServiceControllers userServiceControllers = new UserServiceControllers();
    private User sender;
    private User recipient;
    private NftServiceControllers nftServiceControllers = new NftServiceControllers();
    private UserDataGeneration userDataGeneration = new UserDataGeneration();
    private NftDataGeneration nftDataGeneration = new NftDataGeneration();
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
        response = userServiceControllers.createUser(userDataGeneration.createFullUserData());
        sender = response.as(User.class);
        userToken = sender.getJwtAccessToken();
        senderId = sender.getUser_info().getUserId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");

        // create recipient
        response = userServiceControllers.createUser(userDataGeneration.createFullUserData());
        recipient = response.as(User.class);
        recipientId = recipient.getUser_info().getUserId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");

        // create Nft on sender
        response = nftServiceControllers.createNftOnUser(nftDataGeneration.createNftData(senderId), userToken);
        nft = response.as(Nft.class);
        nftId = nft.getNftData().get(0).getNftId();
        assertEquals(response.statusCode(), 200, "The status code should be 200");

        // create transaction
        response = transactionsServiceControllers
                .createTransaction(senderId, userToken, nftId , recipientId);
        transaction =response.as(Transactions.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transaction created successfully!");
        assertNotNull(transaction, "The user transactions are empty");

        // this because the transaction id doesn't return in the create response
        response = transactionsServiceControllers
                .getUserTransactions(senderId, userToken);
        transaction =response.as(Transactions.class);
        transactionId = transaction.getData().get(0).getTransactionId();

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test()
    public void testGetUserTransactions() {
        response = transactionsServiceControllers
                .getUserTransactions(senderId, userToken);
        transaction =response.as(Transactions.class);

        transactionId = transaction.getData().get(0).getTransactionId();

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(transaction, "The user transactions are empty");
    }

    @Test()
    public void testGetNftTransactions() {
        response = transactionsServiceControllers
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

        response = transactionsServiceControllers.updateTransaction(transactionsData, userToken, transactionId);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
    }

    @AfterMethod
    public void tearDown() {
        userServiceControllers.deleteUser(senderId,userToken);
        userServiceControllers.deleteUser(recipientId,userToken);
        nftServiceControllers.deleteNftDetails(nftId,userToken);
        System.out.println("Users and Nft are Deleted Successfully!!");

        response = transactionsServiceControllers
                .deleteTransaction(userToken, transactionId);
        transaction = response.as(Transactions.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(transaction.getMessage(), "Transaction deleted successfully!");

        transaction =
                transactionsServiceControllers
                        .getTransaction(userToken, transactionId).as(Transactions.class);

        assertEquals(transaction.getData().get(0).getStatus(), "cancelled");
        System.out.println("Transaction Deleted Successfully!!");

    }
}
