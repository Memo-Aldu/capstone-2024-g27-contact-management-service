package com.crm.contactmanagementservice.controller;

import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.service.ContactListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

/**
 * REST controller for managing contact lists.
 * This controller provides endpoints for CRUD operations on contact lists.
 * It uses the ContactListService to perform these operations.
 */
@RestController
@RequestMapping("/api/v1/contactlists")
@AllArgsConstructor
public class ContactListController {

    private final ContactListService contactListService;

    /**
     * Fetches a contact list by its ID.
     * @param id The ID of the contact list.
     * @return The contact list DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactListDTO> getContactListById(@PathVariable UUID id) {
        return ResponseEntity.ok(contactListService.getContactListById(id));
    }

    /**
     * Fetches all contact lists.
     * @return A set of all contact list DTOs.
     */
    @GetMapping
    public ResponseEntity<Set<ContactListDTO>> getAllContactLists() {
        return ResponseEntity.ok(contactListService.getAllContactLists());
    }

    /**
     * Creates a new contact list.
     * @param contactListDTO The contact list DTO to create.
     * @return The created contact list DTO.
     */
    @PostMapping
    public ResponseEntity<ContactListDTO> createContactList(@RequestBody ContactListDTO contactListDTO) {
        return new ResponseEntity<>(contactListService.createContactList(contactListDTO), HttpStatus.CREATED);
    }

    /**
     * Updates a contact list.
     * @param id The ID of the contact list to update.
     * @param contactListDTO The contact list DTO to update.
     * @return The updated contact list DTO.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ContactListDTO> updateContactList(@PathVariable UUID id, @RequestBody ContactListDTO contactListDTO) {
        return ResponseEntity.ok(contactListService.updateContactList(contactListDTO, id));
    }

    /**
     * Deletes a contact list by its ID.
     * @param id The ID of the contact list to delete.
     * @return HTTP status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactListById(@PathVariable UUID id) {
        contactListService.deleteContactListById(id);
        return ResponseEntity.noContent().build();
    }

}