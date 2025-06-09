package com.abhishek.bitespeedproject.controller;

import com.abhishek.bitespeedproject.entity.ContactResponse;
import com.abhishek.bitespeedproject.entity.Person;
import com.abhishek.bitespeedproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/Identity")
    public ResponseEntity<ContactResponse> createContact(@RequestBody Person person   ) {
        System.out.println("Identity api : "+ person);
        ContactResponse contactResponse = contactService.createUser(person);
        return new ResponseEntity<>(contactResponse, HttpStatus.CREATED);
    }

    @GetMapping("/IdentityForm")
    public ResponseEntity<ContactResponse> createContactForm(@RequestParam String email, @RequestParam String phone ) {
        Person person =new Person(email, phone);
        System.out.println("Identity api : "+ person);
        ContactResponse contactResponse = contactService.createUser(person);
        return new ResponseEntity<>(contactResponse, HttpStatus.CREATED);
    }



    @GetMapping("/String")
    public String getMethodName(@RequestParam String user) {
        System.out.println("string api : "+ user);
        return new String(user);
    }
    
}
