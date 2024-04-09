package com.crm.contactmanagementservice.mapper;

import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.entity.ContactListEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Mapper interface for ContactList.
 * This interface provides methods to convert between ContactListEntity and ContactListDTO.
 * It uses MapStruct for the mapping, which is a code generator that simplifies the implementation of mappings between Java bean types.
 */
@Component
@Mapper(componentModel = "spring")
public interface ContactListMapper {

    /**
     * Converts a ContactListEntity to a ContactListDTO.
     * @param entity The ContactListEntity to convert.
     * @return The converted ContactListDTO.
     */
    ContactListDTO toDTO(ContactListEntity entity);

    /**
     * Converts a ContactListDTO to a ContactListEntity.
     * @param dto The ContactListDTO to convert.
     * @return The converted ContactListEntity.
     */
    ContactListEntity toEntity(ContactListDTO dto);

    /**
     * Converts a set of ContactListEntity to a set of ContactListDTO.
     * @param entities The set of ContactListEntity to convert.
     * @return The converted set of ContactListDTO.
     */
    Set<ContactListDTO> toDTOs(Set<ContactListEntity> entities);

    /**
     * Converts a set of ContactListDTO to a set of ContactListEntity.
     * @param dtos The set of ContactListDTO to convert.
     * @return The converted set of ContactListEntity.
     */
    Set<ContactListEntity> toEntities(Set<ContactListDTO> dtos);
}