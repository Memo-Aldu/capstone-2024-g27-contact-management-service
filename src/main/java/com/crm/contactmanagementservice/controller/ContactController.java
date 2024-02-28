package com.crm.contactmanagementservice.controller;

import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contacts")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable UUID id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }

    @GetMapping
    public ResponseEntity<Set<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        return new ResponseEntity<>(contactService.createContact(contactDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable UUID id, @RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok(contactService.updateContact(contactDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactById(@PathVariable UUID id) {
        contactService.deleteContactById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Set<ContactDTO>> searchContactsByName(@PathVariable String name) {
        return ResponseEntity.ok(contactService.searchContactsByName(name));
    }

}
