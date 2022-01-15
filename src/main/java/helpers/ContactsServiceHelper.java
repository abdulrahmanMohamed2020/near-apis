package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.contacts.ContactsData;
import model.transactions.TransactionsData;

import java.util.Collections;

public class ContactsServiceHelper {
    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;
    private String userToken="eyJraWQiOiJmdjZkSFwvQ05Bajk5bE10b2V2K2hrMFVBUWRZeGRyK2dlTGNJYWpqRTlCMD0iLCJhbGciOiJSUzI1NiJ9.eyJvcmlnaW5fanRpIjoiN2NmMGE5ZDQtMTQ1MC00NTBlLWJlMmItYjA1MmRiYWZiYjcwIiwic3ViIjoiZjFiOTQ5NDUtZWI1ZS00YTI0LWIyMmEtYTgxNjkzYWQwZjIyIiwiZXZlbnRfaWQiOiI3Y2IxYzFiNC1hMTU1LTQzMDgtOWQyYy00NGNjOTk1YjdiOTAiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjQyMjMzMzY1LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9hSlUxRThUWVciLCJleHAiOjE2NDIzMTk3NjQsImlhdCI6MTY0MjIzMzM2NSwianRpIjoiNzM5ZjE1MzYtODU4ZS00NzMxLTk5ZGYtMjIyNjA4Y2YxZTRkIiwiY2xpZW50X2lkIjoiMWg4ajM3ZW44ZXEwNGU0bnVsOGw3Z2U0YW4iLCJ1c2VybmFtZSI6Im5jaGV4Y2tpc3Nzc2luYWdpZW4ubmVhciJ9.zWj6pnQvEW5Q9bpUPmcBf_RRiMAShMrRD6FUDJTLca7cTWwSYz7XUB2wk3lShp70po7ncmmndMjPGlnw2hc7VZjHUjOQvP90QltnjxfMqsaxnB2dq1pApDCEuFsNH8d7uw9B4X9eaLBXPfrMCsRmdvwTmevBKQh4Tle4pdKDS_z0dtqZkJhQMdj2qFvxSi8a9M-T-3jmhKodynkVR5l_VaUIhq6ennwspozrtofOnqYTo3EGL_1S5KN_kpq1NqW93xEGnViS7NcAfnAtE5XjCp5YzQD_siEBco73rSY-2Qaq1SOEo_UY5nhlp8A8BnS1Lk6O4jm7ej6Waa5k3L1mTQ";

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
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getContact(String contactId) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_CONTACT.replace("{contactId}",contactId))
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response updateContact(String contactId) {
        ContactsData contactsData = new ContactsData();
        contactsData.setFirstName("Abdo test");
        contactsData.setJobTitle("eng");

        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(contactsData)
                .put(EndPoints.UPDATE_CONTACT.replace("{contactId}", contactId))
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response deleteContact(String contactId) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .delete(EndPoints.DELETE_CONTACT.replace("{contactId}",contactId))
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        return response;
    }

    public Response getContactsOfAnUser(String userId) {
        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_CONTACTS_OF_AN_USER.replace("{userId}",userId))
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

    public Response importOneOrMoreContacts() {

        ContactsData contactsData = new ContactsData();
        contactsData.setOwnerId("av9K25eWMXfHN0ALmC7Vx");
        contactsData.setFirstName("AbdoZ3terHahaha");

        response = RestAssured
                .given().header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(Collections.singletonList(contactsData))
                .log().all()
                .post(EndPoints.IMPORT_CONTACTS)
                .then().assertThat().statusCode(200)
                .extract().response().andReturn();

        response.prettyPrint();
        return response;
    }

}
