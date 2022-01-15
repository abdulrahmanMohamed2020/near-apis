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
    public void createContact() {
        Contacts contacts = contactsServiceHelper.createContact().as(Contacts.class);

        contactId=contacts.getData().get(0).getContactId();
        assertEquals(contacts.getMessage(),"Contact added successfully!");
    }

    @Test()
    public void getContact() {
        Contacts contacts = contactsServiceHelper.getContact("c0na59xpwF6BdA3Kpj5W5").as(Contacts.class);

        System.out.println(contacts.getData().get(0).getFirstName());
        System.out.println(contacts.getData().get(0).getAddress().get(0).getCountry());
        System.out.println(contacts.getData().get(0).getBirthday());
        System.out.println(contacts.getData().get(0).getEmail().get(0).getType());
        System.out.println(contacts.getData().get(0).getJobTitle());
        assertEquals(contacts.getMessage(),"Contact retrieved successfully!");
    }
}
