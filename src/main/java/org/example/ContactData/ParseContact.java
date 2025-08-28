package org.example.ContactData;

import com.microsoft.graph.models.Contact;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.PhysicalAddress;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParseContact
{
    PhysicalAddress _homeAddress = new PhysicalAddress();
    PhysicalAddress _businessAddress = new PhysicalAddress();
    List<String> _businessPhones = new ArrayList<>();
    List<EmailAddress> _emailAddresses = new ArrayList<>();


    // function returns a MSGraph Contact object with all NEW data received from adito
    // it considers to_external and only adds attributes that have changed values
    // fields defined in avoidFields are considered
    // return: Contact object with only attributes oth changed data
    public Contact getContact(String aditoData, Map<String, String> contactMetaData) throws Exception {
        String toExternal = contactMetaData.get("to_external");
        String avoidFields = contactMetaData.get("avoid");
        String deviceSpecifics = contactMetaData.get("devicespecifics");

        Map<String, String> aditoMap = parseToMap(aditoData);
        Map<String, String> toExternalMap = parseToMap(toExternal);
        clearFields(toExternalMap, avoidFields);

        if (aditoData.equalsIgnoreCase(toExternal) || aditoMap.equals(toExternalMap))
            return null; // nothing to update cause maps are identical

        Contact contact = new Contact();

        for (String key : aditoMap.keySet())
        {
            String val1 = aditoMap.get(key);
            String val2 = toExternalMap.get(key);

            if (val1.equalsIgnoreCase(val2))
                continue;

            addDataToContact(contact, key, val1);
        }
        setAddresses(contact);
        setFileAsAttribute(contact, deviceSpecifics);
        return contact;
    }

    private void setFileAsAttribute(Contact contact, String devicespecifics) throws Exception {

        if (devicespecifics != null && devicespecifics.startsWith("fileAsMapping="))
            devicespecifics = devicespecifics.substring("fileAsMapping=".length());

        switch (devicespecifics)
        {
            case "Company" -> contact.setFileAs(contact.getCompanyName()); // Firma
            case "CompanyLastCommaFirst" -> contact.setFileAs(contact.getCompanyName()+contact.getSurname()+","+contact.getGivenName()); // FirmaNachname,Vorname
            case "CompanyLastFirst" -> contact.setFileAs(contact.getCompanyName()+contact.getSurname()+contact.getGivenName()); // FirmaNachnameVorname
            case "CompanyLastSpaceFirst" -> contact.setFileAs(contact.getCompanyName()+contact.getSurname()+" "+contact.getGivenName()); // FirmaNachname Vorname
            case "FirstSpaceLast" -> contact.setFileAs(contact.getGivenName()+" "+contact.getSurname()); // Vorname Nachname
            case "LastCommaFirst" -> contact.setFileAs(contact.getSurname()+","+contact.getGivenName()); // Nachname,Vorname
            case "LastCommaFirstCompany" -> contact.setFileAs(contact.getSurname()+","+contact.getGivenName()+contact.getCompanyName()); // Nachname,VornameFirma
            case "LastFirst" -> contact.setFileAs(contact.getSurname()+contact.getGivenName()); // NachnameVorname
            case "LastFirstCompany" -> contact.setFileAs(contact.getSurname()+contact.getGivenName()+contact.getCompanyName()); // NachnameVornameFirma
            case "LastFirstSuffix" -> contact.setFileAs(contact.getSurname()+contact.getGivenName()); // NachnameVornameZusatz // TODO figure out what msgraph attribute represents 'Zusatz'
            case "LastSpaceFirst" -> contact.setFileAs(contact.getSurname()+" "+contact.getGivenName()); // Nachname Vorname
            case "LastSpaceFirstCompany" -> contact.setFileAs(contact.getSurname()+" "+contact.getGivenName()+contact.getCompanyName()); // Nachname VornameFirma
            case null, default ->
            // TODO should the error if devicespecifics not found be in the syncresult? or just ignore?
                throw new Exception("devicespecifics was defined wron in adito: " + devicespecifics);

        }
    }

    private void setAddresses(Contact contact)
    {
        if (_homeAddress.getStreet() != null || _homeAddress.getPostalCode() != null || _homeAddress.getCity() != null || _homeAddress.getState() != null)
            contact.setHomeAddress(_homeAddress);
        if (_businessAddress.getStreet() != null || _businessAddress.getPostalCode() != null || _businessAddress.getCity() != null)
            contact.setBusinessAddress(_businessAddress);
        if (!_businessPhones.isEmpty())
            contact.setBusinessPhones(_businessPhones);
        if (!_emailAddresses.isEmpty())
            contact.setEmailAddresses(_emailAddresses);
        _emailAddresses.clear();
        _businessPhones.clear();
        _emailAddresses.clear();
    }


    // TODO: figure out:
    //       - anrede
    //       - carphone
    //       - otherfax
    //       - bfax
    //       - companyphone

    // function adds the specific values from adito to the Contact object
    protected void addDataToContact(Contact contact, String key, String value)
    {
        switch (key)
        {
//            case "anrede":
//                // anrede=extendedProperty(completeNameTitle).value
//                break;
            case "funktion":
                contact.setJobTitle(value); break;
            case "name":
                contact.setSurname(value); break;
            case "geb":
                if (value.isEmpty() || !value.matches("\\d{4}-\\d{2}-\\d{2}.*")) contact.setBirthday(null);
                else contact.setBirthday(OffsetDateTime.parse(value));
                break;
            case "vorname":
                contact.setGivenName(value); break;
            case "p_strasse":
                _homeAddress.setStreet(value); break;
            case "p_plz":
                _homeAddress.setPostalCode(value); break;
            case "p_ort":
                _homeAddress.setCity(value); break;
            case "p_lkz":
                _homeAddress.setState(value); break;
            case "bphone1":
            case "bphone2":
                _businessPhones.add(value); break;
            case "mobile":
                contact.setMobilePhone(value); break;
//            case "carphone": // ?
//                break;
//            case "otherfax": // extended properties?
//                break;
//            case "bfax": // extended properties?
//                break;
//            case "companyphone": // extended properties?
//                break;
            case "web":
                contact.setBusinessHomePage(value); break;
            case "firma":
                contact.setCompanyName(value); break;
            case "strasse":
                _businessAddress.setStreet(value); break;
            case "ort":
                _businessAddress.setCity(value); break;
            case "plz":
                _businessAddress.setPostalCode(value); break;
            case "email1":
            case "email2":
            case "email3":
                EmailAddress emailAddress = new EmailAddress();
                emailAddress.setAddress(value);
                _emailAddresses.add(emailAddress); break;
            case "info":
                contact.setPersonalNotes(value); break;
        }
    }


    // function removes all values in the map that represents a value of an attribute that the user specified on avoidFields
    // => value of attribute will be cleared and ttherefore not considered to be updated to Outlook
    public void clearFields(Map<String, String> map, String fieldsToClear)
    {
        if (fieldsToClear == null || fieldsToClear.isEmpty())
            return;
        String[] keys = fieldsToClear.replace("*", "").split(",");
        // fields that the user can't avoid even if specified in avoidFields
        List<String> mandatoryFields = List.of("mapversion", "name", "vorname", "firma", "strasse", "ort", "plz");

        for (String key : keys)
        {
            if (map.containsKey(key) && !mandatoryFields.contains(key))
                map.put(key, "");
        }
    }

    private Map<String, String> parseToMap(String data)
    {
        if (data == null || data.isEmpty())
            return null;
        Map<String, String> map = new LinkedHashMap<>();
        String[] lines = data.split("\\r?\\n");
        for (String line : lines)
        {
            int idx = line.indexOf('=');
            if (idx >= 0)
            {
                String key = line.substring(0, idx).trim();
                String value = line.substring(idx + 1).trim();
                map.put(key, value);
            }
        }
        return map;
    }
}
