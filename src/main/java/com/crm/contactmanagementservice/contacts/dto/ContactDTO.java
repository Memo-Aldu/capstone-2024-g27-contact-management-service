package com.crm.contactmanagementservice.contacts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DTO for Contact information.
 */
public record ContactDTO(
        java.util.UUID id,
        String firstName,
        String lastName,
        String preferredName,
        String email,
        String phone,
        String fax,
        java.util.UUID addressId,
        boolean doNotContact) {
    @JsonIgnore
    public boolean isValidDto() {
        return email != null || phone != null || firstName != null || lastName != null || fax != null || addressId != null || id != null;
    }
}