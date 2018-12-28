package com.xhan.catshare.service;

import com.xhan.catshare.entity.dao.message.alive.PicMessage;
import com.xhan.catshare.entity.dao.message.alive.TextMessage;
import com.xhan.catshare.entity.dto.message.CommentCommentDTO;
import com.xhan.catshare.entity.dto.message.MessageCommentDTO;
import com.xhan.catshare.entity.dto.message.MessageDTO;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public abstract class MessageHelper {

    public abstract List<MessageDTO> findFriendMessagesById(Integer userId);

    public abstract List<MessageDTO> findFriendMessagesById(Integer userId, int page);

    public abstract void saveTextMessage(Integer userId, String content);

    public abstract void savePicMessage(Integer userId, CommonsMultipartFile pics, String content);

    public abstract List<String> getCommentsByPage(int page);

    public abstract void addMessageComment(MessageCommentDTO dto);

    public abstract void addCommentComment(CommentCommentDTO dto);

    public void deleteComment(int commentId, int userId){
        checkBeforeDeleteComment(commentId, userId);
        doDeleteCommentAfterCheck(commentId, userId);
    }

    protected abstract void doDeleteCommentAfterCheck(int commentId, int userId);

    protected abstract void checkBeforeDeleteComment(int commentId, int userId);

    public void deleteMessage(Integer messageId, Integer userId){
        checkBeforeDeleteMessage(messageId, userId);
        doDeleteMessageAfterCheck(messageId, userId);
    }

    protected abstract void doDeleteMessageAfterCheck(Integer messageId, Integer userId);

    protected abstract void checkBeforeDeleteMessage(Integer messageId, Integer userId);

    protected MessageDTO buildFromMessage(TextMessage message){
        return null;
    }

    protected MessageDTO buildFromMessage(PicMessage message){
        return null;
    }

}
