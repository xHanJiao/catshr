package com.xhan.catshare.controller;

import com.xhan.catshare.entity.dao.record.RaiseRecord;
import com.xhan.catshare.entity.generator.UserGenerator;
import com.xhan.catshare.exception.records.AccountNotFoundException;
import com.xhan.catshare.repository.user.UserRepository;
import com.xhan.catshare.repository.record.CurrentRecordRepository;
import com.xhan.catshare.repository.record.DeleteRecordRepository;
import com.xhan.catshare.repository.record.RaiseRecordRepository;
import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static com.xhan.catshare.controller.ControllerConstant.currentRecordURL;
import static com.xhan.catshare.controller.ControllerConstant.friendURL;
import static com.xhan.catshare.entity.dao.record.CurrentRelation.buildPair;
import static com.xhan.catshare.entity.generator.UserGenerator.names;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RelativeControllerTest {

    @Autowired private WebApplicationContext env;
    @Autowired private RaiseRecordRepository rrRepo;
    @Autowired private CurrentRecordRepository crRepo;
    @Autowired private DeleteRecordRepository drRepo;
    @Autowired private UserRepository repo;


    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(env)
                .build();

        UserGenerator.generator()
                .forEach(u -> {
                    u.setChecked(true);
                    repo.save(u);
                });
    }

    @After
    public void tearDown() {
        repo.deleteAll();
        rrRepo.deleteAll();
        drRepo.deleteAll();
        crRepo.deleteAll();
    }

    /**
     * 这个添加好友主要测试一种成功情况和两种
     * 失败情况，也就是测试在已有一个未过期申
     * 请时或者在已经是好友时能否成功报错。还
     * 要测试成功情况。
     */
    @Test
    @Transactional
    public void addFriend() throws Exception {
        // 0 is raiser and 1 is acceptor
        // 构建 0 和 1 之间的未过期申请
        addRaiseRecord(db(names[0]), db(names[1]));
        // 构建 0 和 2 之前的好友关系
        addFriendRecord(names[0], names[2]);

        int name0Id = repo.findIdByAccount(db(names[0]))
                .orElseThrow(AccountNotFoundException::new)
                .getId();
        // 有未过期申请
        mvc.perform(
                post(friendURL)
                        .param("acceptorAccount", db(names[1]))
                .sessionAttr("userId", name0Id)
        ).andExpect(status().isBadRequest());
        // 有好友关系
        mvc.perform(
                post(friendURL)
                        .param("acceptorAccount", db(names[2]))
                        .sessionAttr("userId", name0Id)
        ).andExpect(status().isBadRequest());
        // correct
        mvc.perform(
                post(friendURL)
                        .param("acceptorAccount", db(names[3]))
                        .sessionAttr("userId", name0Id)
        ).andExpect(status().isOk());
    }

    /**
     * 确认添加好友的测试，测试一种正常情况
     * 和两种错误情况（已经是好友和没有这条
     * 记录）这两种情况。
     */
    @Test
    public void confirm() throws Exception {
        IdPair ip = getIdPair(db(names[0]), db(names[1]));
        IdPair ip2 = getIdPair(db(names[2]), db(names[3]));
        IdPair ip3 = getIdPair(db(names[0]), db(names[2]));

        // 构建2 和 3 之间的好友关系
        buildPair(ip2.rid, ip2.aid, db(names[2]),
                names[2], db(names[3]), names[3])
                .forEach(crRepo::save);
        RaiseRecord rr = new RaiseRecord(ip3.rid, ip3.aid);
        rrRepo.save(rr);

        // no raiseRecord
        mvc.perform(post(friendURL)
                        .param("accept", db(names[1]))
                        .sessionAttr("userId", ip.rid))
                .andExpect(status().isBadRequest());

        // already is friend
        mvc.perform(post(friendURL)
                        .param("accept", db(names[3]))
                        .sessionAttr("userId", ip2.rid))
                .andExpect(status().isBadRequest());

        // correct
        mvc.perform(post(friendURL)
                        .param("accept", db(names[2]))
                        .sessionAttr("userId", ip3.rid))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 对删除好友的测试，需要测试两种情况：
     * 正常情况和不是好友的情况。
     */
    @Test
    public void deleteFriend() throws Exception {
        IdPair ip = getIdPair(db(names[0]), db(names[1]));
        IdPair ip2 = getIdPair(db(names[2]), db(names[3]));

        buildPair(ip.rid, ip.aid, db(names[0]),
                names[0], db(names[1]), names[1])
                .forEach(crRepo::save);

        // not friend yet
        mvc.perform(post(friendURL)
                .param("delete", db(names[2]))
                .sessionAttr("userId", ip2.aid))
                .andExpect(status().isBadRequest())
                .andDo(print());

        // correct
        mvc.perform(post(friendURL)
                .param("delete", db(names[1]))
                .sessionAttr("userId", ip.rid))
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    /**
     * 在这里要测试能不能正常获得所有的好友关系
     * 要添加某特定用户作为发起者和接受者的关系
     * 看看能不能把它们都收集到
     */
    @Test
    public void getCurrentRelations() throws Exception {
        popularCurrentRelations();
        IdPair allZero = getIdPair(db(names[0]), db(names[0]));

        mvc.perform(
                get(currentRecordURL)
                        .sessionAttr("userId", allZero.rid))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void popularCurrentRelations() {
        Arrays.stream(names)
                .skip(1).limit(3)
                .forEach(s -> {
                    IdPair ip = getIdPair(db(names[0]), db(s));
                    buildPair(ip.rid, ip.aid, db(names[0]), names[0], db(s), s)
                            .forEach(cr -> {
                                System.out.println(cr);
                                crRepo.save(cr);
                            });
                });
    }

    /**
     * 在这个方法里测试了根据登陆者id获得
     * 所有请求的Id的情况。
     */
//    @Test
//    public void getRaiseRecord(){
//        new RecordGenerator().geneRecords();
//        assert raiseRecordRepository.findAll().size() > 0;
//    }

    @Test
    public void handleExceptions() {
    }

    /**
     * 在这里传入两个名字，因为在后台构建测试数据时，
     * 只是简单地将账户设置为名字+名字，所以只传入名
     * 字便足够了
     * @param rName 发起者的名字
     * @param aName 接受者的名字
     */
    private void addFriendRecord(String rName, String aName){
        IdPair p = getIdPair(db(rName), db(aName));

        buildPair(p.rid, p.aid, db(rName),
                rName, db(aName), aName)
                .forEach(crRepo::save);
    }

    private void addRaiseRecord(String raiser, String acceptor) {
        IdPair p = getIdPair(raiser, acceptor);

        RaiseRecord record = new RaiseRecord(p.getRid(), p.getAid());

        rrRepo.save(record);
    }

    private String db(String name) {
        return name+name;
    }

    private IdPair getIdPair(String raiser, String acceptor){
        int rid = repo.findIdByAccount(raiser)
                .orElseThrow(AccountNotFoundException::new)
                .getId();
        int aid = repo.findIdByAccount(acceptor)
                .orElseThrow(AccountNotFoundException::new)
                .getId();
        return new IdPair(rid, aid);
    }

    @Data
    private class IdPair {
        int rid;
        int aid;

        IdPair(int rid, int aid) {
            this.aid = aid;
            this.rid = rid;
        }
    }

}