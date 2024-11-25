package com.crm.contactmanagementservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/**
 * Entity class for Contact.
 * This class represents a contact in the database.
 * It includes fields for the contact's details and relationships.
 */
@Entity
@Table(name = "contact")
@Getter
@Setter
public class ContactEntity {

    /**
     * The unique identifier for the contact.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    private UUID id;

    /**
     * The first name of the contact.
     */
    @Column(name = "contact_first_name")
    private String firstName;

    /**
     * The last name of the contact.
     */
    @Column(name = "contact_last_name")
    private String lastName;

    /**
     * The preferred name of the contact.
     */
    @Column(name = "contact_preferred_name")
    private String preferredName;

    /**
     * The email address of the contact.
     */
    @Column(name = "contact_email", unique = true)
    private String email;

    /**
     * The phone number of the contact.
     */
    @Column(name = "contact_phone")
    private String phone;

    /**
     * The fax number of the contact.
     */
    @Column(name = "contact_fax")
    private String fax;

    /**
     * The unique identifier for the address of the contact.
     */
    @Column(name = "address_id")
    private UUID addressId;

    /**
     * Flag indicating whether the contact should not be contacted.
     */
    @Column(name = "do_not_contact")
    private boolean doNotContact;

    /**
     * The contact list that the contact belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_list_id", referencedColumnName = "id")
    private ContactListEntity contactList;
}