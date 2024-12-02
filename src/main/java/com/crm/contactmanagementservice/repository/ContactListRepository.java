package com.crm.contactmanagementservice.repository;

import com.crm.contactmanagementservice.entity.ContactListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Repository interface for ContactList.
 * This interface extends JpaRepository and provides methods to interact with the database.
 * It includes custom queries to find a contact list by its id and to find all contact lists by a user's id.
 */
@Repository
public interface ContactListRepository extends JpaRepository<ContactListEntity, UUID> {

    /**
     * Custom query to find a contact list by its id.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * @param id The id of the contact list to find.
     * @return An Optional that may contain the found ContactListEntity.
     */
    @Query(value = "SELECT * FROM public.contact_list WHERE id = :id", nativeQuery = true)
    Optional<ContactListEntity> findContactListEntityById(@Param("id") UUID id);

    /**
     * Custom query to find all contact lists by a user's id.
     * This query is executed natively, meaning it is written in SQL and not JPQL.
     * @param userId The id of the user whose contact lists to find.
     * @return A Set of ContactListEntity that belong to the user.
     */
    @Query(value = "SELECT * FROM public.contact_list WHERE user_id = :userId", nativeQuery = true)
    Set<ContactListEntity> findAllContactListsByUserId(@Param("userId") UUID userId);
}
