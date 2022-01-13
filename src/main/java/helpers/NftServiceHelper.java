package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.nfts.Nft;
import model.nfts.NftData;
import java.io.File;
import java.util.Collections;


public class NftServiceHelper {
    private static String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;

    public NftServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Nft createNftOnUser(String userId,String userToken) {
        File file = new File("resources/test-2.jpg");
        Nft nft = new Nft();
        NftData nftData = new NftData();

        nftData.setTitle("Hello from automation!");
        nftData.setDescription("This is my automation framework!");
        nftData.setOwnerId(userId);

        nft.setNftData(Collections.singletonList(nftData));

        response = RestAssured
                    .given()
                    .header("Authorization", "Bearer " + userToken)
                    .multiPart("file",file,"multipart/form-data")
                    .formParam("data",nft.getNftData())
                    .post(EndPoints.CREATE_NFT).andReturn();

        response.prettyPrint();
        nft = response.as(Nft.class);
        return nft;
    }

    public Nft getAllNftDetails(String userToken) {
        response = RestAssured
                    .given()
                    .header("Authorization", "Bearer " + userToken)
                    .get(EndPoints.GET_ALL_NFTS).andReturn();

        Nft nft = response.as(Nft.class);
        return nft;
    }

    public Nft getSingleNftDetails(String nftId,String userToken) {
        response = RestAssured
                    .given()
                    .header("Authorization", "Bearer " + userToken)
                    .get(EndPoints.GET_SINGLE_NFT.replace("{nftId}",nftId))
                    .andReturn();

        Nft nft = response.as(Nft.class);
        response.prettyPrint();
        return nft;
    }

    public Nft updateNft(String nftId,String userToken) {
        Nft nft = new Nft();
        NftData nftData = new NftData();

        nftData.setTitle("Hello from automation after passing CRUD endpoints!!");
        nftData.setDescription("This is my automation framework from creation to tear down!");

        nft.setNftData(Collections.singletonList(nftData));

        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + userToken)
                .formParam("data",nft.getNftData())
                .put(EndPoints.UPDATE_NFT.replace("{nftId}",nftId)).andReturn();

        response.prettyPrint();
        nft = response.as(Nft.class);
        return nft;
    }

    public Response deleteNftDetails(String nftId,String userToken) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .delete(EndPoints.DELETE_NFT.replace("{nftId}",nftId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }
}
