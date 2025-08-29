package org.example;

import com.microsoft.graph.models.Contact;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
public class ContactController {

    private final App contactService;

    // Spring injects your service here
    public ContactController(App contactService) {
        this.contactService = contactService;
    }


    @GetMapping("/contacts")
    public List<String> getContacts() {
        return contactService.fetchContacts();
    }


    @GetMapping("/trigger")
    public String triggerBackendLogic() {
        // your backend logic here
        System.out.println("Button was pressed! Running backend function...");

        contactService.runRoutine();
        return "Backend logic executed!";
    }
}