package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.contacts.ContactsData;

public class ContactsServiceHelper {
    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;
    private String userToken="eyJraWQiOiJmdjZkSFwvQ05Bajk5bE10b2V2K2hrMFVBUWRZeGRyK2dlTGNJYWpqRTlCMD0iLCJhbGciOiJSUzI1NiJ9.eyJvcmlnaW5fanRpIjoiOTEwMjA2NDgtYjYwMy00YmM5LWEyODYtN2ZmMDk3NGQ3MDQyIiwic3ViIjoiMWUwZWFiYmYtNTFjZS00NzFiLWJiNGItZTE0M2Y4MGMyNTllIiwiZXZlbnRfaWQiOiIzNzljYjA4Yi1kZWZjLTQ4NWQtYTRjMC1mNzkzYjgxYTkwM2QiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjQyMTc5MDgyLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9hSlUxRThUWVciLCJleHAiOjE2NDIyNjU0ODIsImlhdCI6MTY0MjE3OTA4MiwianRpIjoiMGU0Mzc1OGQtYmMxNS00MzFmLWEwMjMtOTg3Mzk5N2Y1NmM2IiwiY2xpZW50X2lkIjoiMWg4ajM3ZW44ZXEwNGU0bnVsOGw3Z2U0YW4iLCJ1c2VybmFtZSI6Im1oY2h4a3F2dmgubmVhciJ9.3PYm-M6letXiiOMwnQz1CFxZl9vhZ0qx_NVYazey--qSxbg9BoQ3vV1BeqO5co9fXVghAbDRtMCdcokUwYPamT-I3mr_MjaS2hvLUW7T_ae3PnkgUdozwmbt4tlOzsd4rOyThpoz11MMXsjkRXpjwWc8KP23Ijal3ODj9CDVcwzYiPNgf52W2KLVdlAW9YdYj4A6qf_qBuUj9gIkPAvUzzMU6-Yx5fJ-ih2P8yF5twvXOc3JgsWD8ViHN225yY2O-1D47tktBBAjPAjHaC42VmRDUfrpuY64pFfQd-_nrRAfsxvW9xzAcP3AAGMjYTl38AFYWW8EFAnWqioWnpQujg";

    public ContactsServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createContact() {

        ContactsData contactsData = new ContactsData();
        contactsData.setOwnerId("v5ny4Mo51he_dotJH0pVr");
        contactsData.setFirstName("Abdo Test3 Contacts");

        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(contactsData)
                .log().all()
                .post(EndPoints.CREATE_CONTACT)
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getContact(String contactId) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_CONTACT.replace("{contactId}",contactId))
                .andReturn();

        response.prettyPrint();
        return response;
    }
}
