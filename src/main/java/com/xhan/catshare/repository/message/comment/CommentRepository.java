package com.xhan.catshare.repository.message.comment;

import com.xhan.catshare.entity.dao.message.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{


}
