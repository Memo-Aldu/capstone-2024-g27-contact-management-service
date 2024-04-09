package com.crm.contactmanagementservice.service;

import com.crm.contactmanagementservice.dto.ContactListDTO;

import java.util.Set;
import java.util.UUID;

/**
 * Service interface for ContactList.
 * This interface provides methods to interact with the ContactListService.
 * It includes methods to get a contact list by its id, get all contact lists, create a new contact list, update a contact list, and delete a contact list by its id.
 */
public interface ContactListService {

    /**
     * Fetches a contact list by its id.
     * @param id The id of the contact list to fetch.
     * @return The fetched ContactListDTO.
     */
    ContactListDTO getContactListById(UUID id);

    /**
     * Fetches all contact lists.
     * @return A Set of all ContactListDTO.
     */
    Set<ContactListDTO> getAllContactLists();

    /**
     * Creates a new contact list.
     * @param contactListDTO The ContactListDTO to create.
     * @return The created ContactListDTO.
     */
    ContactListDTO createContactList(ContactListDTO contactListDTO);

    /**
     * Updates a contact list.
     * @param contactListDTO The ContactListDTO to update.
     * @param id The id of the contact list to update.
     * @return The updated ContactListDTO.
     */
    ContactListDTO updateContactList(ContactListDTO contactListDTO, UUID id);

    /**
     * Deletes a contact list by its id.
     * @param id The id of the contact list to delete.
     */
    void deleteContactListById(UUID id);
}