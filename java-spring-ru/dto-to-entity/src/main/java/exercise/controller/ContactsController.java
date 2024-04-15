package exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @GetMapping("")
    public List<ContactDTO> getContacts() {
        List<Contact> contactList = contactRepository.findAll();

        var result = contactList.stream().map(this::toContactDto).toList();
        return result;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO createContact(@RequestBody ContactCreateDTO newContact) {

        Contact contact = toEntity(newContact);
        Contact savedContact = contactRepository.save(contact);
        return toContactDto(savedContact);
    }

    private ContactDTO toContactDto(Contact contact) {
        return ContactDTO.builder()
        .id(contact.getId())
        .firstName(contact.getFirstName())
        .lastName(contact.getLastName())
        .phone(contact.getPhone())
        .updatedAt(contact.getUpdatedAt())
        .createdAt(contact.getCreatedAt())
        .build();
    }

    private Contact toEntity(ContactCreateDTO contactCreateDTO) {
        return Contact.builder()
        .firstName(contactCreateDTO.getFirstName())
        .lastName(contactCreateDTO.getLastName())
        .phone(contactCreateDTO.getPhone())
        .build();
    }
    // END
}
