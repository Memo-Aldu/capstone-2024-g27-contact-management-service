package com.crm.contactmanagementservice.contacts.repository;

import com.crm.contactmanagementservice.contacts.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {

    @Query(value = "SELECT * FROM contact_service_db.contact c WHERE c.id = :id", nativeQuery = true)
    Optional<ContactEntity> findContactEntityById(@Param("id") UUID id);

    @Query(value = "SELECT * FROM contact_service_db.contact", nativeQuery = true)
    Set<ContactEntity> findAllContactEntities();

    @Query(value = "SELECT * FROM contact_service_db.contact c WHERE c.email = :email", nativeQuery = true)
    Optional<ContactEntity> findContactEntityByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM contact_service_db.contact c WHERE c.phone = :phone", nativeQuery = true)
    Optional<ContactEntity> findContactEntityByPhone(@Param("phone") String phone);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM contact_service_db.contact c WHERE c.id = :id", nativeQuery = true)
    void deleteContactEntityById(@Param("id") UUID id);

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM contact_service_db.contact c WHERE c.email = :email", nativeQuery = true)
    boolean existsContactEntityByEmail(@Param("email") String email);

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM contact_service_db.contact c WHERE c.phone = :phone", nativeQuery = true)
    boolean existsContactEntityByPhone(@Param("phone") String phone);
}
