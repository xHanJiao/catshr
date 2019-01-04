package com.xhan.catshare.service;

import com.xhan.catshare.entity.dao.message.MetaMessage;
import com.xhan.catshare.entity.dao.message.comment.CommToMes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface MessageHelper {

    /**
     * 分页时每页显示的动态数
     */
    int MESSAGE_PAGE = 5;

    /**
     * 通过用户的id获得所有好友的动态，按时间线由近到远排列，并且显示第一页
     * @param id 当前用户的id，从session中取出
     * @return 获得的动态列表
     */
    List<MetaMessage> getFriendMessages(int id);

    /**
     * 通过用户的id获得所有好友的动态，按时间线由近到远排列，并且显示指定的页数
     * 当页数超出时，应当抛出一个RuntimeException
     * todo 细化这里应该用的异常
     * @param id 当前用户的id，从session中取出
     * @param page 要显示的页数
     * @return 获得的动态列表
     */
    List<MetaMessage> getFriendMessages(int id, int page);

    /**
     * 保存一条图片动态
     * @param userId 发送动态的用户的id
     * @param pics Multipart文件
     * @param content 文字描述的内容
     */
    void savePicMessage(Integer userId, CommonsMultipartFile pics, String content);

    /**
     * 保存一条文字动态
     * @param userId 从session中获得的发送动态的用户id
     * @param content 动态的内容（文字）
     */
    void saveTextMessage(Integer userId, String content);

    /**
     * 保存一条针对动态的评论
     * @param content 评论的内容
     * @param messageId 动态的id
     * @param userId 发起评论的用户的id
     * @param ownerName 发起评论的用户的用户名
     */
    void saveCommentToMessage(String content, int messageId, int userId, String ownerName);

    /**
     * 存储一条针对评论的评论
     * @param content 评论的内容
     * @param commentId 针对动态的评论的id（被评论的那个）
     * @param userId 发起评论的用户的id
     * @param ownerName 发起评论的用户的用户名
     * @param messageId 所在的动态id
     * @param acceptorName 被评论的评论的所有者
     * @param acceptorId 被评论的评论的所有者id
     */
    void saveCommentToComment(String content, int messageId, int commentId, int userId, String ownerName, String acceptorName, String acceptorId);

    /**
     * 移除一条针对动态的评论
     * @param messageId 要移除评论的动态的id
     * @param commentId 要移除的评论id
     * @param userId session中获得的用户id，应该是评论所有者或者动态所有者
     */
    void removeCommentOfMessage(int messageId, int commentId, int userId);

    /**
     * 移除一条针对评论的评论
     * @param content 要移除的评论，因为它不是实体，所以只能这样指代
     * @param commentId 要移除的评论的id
     * @param userId session中获得的用户id，它应该是评论的所有者
     */
    void removeCommentOfComment(String content, int commentId, int userId);

    /**
     * 移除一条动态
     * @param messageId 动态的id
     * @param userId session中获得的用户id，应该是动态的所有者
     */
    void removeMessage(int messageId, int userId);

    /**
     * 获得一条动态的第一页评论
     * @param messageId 动态的id
     * @param userId 用户的id
     * @return 返回评论的内容。
     */
    List<CommToMes> getCommentsOfMessage(int messageId, int userId);

    /**
     * 获得一条动态的第page页评论
     * @param messageId 动态的id
     * @param userId 用户的id
     * @param page 获取的页数
     * @return 返回评论的内容。
     */
    List<CommToMes> getCommentsOfMessage(int messageId, int userId, int page);

    /**
     * 获取针对评论的评论，不分页，直接获得所有
     * @param messageId 评论所属的动态的id
     * @param commentId 评论的id
     * @return 返回字符串形式的用户名-评论集合
     */
    String getCommentsOfComments(int messageId, int commentId);

    /**
     * 获取自己发送的动态的第一页（按时间由近到远排序）
     * @param id 自己的id
     * @return 第一页动态的列表
     */
    List<MetaMessage> getSelfMessages(int id);

    /**
     * 以分页的形式获取自己发送过的动态，获得第page页
     * @param id 自己的id
     * @param page 要获取的页数
     * @return 动态的列表
     */
    List<MetaMessage> getSelfMessages(int id, int page);
}
