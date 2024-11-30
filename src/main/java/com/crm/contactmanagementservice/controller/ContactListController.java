package com.crm.contactmanagementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.crm.contactmanagementservice.config.WebSocketHandler;
import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.service.ContactListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * REST controller for managing contact lists.
 * This controller provides endpoints for CRUD operations on contact lists.
 * It uses the ContactListService to perform these operations.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/contact_lists")
@AllArgsConstructor
public class ContactListController {

    private final ContactListService contactListService;
    private final ObjectMapper objectMapper;

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
     * Fetches all contact lists by the user id.
     * @return A set of all contact list DTOs.
     */
    @GetMapping("user/{id}")
    public ResponseEntity<Set<ContactListDTO>> getAllContactListsByUserID(@PathVariable UUID id) {
        Set<ContactListDTO> contactLists = contactListService.getAllContactListsByUserId(id);
        return ResponseEntity.ok(contactLists);
    }
    /**
     * Creates a new contact list.
     * @param contactListDTO The contact list DTO to create.
     * @return The created contact list DTO.
     */
    @PostMapping
    public ResponseEntity<ContactListDTO> createContactList(@RequestBody ContactListDTO contactListDTO) {
        ContactListDTO createdContactList = contactListService.createContactList(contactListDTO);
        try {
            String message = objectMapper.writeValueAsString(createdContactList);
            WebSocketHandler.broadcast(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(createdContactList, HttpStatus.CREATED);
    }

    /**
     * Updates a contact list.
     * @param id The ID of the contact list to update.
     * @param contactListDTO The contact list DTO to update.
     * @return The updated contact list DTO.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ContactListDTO> updateContactList(@PathVariable UUID id, @RequestBody ContactListDTO contactListDTO) {
        ContactListDTO updatedContactList = contactListService.updateContactList(contactListDTO, id);
        try {
            String message = objectMapper.writeValueAsString(updatedContactList);
            WebSocketHandler.broadcast(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(updatedContactList, HttpStatus.OK);
    }

    /**
     * Deletes a contact list by its ID.
     * @param id The ID of the contact list to delete.
     * @return HTTP status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactListById(@PathVariable UUID id) {
        contactListService.deleteContactListById(id);
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
}
