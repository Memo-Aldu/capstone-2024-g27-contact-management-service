package com.crm.contactmanagementservice.contacts.mapper;

import com.crm.contactmanagementservice.contacts.dto.ContactDTO;
import com.crm.contactmanagementservice.contacts.entity.ContactEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO toDTO(ContactEntity entity);
    ContactEntity toEntity(ContactDTO dto);
    Set<ContactDTO> toDTOs(Set<ContactEntity> entities);
    Set<ContactEntity> toEntities(Set<ContactDTO> dtos);
}
