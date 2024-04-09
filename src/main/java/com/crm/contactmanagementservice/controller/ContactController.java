package com.crm.contactmanagementservice.controller;

import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

/**
 * REST controller for managing contacts.
 */
@RestController
@RequestMapping("/api/v1/contacts")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    /**
     * Fetches a contact by its ID.
     * @param id The ID of the contact.
     * @return The contact DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable UUID id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    /**
     * Fetches all contacts.
     * @return A set of all contact DTOs.
     */
    @GetMapping
    public ResponseEntity<Set<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    /**
     * Creates a new contact.
     * @param contactDTO The contact DTO to create.
     * @return The created contact DTO.
     */
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        return new ResponseEntity<>(contactService.createContact(contactDTO), HttpStatus.CREATED);
    }

    /**
     * Updates a contact.
     * @param id The ID of the contact to update.
     * @param contactDTO The contact DTO to update.
     * @return The updated contact DTO.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable UUID id, @RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok(contactService.updateContact(contactDTO, id));
    }

    /**
     * Deletes a contact by its ID.
     * @param id The ID of the contact to delete.
     * @return HTTP status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactById(@PathVariable UUID id) {
        contactService.deleteContactById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches contacts by name.
     * @param name The name to search for.
     * @return A set of contact DTOs that match the search.
     */
    @GetMapping("/search/{name}")
    public ResponseEntity<Set<ContactDTO>> searchContactsByName(@PathVariable String name) {
        return ResponseEntity.ok(contactService.searchContactsByName(name));
    }

}