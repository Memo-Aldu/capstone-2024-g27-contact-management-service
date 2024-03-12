package com.crm.contactmanagementservice.integration;

import com.crm.contactmanagementservice.dto.ContactListDTO;
import com.crm.contactmanagementservice.service.ContactListService;
import com.crm.contactmanagementservice.controller.ContactListController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContactListController.class)
public class ContactListIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactListService contactListService;

    @Autowired
    private ObjectMapper objectMapper;

    private ContactListDTO contactListDTO;

    @BeforeEach
    public void setup() {
        UUID contactListId = UUID.randomUUID();
        contactListDTO = new ContactListDTO(contactListId, "My Contacts", UUID.randomUUID());
    }

    @Test
    @DisplayName("Get ContactList By Id - GET /api/v1/contactlists/{id}")
    public void givenContactListId_whenGetContactListById_thenReturnContactList() throws Exception {
        given(contactListService.getContactListById(contactListDTO.id())).willReturn(contactListDTO);

        mockMvc.perform(get("/api/v1/contactlists/{id}", contactListDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.listName").value(contactListDTO.listName()));
    }

    @Test
    @DisplayName("Create ContactList - POST /api/v1/contactlists")
    public void givenContactListDTO_whenCreateContactList_thenReturnSavedContactList() throws Exception {
        given(contactListService.createContactList(contactListDTO)).willReturn(contactListDTO);

        mockMvc.perform(post("/api/v1/contactlists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactListDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.listName").value(contactListDTO.listName()));
    }

    @Test
    @DisplayName("Update ContactList - PATCH /api/v1/contactlists/{id}")
    public void givenUpdatedContactListDTO_whenUpdateContactList_thenReturnUpdatedContactList() throws Exception {
        given(contactListService.updateContactList(contactListDTO, contactListDTO.id())).willReturn(contactListDTO);

        mockMvc.perform(patch("/api/v1/contactlists/{id}", contactListDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactListDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.listName").value(contactListDTO.listName()));
    }

    @Test
    @DisplayName("Delete ContactList By Id - DELETE /api/v1/contactlists/{id}")
    public void givenContactListId_whenDeleteContactListById_thenStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/contactlists/{id}", contactListDTO.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Get All ContactLists - GET /api/v1/contactlists")
    public void whenGetAllContactLists_thenReturnContactListsList() throws Exception {
        given(contactListService.getAllContactLists()).willReturn(Set.of(contactListDTO));

        mockMvc.perform(get("/api/v1/contactlists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].listName").value(contactListDTO.listName()));
    }
}
