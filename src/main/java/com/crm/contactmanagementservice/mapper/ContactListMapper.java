package com.crm.contactmanagementservice.mapper;

import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.entity.ContactListEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Mapper(componentModel = "spring")
public interface ContactListMapper {
    ContactListDTO toDTO(ContactListEntity entity);
    ContactListEntity toEntity(ContactListDTO dto);
    Set<ContactListDTO> toDTOs(Set<ContactListEntity> entities);
    Set<ContactListEntity> toEntities(Set<ContactListDTO> dtos);
}
