package com.crm.contactmanagementservice.service;

import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.entity.ContactEntity;
import com.crm.contactmanagementservice.mapper.ContactMapper;
import com.crm.contactmanagementservice.repository.ContactRepository;
import com.crm.contactmanagementservice.service.impl.ContactServiceImpl;
import com.crm.contactmanagementservice.exceptions.AppEntityNotFoundException;

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

/**
 * Unit tests for the ContactService.
 * This class tests the ContactService methods by mocking the ContactRepository and ContactMapper and simulating the service's behavior.
 */
@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactServiceImpl contactService;

    private ContactEntity contactEntity;
    private ContactDTO contactDTO;

    /**
     * Sets up the test environment before each test.
     * Initializes the ContactEntity and ContactDTO objects to be used in the tests.
     */
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

    /**
     * Tests the createContact method of the ContactService.
     * The test passes if the returned ContactDTO is not null and its email matches the expected email.
     */
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

    /**
     * Tests the getContactById method of the ContactService.
     * The test passes if the returned ContactDTO is not null and its id matches the expected id.
     */
    @DisplayName("JUnit test for getContactById method")
    @Test
    public void givenContactId_whenGetContactById_thenReturnContactObject() {
        given(contactRepository.findContactEntityById(contactEntity.getId())).willReturn(Optional.of(contactEntity));
        given(contactMapper.toDTO(contactEntity)).willReturn(contactDTO);
        ContactDTO foundContact = contactService.getContactById(contactEntity.getId());
        assertThat(foundContact).isNotNull();
        assertThat(foundContact.id()).isEqualTo(contactEntity.getId());
    }

    /**
     * Tests the getContactByEmail method of the ContactService.
     * The test passes if the returned ContactDTO is not null and its email matches the expected email.
     */
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

    /**
     * Tests the getContactByPhone method of the ContactService.
     * The test passes if the returned ContactDTO is not null and its phone matches the expected phone.
     */
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

    /**
     * Tests the updateContact method of the ContactService.
     * The test passes if the returned ContactDTO is not null and its email matches the expected email.
     */
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

    /**
     * Tests the deleteContactById method of the ContactService.
     * The test passes if the deletion is verified.
     */
    @DisplayName("JUnit test for deleteContactById method")
    @Test
    public void givenContactId_whenDeleteContactById_thenNothing() {
        UUID contactId = UUID.randomUUID();

        doNothing().when(contactRepository).deleteContactEntityById(contactId);

        contactService.deleteContactById(contactId);

        verify(contactRepository, times(1)).deleteContactEntityById(contactId);
    }

    /**
     * Tests the getAllContacts method of the ContactService.
     * The test passes if the returned set of ContactDTOs is not null and has a size of 1.
     */
    @DisplayName("JUnit test for getAllContacts method")
    @Test
    public void whenGetAllContacts_thenReturnContactsList() {
        given(contactRepository.findAllContactEntities()).willReturn(Set.of(contactEntity));
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);

        Set<ContactDTO> contacts = contactService.getAllContacts();

        assertThat(contacts).isNotNull().hasSize(1);
    }

    /**
     * Tests the searchContactsByName method of the ContactService.
     * The test passes if the returned set of ContactDTOs is not null, has a size of 1, and the first name of the first ContactDTO matches the expected first name.
     */
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

    /**
     * Tests the getAllContactsByContactListId method of the ContactService.
     * The test passes if the returned set of ContactDTOs is not null, has a size of 1, and the first ContactDTO matches the expected ContactDTO.
     */
    @DisplayName("JUnit test for getAllContactsByContactListId method")
    @Test
    public void givenContactListId_whenGetAllContactsByContactListId_thenReturnContactsSet() {
        UUID contactListId = UUID.randomUUID();
        Set<ContactEntity> contactEntitySet = new LinkedHashSet<>();
        contactEntitySet.add(contactEntity);

        given(contactRepository.findAllContactsByContactListId(contactListId)).willReturn(contactEntitySet);
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);

        Set<ContactDTO> contacts = contactService.getAllContactsByContactListId(contactListId);

        assertThat(contacts).isNotNull().hasSize(1);
        assertThat(contacts.iterator().next()).isEqualTo(contactDTO);
    }

    /**
     * Tests the getContactById method of the ContactService with a non-existent contact.
     * The test passes if an AppEntityNotFoundException is thrown.
     */
    @DisplayName("JUnit test for getContactById method with non-existent contact")
    @Test
    public void givenNonExistentContactId_whenGetContactById_thenThrowException() {
        given(contactRepository.findContactEntityById(contactEntity.getId())).willReturn(Optional.empty());

        assertThrows(AppEntityNotFoundException.class, () -> contactService.getContactById(contactEntity.getId()));
    }

    /**
     * Tests the getContactByEmail method of the ContactService with a non-existent email.
     * The test passes if an AppEntityNotFoundException is thrown.
     */
    @DisplayName("JUnit test for getContactByEmail method with non-existent email")
    @Test
    public void givenNonExistentEmail_whenGetContactByEmail_thenThrowException() {
        given(contactRepository.findContactEntityByEmail("nonexistentemail@test.com")).willReturn(Optional.empty());

        assertThrows(AppEntityNotFoundException.class, () -> contactService.getContactByEmail("nonexistentemail@test.com"));
    }

    /**
     * Tests the getContactByPhone method of the ContactService with a non-existent phone.
     * The test passes if an AppEntityNotFoundException is thrown.
     */
    @DisplayName("JUnit test for getContactByPhone method with non-existent phone")
    @Test
    public void givenNonExistentPhone_whenGetContactByPhone_thenThrowException() {
        given(contactRepository.findContactEntityByPhone("0000000000")).willReturn(Optional.empty());

        assertThrows(AppEntityNotFoundException.class, () -> contactService.getContactByPhone("0000000000"));
    }

    /**
     * Tests the updateContact method of the ContactService with a non-existent contact.
     * The test passes if an AppEntityNotFoundException is thrown.
     */
    @DisplayName("JUnit test for updateContact method with non-existent contact")
    @Test
    public void givenNonExistentContactId_whenUpdateContact_thenThrowException() {
        given(contactRepository.findContactEntityById(contactEntity.getId())).willReturn(Optional.empty());

        assertThrows(AppEntityNotFoundException.class, () -> contactService.updateContact(contactDTO, contactEntity.getId()));
    }
    /**
     * Tests the deleteContactById method of the ContactService with a non-existent contact.
     * The test passes if an AppEntityNotFoundException is thrown.
     */
    @DisplayName("JUnit test for deleteContactById method with non-existent contact")
    @Test
    public void givenNonExistentContactId_whenDeleteContactById_thenExpectDeletion() {
        doThrow(new AppEntityNotFoundException("Contact not found")).when(contactRepository).deleteContactEntityById(contactEntity.getId());

        assertThrows(AppEntityNotFoundException.class, () -> contactService.deleteContactById(contactEntity.getId()));
        verify(contactRepository, times(1)).deleteContactEntityById(contactEntity.getId());
    }

    /**
     * Tests the getAllContacts method of the ContactService when no contacts exist.
     * The test passes if the returned set of ContactDTOs is empty.
     */
    @DisplayName("JUnit test for getAllContacts method when no contacts exist")
    @Test
    public void whenGetAllContacts_andNoContactsExist_thenReturnEmptySet() {
        given(contactRepository.findAllContactEntities()).willReturn(Collections.emptySet());

        Set<ContactDTO> result = contactService.getAllContacts();

        assertThat(result).isEmpty();
    }

    /**
     * Tests the getAllContactsByContactListId method of the ContactService when no contacts are linked to the contact list.
     * The test passes if the returned set of ContactDTOs is empty.
     */
    @DisplayName("JUnit test for getAllContactsByContactListId method when no contacts linked")
    @Test
    public void givenContactListIdWithNoLinkedContacts_whenGetAllContactsByContactListId_thenReturnEmptySet() {
        UUID contactListId = UUID.randomUUID();
        given(contactRepository.findAllContactsByContactListId(contactListId)).willReturn(Collections.emptySet());

        Set<ContactDTO> result = contactService.getAllContactsByContactListId(contactListId);

        assertThat(result).isEmpty();
    }

    /**
     * Tests the searchContactsByName method of the ContactService when no contacts match the provided name.
     * The test passes if the returned set of ContactDTOs is empty.
     */
    @DisplayName("JUnit test for searchContactsByName method when no matching contacts found")
    @Test
    public void givenNameWithNoMatchingContacts_whenSearchContactsByName_thenReturnEmptySet() {
        String name = "NonExistentName";
        given(contactRepository.searchByName("%" + name + "%")).willReturn(Collections.emptySet());

        Set<ContactDTO> result = contactService.searchContactsByName(name);

        assertThat(result).isEmpty();
    }

    /**
     * Tests the getAllContactsByContactListID method of the ContactService.
     * The test passes if the returned set of ContactDTOs is not null and has a size of 1.
     */
    @DisplayName("JUnit test for getAllContactsByContactListID method")
    @Test
    public void givenContactListId_whenGetAllContactsByContactListID_thenReturnContactsSet() {
        UUID contactListId = UUID.randomUUID();
        Set<ContactEntity> contactEntitySet = new LinkedHashSet<>();
        contactEntitySet.add(contactEntity);

        given(contactRepository.findAllContactsByContactListId(contactListId)).willReturn(contactEntitySet);
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);

        Set<ContactDTO> contacts = contactService.getAllContactsByContactListID(contactListId);

        assertThat(contacts).isNotNull().hasSize(1);
        assertThat(contacts.iterator().next()).isEqualTo(contactDTO);
    }

    /**
     * Tests the getAllContactsByContactListID method of the ContactService when no contacts are linked to the contact list.
     * The test passes if the returned set of ContactDTOs is empty.
     */
    @DisplayName("JUnit test for getAllContactsByContactListID method when no contacts linked")
    @Test
    public void givenContactListIdWithNoLinkedContacts_whenGetAllContactsByContactListID_thenReturnEmptySet() {
        UUID contactListId = UUID.randomUUID();
        given(contactRepository.findAllContactsByContactListId(contactListId)).willReturn(Collections.emptySet());

        Set<ContactDTO> result = contactService.getAllContactsByContactListID(contactListId);

        assertThat(result).isEmpty();
    }
    /**
     * Tests the getAllContactsByUserId method of the ContactService.
     * The test passes if the returned set of ContactDTOs is not null and has a size of 1.
     */
    @DisplayName("JUnit test for getAllContactsByUserId method")
    @Test
    public void givenUserId_whenGetAllContactsByUserId_thenReturnContactsSet() {
        UUID userId = UUID.randomUUID();
        Set<ContactEntity> contactEntitySet = new LinkedHashSet<>();
        contactEntitySet.add(contactEntity);

        given(contactRepository.findAllContactsByUserId(userId)).willReturn(contactEntitySet);
        given(contactMapper.toDTO(any(ContactEntity.class))).willReturn(contactDTO);

        Set<ContactDTO> contacts = contactService.getAllContactsByUserId(userId);

        assertThat(contacts).isNotNull().hasSize(1);
        assertThat(contacts.iterator().next()).isEqualTo(contactDTO);
    }

    /**
     * Tests the getAllContactsByUserId method of the ContactService when no contacts are found.
     * The test passes if the returned set of ContactDTOs is empty.
     */
    @DisplayName("JUnit test for getAllContactsByUserId method when no contacts found")
    @Test
    public void givenUserIdWithNoContacts_whenGetAllContactsByUserId_thenReturnEmptySet() {
        UUID userId = UUID.randomUUID();
        given(contactRepository.findAllContactsByUserId(userId)).willReturn(Collections.emptySet());

        Set<ContactDTO> result = contactService.getAllContactsByUserId(userId);

        assertThat(result).isEmpty();
    }

}
