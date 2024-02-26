package com.crm.contactmanagementservice.contacts.service;


import com.crm.contactmanagementservice.contacts.dto.ContactDTO;
import java.util.Set;
import java.util.UUID;

public interface ContactService {

    ContactDTO getContactById(UUID id);

    ContactDTO getContactByEmail(String email);

    ContactDTO getContactByPhone(String phone);

    Set<ContactDTO> getAllContacts();

    Set<ContactDTO> getAllContactsByContactListId(Long contactListId);

    ContactDTO createContact(ContactDTO contactDTO);

    ContactDTO updateContact(ContactDTO contactDTO, UUID id);

    void deleteContactById(UUID id);
    boolean checkContactDoNotContactFlag(UUID id);

    Set<ContactDTO> searchContactsByFirstName(String firstName);

    Set<ContactDTO> searchContactsByLastName(String lastName);

    Set<ContactDTO> getContactsByPreferredName(String preferredName);
}