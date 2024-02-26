package com.crm.contactmanagementservice.contacts.service.impl;

import com.crm.contactmanagementservice.contacts.service.ContactService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.crm.contactmanagementservice.contacts.dto.ContactDTO;
import com.crm.contactmanagementservice.contacts.entity.ContactEntity;
import com.crm.contactmanagementservice.contacts.exceptions.AppEntityNotFoundException;
import com.crm.contactmanagementservice.contacts.mapper.ContactMapper;
import com.crm.contactmanagementservice.contacts.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper; // Assume this mapper exists for converting entities to DTOs and vice versa

    @Override
    public ContactDTO getContactById(UUID id) {
        log.info("Fetching contact by id: {}", id);
        return contactMapper.toDTO(contactRepository.findContactEntityById(id)
                .orElseThrow(() -> new AppEntityNotFoundException("Contact not found")));
    }

    @Override
    public ContactDTO getContactByEmail(String email) {
        log.info("Fetching contact by email: {}", email);
        return contactMapper.toDTO(contactRepository.findContactEntityByEmail(email)
                .orElseThrow(() -> new AppEntityNotFoundException("Contact not found")));
    }

    @Override
    public ContactDTO getContactByPhone(String phone) {
        log.info("Fetching contact by phone: {}", phone);
        return contactMapper.toDTO(contactRepository.findContactEntityByPhone(phone)
                .orElseThrow(() -> new AppEntityNotFoundException("Contact not found")));
    }

    @Override
    public Set<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts");
        return contactRepository.findAllContactEntities().stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ContactDTO> getAllContactsByContactListId(Long contactListId) {
        log.info("Fetching all contacts by contact list ID: {}", contactListId);
        return contactRepository.findAllContactsByContactListId(contactListId).stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        log.info("Creating new contact");
        ContactEntity contactEntity = contactMapper.toEntity(contactDTO); // Assume this method exists
        return contactMapper.toDTO(contactRepository.save(contactEntity));
    }
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

    @Override
    public void deleteContactById(UUID id) {
        log.info("Deleting contact by id: {}", id);
        contactRepository.deleteContactEntityById(id);
    }
    @Override
    public boolean checkContactDoNotContactFlag(UUID id) {
        ContactEntity contact = contactRepository.findContactEntityById(id)
                .orElseThrow(() -> new AppEntityNotFoundException("Contact not found"));
        return contact.isDoNotContact();
    }

    @Override
    public Set<ContactDTO> searchContactsByFirstName(String firstName) {
        log.info("Searching contacts by first name containing: {}", firstName);
        return contactRepository.searchByFirstName("%" + firstName + "%").stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ContactDTO> searchContactsByLastName(String lastName) {
        log.info("Searching contacts by last name containing: {}", lastName);
        return contactRepository.searchByLastName("%" + lastName + "%").stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ContactDTO> getContactsByPreferredName(String preferredName) {
        log.info("Searching contacts by preferred name containing: {}", preferredName);
        return contactRepository.searchByPreferredName("%" + preferredName + "%").stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
