package tests.contactsTests;

import helpers.ContactsServiceHelper;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.contacts.Contacts;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestContacts {

    private ContactsServiceHelper contactsServiceHelper;
    private UserServiceHelper userServiceHelper;
    private String userToken;
    private String ownerId;
    private String contactId;

    @BeforeClass
    public void init() {
        userServiceHelper = new UserServiceHelper();
        userServiceHelper.createUser();
        userToken = userServiceHelper.getTokenOfUser();
        ownerId = userServiceHelper.getUserIdOfUser();
        contactsServiceHelper = new ContactsServiceHelper();
    }

    @Test()
    public void testCreateContact() {
        Contacts contacts = contactsServiceHelper.createContact(ownerId,userToken).as(Contacts.class);

        contactId=contacts.getData().get(0).getContactId();
        assertEquals(contacts.getMessage(),"Contact added successfully!");
    }

    @Test(priority = 1, dependsOnMethods = {"testCreateContact"})
    public void testGetContact() {
        Contacts contacts = contactsServiceHelper.getContact(contactId,userToken).as(Contacts.class);

        assertEquals(contacts.getMessage(),"Contact retrieved successfully!");
    }

    @Test(priority = 2, dependsOnMethods = {"testCreateContact"})
    public void testUpdateContact() {
        Contacts contacts =
                contactsServiceHelper
                        .updateContact(contactId, userToken).as(Contacts.class);

        assertEquals(contacts.getMessage(),"Contact updated successfully!");
    }

    @Test(priority = 3, dependsOnMethods = {"testCreateContact"})
    public void testDeleteContact() {
        Response response =
                contactsServiceHelper
                        .deleteContact(contactId,userToken);

        assertEquals(response.jsonPath().get("message"), "Contact deleted successfully!");
    }

    @Test(priority = 4, dependsOnMethods = {"testCreateContact"})
    public void verifyTheContactStatusAfterDeleting() {
        Contacts contacts =
                contactsServiceHelper
                        .getContact(contactId,userToken).as(Contacts.class);

        assertEquals(contacts.getData().get(0).getStatus(),"archived");
    }

    @Test(dependsOnMethods = {"testCreateContact"})
    public void testGetContactsOfAnUser() {
        Contacts contacts =
                contactsServiceHelper
                        .getContactsOfAnUser(ownerId, userToken).as(Contacts.class);

        System.out.println(contacts.getData().size());
        assertEquals(contacts.getMessage(),"Contacts retrieved successfully!");
    }

    @Test(dependsOnMethods = {"testCreateContact"})
    public void testImportOneOrMoreContacts() {
        Contacts contacts = contactsServiceHelper.importOneOrMoreContacts(ownerId,userToken).as(Contacts.class);

        assertEquals(contacts.getMessage(),"Contacts added successfully!");
    }

    @AfterClass
    public void tearDown() {
        userServiceHelper.deleteUser(ownerId,userToken);
        System.out.println("User is Deleted Successfully!!");
    }
}