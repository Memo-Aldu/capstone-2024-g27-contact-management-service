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

class ContactListMapperTest {

    private ContactListMapperImpl contactListMapper;

    @BeforeEach
    void setUp() {
        contactListMapper = new ContactListMapperImpl();
    }

    @Test
    void testToDTOWithNonNullEntity() {
        ContactListEntity entity = createTestEntity();
        ContactListDTO dto = contactListMapper.toDTO(entity);

        assertThat(dto).isNotNull();
        assertEntityAndDtoEquality(entity, dto);
    }

    @Test
    void testToEntityWithNonNullDTO() {
        ContactListDTO dto = createTestDto();
        ContactListEntity entity = contactListMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertEntityAndDtoEquality(entity, dto);
    }

    @Test
    void testToDTOsWithNonNullEntities() {
        Set<ContactListEntity> entities = new LinkedHashSet<>();
        entities.add(createTestEntity());
        Set<ContactListDTO> dtos = contactListMapper.toDTOs(entities);

        assertThat(dtos).isNotNull().hasSize(1);
        dtos.forEach(dto -> assertEntityAndDtoEquality(entities.iterator().next(), dto));
    }

    @Test
    void testToEntitiesWithNonNullDTOs() {
        Set<ContactListDTO> dtos = new LinkedHashSet<>();
        dtos.add(createTestDto());
        Set<ContactListEntity> entities = contactListMapper.toEntities(dtos);

        assertThat(entities).isNotNull().hasSize(1);
        entities.forEach(entity -> assertEntityAndDtoEquality(entity, dtos.iterator().next()));
    }

    private ContactListEntity createTestEntity() {
        ContactListEntity entity = new ContactListEntity();
        entity.setId(UUID.randomUUID());
        entity.setListName("Friends");
        entity.setUserId(UUID.randomUUID());
        return entity;
    }

    private ContactListDTO createTestDto() {
        return new ContactListDTO(UUID.randomUUID(), "Family", UUID.randomUUID());
    }

    private void assertEntityAndDtoEquality(ContactListEntity entity, ContactListDTO dto) {
        assertThat(entity.getId()).isEqualTo(dto.id());
        assertThat(entity.getListName()).isEqualTo(dto.listName());
        assertThat(entity.getUserId()).isEqualTo(dto.userId());
    }

    @Test
    void givenNullEntity_whenToDTO_thenReturnNull() {
        assertNull(contactListMapper.toDTO(null));
    }

    @Test
    void givenNullDTO_whenToEntity_thenReturnNull() {
        assertNull(contactListMapper.toEntity(null));
    }

    @Test
    void givenEmptyEntitySet_whenToDTOs_thenReturnEmptySet() {
        assertTrue(contactListMapper.toDTOs(Collections.emptySet()).isEmpty());
    }

    @Test
    void givenEmptyDTOSet_whenToEntities_thenReturnEmptySet() {
        assertTrue(contactListMapper.toEntities(Collections.emptySet()).isEmpty());
    }
}
