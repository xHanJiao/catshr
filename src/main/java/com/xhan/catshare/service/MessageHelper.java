package com.xhan.catshare.service;

import com.xhan.catshare.entity.dao.message.comment.Comment;
import com.xhan.catshare.entity.dto.message.CommentCommentDTO;
import com.xhan.catshare.entity.dto.message.MessageCommentDTO;
import com.xhan.catshare.entity.dto.message.MessageDTO;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.util.List;

public abstract class MessageHelper {

    public abstract List<String> getCommentsByPage(int page);

    public abstract void saveTextMessage(Integer userId, String content);

    public abstract void savePicMessage(Integer userId, CommonsMultipartFile pics, String content);

    public abstract List<MessageDTO> findFriendMessagesById(Integer userId, int page);

    public void addMessageComment(MessageCommentDTO dto, int userId){
        checkMessageComment(dto, userId);
        addMessageComment(buildComment(dto, userId));
    }

    public void addCommentComment(CommentCommentDTO dto, int userId){
        checkCommentComment(dto, userId);
        addCommentComment(buildCommentComment(dto, userId));
    }

    public void deleteMessage(Integer messageId, Integer userId){
        checkBeforeDeleteMessage(messageId, userId);
        doDeleteMessageAfterCheck(messageId, userId);
    }

    public void deleteCommentComment(String content, int commentId, int userId){
        checkIsOwner(content, commentId, userId);
        updateCommentRemoveSubComment(commentId, content);
    }

    public void deleteComment(int commentId, int messageId, int userId){
        checkIsOwner(messageId, commentId, userId);
        deleteComment(buildComment(commentId));
    }

    public abstract void deleteComment(Comment comment);

    protected abstract Comment buildComment(int commentId);

    protected abstract void updateCommentRemoveSubComment(int commentId, String content);

    protected abstract void checkIsOwner(String content, int commentId, int userId);

    protected abstract void checkIsOwner(int messageId, int commentId, int userId);

    protected abstract Comment buildCommentComment(CommentCommentDTO dto, int userId);

    protected abstract void addCommentComment(Comment comment);

    protected abstract void checkCommentComment(CommentCommentDTO dto, int userId);

    protected abstract void doDeleteCommentAfterCheck(int commentId, int userId);

    protected abstract void checkBeforeDeleteComment(int commentId, int userId);

    protected abstract Comment buildComment(MessageCommentDTO dto, int userId);

    protected abstract void addMessageComment(Comment comment);

    protected abstract void checkMessageComment(MessageCommentDTO dto, int userId);

    protected abstract void doDeleteMessageAfterCheck(Integer messageId, Integer userId);

    protected abstract void checkBeforeDeleteMessage(Integer messageId, Integer userId);

}
