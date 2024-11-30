package com.crm.contactmanagementservice.controller;

import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.service.ContactService;
import com.crm.contactmanagementservice.config.WebSocketHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * REST controller for managing contacts.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/contacts")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final ObjectMapper objectMapper;

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
     * Fetches all contacts from a contact list by its ID.
     * @param id The ID of the contact list.
     * @return A Set of ContactDTO representing all contacts in the specified contact list.
     */
    @GetMapping("/contact-list/{id}")
    public ResponseEntity<Set<ContactDTO>> getAllContactsByContactListID(@PathVariable UUID id) {
        return ResponseEntity.ok(contactService.getAllContactsByContactListID(id));
    }

    /**
     * Fetches all contacts for a given user ID.
     * @param userId The ID of the user whose contacts to fetch.
     * @return A ResponseEntity containing a set of ContactDTO representing all contacts for the specified user.
     */
    @GetMapping("user/{userId}")
    public ResponseEntity<Set<ContactDTO>> getAllContactsByUserId(@PathVariable UUID userId) {
        Set<ContactDTO> contacts = contactService.getAllContactsByContactListId(userId);
        return ResponseEntity.ok(contacts);
    }

    /**
     * Creates a new contact.
     * @param contactDTO The contact DTO to create.
     * @return The created contact DTO.
     */
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        ContactDTO createdContact = contactService.createContact(contactDTO);
        try {
            String message = objectMapper.writeValueAsString(createdContact);
            WebSocketHandler.broadcast(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    /**
     * Updates a contact.
     * @param id The ID of the contact to update.
     * @param contactDTO The contact DTO to update.
     * @return The updated contact DTO.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable UUID id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = contactService.updateContact(contactDTO, id);
        try {
            String message = objectMapper.writeValueAsString(updatedContact);
            WebSocketHandler.broadcast(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(updatedContact);
    }

    /**
     * Deletes a contact by its ID.
     * @param id The ID of the contact to delete.
     * @return HTTP status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactById(@PathVariable UUID id) {
        contactService.deleteContactById(id);
        try {
            Map<String, Object> deleteMessage = new HashMap<>();
            deleteMessage.put("id", id.toString());
            deleteMessage.put("deleted", true);
            String message = objectMapper.writeValueAsString(deleteMessage);
            WebSocketHandler.broadcast(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
