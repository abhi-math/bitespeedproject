package com.abhishek.bitespeedproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponse {
    private Integer primaryContactId;
    private List<String> emails;
    private List<String> phones;
    private List<Integer> secondaryContactIds;
}
