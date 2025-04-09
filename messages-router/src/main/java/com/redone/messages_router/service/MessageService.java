package com.redone.messages_router.service;

import com.redone.messages_router.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Page<MessageDTO> getAllMessages(Pageable pageable);
    MessageDTO getMessageById(Long id);
}
