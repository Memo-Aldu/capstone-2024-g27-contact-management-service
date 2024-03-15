package com.crm.contactmanagementservice.repository;

import com.crm.contactmanagementservice.entity.ContactListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Repository
public interface ContactListRepository extends JpaRepository<ContactListEntity, UUID> {

    @Query(value = "SELECT * FROM contact_service_db.contact_list WHERE id = :id", nativeQuery = true)
    Optional<ContactListEntity> findContactListEntityById(@Param("id") UUID id);

    @Query(value = "SELECT * FROM contact_service_db.contact_list WHERE user_id = :userId", nativeQuery = true)
    Set<ContactListEntity> findAllContactListsByUserId(@Param("userId") UUID userId);
}
