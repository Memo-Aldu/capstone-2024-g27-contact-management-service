package com.crm.contactmanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ContactDTO(
        java.util.UUID id,
        java.util.UUID contactListId,
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
        return contactListId != null || email != null || phone != null || firstName != null || lastName != null || fax != null || addressId != null || id != null;
    }
}