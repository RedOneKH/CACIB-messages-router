package com.redone.messages_router.repository;

import com.redone.messages_router.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
