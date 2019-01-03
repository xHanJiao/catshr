package com.xhan.catshare.repository.message.comment;

import com.xhan.catshare.entity.dao.message.comment.MetaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaCommentRepository extends JpaRepository<MetaComment, Integer>{

}
