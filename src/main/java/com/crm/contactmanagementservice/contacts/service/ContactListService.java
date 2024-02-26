package com.crm.contactmanagementservice.contacts.service;

import com.crm.contactmanagementservice.contacts.dto.ContactListDTO;
import java.util.Set;
import java.util.UUID;

public interface ContactListService {

    ContactListDTO getContactListById(UUID id);

    Set<ContactListDTO> getAllContactLists();

    ContactListDTO createContactList(ContactListDTO contactListDTO);

    ContactListDTO updateContactList(ContactListDTO contactListDTO, UUID id);

    void deleteContactListById(UUID id);
}
