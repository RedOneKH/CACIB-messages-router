package com.redone.messages_router.model;

import com.redone.messages_router.dto.MessageDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "sender", columnDefinition = "TEXT")
    private String sender;

    @Column(name = "received_timestamp")
    private LocalDateTime receivedTimestamp;

    public MessageDTO toDto() {
        return MessageDTO.builder()
                .id(id)
                .messageId(messageId)
                .content(content)
                .sender(sender)
                .receivedTimestamp(receivedTimestamp)
                .build();
    }
}