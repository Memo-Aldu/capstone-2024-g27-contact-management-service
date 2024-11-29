package com.crm.contactmanagementservice.service.impl;

import com.crm.contactmanagementservice.service.ContactService;
import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.entity.ContactEntity;
import com.crm.contactmanagementservice.exceptions.AppEntityNotFoundException;
import com.crm.contactmanagementservice.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.crm.contactmanagementservice.mapper.ContactMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing contacts.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    /**
     * Fetches a contact by its ID.
     * @param id The ID of the contact.
     * @return The contact DTO.
     * @throws AppEntityNotFoundException if the contact is not found.
     */
    @Override
    public ContactDTO getContactById(UUID id) {
        log.info("Fetching contact by id: {}", id);
        return contactMapper.toDTO(contactRepository.findContactEntityById(id)
                .orElseThrow(() -> new AppEntityNotFoundException("Contact not found")));
    }

    /**
     * Fetches a contact by its email.
     * @param email The email of the contact.
     * @return The contact DTO.
     * @throws AppEntityNotFoundException if the contact is not found.
     */
    @Override
    public ContactDTO getContactByEmail(String email) {
        log.info("Fetching contact by email: {}", email);
        return contactMapper.toDTO(contactRepository.findContactEntityByEmail(email)
                .orElseThrow(() -> new AppEntityNotFoundException("Contact not found")));
    }

    /**
     * Fetches a contact by its phone number.
     * @param phone The phone number of the contact.
     * @return The contact DTO.
     * @throws AppEntityNotFoundException if the contact is not found.
     */
    @Override
    public ContactDTO getContactByPhone(String phone) {
        log.info("Fetching contact by phone: {}", phone);
        return contactMapper.toDTO(contactRepository.findContactEntityByPhone(phone)
                .orElseThrow(() -> new AppEntityNotFoundException("Contact not found")));
    }

    /**
     * Fetches all contacts.
     * @return A set of all contact DTOs.
     */
    @Override
    public Set<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts");
        return contactRepository.findAllContactEntities().stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Fetches all contacts from a contact list by its ID.
     * @param id The ID of the contact list.
     * @return A Set of ContactDTO representing all contacts in the specified contact list.
     */
    @Override
    public Set<ContactDTO> getAllContactsByContactListID(UUID id) {
        log.info("Fetching all contacts by contact list ID: {}", id);
        return contactRepository.findAllContactsByContactListId(id).stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }
    /**
     * Fetches all contacts from a user ID.
     * @param id The ID of the contact list.
     * @return A Set of ContactDTO representing all contacts in the specified contact list.
     */
    @Override
    public Set<ContactDTO> getAllContactsByUserId(UUID id) {
        log.info("Fetching all contacts by user ID: {}", id);
        return contactRepository.findAllContactsByUserId(id).stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Fetches all contacts by a contact list ID.
     * @param contactListId The ID of the contact list.
     * @return A set of all contact DTOs in the contact list.
     */
    @Override
    public Set<ContactDTO> getAllContactsByContactListId(UUID contactListId) {
        log.info("Fetching all contacts by contact list ID: {}", contactListId);
        return contactRepository.findAllContactsByContactListId(contactListId).stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Creates a new contact.
     * @param contactDTO The contact DTO to create.
     * @return The created contact DTO.
     */
    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        log.info("Creating new contact");
        ContactEntity contactEntity = contactMapper.toEntity(contactDTO);
        return contactMapper.toDTO(contactRepository.save(contactEntity));
    }

    /**
     * Updates a contact.
     * @param contactDTO The contact DTO to update.
     * @param id The ID of the contact to update.
     * @return The updated contact DTO.
     * @throws AppEntityNotFoundException if the contact is not found.
     */
    @Override
    public ContactDTO updateContact(ContactDTO contactDTO, UUID id) {
        log.info("Updating contact with id: {}", id);
        ContactEntity contactEntity = contactRepository.findContactEntityById(id)
                .orElseThrow(() -> new AppEntityNotFoundException("Contact not found with id: " + id));

        if(contactDTO.firstName() != null) {
            contactEntity.setFirstName(contactDTO.firstName());
        }
        if(contactDTO.lastName() != null) {
            contactEntity.setLastName(contactDTO.lastName());
        }
        if(contactDTO.preferredName() != null) {
            contactEntity.setPreferredName(contactDTO.preferredName());
        }
        if(contactDTO.email() != null) {
            contactEntity.setEmail(contactDTO.email());
        }
        if(contactDTO.phone() != null) {
            contactEntity.setPhone(contactDTO.phone());
        }
        if(contactDTO.fax() != null) {
            contactEntity.setFax(contactDTO.fax());
        }
        contactEntity.setAddressId(contactDTO.addressId());
        contactEntity.setDoNotContact(contactDTO.doNotContact());

        return contactMapper.toDTO(contactRepository.save(contactEntity));
    }

    /**
     * Deletes a contact by its ID.
     * @param id The ID of the contact to delete.
     */
    @Override
    public void deleteContactById(UUID id) {
        log.info("Deleting contact by id: {}", id);
        contactRepository.deleteContactEntityById(id);
    }

    /**
     * Searches contacts by name.
     * @param name The name to search for.
     * @return A set of contact DTOs that match the search.
     */
    @Override
    public Set<ContactDTO> searchContactsByName(String name) {
        log.info("Searching contacts by first name, last name or preferred containing: {}", name);
        return contactRepository.searchByName("%" + name + "%").stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
