package tests.nftsTests;

import helpers.NftServiceHelper;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.nfts.Nft;
import model.nfts.NftData;
import model.users.UserData;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestNfts {
    private NftServiceHelper nftServiceHelper;
    private UserServiceHelper userServiceHelper;
    private String userToken;
    private String userId;
    private String nftId;

    @BeforeClass
    public void init() {
        userServiceHelper = new UserServiceHelper();
        userServiceHelper.createUser();
        userToken = userServiceHelper.getTokenOfUser();
        userId = userServiceHelper.getUserIdOfUser();
        nftServiceHelper = new NftServiceHelper();
    }

    @Test
    public void testGetAllNfts() {
        Nft allNft = nftServiceHelper.getAllNftDetails(userToken);
        System.out.println(allNft.getNftData().size());

        assertEquals(allNft.getMessage(),"NFTs fetched successfully!");
        assertEquals(nftServiceHelper.getStatusCode(),200, "The status code is wrong");
    }

    @Test(priority = 0)
    public void testCreateNft() {
        Nft nft = nftServiceHelper.createNftOnUser(userId,userToken);
        nftId = nft.getNftData().get(0).getNftId();

        assertEquals(nftServiceHelper.getStatusCode(),200);
        assertEquals(nft.getMessage(),"NFT creation successful!");
        assertTrue(!nft.getNftData().get(0).getTitle().isEmpty(),"The Nft Title is empty");
        assertTrue(!nft.getNftData().get(0).getDescription().isEmpty(),"The Nft Description is empty");
        assertTrue(!nft.getNftData().get(0).getFileUrl().isEmpty(),"The Nft File Url is empty");
        assertTrue(!nft.getNftData().get(0).getOwnerId().isEmpty(),"The Nft Owner ID is empty");
    }


    @Test(priority = 1,dependsOnMethods = {"testCreateNft"})
    public void testGetSingleNft() {
        Nft singleNft = nftServiceHelper.getSingleNftDetails(nftId,userToken);

        assertEquals(nftServiceHelper.getStatusCode(),200, "The status code is wrong");
        assertEquals(singleNft.getMessage(),"NFT retrieved successfully!");
        assertTrue(!singleNft.getNftData().isEmpty(),"The Nft Data is empty");
        assertTrue(!singleNft.getNftData().get(0).getTitle().isEmpty(),"The Nft Title is empty");
        assertTrue(!singleNft.getNftData().get(0).getDescription().isEmpty(),"The Nft Description is empty");
        assertTrue(!singleNft.getNftData().get(0).getFileUrl().isEmpty(),"The Nft File Url is empty");
        assertTrue(!singleNft.getNftData().get(0).getOwnerId().isEmpty(),"The Nft Owner ID is empty");
    }

    @Test(priority = 2,dependsOnMethods = {"testCreateNft"})
    public void testUpdateNft() {
        Nft nft = nftServiceHelper.updateNft(nftId,userToken);

        assertEquals(nftServiceHelper.getStatusCode(),200);
        assertEquals(nft.getMessage(),"NFT updated successfully!");
    }

    @Test(priority = 3,dependsOnMethods = {"testCreateNft"})
    public void testDeleteNft() {
        Response response = nftServiceHelper.deleteNftDetails(nftId,userToken);

        assertEquals(response.statusCode(),200, "The status code is wrong");
        assertEquals(response.jsonPath().get("message"), "NFT deleted successfully!");
    }

    @Test(priority = 4,dependsOnMethods = {"testDeleteNft"})
    public void verifyTheNftStatusAfterDeleting() {
        Nft nft = nftServiceHelper.getSingleNftDetails(nftId,userToken);

        assertEquals(nftServiceHelper.getStatusCode(), 200, "The status code should be 200");
        assertTrue(nft.getNftData().get(0).getStatus().equals("deleted"));
    }

    @AfterClass
    public void tearDown() {
        userServiceHelper.deleteUser(userId,userToken);
        System.out.println("User is Deleted Successfully!!");
    }
}
