package com.crm.contactmanagementservice.service.impl;

import com.crm.contactmanagementservice.service.ContactListService;
import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.mapper.ContactListMapper;
import com.crm.contactmanagementservice.repository.ContactListRepository;
import com.crm.contactmanagementservice.entity.ContactListEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for ContactList.
 * This class implements the ContactListService interface and provides methods to interact with the ContactListRepository.
 * It uses a ContactListMapper to convert between ContactListEntity and ContactListDTO.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ContactListServiceImpl implements ContactListService {

    private final ContactListRepository contactListRepository;
    private final ContactListMapper contactListMapper;

    /**
     * Fetches a contact list by its id.
     * @param id The id of the contact list to fetch.
     * @return The fetched ContactListDTO.
     * @throws RuntimeException if the contact list is not found.
     */
    @Override
    public ContactListDTO getContactListById(UUID id) {
        log.info("Fetching contact list by id: {}", id);
        return contactListMapper.toDTO(contactListRepository.findContactListEntitiesById(id)
                .orElseThrow(() -> new RuntimeException("ContactList not found"))); // Customize exception as needed
    }

    /**
     * Fetches all contact lists.
     * @return A Set of all ContactListDTO.
     */
    @Override
    public Set<ContactListDTO> getAllContactLists() {
        log.info("Fetching all contact lists");
        return contactListRepository.findAll().stream()
                .map(contactListMapper::toDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Creates a new contact list.
     * @param contactListDTO The ContactListDTO to create.
     * @return The created ContactListDTO.
     */
    @Override
    public ContactListDTO createContactList(ContactListDTO contactListDTO) {
        log.info("Creating new contact list");
        ContactListEntity contactListEntity = contactListMapper.toEntity(contactListDTO);
        return contactListMapper.toDTO(contactListRepository.save(contactListEntity));
    }

    /**
     * Updates a contact list.
     * @param contactListDTO The ContactListDTO to update.
     * @param id The id of the contact list to update.
     * @return The updated ContactListDTO.
     * @throws RuntimeException if the contact list is not found.
     */
    @Override
    public ContactListDTO updateContactList(ContactListDTO contactListDTO, UUID id) {
        log.info("Updating contact list with id: {}", id);
        ContactListEntity contactListEntity = contactListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactList not found with id: " + id));

        contactListEntity.setListName(contactListDTO.listName());
        contactListEntity.setUserId(contactListDTO.userId());

        return contactListMapper.toDTO(contactListRepository.save(contactListEntity));
    }

    /**
     * Deletes a contact list by its id.
     * @param id The id of the contact list to delete.
     */
    @Override
    public void deleteContactListById(UUID id) {
        log.info("Deleting contact list by id: {}", id);
        contactListRepository.deleteById(id);
    }
}