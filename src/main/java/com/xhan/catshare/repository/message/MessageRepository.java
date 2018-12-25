package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    @Query("select m from Message m, com.xhan.catshare.entity.dao.record.CurrentRelation AS cr" +
            " where m.ownerId=cr.acceptorId AND cr.raiserId=?1")
    List<Message> findMessagesByFriendShip(Integer id);

    List<Message> findByOwnerId(Integer id);
}
