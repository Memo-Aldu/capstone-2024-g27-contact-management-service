package com.crm.contactmanagementservice.repository;

import com.crm.contactmanagementservice.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Repository interface for Contact.
 * This interface extends JpaRepository and provides methods to interact with the database.
 * It includes custom queries to find a contact by its id, email, phone, and name,
 * to find all contacts, to find all contacts by a contact list's id, and to delete a contact by its id.
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {

    /**
     * Custom query to find a contact by its id.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * @param id The id of the contact to find.
     * @return An Optional that may contain the found ContactEntity.
     */
    @Query(value = "SELECT * FROM public.contact c WHERE c.id = :id", nativeQuery = true)
    Optional<ContactEntity> findContactEntityById(@Param("id") UUID id);

    /**
     * Custom query to find all contacts.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * @return A Set of all ContactEntity.
     */
    @Query(value = "SELECT * FROM public.contact", nativeQuery = true)
    Set<ContactEntity> findAllContactEntities();

    /**
     * Custom query to find all contacts by a contact list's id.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * @param contactListId The id of the contact list whose contacts to find.
     * @return A Set of ContactEntity that belong to the contact list.
     */
    @Query(value = "SELECT * FROM public.contact WHERE contact_list_id = :contactListId", nativeQuery = true)
    Set<ContactEntity> findAllContactsByContactListId(@Param("contactListId") UUID contactListId);

    /**
     * Custom query to find all contacts for a given user ID.
     * This query fetches all contact lists for the user and then lists all contacts from those contact lists.
     * @param userId The ID of the user whose contacts to find.
     * @return A Set of ContactEntity that belong to the user's contact lists.
     */
    @Query(value = "SELECT c.* FROM public.contact c " +
            "JOIN public.contact_list cl ON c.contact_list_id = cl.id " +
            "WHERE cl.user_id = :userId", nativeQuery = true)
    Set<ContactEntity> findAllContactsByUserId(@Param("userId") UUID userId);

    /**
     * Custom query to find a contact by its email.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * @param email The email of the contact to find.
     * @return An Optional that may contain the found ContactEntity.
     */
    @Query(value = "SELECT * FROM public.contact c WHERE c.contact_email = :email", nativeQuery = true)
    Optional<ContactEntity> findContactEntityByEmail(@Param("email") String email);

    /**
     * Custom query to find a contact by its phone.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * @param phone The phone of the contact to find.
     * @return An Optional that may contain the found ContactEntity.
     */
    @Query(value = "SELECT * FROM public.contact c WHERE c.contact_phone = :phone", nativeQuery = true)
    Optional<ContactEntity> findContactEntityByPhone(@Param("phone") String phone);

    /**
     * Custom query to search for contacts by name.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * It searches for contacts whose first name, last name, or preferred name matches the provided name.
     * @param name The name to search for.
     * @return A Set of ContactEntity that match the search criteria.
     */
    @Query(value = "SELECT * FROM public.contact c WHERE LOWER(c.contact_first_Name) LIKE LOWER(:name) OR LOWER(c.contact_last_name) LIKE LOWER(:name) OR LOWER(c.contact_preferred_name) LIKE LOWER(:name)", nativeQuery = true)
    Set<ContactEntity> searchByName(@Param("name") String name);

    /**
     * Custom query to delete a contact by its id.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * It is a modifying query, meaning it changes the database, and is therefore annotated with @Transactional.
     * @param id The id of the contact to delete.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM public.contact c WHERE c.id = :id", nativeQuery = true)
    void deleteContactEntityById(@Param("id") UUID id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE public.contact c SET c.contact_list_id = :contactListId WHERE c.id = :contactId", nativeQuery = true)
    void setContactListId(UUID contactListId, UUID contactId);
}