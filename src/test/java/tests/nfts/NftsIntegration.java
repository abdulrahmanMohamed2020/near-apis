package tests.nfts;

import data_generation.NftDataGeneration;
import data_generation.UserDataGeneration;
import controllers.NftServiceControllers;
import controllers.UserServiceControllers;
import io.restassured.response.Response;
import model.nfts.Nft;
import model.nfts.NftData;
import model.users.User;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class NftsIntegration {
    private NftServiceControllers nftServiceControllers = new NftServiceControllers();
    private UserServiceControllers userServiceControllers = new UserServiceControllers();
    private UserDataGeneration userDataGeneration = new UserDataGeneration();
    private NftDataGeneration nftDataGeneration = new NftDataGeneration();
    private Response response;
    private User user;
    private NftData nftData = new NftData();
    private String userToken;
    private String userId;
    private String nftId;
    private Nft nft;

    @BeforeMethod
    public void setUp() {
        user = userServiceControllers.createUser(userDataGeneration.createFullUserData()).as(User.class);
        userToken = user.getJwtAccessToken();
        userId = user.getUserData().getUserId();

        nftData = nftDataGeneration.createNftData(userId);

        response = nftServiceControllers.createNftOnUser(nftData,userToken);
        nft = response.as(Nft.class);
        nftId = nft.getNftData().get(0).getNftId();

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(nft.getMessage(),"NFT creation successful!");
        assertFalse(nft.getNftData().get(0).getTitle().isEmpty(), "The Nft Title is empty");
        assertFalse(nft.getNftData().get(0).getDescription().isEmpty(), "The Nft Description is empty");
        assertFalse(nft.getNftData().get(0).getFileUrl().isEmpty(), "The Nft File Url is empty");
        assertFalse(nft.getNftData().get(0).getOwnerId().isEmpty(), "The Nft Owner ID is empty");
    }

//    @Test
//    public void testGetAllNfts() {
//        response = nftServiceControllers.getAllNftDetails(userToken);
//        Nft allNft = response.as(Nft.class);
//
//        assertEquals(response.statusCode(), 200, "The status code should be 200");
//        assertEquals(allNft.getMessage(),"NFTs fetched successfully!");
//    }

    @Test()
    public void testGetSingleNft() {
        response = nftServiceControllers.getSingleNftDetails(nftId,userToken);
        nft = response.as(Nft.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(nft.getMessage(),"NFT retrieved successfully!");
        assertFalse(nft.getNftData().isEmpty(), "The Nft Data is empty");
        assertFalse(nft.getNftData().get(0).getTitle().isEmpty(), "The Nft Title is empty");
        assertFalse(nft.getNftData().get(0).getDescription().isEmpty(), "The Nft Description is empty");
        assertFalse(nft.getNftData().get(0).getFileUrl().isEmpty(), "The Nft File Url is empty");
        assertFalse(nft.getNftData().get(0).getOwnerId().isEmpty(), "The Nft Owner ID is empty");
    }

    @Test()
    public void testUpdateNft() {
        nftData.setTitle("Hello from automation after passing CRUD endpoints!!");
        nftData.setDescription("This is my automation framework from creation to tear down!");

        response = nftServiceControllers.updateNft(nftData,nftId,userToken);

        Nft nft = nftServiceControllers.getSingleNftDetails(nftId,userToken).as(Nft.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(nft.getNftData().get(0).getTitle(),
                "Hello from automation after passing CRUD endpoints!!");
        assertEquals(nft.getNftData().get(0).getDescription(),
                "This is my automation framework from creation to tear down!");
    }

    @AfterMethod
    public void tearDown() {
        userServiceControllers.deleteUser(userId,userToken);
        response = nftServiceControllers.deleteNftDetails(nftId,userToken);
        nft = nftServiceControllers.getSingleNftDetails(nftId,userToken).as(Nft.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(nft.getNftData().get(0).getStatus(), "deleted");
    }
}
