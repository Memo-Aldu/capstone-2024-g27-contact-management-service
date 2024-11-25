package com.crm.contactmanagementservice.dto;

/**
 * Data Transfer Object for ContactList.
 * This record class encapsulates all the fields related to a ContactList.
 */
public record ContactListDTO(
        java.util.UUID id, // The unique identifier for the contact list
        String listName, // The name of the contact list
        java.util.UUID userId) // The unique identifier for the user who owns the contact list
{

}