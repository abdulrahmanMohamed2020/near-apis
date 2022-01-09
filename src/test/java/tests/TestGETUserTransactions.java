package tests;

import helpers.TransactionsServiceHelper;
import model.transactions.Transactions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestGETUserTransactions {

    private TransactionsServiceHelper transactionsServiceHelper;
    private String userToken="eyJraWQiOiJmdjZkSFwvQ05Bajk5bE10b2V2K2hrMFVBUWRZeGRyK2dlTGNJYWpqRTlCMD0iLCJhbGciOiJSUzI1NiJ9.eyJvcmlnaW5fanRpIjoiYjUyYzY4NWYtNTFiOS00YTFkLWE2Y2YtMzdiZDc4NmJjNDhkIiwic3ViIjoiN2JmZjVjMzgtNjQyOC00NWZkLWJhODUtNjRjZDY2YjNlYzc4IiwiZXZlbnRfaWQiOiIwNDc1MzY5Ni0zMGJlLTQ1YTQtODFmNS0yZDhiNTMzNjNmYmIiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjQxNjgxNzY2LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9hSlUxRThUWVciLCJleHAiOjE2NDE3NjgxNjYsImlhdCI6MTY0MTY4MTc2NiwianRpIjoiNGM2ZGJmMDMtNDUxZC00NjhmLTk3YWMtOTNlMTQ1Zjk5YjIxIiwiY2xpZW50X2lkIjoiMWg4ajM3ZW44ZXEwNGU0bnVsOGw3Z2U0YW4iLCJ1c2VybmFtZSI6ImhhbWFkYTExLm5lYXIifQ.OKTeIUzuQy8ewcEFgClLrbgrymoZR6DwbKM9njNDJQc5QQbnVGgQU0m4KR6-C77luOhGi48TatlqHsTGAtu3o0e9huVnLSKNLF0JtDuyOXIhyX-8iQFoe4tgeeF-ys9hpmU7cAlzzo1TBN_hbsSFERpbiTvLncbS64PeZyEdiD72NwImxT_mZaZTEpQc8JnyGG9x8jn4DviAoYmt1iFxwFArU7mpiYiU9e2X96WRt5efVXTGN4Ifa9xH-8gT5N1UIhUZQzliB6jzrkV1EZIscP987aTiPvKV7mqeeO5bC_Lpsme7aiBv4ydMrAposRVvDBhMh8nCMF0ckFNhEnxnlQ";
    private String userId="LybTF4xi1iHA9mOyewUPZ";

    @BeforeClass
    public void init() {
        transactionsServiceHelper = new TransactionsServiceHelper();
    }

    @Test
    public void testGetUserTransactions() {
        Transactions userTransactions = transactionsServiceHelper.getUserTransactions(userId, userToken);

        assertEquals(transactionsServiceHelper.getUserStatusCode(), 200, "The status code should be 200");
        assertNotNull(userTransactions, "The user transactions are empty");
        assertFalse(userTransactions.getData().isEmpty(), "The user transactions are empty");
        assertEquals(userTransactions.getData().get(0).getType(),"gift");
    }
}
