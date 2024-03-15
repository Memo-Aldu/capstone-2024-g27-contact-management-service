package com.crm.contactmanagementservice.service;
import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.entity.ContactEntity;
import com.crm.contactmanagementservice.entity.ContactListEntity;
import com.crm.contactmanagementservice.mapper.ContactMapper;
import com.crm.contactmanagementservice.repository.ContactRepository;
import com.crm.contactmanagementservice.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactUnitTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactServiceImpl contactService;

    private ContactEntity contactEntity;


    private ContactDTO contactDTO;

    @BeforeEach
    public void setup(){
        UUID id = UUID.randomUUID();
        contactEntity = new ContactEntity();
        contactEntity.setId(id);
        contactEntity.setFirstName("June");
        contactEntity.setLastName("Thomas");
        contactEntity.setEmail("junethomas@test.com");
        contactEntity.setPhone("1234567890");

        contactDTO = new ContactDTO(id, null, "June", "Thomas", null, "junethomas@test.com", "1234567890", null, null, false);
    }

    @DisplayName("JUnit test for createContact method")
    @Test
    public void givenContactObject_whenCreateContact_thenReturnContactObject() {
            given(contactMapper.toEntity(any(ContactDTO.class))).willReturn(contactEntity);
            given(contactRepository.save(any(ContactEntity.class))).willReturn(contactEntity);
            given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);
            ContactDTO savedContact = contactService.createContact(contactDTO);
            assertThat(savedContact).isNotNull();
            assertThat(savedContact.email()).isEqualTo(contactEntity.getEmail());
    }

    @DisplayName("JUnit test for getContactById method")
    @Test
    public void givenContactId_whenGetContactById_thenReturnContactObject() {

        given(contactRepository.findContactEntityById(contactEntity.getId())).willReturn(Optional.of(contactEntity));
        given(contactMapper.toDTO(contactEntity)).willReturn(contactDTO);
        ContactDTO foundContact = contactService.getContactById(contactEntity.getId());
        assertThat(foundContact).isNotNull();
        assertThat(foundContact.id()).isEqualTo(contactEntity.getId());
    }

    @DisplayName("JUnit test for getContactByEmail method")
    @Test
    public void givenContactEmail_whenGetContactByEmail_thenReturnContactObject() {
        String email = contactEntity.getEmail();
        given(contactRepository.findContactEntityByEmail(email)).willReturn(Optional.of(contactEntity));
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);
        ContactDTO resultContact = contactService.getContactByEmail(email);
        assertThat(resultContact).isNotNull();
        assertThat(resultContact.email()).isEqualTo(email);
    }

    @DisplayName("JUnit test for getContactByPhone method")
    @Test
    public void givenContactPhone_whenGetContactByPhone_thenReturnContactObject() {
        String phone = contactEntity.getPhone();

        given(contactRepository.findContactEntityByPhone(phone)).willReturn(Optional.of(contactEntity));
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);
        ContactDTO resultContact = contactService.getContactByPhone(phone);
        assertThat(resultContact).isNotNull();
        assertThat(resultContact.phone()).isEqualTo(phone);
    }



    @DisplayName("JUnit test for getContactById method with non-existent contact")
    @Test
    public void givenNonExistentContactId_whenGetContactById_thenThrowException() {
        UUID nonExistentId = UUID.randomUUID();
        given(contactRepository.findContactEntityById(nonExistentId)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> contactService.getContactById(nonExistentId));
    }

    @DisplayName("JUnit test for updateContact method")
    @Test
    public void givenUpdatedContactObject_whenUpdateContact_thenReturnUpdatedContactObject() {
        UUID contactId = contactEntity.getId();
        ContactDTO updatedContactDTO = new ContactDTO(contactId, null, "June", "Thomas", null, "junethomas@test.com", "0987654321", null, null, false);

        given(contactRepository.findContactEntityById(contactId)).willReturn(Optional.of(contactEntity));
        given(contactRepository.save(any(ContactEntity.class))).willReturn(contactEntity);
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(updatedContactDTO);

        ContactDTO updatedContact = contactService.updateContact(updatedContactDTO, contactId);

        assertThat(updatedContact).isNotNull();
        assertThat(updatedContact.email()).isEqualTo("junethomas@test.com");
    }

    @DisplayName("JUnit test for deleteContactById method")
    @Test
    public void givenContactId_whenDeleteContactById_thenNothing() {
        UUID contactId = UUID.randomUUID();

        doNothing().when(contactRepository).deleteContactEntityById(contactId);

        contactService.deleteContactById(contactId);

        verify(contactRepository, times(1)).deleteContactEntityById(contactId);
    }


    @DisplayName("JUnit test for getAllContacts method")
    @Test
    public void whenGetAllContacts_thenReturnContactsList() {
        given(contactRepository.findAllContactEntities()).willReturn(Set.of(contactEntity));
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);

        Set<ContactDTO> contacts = contactService.getAllContacts();

        assertThat(contacts).isNotNull().hasSize(1);
    }

    @DisplayName("JUnit test for searchContactsByName method")
    @Test
    public void givenName_whenSearchContactsByName_thenReturnContactsList() {
        Set<ContactEntity> entities = Set.of(contactEntity);
        String name = "June";

        given(contactRepository.searchByName("%" + name + "%")).willReturn(entities);
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);

        Set<ContactDTO> result = contactService.searchContactsByName(name);

        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.iterator().next().firstName()).isEqualTo(contactDTO.firstName());
    }

}
