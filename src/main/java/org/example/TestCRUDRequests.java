package org.example;

import com.microsoft.graph.models.*;
import com.microsoft.graph.serviceclient.GraphServiceClient;

import java.util.LinkedList;

public class TestCRUDRequests {




    public enum INFO
    {
        HOME_ADDRESS_STREET,
        HOME_ADDRESS_CITY,
        HOME_ADDRESS_STATE,

    }
    // GET specific user with all his infos
    public static User getSpecificUser(GraphServiceClient graphClient, String userID)
    {
        return graphClient.users().byUserId(userID).get();
    }


    // GET specific contact with all his infos
    public static Contact getSpecificContact(GraphServiceClient graphClient, String userID, String contactID)
    {
        return graphClient.users().byUserId(userID).contacts().byContactId(contactID).get();
    }


    // GET all contacts and his infos of a specific user
    public static ContactCollectionResponse getUsersContacts(GraphServiceClient graphClient, String userID)
    {
        return graphClient.users().byUserId(userID).contacts().get();
    }


    // GET all Folders of a specific user
    public static ContactFolderCollectionResponse getAllFolderOfUser(GraphServiceClient graphClient, String userID)
    {
        return graphClient.users().byUserId(userID).contactFolders().get();
    }


    // GET id of contact with display name
    public static String getSpecificContactIdFromDisplayName(GraphServiceClient graphClient, String userID, String displayName) throws Exception {
        var dude = graphClient.users().byUserId(userID).contacts().get(requestConfiguration -> {
                requestConfiguration.queryParameters.filter = "(displayName eq '" + displayName + "')";
            requestConfiguration.queryParameters.count = true;
                requestConfiguration.headers.add("ConsistencyLevel", "eventual");
            });
        if (dude.getValue() == null || dude.getValue().isEmpty())
            throw new Exception("Cant retrieve ParseContact Id cause display name not found");
        if (dude.getOdataCount() != 1)
            throw new Exception("Received more than one entry with this name. Should not happen lol");
        return dude.getValue().getFirst().getId();
    }


    // print all folders (ALSO all CHILD FOLDERS of a specific user
    /*  keymapversion=4
        anrede=extendedProperty(completeNameTitle).value
      x funktion=jobTitle
      x name=surname
      x geb=birthday
      x vorname=givenName
        p_strasse=physicalAddresses(Home).street
        p_plz=physicalAddresses(Home).postalCode
        p_ort=physicalAddresses(Home).city
        p_lkz=physicalAddresses(Home).countryOrRegion
        bphone1=phoneNumbers(BusinessPhone).value
        bphone2=phoneNumbers(BusinessPhone2).value
        mobile=phoneNumbers(MobilePhone).value
        carphone=phoneNumbers(CarPhone).value
        otherfax=phoneNumbers(OtherFax).value
        bfax=phoneNumbers(BusinessFax).value
        pphone=phoneNumbers(PrimaryPhone).value
        companyphone=phoneNumbers(CompanyMainPhone).value
        web=businessHomePage
        firma=companyName
        strasse=physicalAddresses(Business).street
        ort=physicalAddresses(Business).city
        plz=physicalAddresses(Business).postalCode
        info=body
        email1=emailAddresses(EmailAddress1).value
        email2=emailAddresses(EmailAddress2).value
        email3=emailAddresses(EmailAddress3).value*/
    private static void printUserOrContactData(Contact dude)
    {
        System.out.println();
        System.out.println("Name\t\t\t  :\t" +  dude.getDisplayName());
        System.out.println("---------------------------------------------------------");
        System.out.println("Name Title\t\t  :\t" + dude.getTitle());
        System.out.println("Job Title\t\t  :\t" + dude.getJobTitle());
        System.out.println("Surname\t\t\t  :\t" + dude.getSurname());
        System.out.println("Birthday\t\t  :\t" + dude.getBirthday());
        System.out.println("First Name\t\t  :\t" + dude.getGivenName());
        System.out.println();
        System.out.println("Physical Addresses (Home)");
        System.out.println("\tStreet\t\t  :\t" + dude.getHomeAddress().getStreet());
        System.out.println("\tPostal Code\t  :\t" + dude.getHomeAddress().getPostalCode());
        System.out.println("\tCity\t\t  :\t"  + dude.getHomeAddress().getCity());
        System.out.println("\tCountry/Region:\t"  + dude.getHomeAddress().getCountryOrRegion());
        System.out.println();
        System.out.println("Phone Numbers");
        if (dude.getBusinessPhones() != null && !dude.getBusinessPhones().isEmpty()) {
            if (dude.getBusinessPhones().size() > 0)
                System.out.println("\tBusiness 1\t  :\t" + dude.getBusinessPhones().get(0));
            if (dude.getBusinessPhones().size() > 1)
                System.out.println("\tBusiness 2\t  :\t" + dude.getBusinessPhones().get(1));
        }
        else
                System.out.println("\tBusiness 2\t  :\t[empty]");
        System.out.println("\tMobile\t\t  :\t" + dude.getMobilePhone());
//        System.out.println("\tCar\t\t\t  :\t" + dude.get);
        System.out.println("\tOther Fax\t  :\t");
        System.out.println("\tBusiness Fax  :\t");
        System.out.println("\tPrimary\t\t  :\t");
        System.out.println("\tCompany\t\t  :\t");
        System.out.println();
        System.out.println("Business Homepage :\t" + dude.getBusinessHomePage());
        System.out.println("Company Name\t  :\t" + dude.getCompanyName());
        System.out.println();
        System.out.println("Physical Addresses (Business)");
        System.out.println("\tStreet\t\t  :\t" + dude.getBusinessAddress().getStreet());
        System.out.println("\tCity\t\t  :\t" + dude.getBusinessAddress().getCity());
        System.out.println("\tPostal Code\t  :\t" + dude.getBusinessAddress().getPostalCode());
        System.out.println();
        System.out.println("Info/Body\t\t  :\t" + dude.getPersonalNotes());
        System.out.println();
        System.out.println("E-Mail Address 1  :\t" + dude.getEmailAddresses().get(0).getAddress());
        System.out.println("E-Mail Address 2  :\t" + dude.getEmailAddresses().get(1).getAddress());
        System.out.println("E-Mail Address 3  :\t" + dude.getEmailAddresses().get(2).getAddress());
        System.out.println("---------------------------------------------------------");
    }

    public static void printAllContactDataWithAllFoldersAndContacts(GraphServiceClient graphClient, String userID, String contactID)
    {
        Contact dude = getSpecificContact(graphClient, userID, contactID);
        printUserOrContactData(dude);
    }



    // POST new contact for a specific contact
    public static void createNewContactForUser(GraphServiceClient graphClient, String userID, TestMyContact myContact)
    {
        Contact contact = new Contact();
        contact.setGivenName(myContact.get_firstName());
        contact.setSurname(myContact.get_lastName());
        LinkedList<EmailAddress> emailAddresses = new LinkedList<>();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setAddress(myContact.get_emailAddress());
        emailAddress.setName(myContact.get_emailName());
        emailAddresses.add(emailAddress);
        contact.setEmailAddresses(emailAddresses);
        LinkedList<String> businessPhones = new LinkedList<>();
        businessPhones.add(myContact.get_phone());
        contact.setBusinessPhones(businessPhones);
        graphClient.users().byUserId(userID).contacts().post(contact);
    }


    // POST a new folder with Display Name
    public static void createNewFolder(GraphServiceClient graphClient, String userID, String folderName)
    {
        ContactFolder contactFolder = new ContactFolder();
        contactFolder.setDisplayName(folderName);
        graphClient.users().byUserId(userID).contactFolders().post(contactFolder);
    }


    // POST new ParseContact in specific folder
    public static void createNewUserInSpecificFolder(GraphServiceClient graphClient, String userID, String folderID, TestMyContact newContact)
    {
        Contact contact = new Contact();
        contact.setParentFolderId(folderID);
        contact.setDisplayName(newContact.get_firstName() + " " + newContact.get_lastName());
        contact.setGivenName(newContact.get_lastName());
        contact.setInitials("Beer");
        graphClient.users().byUserId(userID).contactFolders().byContactFolderId(folderID).contacts().post(contact);
    }


    // DELETE a specific contact from a specific user
    public static void deleteSpecificContactFromSpecificUser(GraphServiceClient graphClient, String userID, String contactID)
    {
        graphClient.users().byUserId(userID).contacts().byContactId(contactID).delete();
    }


    // DELETE a specific folder from a specific user
    public static void deleteFolderFromSpecificUser(GraphServiceClient graphClient, String userID, String folderID)
    {
        graphClient.users().byUserId(userID).contactFolders().byContactFolderId(folderID).delete();
    }


    // UPDATE soe info in a specific contact
    public static void updateInfoInContact(GraphServiceClient graphClient, String userID, String contactID)
    {
        Contact contact = new Contact();
        PhysicalAddress homeAddress = new PhysicalAddress();
        homeAddress.setStreet("NEW STREET");
        homeAddress.setCity("NEW CITY");
        homeAddress.setState("NEW STATE");
        contact.setHomeAddress(homeAddress);
        graphClient.users().byUserId(userID).contacts().byContactId(contactID).patch(contact);
    }


    // UPDATE contact folder name of a specific contact
    public static void updateContactFolderName(GraphServiceClient graphClient, String userID, String folderID, String newName)
    {
        ContactFolder contactFolder = new ContactFolder();
        contactFolder.setDisplayName(newName);
        graphClient.users().byUserId(userID).contactFolders().byContactFolderId(folderID).patch(contactFolder);
    }

    public static String getSpecificContactFolderIdFromDisplayName(GraphServiceClient graphClient, String userID, String displayName) throws Exception {
        var folder = graphClient.users().byUserId(userID).contactFolders().get(requestConfiguration -> {
            requestConfiguration.queryParameters.filter = "(displayName eq '" + displayName + "')";
            requestConfiguration.queryParameters.count = true;
            requestConfiguration.headers.add("ConsistencyLevel", "eventual");
        });
        if (folder.getValue() == null || folder.getValue().isEmpty())
            throw new Exception("Cant retrieve ParseContact Folder Id cause display name not found");
        if (folder.getOdataCount() != 1)
            throw new Exception("Received more than one entry with this name. Should not happen lol");
        return folder.getValue().getFirst().getId();
    }

    public static void updateContactName(GraphServiceClient graphClient, String user, String contactID, String newName) {

        Contact contact = new Contact();
        contact.setDisplayName(newName);
        graphClient.users().byUserId(user).contacts().byContactId(contactID).patch(contact);

    }
}
