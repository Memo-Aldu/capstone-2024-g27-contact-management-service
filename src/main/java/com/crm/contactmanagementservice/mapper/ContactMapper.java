package com.crm.contactmanagementservice.mapper;

import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.entity.ContactEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Mapper interface for Contact.
 * This interface provides methods to convert between ContactEntity and ContactDTO.
 * It uses MapStruct for the mapping, which is a code generator that simplifies the implementation of mappings between Java bean types.
 */
@Component
@Mapper(componentModel = "spring")
public interface ContactMapper {

    /**
     * Converts a ContactEntity to a ContactDTO.
     * @param entity The ContactEntity to convert.
     * @return The converted ContactDTO.
     */
    ContactDTO toDTO(ContactEntity entity);

    /**
     * Converts a ContactDTO to a ContactEntity.
     * @param dto The ContactDTO to convert.
     * @return The converted ContactEntity.
     */
    ContactEntity toEntity(ContactDTO dto);

    /**
     * Converts a set of ContactEntity to a set of ContactDTO.
     * @param entities The set of ContactEntity to convert.
     * @return The converted set of ContactDTO.
     */
    Set<ContactDTO> toDTOs(Set<ContactEntity> entities);

    /**
     * Converts a set of ContactDTO to a set of ContactEntity.
     * @param dtos The set of ContactDTO to convert.
     * @return The converted set of ContactEntity.
     */
    Set<ContactEntity> toEntities(Set<ContactDTO> dtos);
}