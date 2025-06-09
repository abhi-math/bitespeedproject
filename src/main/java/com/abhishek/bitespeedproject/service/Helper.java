package com.abhishek.bitespeedproject.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhishek.bitespeedproject.entity.Contact;
import com.abhishek.bitespeedproject.entity.ContactResponse;
import com.abhishek.bitespeedproject.repository.ContactRepository;

@Service
public class Helper {

    @Autowired
    private ContactRepository contactRepo;


    //Required contacts having ids also
    public ContactResponse createContactResponseFromContactsUpdateDB(List<Contact> contacts) {
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setEmails(new ArrayList<>());
        contactResponse.setPhones(new ArrayList<>());
        contactResponse.setSecondaryContactIds(new ArrayList<>());

        for (Contact contact : contacts) {

            contactResponse.getEmails().add(contact.getEmail());
            contactResponse.getPhones().add(contact.getPhone());
            if (contactResponse.getPrimaryContactId() == null) {
                contactResponse.setPrimaryContactId(contact.getId());
                contactRepo.save(new Contact(contact.getId(),contact.getEmail(),contact.getPhone(),null, "primary"));
            } else {
                contactResponse.getSecondaryContactIds().add(contact.getId());
                contactRepo.save(new Contact(contact.getId(),contact.getEmail(),contact.getPhone(),contact.getLinkedId(), "secondary"));
            }
        }
        return contactResponse;
    }

    public ContactResponse createContactResponseFromIdsUpdateDB(List<Integer> contactIds) {
        return createContactResponseFromContactsUpdateDB(contactRepo.findAllById(contactIds));
    }


    public Set<Contact> relatedContacts(List<Contact> contacts){
    //Find All related contacts
        Set<Contact> relCons = new HashSet<>();
        Queue<Contact> que = new LinkedList<>(contacts);

        while (!que.isEmpty()) {
            Contact c = que.poll();

            if(relCons.add(c)){
                contactRepo.findAllByEmailOrPhone(c.getEmail(),c.getPhone()).forEach(cont -> {
                    if(!relCons.contains(cont))
                    que.add(cont);
                });;
            }
        }
        return relCons;
    }
}
