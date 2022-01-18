package generatingData;

import com.github.javafaker.Faker;
import model.nfts.NftData;

public class GenerateNftData {

    Faker faker = new Faker();
    NftData nftData = new NftData();

    public NftData createNftData(String userId) {
        nftData.setTitle(faker.howIMetYourMother().catchPhrase());
        nftData.setDescription(faker.howIMetYourMother().quote());
        nftData.setOwnerId(userId);

        return nftData;
    }

}
