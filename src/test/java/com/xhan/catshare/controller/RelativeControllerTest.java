package com.xhan.catshare.controller;

import com.xhan.catshare.entity.dao.record.CurrentRelation;
import com.xhan.catshare.entity.dao.record.RaiseRecord;
import com.xhan.catshare.entity.dao.user.UserDO;
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

import java.time.Instant;
import java.util.*;

import static com.xhan.catshare.controller.ControllerConstant.currentRecordURL;
import static com.xhan.catshare.controller.ControllerConstant.friendURL;
import static com.xhan.catshare.entity.generator.UserGenerator.names;
import static java.util.stream.Collectors.toList;
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

    private Map<Integer, List<RaiseRecord>> asRaiser = new HashMap<>();
    private Map<Integer, List<RaiseRecord>> asAcceptor = new HashMap<>();

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
        addRaiseRecord(db(names[0]), db(names[1]));
        addFriendRecord(db(names[0]), db(names[2]));

        int name0Id = repo.findIdByAccount(db(names[0]))
                .orElseThrow(AccountNotFoundException::new)
                .getId();
        // error first
        mvc.perform(
                post(friendURL)
                        .param("acceptorAccount", db(names[1]))
                .sessionAttr("userId", name0Id)
        ).andExpect(status().isBadRequest());
        // error second
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
        CurrentRelation cr = new CurrentRelation(ip2.rid, ip2.aid);
        RaiseRecord rr = new RaiseRecord(ip3.rid, ip3.aid);
        crRepo.save(cr);
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

        CurrentRelation cr = new CurrentRelation(ip.rid, ip.aid);
        crRepo.save(cr);

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
        List<CurrentRelation> asRaiser= Arrays.stream(names)
                .skip(1).limit(3)
                .map(s -> getIdPair(db(names[0]), db(s)))
                .map(p -> new CurrentRelation(p.rid, p.aid))
                .collect(toList());
        assert asRaiser.size() > 0;
        asRaiser.forEach(System.out::println);

        List<CurrentRelation> asAcceptor= Arrays.stream(names)
                .skip(4)
                .map(s -> getIdPair(db(s), db(names[0])))
                .map(p -> new CurrentRelation(p.rid, p.aid))
                .collect(toList());
        assert asAcceptor.size() > 0;
        asAcceptor.forEach(System.out::println);

        crRepo.saveAll(asAcceptor);
        crRepo.saveAll(asRaiser);

    }

    /**
     * 在这个方法里测试了根据登陆者id获得
     * 所有请求的Id的情况。
     */
    @Test
    public void getRaiseRecord(){
        new RecordGenerator().geneRecords();
        assert rrRepo.findAll().size() > 0;
    }

    @Test
    public void handleExceptions() {
    }

    private void addFriendRecord(String raiser, String acceptor){
        IdPair p = getIdPair(raiser, acceptor);

        CurrentRelation curr = new CurrentRelation(p.getRid(), p.getAid());

        crRepo.save(curr);
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

    @Test
    public void RecordGeneratorTest(){
        RecordGenerator gene = new RecordGenerator();
        gene.geneRecords();
        crRepo.findAll().forEach(System.out::println);
        rrRepo.findAll().forEach(System.out::println);
    }

    private class RecordGenerator{
        void geneRecords(){
            // 如果反正都要得到全部的关系的话，不如直接遍历一遍
            randomSaving(getAllId(), new Random(Instant.now().getEpochSecond()));

        }

        private void randomSaving(List<Integer> allId, Random sig) {
            for (int i=0;i<allId.size();i++){
                for (int j=i+1;j<allId.size();j++)
                {
                    if (sig.nextInt() % 2==0){
                        // i as raiser
                        if (sig.nextInt() % 2==0){
                            rrRepo.save(new RaiseRecord(allId.get(i), allId.get(j)));
                        }else {
                            // save CurrentRelation
                            crRepo.save(new CurrentRelation(allId.get(i), allId.get(j)));
                        }
                    }else {
                        // i as acceptor
                        if (sig.nextInt() % 2==0){
                            // save raiseRecord
                            rrRepo.save(new RaiseRecord(allId.get(j), allId.get(i)));
                        }else {
                            // save CurrentRelation
                            crRepo.save(new CurrentRelation(allId.get(j), allId.get(i)));
                        }
                    }
                }
            }
        }

        private List<Integer> getAllId() {
            return repo.findAll().stream()
                    .map(UserDO::getId)
                    .collect(toList());
        }
    }
}