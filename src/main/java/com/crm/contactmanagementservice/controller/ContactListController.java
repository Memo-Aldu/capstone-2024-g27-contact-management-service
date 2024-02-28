package com.crm.contactmanagementservice.controller;

import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.service.ContactListService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contactlists")
@AllArgsConstructor
public class ContactListController {

    private final ContactListService contactListService;

    @GetMapping("/{id}")
    public ResponseEntity<ContactListDTO> getContactListById(@PathVariable UUID id) {
        return ResponseEntity.ok(contactListService.getContactListById(id));
    }

    @GetMapping
    public ResponseEntity<Set<ContactListDTO>> getAllContactLists() {
        return ResponseEntity.ok(contactListService.getAllContactLists());
    }

    @PostMapping
    public ResponseEntity<ContactListDTO> createContactList(@RequestBody ContactListDTO contactListDTO) {
        return new ResponseEntity<>(contactListService.createContactList(contactListDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactListDTO> updateContactList(@PathVariable UUID id, @RequestBody ContactListDTO contactListDTO) {
        return ResponseEntity.ok(contactListService.updateContactList(contactListDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactListById(@PathVariable UUID id) {
        contactListService.deleteContactListById(id);
        return ResponseEntity.noContent().build();
    }

}
