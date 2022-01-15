package tests.contactsTests;

import helpers.ContactsServiceHelper;
import io.restassured.response.Response;
import model.contacts.Contacts;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestContacts {

    private ContactsServiceHelper contactsServiceHelper;
    private String contactId;

    @BeforeClass
    public void init() {
        contactsServiceHelper = new ContactsServiceHelper();
    }

    @Test()
    public void testCreateContact() {
        Contacts contacts = contactsServiceHelper.createContact().as(Contacts.class);

        contactId=contacts.getData().get(0).getContactId();
        assertEquals(contacts.getMessage(),"Contact added successfully!");
    }

    @Test()
    public void testGetContact() {
        Contacts contacts = contactsServiceHelper.getContact("c0na59xpwF6BdA3Kpj5W5").as(Contacts.class);

        System.out.println(contacts.getData().get(0).getFirstName());
        System.out.println(contacts.getData().get(0).getAddress().get(0).getCountry());
        System.out.println(contacts.getData().get(0).getBirthday());
        System.out.println(contacts.getData().get(0).getEmail().get(0).getType());
        System.out.println(contacts.getData().get(0).getJobTitle());
        assertEquals(contacts.getMessage(),"Contact retrieved successfully!");
    }

    @Test()
    public void testUpdateContact() {
        Contacts contacts = contactsServiceHelper.updateContact("c0na59xpwF6BdA3Kpj5W5").as(Contacts.class);
        assertEquals(contacts.getMessage(),"Contact updated successfully!");

        contacts = contactsServiceHelper.getContact("c0na59xpwF6BdA3Kpj5W5").as(Contacts.class);
    }

    @Test()
    public void testDeleteContact() {
        Response response = contactsServiceHelper.deleteContact("zB7XOReMFLyJsNEbzvwrY");

        assertEquals(response.jsonPath().get("message"), "Contact deleted successfully!");
    }

    @Test()
    public void verifyTheContactStatusAfterDeleting() {
        Contacts contacts = contactsServiceHelper.getContact("zB7XOReMFLyJsNEbzvwrY").as(Contacts.class);

        assertEquals(contacts.getData().get(0).getStatus(),"archived");
    }

    @Test()
    public void testGetContactsOfAnUser() {
        Contacts contacts =
                contactsServiceHelper
                        .getContactsOfAnUser("v5ny4Mo51he_dotJH0pVr").as(Contacts.class);

        System.out.println(contacts.getData().size());
        assertEquals(contacts.getMessage(),"Contacts retrieved successfully!");
    }

    @Test()
    public void testImportOneOrMoreContacts() {
        Contacts contacts = contactsServiceHelper.importOneOrMoreContacts().as(Contacts.class);

        assertEquals(contacts.getMessage(),"Contacts added successfully!");
    }
}