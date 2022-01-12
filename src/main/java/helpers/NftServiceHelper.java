package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.nfts.Nft;

public class NftServiceHelper {
    private static String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;
    private String userToken = "eyJraWQiOiJmdjZkSFwvQ05Bajk5bE10b2V2K2hrMFVBUWRZeGRyK2dlTGNJYWpqRTlCMD0iLCJhbGciOiJSUzI1NiJ9.eyJvcmlnaW5fanRpIjoiNjE1ZDIyOTEtZDY3My00OGMzLTg1MjItMDA1ZTM1NThjZjAwIiwic3ViIjoiNjA4OGYzNTUtN2ZiZi00ODI5LWI0MGMtZmVmOTY0MTQ1ODI2IiwiZXZlbnRfaWQiOiJhOWIyOTEwOS05ZmU3LTRkYjktYWU5Yy05YTY4N2E0NjgwYzEiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjQxOTIzNTMwLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9hSlUxRThUWVciLCJleHAiOjE2NDIwMDk5MzAsImlhdCI6MTY0MTkyMzUzMCwianRpIjoiOGI3YTU0MzQtZjUwZC00YjIwLTk0YTAtZTBhNTYxYjQ4NTRkIiwiY2xpZW50X2lkIjoiMWg4ajM3ZW44ZXEwNGU0bnVsOGw3Z2U0YW4iLCJ1c2VybmFtZSI6InBvaXN5Lm5lYXIifQ.hoBUg8zupXyxqG3xWaVKORZ_eAiogSrJjqiS0_otaiW_ErMsY0MAKpiZGIvc4Ls4S11f4WgF8Hv8tQ-X7dMWozOe26ZaXlWiSgDbTIm1Ztw-Z2HPrKezgGG-wgdBrie6rJptRk-uMnVyurlm9wAkz9WBfJP_guJdGg1xe23Qdd-xdpfmdIS4EEzjtlsQcLQv93kjYs60ynsF01z0GCbGIH-gN0ozTmmJTc72tI57une4D6zgsVbJmuzBig3LNXrynZ0CwgaqHkxKEqCsrF1mYu3b15GqIqeOpoknmcQzMMbsZ-nBgjZcaCWzAmRQo8PhTiZLWpIeB3ovszKmGSEeKQ";

    public NftServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Nft getAllNftDetails() {
        response = RestAssured.get(EndPoints.GET_ALL_NFTS).andReturn();

        Nft nft = response.as(Nft.class);
        //response.prettyPrint();
        return nft;
    }

    public Nft getSingleNftDetails() {
        response = RestAssured.get(EndPoints.GET_SINGLE_NFT.replace("{nftId}","UHhm-r3JVlls1OGfs6KBp"))
                    .andReturn();

        Nft nft = response.as(Nft.class);
        response.prettyPrint();
        return nft;
    }

    public Response deleteNftDetails() {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .delete(EndPoints.DELETE_NFT.replace("{nftId}","UHhm-r3JVlls1OGfs6KBp"))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }
}
