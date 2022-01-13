package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.nfts.Nft;

public class NftServiceHelper {
    private static String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;
    private String userToken = "eyJraWQiOiJmdjZkSFwvQ05Bajk5bE10b2V2K2hrMFVBUWRZeGRyK2dlTGNJYWpqRTlCMD0iLCJhbGciOiJSUzI1NiJ9.eyJvcmlnaW5fanRpIjoiYjEwNzRjMDQtYzIyMy00ZWJhLTg3OWEtNTA4Yjk1NGIxMjk4Iiwic3ViIjoiOGI5YTliY2EtODdjNS00OTIzLWFkYjEtNDZmNThkZTNkY2I5IiwiZXZlbnRfaWQiOiJmNTNiMGM1Mi1hMGI3LTQ2YWYtOWVhNy00ZjZjM2EwY2Q0MDIiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjQyMDMyMjc5LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9hSlUxRThUWVciLCJleHAiOjE2NDIxMTg2NzksImlhdCI6MTY0MjAzMjI3OSwianRpIjoiODQ3YzM5NTEtMmU3OC00MzNkLThmZjktOTVmZjZjMTNkODJjIiwiY2xpZW50X2lkIjoiMWg4ajM3ZW44ZXEwNGU0bnVsOGw3Z2U0YW4iLCJ1c2VybmFtZSI6Im15dGVzdGFjYzE5MC5uZWFyIn0.ECwgLeXQcv-C8Nb0U3bEJHuWRZBCEIsclGV_tmARaQ9MkSI2ghWZK_DdsKuX_1XhXdllTyiQyNTMInksxSZMCfRmlHJATIZIidehHuzlFNpxEfvRyPLnbGuuRb2b-EHUDgDJoz0J6cNQuSgcK_O_IKtcB1wR3h5jpLA3IveIfOXJVABxzyTCYjE58z5Ze_JLvEr4LErbtEWdyPeUiazOwi24cvE6lcol0_4MRM9Vczh-JKZXpSVitFHiw00GPCoyLR6_ystaqrLNMesqnNOGl42iMo73kWe9IVVo2rxT_bjo1W-DBRZ05o2SKT5ci8oooXfhJMhEYgwow8hepzNygw";

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
