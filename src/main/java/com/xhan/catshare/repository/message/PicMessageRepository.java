package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.alive.PicMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PicMessageRepository extends JpaRepository<PicMessage, Integer> {

}
