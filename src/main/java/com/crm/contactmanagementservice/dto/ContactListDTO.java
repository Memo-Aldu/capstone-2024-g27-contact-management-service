package com.crm.contactmanagementservice.dto;

/**
 * DTO for ContactLists information.
 */
public record ContactListDTO(
        java.util.UUID id,
        String listName,
        java.util.UUID userId) {
}
