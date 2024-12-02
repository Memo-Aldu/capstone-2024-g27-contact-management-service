package com.crm.contactmanagementservice.integration;

import com.crm.contactmanagementservice.dto.ContactDTO;
import com.crm.contactmanagementservice.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crm.contactmanagementservice.controller.ContactController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the ContactController.
 * This class tests the ContactController methods by mocking the ContactService and simulating HTTP requests.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(ContactController.class)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class ContactIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    private ContactDTO contactDTO;

    /**
     * Sets up the test environment before each test.
     * Creates a ContactDTO object to be used in the tests.
     */
    @BeforeEach
    public void setup() {
        UUID contactId = UUID.randomUUID();
        contactDTO = new ContactDTO(contactId, null, "June", "Thomas", null, "junethomas@test.com", "1234567890", null, null, false);
    }

    /**
     * Tests the getContactById method of the ContactController.
     * The test passes if the HTTP status is OK and the returned ContactDTO's email matches the expected email.
     */
    @Test
    @DisplayName("Get Contact By Id - GET /api/v1/contacts/{id}")
    public void givenContactId_whenGetContactById_thenReturnContact() throws Exception {
        given(contactService.getContactById(contactDTO.id())).willReturn(contactDTO);

        mockMvc.perform(get("/api/v1/contacts/{id}", contactDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(contactDTO.email()));
    }

    /**
     * Tests the createContact method of the ContactController.
     * The test passes if the HTTP status is Created and the returned ContactDTO's email matches the expected email.
     */
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

    /**
     * Tests the updateContact method of the ContactController.
     * The test passes if the HTTP status is OK and the returned ContactDTO's email matches the expected email.
     */
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

    /**
     * Tests the deleteContactById method of the ContactController.
     * The test passes if the HTTP status is No Content.
     */
    @Test
    @DisplayName("Delete Contact By Id - DELETE /api/v1/contacts/{id}")
    public void givenContactId_whenDeleteContactById_thenStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/contacts/{id}", contactDTO.id()))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests the searchContactsByName method of the ContactController.
     * The test passes if the HTTP status is OK and the returned ContactDTO's first name matches the expected first name.
     */
    @Test
    @DisplayName("Search Contacts By Name - GET /api/v1/contacts/search/{name}")
    public void givenContactName_whenSearchContactsByName_thenReturnContactsList() throws Exception {
        given(contactService.searchContactsByName("June")).willReturn(Set.of(contactDTO));

        mockMvc.perform(get("/api/v1/contacts/search/{name}", "June"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value(contactDTO.firstName()));
    }

    /**
     * Tests the getAllContactsByContactListID method of the ContactController.
     * The test passes if the HTTP status is OK and the returned set of ContactDTOs is not empty.
     */
    @Test
    @DisplayName("Get All Contacts By Contact List ID - GET /api/v1/contacts/contact-list/{id}")
    public void givenContactListId_whenGetAllContactsByContactListID_thenReturnContactsSet() throws Exception {
        UUID contactListId = UUID.randomUUID();
        given(contactService.getAllContactsByContactListID(contactListId)).willReturn(Set.of(contactDTO));

        mockMvc.perform(get("/api/v1/contacts/contact-list/{id}", contactListId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value(contactDTO.email()));
    }

    /**
     * Tests the getAllContactsByContactListID method of the ContactController when no contacts are found.
     * The test passes if the HTTP status is OK and the returned set of ContactDTOs is empty.
     */
    @Test
    @DisplayName("Get All Contacts By Contact List ID - GET /api/v1/contacts/contact-list/{id} - No Contacts Found")
    public void givenContactListId_whenGetAllContactsByContactListID_thenReturnEmptySet() throws Exception {
        UUID contactListId = UUID.randomUUID();
        given(contactService.getAllContactsByContactListID(contactListId)).willReturn(Collections.emptySet());

        mockMvc.perform(get("/api/v1/contacts/contact-list/{id}", contactListId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    @DisplayName("Get All Contacts By User ID - GET /api/v1/contacts/user/{userId}")
    public void givenUserId_whenGetAllContactsByUserId_thenReturnContacts() throws Exception {
        UUID userId = UUID.randomUUID();
        given(contactService.getAllContactsByUserId(userId)).willReturn(Set.of(contactDTO));

        mockMvc.perform(get("/api/v1/contacts/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value(contactDTO.email()));
    }

    @Test
    @DisplayName("Get All Contacts By User ID - GET /api/v1/contacts/user/{userId} - No Contacts Found")
    public void givenUserId_whenGetAllContactsByUserId_thenReturnEmptySet() throws Exception {
        UUID userId = UUID.randomUUID();
        given(contactService.getAllContactsByUserId(userId)).willReturn(Collections.emptySet());

        mockMvc.perform(get("/api/v1/contacts/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }
}
