package com.redone.messages_router.service;

import com.redone.messages_router.config.MQConfigurationProperties;
import com.redone.messages_router.model.Message;
import com.redone.messages_router.repository.MessageRepository;
import com.redone.messages_router.repository.PartnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@EnableScheduling
public class MQListenerService {

    @Autowired
    private MQConfigurationProperties mqProperties;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Scheduled(fixedRate = 5000) // Poll every 5 seconds
    @Transactional
    public void receiveMessages() throws Exception{
        var receivedMessage = jmsTemplate.receive(mqProperties.getQueue());
        if (receivedMessage != null) {
            Message message = new Message();
            message.setMessageId(receivedMessage.getJMSMessageID());
            message.setReceivedTimestamp(LocalDateTime.now());
            message.setContent(receivedMessage.getBody(String.class));
            message.setSender(receivedMessage.getStringProperty("Sender"));
            messageRepository.save(message);
        }
    }
}