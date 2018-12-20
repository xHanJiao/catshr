package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextMessageRepository extends JpaRepository<TextMessage, Integer>{

    List<TextMessage> findByOwnerId(Integer ownerId);

}
