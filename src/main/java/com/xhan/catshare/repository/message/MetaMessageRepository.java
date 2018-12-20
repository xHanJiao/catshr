package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.MetaMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetaMessageRepository extends JpaRepository<MetaMessage, Integer> {

    List<MetaMessage> findByOwnerId(Integer ownerId);

    Page<MetaMessage> findByOwnerId(Integer ownerId, Pageable pageable);
}
