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

@Service
@AllArgsConstructor
@Slf4j
public class ContactListServiceImpl implements ContactListService {

    private final ContactListRepository contactListRepository;
    private final ContactListMapper contactListMapper;

    @Override
    public ContactListDTO getContactListById(UUID id) {
        log.info("Fetching contact list by id: {}", id);
        return contactListMapper.toDTO(contactListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactList not found"))); // Customize exception as needed
    }

    @Override
    public Set<ContactListDTO> getAllContactLists() {
        log.info("Fetching all contact lists");
        return contactListRepository.findAll().stream()
                .map(contactListMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ContactListDTO createContactList(ContactListDTO contactListDTO) {
        log.info("Creating new contact list");
        ContactListEntity contactListEntity = contactListMapper.toEntity(contactListDTO);
        return contactListMapper.toDTO(contactListRepository.save(contactListEntity));
    }

    @Override
    public ContactListDTO updateContactList(ContactListDTO contactListDTO, UUID id) {
        log.info("Updating contact list with id: {}", id);
        ContactListEntity contactListEntity = contactListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContactList not found with id: " + id));

        contactListEntity.setListName(contactListDTO.listName());
        contactListEntity.setUserId(contactListDTO.userId());

        return contactListMapper.toDTO(contactListRepository.save(contactListEntity));
    }

    @Override
    public void deleteContactListById(UUID id) {
        log.info("Deleting contact list by id: {}", id);
        contactListRepository.deleteById(id);
    }
}
