package com.crm.contactmanagementservice.service;


import com.crm.contactmanagementservice.dto.ContactDTO;

import java.util.Set;
import java.util.UUID;

public interface ContactService {

    ContactDTO getContactById(UUID id);

    ContactDTO getContactByEmail(String email);

    ContactDTO getContactByPhone(String phone);

    Set<ContactDTO> getAllContacts();

    Set<ContactDTO> getAllContactsByContactListId(UUID contactListId);

    ContactDTO createContact(ContactDTO contactDTO);

    ContactDTO updateContact(ContactDTO contactDTO, UUID id);

    void deleteContactById(UUID id);

    Set<ContactDTO> searchContactsByName(String name);
}