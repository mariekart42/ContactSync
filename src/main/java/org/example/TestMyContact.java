package org.example;

public class TestMyContact {
    private String _firstName;
    private String _lastName;
    private String _emailAddress;
    private String _emailName;
    private String _phone;
    
    public TestMyContact(String firstName, String lastName, String emailAddress, String emailName, String phone)
    {
        _firstName = firstName; 
        _lastName = lastName;
        _emailAddress = emailAddress;
        _emailName = emailName;
        _phone = phone;
    }

    public String get_firstName() { return _firstName; }
    public String get_lastName() { return _lastName; }
    public String get_emailAddress() { return _emailAddress; }
    public String get_emailName() { return _emailName; }
    public String get_phone() { return _phone; }
}
