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

class ContactMapperTest {

    private ContactMapperImpl contactMapper;

    @BeforeEach
    void setUp() {
        contactMapper = new ContactMapperImpl();
    }

    @Test
    void testToDTOWithNonNullEntity() {
        ContactEntity entity = createTestEntity();
        ContactDTO dto = contactMapper.toDTO(entity);

        assertThat(dto).isNotNull();
        assertEntityAndDtoEquality(entity, dto);
    }

    @Test
    void testToEntityWithNonNullDTO() {
        ContactDTO dto = createTestDto();
        ContactEntity entity = contactMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertEntityAndDtoEquality(entity, dto);
    }

    @Test
    void testToDTOsWithNonNullEntities() {
        Set<ContactEntity> entities = new LinkedHashSet<>();
        entities.add(createTestEntity());
        Set<ContactDTO> dtos = contactMapper.toDTOs(entities);

        assertThat(dtos).isNotNull().hasSize(1);
        dtos.forEach(dto -> assertEntityAndDtoEquality(entities.iterator().next(), dto));
    }

    @Test
    void testToEntitiesWithNonNullDTOs() {
        Set<ContactDTO> dtos = new LinkedHashSet<>();
        dtos.add(createTestDto());
        Set<ContactEntity> entities = contactMapper.toEntities(dtos);

        assertThat(entities).isNotNull().hasSize(1);
        entities.forEach(entity -> assertEntityAndDtoEquality(entity, dtos.iterator().next()));
    }

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

    private ContactDTO createTestDto() {
        return new ContactDTO(UUID.randomUUID(), UUID.randomUUID(), "John", "Doe", "Johnny",
                "john.doe@example.com", "1234567890", "0987654321", UUID.randomUUID(), true);
    }

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


    @Test
    void givenNullEntity_whenToDTO_thenReturnNull() {
        assertNull(contactMapper.toDTO(null));
    }

    @Test
    void givenNullDTO_whenToEntity_thenReturnNull() {
        assertNull(contactMapper.toEntity(null));
    }

    @Test
    void givenEmptyEntitySet_whenToDTOs_thenReturnEmptySet() {
        assertTrue(contactMapper.toDTOs(Collections.emptySet()).isEmpty());
    }

    @Test
    void givenEmptyDTOSet_whenToEntities_thenReturnEmptySet() {
        assertTrue(contactMapper.toEntities(Collections.emptySet()).isEmpty());
    }
}
