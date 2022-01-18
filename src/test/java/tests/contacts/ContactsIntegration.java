package tests.contacts;

import data_generation.UserDataGeneration;
import controllers.ContactsServiceControllers;
import controllers.UserServiceControllers;
import io.restassured.response.Response;
import model.contacts.Contacts;
import model.users.User;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

public class ContactsIntegration {

    private ContactsServiceControllers contactsServiceControllers = new ContactsServiceControllers();
    private UserServiceControllers userServiceControllers = new UserServiceControllers();
    private UserDataGeneration userDataGeneration = new UserDataGeneration();
    private Response response;
    private User user;
    private Contacts contacts;
    private String userToken;
    private String ownerId;
    private String contactId;

    @BeforeMethod
    public void setUp() {
        user = userServiceControllers.createUser(userDataGeneration.createFullUserData()).as(User.class);
        userToken = user.getJwtAccessToken();
        ownerId = user.getUser_info().getUserId();

        response = contactsServiceControllers.createContact(ownerId,userToken);
        contacts = response.as(Contacts.class);

        contactId=contacts.getData().get(0).getContactId();

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contact added successfully!");
    }

    @Test()
    public void testGetContact() {
        response = contactsServiceControllers.getContact(contactId,userToken);
        contacts = response.as(Contacts.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contact retrieved successfully!");
    }

    @Test()
    public void testUpdateContact() {
        response = contactsServiceControllers
                .updateContact(contactId, userToken);
        contacts = response.as(Contacts.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contact updated successfully!");
    }

    @Test()
    public void testGetContactsOfAnUser() {
        response = contactsServiceControllers
                .getContactsOfAnUser(ownerId, userToken);
        contacts = response.as(Contacts.class);

        System.out.println(contacts.getData().size());
        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contacts retrieved successfully!");
    }

    @Test()
    public void testImportOneOrMoreContacts() {
        response = contactsServiceControllers.importOneOrMoreContacts(ownerId,userToken);
        contacts = response.as(Contacts.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(),"Contacts added successfully!");
    }

    @AfterMethod
    public void tearDown() {
        userServiceControllers.deleteUser(ownerId,userToken);

        response = contactsServiceControllers
                .deleteContact(contactId,userToken);
        contacts =response.as(Contacts.class);

        assertEquals(response.statusCode(), 200, "The status code should be 200");
        assertEquals(contacts.getMessage(), "Contact deleted successfully!");

        contacts =
                contactsServiceControllers
                        .getContact(contactId,userToken).as(Contacts.class);
        assertEquals(contacts.getData().get(0).getStatus(),"archived");
    }
}