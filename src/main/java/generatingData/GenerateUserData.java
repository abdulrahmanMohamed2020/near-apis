package generatingData;

import com.github.javafaker.Faker;
import model.users.UserData;

public class GenerateUserData {
    Faker faker = new Faker();
    private UserData userData = new UserData();

    public UserData createFullUserData() {
        userData.setFullName(faker.name().fullName());
        userData.setWalletName(faker.bothify("??????###.near"));
        userData.setEmail(faker.bothify("?????##@test.com"));
        userData.setPhone(faker.phoneNumber().cellPhone());
        return userData;
    }

    public UserData createUserDataWithoutWalletName() {
        userData.setFullName(faker.name().fullName());
        userData.setEmail(faker.bothify("?????##@test.com"));
        userData.setPhone(faker.phoneNumber().phoneNumber());
        userData.setWalletName(null);

        return userData;
    }

    public UserData createUserDataWithoutEmailAndPhone() {
        userData.setFullName(faker.name().fullName());
        userData.setWalletName(faker.bothify("???#???##.near"));
        userData.setEmail(null);
        userData.setPhone(null);

        return userData;
    }


}
