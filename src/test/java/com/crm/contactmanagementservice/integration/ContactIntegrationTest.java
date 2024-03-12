package com.crm.contactmanagementservice.integration;

import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crm.contactmanagementservice.controller.ContactController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ContactController.class)
public class ContactIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    private ContactDTO contactDTO;

    @BeforeEach
    public void setup() {
        UUID contactId = UUID.randomUUID();
        contactDTO = new ContactDTO(contactId, null, "June", "Thomas", null, "junethomas@test.com", "1234567890", null, null, false);
    }

    @Test
    @DisplayName("Get Contact By Id - GET /api/v1/contacts/{id}")
    public void givenContactId_whenGetContactById_thenReturnContact() throws Exception {
        given(contactService.getContactById(contactDTO.id())).willReturn(contactDTO);

        mockMvc.perform(get("/api/v1/contacts/{id}", contactDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(contactDTO.email()));
    }

    @Test
    @DisplayName("Create Contact - POST /api/v1/contacts")
    public void givenContactDTO_whenCreateContact_thenReturnSavedContact() throws Exception {
        given(contactService.createContact(contactDTO)).willReturn(contactDTO);

        mockMvc.perform(post("/api/v1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(contactDTO.email()));
    }

    @Test
    @DisplayName("Update Contact - PATCH /api/v1/contacts/{id}")
    public void givenUpdatedContactDTO_whenUpdateContact_thenReturnUpdatedContact() throws Exception {
        given(contactService.updateContact(contactDTO, contactDTO.id())).willReturn(contactDTO);

        mockMvc.perform(patch("/api/v1/contacts/{id}", contactDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(contactDTO.email()));
    }

    @Test
    @DisplayName("Delete Contact By Id - DELETE /api/v1/contacts/{id}")
    public void givenContactId_whenDeleteContactById_thenStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/contacts/{id}", contactDTO.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Search Contacts By Name - GET /api/v1/contacts/search/{name}")
    public void givenContactName_whenSearchContactsByName_thenReturnContactsList() throws Exception {
        given(contactService.searchContactsByName("June")).willReturn(Set.of(contactDTO));

        mockMvc.perform(get("/api/v1/contacts/search/{name}", "June"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value(contactDTO.firstName()));
    }


}
