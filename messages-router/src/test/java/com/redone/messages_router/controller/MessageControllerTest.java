package com.redone.messages_router.controller;

import com.redone.messages_router.dto.MessageDTO;
import com.redone.messages_router.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void testGetMessageById() throws Exception {
        // Given
        MessageDTO messageDTO = MessageDTO.builder()
                .id(1L)
                .messageId("MSG123")
                .content("Test content")
                .build();

        when(messageService.getMessageById(1L)).thenReturn(messageDTO);

        // When & Then
        mockMvc.perform(get("/api/messages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.messageId").value("MSG123"))
                .andExpect(jsonPath("$.content").value("Test content"));
    }
}
