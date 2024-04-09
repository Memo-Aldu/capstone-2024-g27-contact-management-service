package com.crm.contactmanagementservice.mapper;

import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.entity.ContactEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the ContactMapper.
 * This class tests the ContactMapper methods by creating test ContactDTO and ContactEntity objects and comparing the results of the mapping.
 */
class ContactMapperTest {

    private ContactMapperImpl contactMapper;

    /**
     * Sets up the test environment before each test.
     * Initializes the ContactMapper.
     */
    @BeforeEach
    void setUp() {
        contactMapper = new ContactMapperImpl();
    }

    /**
     * Tests the toDTO method of the ContactMapper with a non-null ContactEntity.
     * The test passes if the returned ContactDTO is not null and its fields match the ContactEntity's fields.
     */
    @Test
    void testToDTOWithNonNullEntity() {
        ContactEntity entity = createTestEntity();
        ContactDTO dto = contactMapper.toDTO(entity);

        assertThat(dto).isNotNull();
        assertEntityAndDtoEquality(entity, dto);
    }

    /**
     * Tests the toEntity method of the ContactMapper with a non-null ContactDTO.
     * The test passes if the returned ContactEntity is not null and its fields match the ContactDTO's fields.
     */
    @Test
    void testToEntityWithNonNullDTO() {
        ContactDTO dto = createTestDto();
        ContactEntity entity = contactMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertEntityAndDtoEquality(entity, dto);
    }

    /**
     * Tests the toDTOs method of the ContactMapper with a non-null set of ContactEntities.
     * The test passes if the returned set of ContactDTOs is not null, has the same size as the input set, and each ContactDTO's fields match the corresponding ContactEntity's fields.
     */
    @Test
    void testToDTOsWithNonNullEntities() {
        Set<ContactEntity> entities = new LinkedHashSet<>();
        entities.add(createTestEntity());
        Set<ContactDTO> dtos = contactMapper.toDTOs(entities);

        assertThat(dtos).isNotNull().hasSize(1);
        dtos.forEach(dto -> assertEntityAndDtoEquality(entities.iterator().next(), dto));
    }

    /**
     * Tests the toEntities method of the ContactMapper with a non-null set of ContactDTOs.
     * The test passes if the returned set of ContactEntities is not null, has the same size as the input set, and each ContactEntity's fields match the corresponding ContactDTO's fields.
     */
    @Test
    void testToEntitiesWithNonNullDTOs() {
        Set<ContactDTO> dtos = new LinkedHashSet<>();
        dtos.add(createTestDto());
        Set<ContactEntity> entities = contactMapper.toEntities(dtos);

        assertThat(entities).isNotNull().hasSize(1);
        entities.forEach(entity -> assertEntityAndDtoEquality(entity, dtos.iterator().next()));
    }

    /**
     * Creates a test ContactEntity with random UUIDs and a fixed list name.
     * @return The created ContactEntity.
     */
    private ContactEntity createTestEntity() {
        ContactEntity entity = new ContactEntity();
        entity.setId(UUID.randomUUID());
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setPreferredName("Johnny");
        entity.setEmail("john.doe@example.com");
        entity.setPhone("1234567890");
        entity.setFax("0987654321");
        entity.setAddressId(UUID.randomUUID());
        entity.setDoNotContact(true);
        return entity;
    }

    /**
     * Creates a test ContactDTO with random UUIDs and a fixed list name.
     * @return The created ContactDTO.
     */
    private ContactDTO createTestDto() {
        return new ContactDTO(UUID.randomUUID(), UUID.randomUUID(), "John", "Doe", "Johnny",
                "john.doe@example.com", "1234567890", "0987654321", UUID.randomUUID(), true);
    }

    /**
     * Asserts that a ContactEntity and a ContactDTO are equal by comparing their fields.
     * @param entity The ContactEntity to compare.
     * @param dto The ContactDTO to compare.
     */
    private void assertEntityAndDtoEquality(ContactEntity entity, ContactDTO dto) {
        assertThat(entity.getId()).isEqualTo(dto.id());
        assertThat(entity.getFirstName()).isEqualTo(dto.firstName());
        assertThat(entity.getLastName()).isEqualTo(dto.lastName());
        assertThat(entity.getPreferredName()).isEqualTo(dto.preferredName());
        assertThat(entity.getEmail()).isEqualTo(dto.email());
        assertThat(entity.getPhone()).isEqualTo(dto.phone());
        assertThat(entity.getFax()).isEqualTo(dto.fax());
        assertThat(entity.getAddressId()).isEqualTo(dto.addressId());
        assertThat(entity.isDoNotContact()).isEqualTo(dto.doNotContact());
    }

    /**
     * Tests the toDTO method of the ContactMapper with a null ContactEntity.
     * The test passes if the returned ContactDTO is null.
     */
    @Test
    void givenNullEntity_whenToDTO_thenReturnNull() {
        assertNull(contactMapper.toDTO(null));
    }

    /**
     * Tests the toEntity method of the ContactMapper with a null ContactDTO.
     * The test passes if the returned ContactEntity is null.
     */
    @Test
    void givenNullDTO_whenToEntity_thenReturnNull() {
        assertNull(contactMapper.toEntity(null));
    }

    /**
     * Tests the toDTOs method of the ContactMapper with an empty set of ContactEntities.
     * The test passes if the returned set of ContactDTOs is empty.
     */
    @Test
    void givenEmptyEntitySet_whenToDTOs_thenReturnEmptySet() {
        assertTrue(contactMapper.toDTOs(Collections.emptySet()).isEmpty());
    }

    /**
     * Tests the toEntities method of the ContactMapper with an empty set of ContactDTOs.
     * The test passes if the returned set of ContactEntities is empty.
     */
    @Test
    void givenEmptyDTOSet_whenToEntities_thenReturnEmptySet() {
        assertTrue(contactMapper.toEntities(Collections.emptySet()).isEmpty());
    }
}