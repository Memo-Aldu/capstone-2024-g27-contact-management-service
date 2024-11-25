package com.crm.contactmanagementservice.mapper;

import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.entity.ContactListEntity;
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
 * Unit tests for the ContactListMapper.
 * This class tests the ContactListMapper methods by creating test ContactListDTO and ContactListEntity objects and comparing the results of the mapping.
 */
class ContactListMapperTest {

    private ContactListMapperImpl contactListMapper;

    /**
     * Sets up the test environment before each test.
     * Initializes the ContactListMapper.
     */
    @BeforeEach
    void setUp() {
        contactListMapper = new ContactListMapperImpl();
    }

    /**
     * Tests the toDTO method of the ContactListMapper with a non-null ContactListEntity.
     * The test passes if the returned ContactListDTO is not null and its fields match the ContactListEntity's fields.
     */
    @Test
    void testToDTOWithNonNullEntity() {
        ContactListEntity entity = createTestEntity();
        ContactListDTO dto = contactListMapper.toDTO(entity);

        assertThat(dto).isNotNull();
        assertEntityAndDtoEquality(entity, dto);
    }

    /**
     * Tests the toEntity method of the ContactListMapper with a non-null ContactListDTO.
     * The test passes if the returned ContactListEntity is not null and its fields match the ContactListDTO's fields.
     */
    @Test
    void testToEntityWithNonNullDTO() {
        ContactListDTO dto = createTestDto();
        ContactListEntity entity = contactListMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertEntityAndDtoEquality(entity, dto);
    }

    /**
     * Tests the toDTOs method of the ContactListMapper with a non-null set of ContactListEntities.
     * The test passes if the returned set of ContactListDTOs is not null, has the same size as the input set, and each ContactListDTO's fields match the corresponding ContactListEntity's fields.
     */
    @Test
    void testToDTOsWithNonNullEntities() {
        Set<ContactListEntity> entities = new LinkedHashSet<>();
        entities.add(createTestEntity());
        Set<ContactListDTO> dtos = contactListMapper.toDTOs(entities);

        assertThat(dtos).isNotNull().hasSize(1);
        dtos.forEach(dto -> assertEntityAndDtoEquality(entities.iterator().next(), dto));
    }

    /**
     * Tests the toEntities method of the ContactListMapper with a non-null set of ContactListDTOs.
     * The test passes if the returned set of ContactListEntities is not null, has the same size as the input set, and each ContactListEntity's fields match the corresponding ContactListDTO's fields.
     */
    @Test
    void testToEntitiesWithNonNullDTOs() {
        Set<ContactListDTO> dtos = new LinkedHashSet<>();
        dtos.add(createTestDto());
        Set<ContactListEntity> entities = contactListMapper.toEntities(dtos);

        assertThat(entities).isNotNull().hasSize(1);
        entities.forEach(entity -> assertEntityAndDtoEquality(entity, dtos.iterator().next()));
    }

    /**
     * Creates a test ContactListEntity with random UUIDs and a fixed list name.
     * @return The created ContactListEntity.
     */
    private ContactListEntity createTestEntity() {
        ContactListEntity entity = new ContactListEntity();
        entity.setId(UUID.randomUUID());
        entity.setListName("Friends");
        entity.setUserId(UUID.randomUUID());
        return entity;
    }

    /**
     * Creates a test ContactListDTO with random UUIDs and a fixed list name.
     * @return The created ContactListDTO.
     */
    private ContactListDTO createTestDto() {
        return new ContactListDTO(UUID.randomUUID(), "Family", UUID.randomUUID());
    }

    /**
     * Asserts that a ContactListEntity and a ContactListDTO are equal by comparing their fields.
     * @param entity The ContactListEntity to compare.
     * @param dto The ContactListDTO to compare.
     */
    private void assertEntityAndDtoEquality(ContactListEntity entity, ContactListDTO dto) {
        assertThat(entity.getId()).isEqualTo(dto.id());
        assertThat(entity.getListName()).isEqualTo(dto.listName());
        assertThat(entity.getUserId()).isEqualTo(dto.userId());
    }

    /**
     * Tests the toDTO method of the ContactListMapper with a null ContactListEntity.
     * The test passes if the returned ContactListDTO is null.
     */
    @Test
    void givenNullEntity_whenToDTO_thenReturnNull() {
        assertNull(contactListMapper.toDTO(null));
    }

    /**
     * Tests the toEntity method of the ContactListMapper with a null ContactListDTO.
     * The test passes if the returned ContactListEntity is null.
     */
    @Test
    void givenNullDTO_whenToEntity_thenReturnNull() {
        assertNull(contactListMapper.toEntity(null));
    }

    /**
     * Tests the toDTOs method of the ContactListMapper with an empty set of ContactListEntities.
     * The test passes if the returned set of ContactListDTOs is empty.
     */
    @Test
    void givenEmptyEntitySet_whenToDTOs_thenReturnEmptySet() {
        assertTrue(contactListMapper.toDTOs(Collections.emptySet()).isEmpty());
    }

    /**
     * Tests the toEntities method of the ContactListMapper with an empty set of ContactListDTOs.
     * The test passes if the returned set of ContactListEntities is empty.
     */
    @Test
    void givenEmptyDTOSet_whenToEntities_thenReturnEmptySet() {
        assertTrue(contactListMapper.toEntities(Collections.emptySet()).isEmpty());
    }
}