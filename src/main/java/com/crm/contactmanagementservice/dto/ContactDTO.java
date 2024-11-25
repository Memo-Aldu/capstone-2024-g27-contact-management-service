package com.crm.contactmanagementservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data Transfer Object for Contact.
 * This record class encapsulates all the fields related to a Contact.
 * It also includes a method to check if the DTO is valid.
 */
public record ContactDTO(
        java.util.UUID id, // The unique identifier for the contact
        java.util.UUID contactListId, // The unique identifier for the contact list the contact belongs to
        String firstName, // The first name of the contact
        String lastName, // The last name of the contact
        String preferredName, // The preferred name of the contact
        String email, // The email address of the contact
        String phone, // The phone number of the contact
        String fax, // The fax number of the contact
        java.util.UUID addressId, // The unique identifier for the address of the contact
        boolean doNotContact) { // Flag indicating whether the contact should not be contacted

    /**
     * Checks if the DTO is valid.
     * A DTO is considered valid if any of its fields are not null.
     * @return true if the DTO is valid, false otherwise.
     */
    @JsonIgnore
    public boolean isValidDto() {
        return contactListId != null || email != null || phone != null || firstName != null || lastName != null || fax != null || addressId != null || id != null;
    }
}