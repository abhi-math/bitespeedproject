package com.abhishek.bitespeedproject.service;

import com.abhishek.bitespeedproject.entity.Contact;
import com.abhishek.bitespeedproject.entity.ContactResponse;
import com.abhishek.bitespeedproject.entity.Person;
import com.abhishek.bitespeedproject.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private Helper helper;
    //             .email(primaryContactByEmail.getEmail())
    //             .phone(primaryContactByPhone.getPhone())
    //             .build());
    // }

    public ContactResponse createUser(Person person) {
        List<Contact> contacts = contactRepository.findAllByEmailOrPhone(person.getEmail(), person.getPhone());
        List<Contact> contactsByEmail = contactRepository.findAllByEmail(person.getEmail());
        List<Contact> contactsByPhone = contactRepository.findAllByPhone(person.getPhone());

        // When no match of email or phone
        if (contacts.size() == 0) {
            Contact newContact = contactRepository.save(
                    Contact.builder()
                            .email(person.getEmail())
                            .phone(person.getPhone())
                            .linkedId(null)
                            .linkedPreference("primary")
                            .build());

            return ContactResponse.builder()
                    .primaryContactId(newContact.getId())
                    .emails(List.of(newContact.getEmail()))
                    .phones(List.of(newContact.getPhone()))
                    .secondaryContactIds(List.of())
                    .build();
        }

        if (contactsByEmail.isEmpty()) {

            List<Contact> cont = new ArrayList<>(helper.relatedContacts(contacts));
            Contact newContact = contactRepository.save(
                    Contact.builder()
                            .email(person.getEmail())
                            .phone(person.getPhone())
                            .linkedId(cont.get(0).getId())
                            .linkedPreference("secondary")
                            .build());
            cont.add(newContact);

            return helper.createContactResponseFromContactsUpdateDB(cont);

        }

        if (contactsByPhone.isEmpty()) {

            List<Contact> cont = new ArrayList<>(helper.relatedContacts(contacts));
            Contact newContact = contactRepository.save(
                    Contact.builder()
                            .email(person.getEmail())
                            .phone(person.getPhone())
                            .linkedId(cont.get(0).getId())
                            .linkedPreference("secondary")
                            .build());
            cont.add(newContact);

            return helper.createContactResponseFromContactsUpdateDB(cont);

        }

        List<Contact> cont = new ArrayList<>(helper.relatedContacts(contacts));

        return helper.createContactResponseFromContactsUpdateDB(cont);

    }

}
