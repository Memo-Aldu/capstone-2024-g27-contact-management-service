package com.crm.contactmanagementservice.service;

import com.crm.contactmanagementservice.dto.ContactDTO;

import java.util.Set;
import java.util.UUID;

/**
 * Service interface for Contact.
 * This interface provides methods to interact with the ContactService.
 * It includes methods to get a contact by its id, email, phone, get all contacts, get all contacts by a contact list's id,
 * create a new contact, update a contact, delete a contact by its id, and search contacts by name.
 */
public interface ContactService {

    /**
     * Fetches a contact by its id.
     * @param id The id of the contact to fetch.
     * @return The fetched ContactDTO.
     */
    ContactDTO getContactById(UUID id);

    /**
     * Fetches a contact by its email.
     * @param email The email of the contact to fetch.
     * @return The fetched ContactDTO.
     */
    ContactDTO getContactByEmail(String email);

    /**
     * Fetches a contact by its phone.
     * @param phone The phone of the contact to fetch.
     * @return The fetched ContactDTO.
     */
    ContactDTO getContactByPhone(String phone);

    /**
     * Fetches all contacts.
     * @return A Set of all ContactDTO.
     */
    Set<ContactDTO> getAllContacts();

    /**
     * Fetches all contacts from a contact list.
     * @return A Set of all ContactDTO.
     */
    Set<ContactDTO> getAllContactsByContactListID(UUID id);

    /**
     * Fetches all contacts from a contact list.
     * @return A Set of all ContactDTO.
     */
    Set<ContactDTO> getAllContactsByUserId(UUID id);

    /**
     * Fetches all contacts by a contact list's id.
     * @param contactListId The id of the contact list whose contacts to fetch.
     * @return A Set of ContactDTO that belong to the contact list.
     */
    Set<ContactDTO> getAllContactsByContactListId(UUID contactListId);

    /**
     * Creates a new contact.
     * @param contactDTO The ContactDTO to create.
     * @return The created ContactDTO.
     */
    ContactDTO createContact(ContactDTO contactDTO);

    /**
     * Updates a contact.
     * @param contactDTO The ContactDTO to update.
     * @param id The id of the contact to update.
     * @return The updated ContactDTO.
     */
    ContactDTO updateContact(ContactDTO contactDTO, UUID id);

    /**
     * Deletes a contact by its id.
     * @param id The id of the contact to delete.
     */
    void deleteContactById(UUID id);

    /**
     * Searches for contacts by name.
     * @param name The name to search for.
     * @return A Set of ContactDTO that match the search criteria.
     */
    Set<ContactDTO> searchContactsByName(String name);
}
