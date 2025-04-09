package com.redone.messages_router.service;

import com.redone.messages_router.dto.MessageDTO;
import com.redone.messages_router.model.Message;
import com.redone.messages_router.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Page<MessageDTO> getAllMessages(Pageable pageable) {
        Page<Message> messagePage = messageRepository.findAll(pageable);
        return messagePage.map(Message::toDto);
    }

    @Override
    public MessageDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElse(null);
        return message.toDto();
    }
}
