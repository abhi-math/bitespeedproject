package com.abhishek.bitespeedproject.repository;

import com.abhishek.bitespeedproject.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findAllByEmailOrPhone(String email, String phone);
    List<Contact> findAllByEmail(String email);
    List<Contact> findAllByPhone(String phone);

    
}
