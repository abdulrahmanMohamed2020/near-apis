package tests;

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

public class TestGETUserTransactions {

    private TransactionsServiceHelper transactionsServiceHelper;
    private UserServiceHelper userServiceHelper;
    private User user;
    private String userToken;
    private String userId;
    private String nftId="Iphqq5J5JHnVHOeYTyxyU";
    private String transactionId="HqmwCqVADQY6DzfASX1Tj";

    // test token = "eyJraWQiOiJmdjZkSFwvQ05Bajk5bE10b2V2K2hrMFVBUWRZeGRyK2dlTGNJYWpqRTlCMD0iLCJhbGciOiJSUzI1NiJ9.eyJvcmlnaW5fanRpIjoiYjUyYzY4NWYtNTFiOS00YTFkLWE2Y2YtMzdiZDc4NmJjNDhkIiwic3ViIjoiN2JmZjVjMzgtNjQyOC00NWZkLWJhODUtNjRjZDY2YjNlYzc4IiwiZXZlbnRfaWQiOiIwNDc1MzY5Ni0zMGJlLTQ1YTQtODFmNS0yZDhiNTMzNjNmYmIiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjQxNjgxNzY2LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9hSlUxRThUWVciLCJleHAiOjE2NDE3NjgxNjYsImlhdCI6MTY0MTY4MTc2NiwianRpIjoiNGM2ZGJmMDMtNDUxZC00NjhmLTk3YWMtOTNlMTQ1Zjk5YjIxIiwiY2xpZW50X2lkIjoiMWg4ajM3ZW44ZXEwNGU0bnVsOGw3Z2U0YW4iLCJ1c2VybmFtZSI6ImhhbWFkYTExLm5lYXIifQ.OKTeIUzuQy8ewcEFgClLrbgrymoZR6DwbKM9njNDJQc5QQbnVGgQU0m4KR6-C77luOhGi48TatlqHsTGAtu3o0e9huVnLSKNLF0JtDuyOXIhyX-8iQFoe4tgeeF-ys9hpmU7cAlzzo1TBN_hbsSFERpbiTvLncbS64PeZyEdiD72NwImxT_mZaZTEpQc8JnyGG9x8jn4DviAoYmt1iFxwFArU7mpiYiU9e2X96WRt5efVXTGN4Ifa9xH-8gT5N1UIhUZQzliB6jzrkV1EZIscP987aTiPvKV7mqeeO5bC_Lpsme7aiBv4ydMrAposRVvDBhMh8nCMF0ckFNhEnxnlQ"
    // test user ID = LybTF4xi1iHA9mOyewUPZ

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
    public void testGetUserTransactions() {
        Transactions userTransactions = transactionsServiceHelper.getUserTransactions(userId, userToken);

        assertEquals(transactionsServiceHelper.getUserStatusCode(), 200, "The status code should be 200");
        assertEquals(userTransactions.getMessage(),"Transactions retrieved successfully!");
        assertNotNull(userTransactions, "The user transactions are empty");
    }

    @Test
    public void testGetNftTransactions() {
        Transactions nftTransactions = transactionsServiceHelper.getNftTransactions(nftId, userToken);

        assertEquals(transactionsServiceHelper.getUserStatusCode(), 200, "The status code should be 200");
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

        assertEquals(transactionsServiceHelper.getUserStatusCode(), 200, "The status code should be 200");

    }

    @AfterClass
    public void tearDown() {
        userServiceHelper.deleteUser(userId,userToken);
        System.out.println("User is Deleted Successfully!!");
    }
}
