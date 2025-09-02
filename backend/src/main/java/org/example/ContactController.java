package org.example;

import com.azure.core.annotation.Get;
import com.microsoft.graph.models.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ContactController {

    private final App contactService;

    @PostMapping("/submit")
    public String receiveMessage(@RequestBody Map<String, String> payload) {
        String msg = payload.get("message");
        System.out.println("Received: " + msg);
        return "Server received: " + msg;
    }


    @GetMapping("/principals")
    public List<Map<String, String>> getPrincipals() {

        return contactService.getPrincipals();
    }

    @GetMapping("/getContactDataFromPrincipalId")
    public List<Map<String, String>> getContactDataFromPrincipalId(@RequestParam int principalId) {
        return contactService.getContactDataFromPrincipalId(principalId);
    }



    // Spring injects your service here
    public ContactController(App contactService) {
        this.contactService = contactService;
    }


//    @GetMapping("/contacts")
//    public List<String> getContacts() {
//        return contactService.fetchContacts();
//    }
//
//
//    @GetMapping("/trigger")
//    public String triggerBackendLogic() {
//        // your backend logic here
//        System.out.println("Button was pressed! Running backend function...");
//
//        contactService.runRoutine();
//        return "Backend logic executed!";
//    }
}