package com.clx.clxdash.message;

import com.clx.clxdash.jpa.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
}
