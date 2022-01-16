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
    private Response response;
    private User user;
    private Contacts contacts;
    private String userToken;
    private String ownerId;
    private String contactId;

    @BeforeMethod
    public void setUp() {
        user = userServiceHelper.createUser().as(User.class);
        userToken = user.getJwtAccessToken();
        ownerId = user.getUserData().getUserId();

        response = contactsServiceHelper.createContact(ownerId,userToken);
        contacts = response.as(Contacts.class);

        contactId=contacts.getData().get(0).getContactId();

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contact added successfully!");
    }

    @Test()
    public void testGetContact() {
        response = contactsServiceHelper.getContact(contactId,userToken);
        contacts = response.as(Contacts.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contact retrieved successfully!");
    }

    @Test()
    public void testUpdateContact() {
        response = contactsServiceHelper
                .updateContact(contactId, userToken);
        contacts = response.as(Contacts.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contact updated successfully!");
    }

    @Test()
    public void testGetContactsOfAnUser() {
        response = contactsServiceHelper
                .getContactsOfAnUser(ownerId, userToken);
        contacts = response.as(Contacts.class);

        System.out.println(contacts.getData().size());
        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contacts retrieved successfully!");
    }

    @Test()
    public void testImportOneOrMoreContacts() {
        response = contactsServiceHelper.importOneOrMoreContacts(ownerId,userToken);
        contacts = response.as(Contacts.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contacts added successfully!");
    }

    @AfterMethod
    public void tearDown() {
        userServiceHelper.deleteUser(ownerId,userToken);

        response = contactsServiceHelper
                .deleteContact(contactId,userToken);
        contacts =response.as(Contacts.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(), "Contact deleted successfully!");

        contacts =
                contactsServiceHelper
                        .getContact(contactId,userToken).as(Contacts.class);
        assertEquals(contacts.getData().get(0).getStatus(),"archived");
    }
}