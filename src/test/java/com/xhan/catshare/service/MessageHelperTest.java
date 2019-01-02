package com.xhan.catshare.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * 在这个类的测试中要注意的地方：因为将许多实体的主键都暴露出去了，
 * 所以在调用方法时，一定要注意校验发来的主键是不是满足关系，否则
 * 就要抛出异常。也就是说在测试时，一定要注意错误情况能否捕捉到
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageHelperTest {

    /**
     * 这个类在测试时需要构建几个不同的用户，构建用户间的好友关系，
     * 每个用户都要构建几个动态，还要对动态随机构建几个评论，以及
     * 评论的评论
     */
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFriendMessages() {
    }

    @Test
    public void getFriendMessages1() {
    }

    @Test
    public void savePicMessage() {
    }

    @Test
    public void saveTextMessage() {
    }

    @Test
    public void saveCommentToMessage() {
    }

    @Test
    public void saveCommentToComment() {
    }

    @Test
    public void removeCommentOfMessage() {
    }

    @Test
    public void removeCommentOfComment() {
    }

    @Test
    public void removeMessage() {
    }

    @Test
    public void getCommentsOfMessage() {
    }

    @Test
    public void getCommentsOfMessage1() {
    }

    @Test
    public void getCommentsOfComments() {
    }

    @Test
    public void getSelfMessages() {
    }

    @Test
    public void getSelfMessages1() {
    }
}