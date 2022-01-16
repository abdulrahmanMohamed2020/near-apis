package helpers;

import apiuitls.ConfigManager;
import constants.EndPoints;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.contacts.ContactsData;

import java.util.Collections;

public class ContactsServiceHelper {
    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl").replace("\"","");
    private Response response;

    public ContactsServiceHelper() {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createContact(String ownerId, String userToken) {

        ContactsData contactsData = new ContactsData();
        contactsData.setOwnerId(ownerId);
        contactsData.setFirstName("Automation");
        contactsData.setDob("1997-01-14");
        contactsData.setLastName("Test");

        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(contactsData)
                .log().all()
                .post(EndPoints.CREATE_CONTACT)
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response getContact(String contactId, String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .log().all()
                .get(EndPoints.GET_CONTACT.replace("{contactId}",contactId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response updateContact(String contactId, String userToken) {
        ContactsData contactsData = new ContactsData();
        contactsData.setFirstName("Abdo test");
        contactsData.setJobTitle("eng");

        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(contactsData)
                .put(EndPoints.UPDATE_CONTACT.replace("{contactId}", contactId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response deleteContact(String contactId, String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .delete(EndPoints.DELETE_CONTACT.replace("{contactId}",contactId))
                .andReturn();

        return response;
    }

    public Response getContactsOfAnUser(String userId, String userToken) {
        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .get(EndPoints.GET_CONTACTS_OF_AN_USER.replace("{userId}",userId))
                .andReturn();

        response.prettyPrint();
        return response;
    }

    public Response importOneOrMoreContacts(String ownerId,String userToken) {

        ContactsData contactsData = new ContactsData();
        contactsData.setOwnerId(ownerId);
        contactsData.setFirstName("Test import contacts");
        contactsData.setDob("1997-01-14");
        contactsData.setLastName("Automation");

        response = RestAssured
                .given().filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(Collections.singletonList(contactsData))
                .log().all()
                .post(EndPoints.IMPORT_CONTACTS)
                .andReturn();

        response.prettyPrint();
        return response;
    }

}
