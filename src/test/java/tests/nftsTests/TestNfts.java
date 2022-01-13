package tests.nftsTests;

import helpers.NftServiceHelper;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.nfts.Nft;
import model.users.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestNfts {
    private NftServiceHelper nftServiceHelper;
    private UserServiceHelper userServiceHelper;
    private User user;
    private String userToken;
    private String userId;
    private String nftId="Iphqq5J5JHnVHOeYTyxyU";

    @BeforeClass
    public void init() {
        nftServiceHelper = new NftServiceHelper();
        //user = userServiceHelper.createUserBody();
        //userServiceHelper.createUser(user);
        //userToken = userServiceHelper.getTokenOfUser();
        //userId = userServiceHelper.getUserIdOfUser();
    }

    @Test
    public void testGetAllNfts() {
        Nft allNft = nftServiceHelper.getAllNftDetails();
        System.out.println(allNft.getNftData().size());

        assertEquals(allNft.getMessage(),"NFTs fetched successfully!");
        assertEquals(nftServiceHelper.getStatusCode(),200, "The status code is wrong");
    }

    @Test
    public void testGetSingleNft() {
        Nft singleNft = nftServiceHelper.getSingleNftDetails();
        //System.out.println(allNft.getNftData().size());

        assertEquals(nftServiceHelper.getStatusCode(),200, "The status code is wrong");
        assertEquals(singleNft.getMessage(),"NFT retrieved successfully!");
        assertTrue(!singleNft.getNftData().isEmpty(),"The Nft Data is empty");
        assertTrue(!singleNft.getNftData().get(0).getTitle().isEmpty(),"The Nft Title is empty");
        assertTrue(!singleNft.getNftData().get(0).getDescription().isEmpty(),"The Nft Description is empty");
        assertTrue(!singleNft.getNftData().get(0).getFileUrl().isEmpty(),"The Nft File Url is empty");
        assertTrue(!singleNft.getNftData().get(0).getOwnerId().isEmpty(),"The Nft Owner ID is empty");
    }

    @Test
    public void testDeleteNft() {
        Response response = nftServiceHelper.deleteNftDetails();

        assertEquals(response.statusCode(),200, "The status code is wrong");
        assertEquals(response.jsonPath().get("message"), "NFT deleted successfully!");
    }

    @Test
    public void verifyTheNftStatusAfterDeleting() {
        Nft nft = nftServiceHelper.getSingleNftDetails();

        assertEquals(nftServiceHelper.getStatusCode(), 200, "The status code should be 200");
        assertTrue(nft.getNftData().get(0).getStatus().equals("deleted"));
    }

//    @AfterClass
//    public void tearDown() {
//        userServiceHelper.deleteUser(userId,userToken);
//        System.out.println("User is Deleted Successfully!!");
//    }
}
