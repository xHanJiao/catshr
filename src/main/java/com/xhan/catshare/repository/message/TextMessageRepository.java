package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.alive.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextMessageRepository extends JpaRepository<TextMessage, Integer> {

}
