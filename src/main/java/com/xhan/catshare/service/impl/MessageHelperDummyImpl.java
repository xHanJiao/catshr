package com.xhan.catshare.service.impl;

import com.xhan.catshare.entity.dao.message.MetaMessage;
import com.xhan.catshare.entity.dao.message.comment.Comment;
import com.xhan.catshare.entity.dto.message.MetaMessageDTO;
import com.xhan.catshare.service.MessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Service
public class MessageHelperDummyImpl implements MessageHelper {


    @Override
    public List<MetaMessageDTO> getFriendMessages(int id) {
        return null;
    }

    @Override
    public List<MetaMessageDTO> getFriendMessages(int id, int page) {
        return null;
    }

    @Override
    public void savePicMessage(Integer userId, CommonsMultipartFile pics, String content) {

    }

    @Override
    public void saveTextMessage(Integer userId, String content) {

    }

    @Override
    public void saveCommentToMessage(String content, int messageId, int userId) {

    }

    @Override
    public void saveCommentToComment(String content, int commentId, int userId) {

    }

    @Override
    public void removeCommentOfMessage(int messageId, int commentId, int userId) {

    }

    @Override
    public void removeCommentOfComment(String content, int commentId, int userId) {

    }

    @Override
    public void removeMessage(int messageId, int userId) {

    }

    @Override
    public List<Comment> getCommentsOfMessage(int messageId, int userId) {
        return null;
    }

    @Override
    public List<Comment> getCommentsOfMessage(int messageId, int userId, int page) {
        return null;
    }

    @Override
    public String getCommentsOfComments(int messageId, int commentId) {
        return null;
    }

    @Override
    public List<MetaMessage> getSelfMessages(int id) {
        return null;
    }

    @Override
    public List<MetaMessage> getSelfMessages(int id, int page) {
        return null;
    }
}
