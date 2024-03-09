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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactListServiceTests {

    @Mock
    private ContactListRepository contactListRepository;

    @Mock
    private ContactListMapper contactListMapper;

    @InjectMocks
    private ContactListServiceImpl contactListService;

    private ContactListEntity contactListEntity;
    private ContactListDTO contactListDTO;

    @BeforeEach
    public void setup() {
        UUID id = UUID.randomUUID();
        contactListEntity = new ContactListEntity();
        contactListEntity.setId(id);
        contactListEntity.setListName("Soussi's Contacts");

        contactListDTO = new ContactListDTO(id, "Soussi's Contacts", null);
    }

    @DisplayName("JUnit test for getContactListById method")
    @Test
    public void givenContactListId_whenGetContactListById_thenReturnContactListObject() {
        given(contactListRepository.findById(contactListEntity.getId())).willReturn(Optional.of(contactListEntity));
        given(contactListMapper.toDTO(contactListEntity)).willReturn(contactListDTO);
        ContactListDTO foundContactList = contactListService.getContactListById(contactListEntity.getId());
        assertThat(foundContactList).isNotNull();
        assertThat(foundContactList.id()).isEqualTo(contactListEntity.getId());
    }

    @DisplayName("JUnit test for getContactListById method with non-existent ID")
    @Test
    public void givenNonExistentContactListId_whenGetContactListById_thenThrowException() {
        UUID nonExistentId = UUID.randomUUID();
        given(contactListRepository.findById(nonExistentId)).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> contactListService.getContactListById(nonExistentId));
    }

    @DisplayName("JUnit test for getAllContactLists method")
    @Test
    public void whenGetAllContactLists_thenReturnContactListsSet() {
        given(contactListRepository.findAll()).willReturn(List.of(contactListEntity));
        given(contactListMapper.toDTO(any(ContactListEntity.class))).willReturn(contactListDTO);

        Set<ContactListDTO> contactLists = contactListService.getAllContactLists();

        assertThat(contactLists).isNotNull().hasSize(1);
    }

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
    @DisplayName("JUnit test for deleteContactListById method")
    @Test
    public void givenContactListId_whenDeleteContactListById_thenVerifyDeletion() {
        UUID id = UUID.randomUUID();
        doNothing().when(contactListRepository).deleteById(id);

        contactListService.deleteContactListById(id);

        verify(contactListRepository, times(1)).deleteById(id);
    }

}