package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.nfts.NftData;

import java.io.File;

public class NftServiceHelper {
    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;

    public NftServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createNftOnUser(NftData nftData,String userToken) {
        File file = new File("resources/test-2.jpg");

        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .multiPart("file",file,"multipart/form-data")
                .formParam("data",nftData)
                .post(EndPoints.CREATE_NFT)
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getAllNftDetails(String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_ALL_NFTS)
                .andReturn();

        return response;
    }

    public Response getSingleNftDetails(String nftId,String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_SINGLE_NFT.replace("{nftId}",nftId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response updateNft(NftData nftData,String nftId,String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .formParam("data",nftData)
                .put(EndPoints.UPDATE_NFT.replace("{nftId}",nftId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response deleteNftDetails(String nftId,String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .delete(EndPoints.DELETE_NFT.replace("{nftId}",nftId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    // Not ready for testing yet
    public Response claimNft(String userId,String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(userId)
                .post(EndPoints.CLAIM_NFT.replace("{nftId}","nftId"))
                .andReturn();

        response.prettyPrint();
        return response;
    }
}
