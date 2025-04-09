package com.redone.messages_router.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageDTO {
    private Long id;
    private String messageId;
    private String content;
    private String sender;
    private LocalDateTime receivedTimestamp;

}