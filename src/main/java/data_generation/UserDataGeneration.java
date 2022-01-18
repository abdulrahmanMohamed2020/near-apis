package data_generation;

import com.github.javafaker.Faker;
import model.users.UserData;

public class UserDataGeneration {
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

    public String createFullName() {
        return faker.name().fullName();
    }

    public UserData createUserEmail() {
        userData.setEmail(faker.bothify("?????##@test.com"));
        return userData;
    }

    public UserData createUserPhone() {
        userData.setPhone(faker.phoneNumber().cellPhone());
        return userData;
    }


}
