package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.MetaMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MetaMessageRepository extends JpaRepository<MetaMessage, Integer>{

    @Query("select m from MetaMessage m, com.xhan.catshare.entity.dao.record.CurrentRelation AS cr" +
            " where m.ownerId=cr.acceptorId AND cr.raiserId=?1")
    List<MetaMessage> findMessagesByFriendShip(Integer id);

    @Query("select m from MetaMessage m, com.xhan.catshare.entity.dao.record.CurrentRelation AS cr" +
            " where m.ownerId=cr.acceptorId AND cr.raiserId=?1")
    Page<MetaMessage> findMessagesByFriendShip(Integer id, Pageable page);

    List<MetaMessage> findByOwnerId(Integer id);
}
