package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.nfts.NftData;

import java.io.File;

public class NftServiceHelper {
    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;
    private NftData nftData = new NftData();

    public NftServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createNftOnUser(String userId,String userToken) {
        File file = new File("resources/test-2.jpg");
        createNftDataForUser(userId);

        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + userToken)
                .multiPart("file",file,"multipart/form-data")
                .formParam("data",nftData)
                .post(EndPoints.CREATE_NFT)
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getAllNftDetails(String userToken) {
        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_ALL_NFTS)
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        return response;
    }

    public Response getSingleNftDetails(String nftId,String userToken) {
        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_SINGLE_NFT.replace("{nftId}",nftId))
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response updateNft(String nftId,String userToken) {
        NftData nftData = new NftData();

        nftData.setTitle("Hello from automation after passing CRUD endpoints!!");
        nftData.setDescription("This is my automation framework from creation to tear down!");

        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + userToken)
                .formParam("data",nftData)
                .put(EndPoints.UPDATE_NFT.replace("{nftId}",nftId))
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response deleteNftDetails(String nftId,String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .delete(EndPoints.DELETE_NFT.replace("{nftId}",nftId))
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    // Not ready for testing yet
    public Response claimNft(String userId,String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(userId)
                .post(EndPoints.CLAIM_NFT.replace("{nftId}","nftId"))
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public void createNftDataForUser(String userId) {
        nftData.setTitle("Hello from automation!");
        nftData.setDescription("This is my automation framework!");
        nftData.setOwnerId(userId);
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }
}
