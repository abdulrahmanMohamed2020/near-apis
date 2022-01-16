package tests.nftsTests;

import helpers.NftServiceHelper;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.nfts.Nft;
import model.nfts.NftData;
import model.users.User;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestNfts {
    private NftServiceHelper nftServiceHelper = new NftServiceHelper();
    private UserServiceHelper userServiceHelper = new UserServiceHelper();
    private NftData nftData = new NftData();
    private String userToken;
    private String userId;
    private String nftId;
    private Nft nft;

    @BeforeMethod
    public void setUp() {
        userToken = userServiceHelper.createUser().as(User.class).getJwtAccessToken();
        userId = userServiceHelper.createUser().as(User.class).getUserData().getUserId();

        nft = nftServiceHelper.createNftOnUser(userId,userToken).as(Nft.class);
        nftId = nft.getNftData().get(0).getNftId();

        assertEquals(nft.getMessage(),"NFT creation successful!");
        assertFalse(nft.getNftData().get(0).getTitle().isEmpty(), "The Nft Title is empty");
        assertFalse(nft.getNftData().get(0).getDescription().isEmpty(), "The Nft Description is empty");
        assertFalse(nft.getNftData().get(0).getFileUrl().isEmpty(), "The Nft File Url is empty");
        assertFalse(nft.getNftData().get(0).getOwnerId().isEmpty(), "The Nft Owner ID is empty");
    }

    @Test
    public void testGetAllNfts() {
        Nft allNft = nftServiceHelper.getAllNftDetails(userToken).as(Nft.class);
        System.out.println(allNft.getNftData().size());

        assertEquals(allNft.getMessage(),"NFTs fetched successfully!");
        assertEquals(nftServiceHelper.getStatusCode(),200, "The status code is wrong");
    }

    @Test()
    public void testGetSingleNft() {
        Nft singleNft = nftServiceHelper.getSingleNftDetails(nftId,userToken).as(Nft.class);

        assertEquals(singleNft.getMessage(),"NFT retrieved successfully!");
        assertFalse(singleNft.getNftData().isEmpty(), "The Nft Data is empty");
        assertFalse(singleNft.getNftData().get(0).getTitle().isEmpty(), "The Nft Title is empty");
        assertFalse(singleNft.getNftData().get(0).getDescription().isEmpty(), "The Nft Description is empty");
        assertFalse(singleNft.getNftData().get(0).getFileUrl().isEmpty(), "The Nft File Url is empty");
        assertFalse(singleNft.getNftData().get(0).getOwnerId().isEmpty(), "The Nft Owner ID is empty");
    }

    @Test()
    public void testUpdateNft() {
        nftData.setTitle("Hello from automation after passing CRUD endpoints!!");
        nftData.setDescription("This is my automation framework from creation to tear down!");

        nftServiceHelper.updateNft(nftData,nftId,userToken).as(Nft.class);

        Nft nft = nftServiceHelper.getSingleNftDetails(nftId,userToken).as(Nft.class);

        assertEquals(nftServiceHelper.getStatusCode(),200);
        assertEquals(nft.getNftData().get(0).getTitle(),
                "Hello from automation after passing CRUD endpoints!!");
        assertEquals(nft.getNftData().get(0).getDescription(),
                "This is my automation framework from creation to tear down!");
    }

    @AfterMethod
    public void tearDown() {
        userServiceHelper.deleteUser(userId,userToken);
        Response response = nftServiceHelper.deleteNftDetails(nftId,userToken);
        Nft nft = nftServiceHelper.getSingleNftDetails(nftId,userToken).as(Nft.class);

        assertEquals(response.statusCode(),200, "The status code is wrong");
        assertEquals(nft.getNftData().get(0).getStatus(), "deleted");
    }
}
