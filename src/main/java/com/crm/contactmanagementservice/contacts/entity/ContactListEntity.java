package com.crm.contactmanagementservice.contacts.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "contact_list")
@Getter
@Setter
public class ContactListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "list_name")
    private String listName;

    @Column(name = "user_id")
    private UUID userId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contactList", cascade = CascadeType.ALL)
    private Set<ContactEntity> contacts = new HashSet<>();
}
