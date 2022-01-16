package tests.contactsTests;

import helpers.ContactsServiceHelper;
import helpers.UserServiceHelper;
import io.restassured.response.Response;
import model.contacts.Contacts;
import model.users.User;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

public class TestContacts {

    private ContactsServiceHelper contactsServiceHelper = new ContactsServiceHelper();
    private UserServiceHelper userServiceHelper = new UserServiceHelper();
    private User user;
    private Contacts contacts;
    private String userToken;
    private String ownerId;
    private String contactId;

    @BeforeMethod
    public void init() {
        user = userServiceHelper.createUser().as(User.class);
        userToken = user.getJwtAccessToken();
        ownerId = user.getUserData().getUserId();

        contacts = contactsServiceHelper.createContact(ownerId,userToken).as(Contacts.class);

        contactId=contacts.getData().get(0).getContactId();
        assertEquals(contacts.getMessage(),"Contact added successfully!");
    }

    @Test()
    public void testGetContact() {
        contacts = contactsServiceHelper.getContact(contactId,userToken).as(Contacts.class);

        assertEquals(contacts.getMessage(),"Contact retrieved successfully!");
    }

    @Test()
    public void testUpdateContact() {
        contacts =
                contactsServiceHelper
                        .updateContact(contactId, userToken).as(Contacts.class);

        assertEquals(contacts.getMessage(),"Contact updated successfully!");
    }

    @Test()
    public void testGetContactsOfAnUser() {
        contacts =
                contactsServiceHelper
                        .getContactsOfAnUser(ownerId, userToken).as(Contacts.class);

        System.out.println(contacts.getData().size());
        assertEquals(contacts.getMessage(),"Contacts retrieved successfully!");
    }

    @Test()
    public void testImportOneOrMoreContacts() {
        contacts = contactsServiceHelper.importOneOrMoreContacts(ownerId,userToken).as(Contacts.class);

        assertEquals(contacts.getMessage(),"Contacts added successfully!");
    }

    @AfterMethod
    public void tearDown() {
        userServiceHelper.deleteUser(ownerId,userToken);

        contacts =
                contactsServiceHelper
                        .deleteContact(contactId,userToken).as(Contacts.class);
        assertEquals(contacts.getMessage(), "Contact deleted successfully!");

        contacts =
                contactsServiceHelper
                        .getContact(contactId,userToken).as(Contacts.class);
        assertEquals(contacts.getData().get(0).getStatus(),"archived");
    }
}