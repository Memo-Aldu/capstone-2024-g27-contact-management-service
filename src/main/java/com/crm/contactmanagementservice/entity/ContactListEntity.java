package com.crm.contactmanagementservice.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity class for ContactList.
 * This class represents a contact list in the database.
 * It includes fields for the contact list's details and relationships.
 */
@Entity
@Table(name = "contact_list")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactListEntity {

    /**
     * The unique identifier for the contact list.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    private UUID id;

    /**
     * The name of the contact list.
     */
    @Column(name = "list_name")
    private String listName;

    /**
     * The unique identifier for the user who owns the contact list.
     */
    @Column(name = "user_id")
    private UUID userId;

    /**
     * The contacts that belong to the contact list.
     * This is a one-to-many relationship.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contactList", cascade = CascadeType.DETACH)
    private Set<ContactEntity> contacts = new HashSet<>();
}