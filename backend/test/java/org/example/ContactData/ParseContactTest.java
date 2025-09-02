package org.example.ContactData;

import com.microsoft.graph.models.Contact;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParseContactTest {

    @Test
    void parseContactDifferentValues() {
        ParseContact parse = new ParseContact();
        Contact parseContact1 = new Contact();
        Contact parseContact2 = new Contact();
        parse.addDataToContact(parseContact1, "info", "informat");
        parse.addDataToContact(parseContact2, "info", "");
        assertAll(() -> assertNotEquals(parseContact1.getPersonalNotes(), parseContact2.getPersonalNotes()));
        parse.addDataToContact(parseContact2, "info", "informat");
        assertAll(() -> assertEquals(parseContact1.getPersonalNotes(), parseContact2.getPersonalNotes()));
    }


    @Test
    void clearFields() {
    }
}