package com.xhan.catshare.service;

import com.xhan.catshare.entity.dao.message.comment.Comment;
import com.xhan.catshare.entity.dao.record.CurrentRelation;
import com.xhan.catshare.entity.dto.message.MetaMessageDTO;
import com.xhan.catshare.entity.generator.FriendShipAndMetaMessageGenerator;
import com.xhan.catshare.repository.message.comment.CommentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.*;

import static org.junit.Assert.*;

/**
 * 在这个类的测试中要注意的地方：因为将许多实体的主键都暴露出去了，
 * 所以在调用方法时，一定要注意校验发来的主键是不是满足关系，否则
 * 就要抛出异常。也就是说在测试时，一定要注意错误情况能否捕捉到
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageHelperTest extends FriendShipAndMetaMessageGenerator {

    @Autowired private CommentRepository commentRepository;

    @Autowired private MessageHelper messageHelper;

    /**
     * 在初始化时，构建了一些用户，并在其中随机构建了一些
     * 好友关系，每个好友关系都还同时构建了两条动态，然后
     * 随机生成了五个评论
     */
    @Before
    public void setUp() {
        populateUsers();
        RepeatHybridMessageGenerator(
                new Random(new Date().getTime())
                        .nextInt(10)+1);
        generateComments(5);
    }

    @After
    public void tearDown() {
        clearDB();
    }

    @Test
    public void getFriendMessages() throws Exception {

        int hostId = randomGetUserIdWithCr();

        checkMetaMessageDTOs(hostId, messageHelper.getFriendMessages(hostId));

    }

    @Test
    public void getFriendMessages1() throws Exception {
        int hostId = randomGetUserIdWithCr();
        int i = 0;

        // 校验页数为负数
        try {
            messageHelper.getFriendMessages(hostId, -1);
        }catch (RuntimeException e){
            i += 1;
        }
        // 校验页数极大
        assertEquals(messageHelper.getFriendMessages(hostId, 111).size()
                , 0);
        // 校验正常情况
        checkMetaMessageDTOs(hostId, messageHelper.getFriendMessages(hostId));
        assertEquals(i, 1);
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

    /**
     * 这个函数的目的是生成一些评论，具体做法是：
     * 1、随机取得一些好友关系
     * 2、随机选择一对好友中的一个
     * 3、让他随机选择一个好友，选择一个动态进行评论
     */
    private void generateComments(int commentNum) {
        List<CurrentRelation> currentRelations = currentRecordRepository.findAll();
        Collection<CurrentRelation> crs = new HashSet<>(16);

        getRandomCurrentRelations(commentNum, currentRelations, crs);

        crs.forEach(this::buildAndSaveCommentFromCurrentRelation);

    }

    private int randomGetUserIdWithCr() {
        List<CurrentRelation> crs = currentRecordRepository.findAll();

        return crs.get(new Random().nextInt(crs.size()))
                .getRaiserId();
    }

    private void buildAndSaveCommentFromCurrentRelation(CurrentRelation cr){
        String commentPattern = "hello %s, my id is %s";
        int commentOwnerId = cr.getRaiserId();
        int messageOwnerId = cr.getAcceptorId();
        int messageId = metaMessageRepository
                .findByOwnerId(messageOwnerId)
                .get(0)
                .getId();
        commentRepository.save(
                new Comment(commentOwnerId,
                        messageId,
                        String.format(commentPattern, cr.getAccount(), commentOwnerId))
        );
    }

    private void getRandomCurrentRelations(int commentNum,
                                           List<CurrentRelation> currentRelations,
                                           Collection<CurrentRelation> crs) {
        Random personSelector = new Random(Instant.now().getEpochSecond());
        while (commentNum > 0){
            if (crs.add(currentRelations
                    .get(personSelector
                            .nextInt(currentRelations.size())))){
                commentNum--;
            }
        }
    }

    private void checkMetaMessageDTOs(int hostId, List<MetaMessageDTO> metaMessages) throws Exception {
        // 校验分页
        assertTrue(metaMessages.size() < MessageHelper.MESSAGE_PAGE);
        // 校验获得的都是好友的动态
        metaMessages.forEach(
                mm ->
                        assertTrue(currentRecordRepository
                                .existsByRaiserIdAndAcceptorId(mm.getOwnerId(), hostId))
        );
        // 校验是有顺序的
        for (int i=1; i<metaMessages.size(); i++){
            if(metaMessages.get(i).getId() > metaMessages.get(i-1).getId()){
                throw new Exception("no order");
            }
        }
    }

    @Override
    protected void clearDB() {
        super.clearDB();
        commentRepository.deleteAll();
    }
}