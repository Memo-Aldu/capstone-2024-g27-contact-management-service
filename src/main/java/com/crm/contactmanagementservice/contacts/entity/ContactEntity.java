package com.crm.contactmanagementservice.contacts.entity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "contact")
@Getter
@Setter
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "contact_first_name")
    private String firstName;

    @Column(name = "contact_last_name")
    private String lastName;

    @Column(name = "contact_preferred_name")
    private String preferredName;

    @Column(name = "contact_email")
    private String email;

    @Column(name = "contact_phone")
    private String phone;

    @Column(name = "contact_fax")
    private String fax;

    @Column(name = "address_id")
    private UUID addressId;

    @Column(name = "do_not_contact", nullable = false)
    private boolean doNotContact;
}
