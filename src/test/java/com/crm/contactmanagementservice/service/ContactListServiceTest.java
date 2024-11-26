package com.crm.contactmanagementservice.service;

import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.entity.ContactListEntity;
import com.crm.contactmanagementservice.mapper.ContactListMapper;
import com.crm.contactmanagementservice.repository.ContactListRepository;
import com.crm.contactmanagementservice.service.impl.ContactListServiceImpl;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ContactListService.
 * This class tests the ContactListService methods by mocking the ContactListRepository and ContactListMapper and simulating the service's behavior.
 */
@ExtendWith(MockitoExtension.class)
public class ContactListServiceTest {

    @Mock
    private ContactListRepository contactListRepository;

    @Mock
    private ContactListMapper contactListMapper;

    @InjectMocks
    private ContactListServiceImpl contactListService;

    private ContactListEntity contactListEntity;
    private ContactListDTO contactListDTO;

    /**
     * Sets up the test environment before each test.
     * Initializes the ContactListEntity and ContactListDTO objects to be used in the tests.
     */
    @BeforeEach
    public void setup() {
        UUID id = UUID.randomUUID();
        contactListEntity = new ContactListEntity();
        contactListEntity.setId(id);
        contactListEntity.setListName("Soussi's Contacts");

        contactListDTO = new ContactListDTO(id, "Soussi's Contacts", null);
    }

    /**
     * Tests the getContactListById method of the ContactListService.
     * The test passes if the returned ContactListDTO is not null and its id matches the expected id.
     */
    @DisplayName("JUnit test for getContactListById method")
    @Test
    public void givenContactListId_whenGetContactListById_thenReturnContactListObject() {
        given(contactListRepository.findContactListEntitiesById(contactListEntity.getId())).willReturn(Optional.of(contactListEntity));
        given(contactListMapper.toDTO(contactListEntity)).willReturn(contactListDTO);
        ContactListDTO foundContactList = contactListService.getContactListById(contactListEntity.getId());
        assertThat(foundContactList).isNotNull();
        assertThat(foundContactList.id()).isEqualTo(contactListEntity.getId());
    }

    /**
     * Tests the getContactListById method of the ContactListService with a non-existent id.
     * The test passes if a RuntimeException is thrown.
     */
    @DisplayName("JUnit test for getContactListById method with non-existent ID")
    @Test
    public void givenNonExistentContactListId_whenGetContactListById_thenThrowException() {
        UUID nonExistentId = UUID.randomUUID();
        given(contactListRepository.findContactListEntitiesById(nonExistentId)).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> contactListService.getContactListById(nonExistentId));
    }

    /**
     * Tests the getAllContactLists method of the ContactListService.
     * The test passes if the returned set of ContactListDTOs is not null and has a size of 1.
     */
    @DisplayName("JUnit test for getAllContactLists method")
    @Test
    public void whenGetAllContactLists_thenReturnContactListsSet() {
        given(contactListRepository.findAll()).willReturn(List.of(contactListEntity));
        given(contactListMapper.toDTO(any(ContactListEntity.class))).willReturn(contactListDTO);

        Set<ContactListDTO> contactLists = contactListService.getAllContactLists();

        assertThat(contactLists).isNotNull().hasSize(1);
    }

    /**
     * Tests the createContactList method of the ContactListService.
     * The test passes if the returned ContactListDTO is not null and its list name matches the expected list name.
     */
    @DisplayName("JUnit test for createContactList method")
    @Test
    public void givenContactListDTO_whenCreateContactList_thenReturnSavedContactListDTO() {
        given(contactListMapper.toEntity(any(ContactListDTO.class))).willReturn(contactListEntity);
        given(contactListRepository.save(any(ContactListEntity.class))).willReturn(contactListEntity);
        given(contactListMapper.toDTO(any(ContactListEntity.class))).willReturn(contactListDTO);

        ContactListDTO savedContactList = contactListService.createContactList(contactListDTO);

        assertThat(savedContactList).isNotNull();
        assertThat(savedContactList.listName()).isEqualTo("Soussi's Contacts");
    }

    /**
     * Tests the updateContactList method of the ContactListService.
     * The test passes if the returned ContactListDTO is not null and its list name matches the expected list name.
     */
    @DisplayName("JUnit test for updateContactList method")
    @Test
    public void givenUpdatedContactListDTO_whenUpdateContactList_thenReturnUpdatedContactListDTO() {
        given(contactListRepository.findById(contactListEntity.getId())).willReturn(Optional.of(contactListEntity));
        given(contactListRepository.save(any(ContactListEntity.class))).willReturn(contactListEntity);
        given(contactListMapper.toDTO(any(ContactListEntity.class))).willReturn(contactListDTO);

        ContactListDTO updatedContactList = contactListService.updateContactList(contactListDTO, contactListEntity.getId());

        assertThat(updatedContactList).isNotNull();
        assertThat(updatedContactList.listName()).isEqualTo("Soussi's Contacts");
    }

    /**
     * Tests the deleteContactListById method of the ContactListService.
     * The test passes if the deletion is verified.
     */
    @DisplayName("JUnit test for deleteContactListById method")
    @Test
    public void givenContactListId_whenDeleteContactListById_thenVerifyDeletion() {
        UUID id = UUID.randomUUID();
        doNothing().when(contactListRepository).deleteById(id);

        contactListService.deleteContactListById(id);

        verify(contactListRepository, times(1)).deleteById(id);
    }

    /**
     * Tests the getAllContactLists method of the ContactListService when no contact lists exist.
     * The test passes if the returned set of ContactListDTOs is empty.
     */
    @DisplayName("JUnit test for getAllContactLists method when no contact lists exist")
    @Test
    public void whenGetAllContactLists_andNoContactListsExist_thenReturnEmptySet() {
        given(contactListRepository.findAll()).willReturn(Collections.emptyList());

        Set<ContactListDTO> result = contactListService.getAllContactLists();

        assertThat(result).isEmpty();
    }

    /**
     * Tests the updateContactList method of the ContactListService with a non-existent contact list.
     * The test passes if a RuntimeException is thrown.
     */
    @DisplayName("JUnit test for updateContactList method with non-existent contact list")
    @Test
    public void givenNonExistentContactListId_whenUpdateContactList_thenThrowException() {
        UUID nonExistentId = UUID.randomUUID();
        given(contactListRepository.findById(nonExistentId)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> contactListService.updateContactList(contactListDTO, nonExistentId));
    }

    /**
     * Tests the deleteContactListById method of the ContactListService with a non-existent contact list.
     * The test passes if a RuntimeException is thrown.
     */
    @DisplayName("JUnit test for deleteContactListById method with non-existent contact list")
    @Test
    public void givenNonExistentContactListId_whenDeleteContactListById_thenExpectDeletion() {
        UUID nonExistentId = UUID.randomUUID();
        doThrow(new RuntimeException("ContactList not found")).when(contactListRepository).deleteById(nonExistentId);

        assertThrows(RuntimeException.class, () -> contactListService.deleteContactListById(nonExistentId));
        verify(contactListRepository, times(1)).deleteById(nonExistentId);
    }
}