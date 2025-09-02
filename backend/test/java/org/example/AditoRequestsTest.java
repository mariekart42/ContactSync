package org.example;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AditoRequestsTest {

    @Test
    void getContactStatusTO_CREATE() {
        Map<String, String> contact = new HashMap<>();

        contact.put("abostart", null);
        contact.put("aboende", "2010-12-13");
        contact.put("changed", "2010-12-13");
        contact.put("synced", null);

        AditoRequests.CONTACT_STATUS result = AditoRequests.getContactStatus(contact);
        assertEquals(AditoRequests.CONTACT_STATUS.TO_CREATE, result);
    }

    @Test
    void getContactStatusTO_CHANGE() {
        Map<String, String> contact = new HashMap<>();

        contact.put("abostart", "2010-12-13");
        contact.put("aboende", null);
        contact.put("changed", "2023-12-13");
        contact.put("synced", "2023-01-01");

        AditoRequests.CONTACT_STATUS result = AditoRequests.getContactStatus(contact);
        assertEquals(AditoRequests.CONTACT_STATUS.TO_CHANGE, result);
    }

    @Test
    void getContactStatusTO_DELETE() {
        Map<String, String> contact = new HashMap<>();

        contact.put("abostart", "2010-12-13");
        contact.put("aboende", "2024-12-13");
        contact.put("changed", "2023-12-13");
        contact.put("synced", "2023-01-01");

        AditoRequests.CONTACT_STATUS result = AditoRequests.getContactStatus(contact);
        assertEquals(AditoRequests.CONTACT_STATUS.TO_DELETE, result);
    }

    @Test
    void getContactStatusUNCHANGED() {
        Map<String, String> contact = new HashMap<>();

        contact.put("abostart", "2023-08-01");
        contact.put("aboende", "2023-08-10");
        contact.put("changed", "2023-08-05");
        contact.put("synced", "2023-08-10");

        AditoRequests.CONTACT_STATUS result = AditoRequests.getContactStatus(contact);
        assertEquals(AditoRequests.CONTACT_STATUS.UNCHANGED, result);
    }

    @Test
    void getContactStatusInvalidInput() {
        Map<String, String> contact = new HashMap<>();

        contact.put("abostart", "brei");
        contact.put("aboende", null);
        contact.put("changed", "2023-22-11");
        contact.put("synced", "2022-22-11");

        AditoRequests.CONTACT_STATUS result = AditoRequests.getContactStatus(contact);
        assertNull(result);
    }
}