package com.redone.messages_router.service;

import com.redone.messages_router.dto.MessageDTO;
import com.redone.messages_router.model.Message;
import com.redone.messages_router.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageServiceTest {
    @MockBean
    private MessageRepository messageRepository;


    @Autowired
    private MessageService messageService;

    @Test
    public void testGetMessageById() {
        // Given
        Long messageId = 1L;
        Message message = new Message();
        message.setId(messageId);
        message.setMessageId("MSG123");
        message.setContent("Test content");

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        // When
        MessageDTO result = messageService.getMessageById(messageId);

        // Then
        assertNotNull(result);
        assertEquals(messageId, result.getId());
        assertEquals("MSG123", result.getMessageId());
        assertEquals("Test content", result.getContent());
        verify(messageRepository).findById(messageId);
    }
}
