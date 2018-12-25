package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.Message;
import com.xhan.catshare.entity.dao.record.CurrentRelation;
import com.xhan.catshare.entity.dao.record.Pair;
import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.generator.UserGenerator;
import com.xhan.catshare.repository.record.CurrentRecordRepository;
import com.xhan.catshare.repository.record.DeleteRecordRepository;
import com.xhan.catshare.repository.record.RaiseRecordRepository;
import com.xhan.catshare.repository.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageRepositoryTest {

    @Autowired private RaiseRecordRepository rrRepo;
    @Autowired private CurrentRecordRepository crRepo;
    @Autowired private DeleteRecordRepository drRepo;
    @Autowired private UserRepository repo;
    @Autowired private MessageRepository mRepo;
    private String pattern = "I'm %s and my friend is %s";


    @Before
    public void setUp() {
        assert repo != null;
        assert rrRepo != null;
        assert crRepo != null;
        assert drRepo != null;

        UserGenerator.generator()
                .forEach(u -> {
                    u.setChecked(true);
                    repo.save(u);
                });
        Random times = new Random(new Date().getTime());
        messageGenerator(times.nextInt(5));
    }

    @After
    public void tearDown() {
        repo.deleteAll();
        rrRepo.deleteAll();
        drRepo.deleteAll();
        crRepo.deleteAll();
    }

    @Test
    public void show(){
        System.out.println("---------------relations---------------");
        crRepo.findAll().forEach(System.out::println);
        System.out.println("---------------messages---------------");
        mRepo.findAll().forEach(System.out::println);
    }

    /**
     * 要验证的效果：可以通过一个userId得到他所有
     * 好友发的message
     * 验证的思路：从所有好友关系中随机选出一个好
     * 友关系，然后得到host的所有好友，并且得到他们
     * 发送的message集合，再通过这个方法得到另一个
     * 集合，判断这两个集合相等。
     */
    @Test
    public void findMessagesByFriendShip(){
        //fixme 有时候crNum会不是正数
        Random random = new Random(new Date().getTime());
        CurrentRelation cr = crRepo.findAll()
                .get(random.nextInt(crRepo.findAll().size()));

        List<Message> checks = new ArrayList<>();
        crRepo.findByRaiserId(cr.getRaiserId()).stream()
                .map(Pair::getAcceptorId)
                .map(mRepo::findByOwnerId)
                .forEach(checks::addAll);

        List<Message> messages = mRepo
                .findMessagesByFriendShip(cr.getRaiserId());
        assert checks.equals(messages);
    }

    /**
     * 随机生成几条信息加入到用户中，并且随机生成
     * 用户中的关系
     * 生成关系的方法：首先获得所有的用户的id，然
     * 后每次随机从其中顺序取出两个值，然后放回去，
     * 将取出的两个值放入一个IDPair中，然后把所有
     * 的IdPair加入一个Set以防有重复的出现。收集
     * 够若干数量的IDPair后，就将它们全部加入到
     * 当前可用关系中
     * tips：每次加入IDPair的时候，要一次加入一对
     */
    private void messageGenerator(int times) {
        List<UserDO> users = repo.findAll();
        int userNum = users.size();
        assert times < ((userNum-1) * userNum / 2);
        Set<IdPair> idSets = new HashSet<>(16);
        Random random = new Random(new Date().getTime());
        while (times > 0){
            int index1 = random.nextInt(users.size());
            int index2 = random.nextInt(users.size());
            if(index1 != index2){
                List<IdPair> pairs = new IdPair().getPairs(index1, index2);
                if(idSets.addAll(pairs)){
                    times--;
                }
            }
        }
        idSets.forEach(p -> {
                    crRepo.save(new CurrentRelation(
                                    users.get(p.rid).getId(),
                                    users.get(p.aid).getId(),
                                    users.get(p.aid).getAccount(),
                                    users.get(p.aid).getUsername()));
                    mRepo.save(buildMessageFromPair(p, users));
                }
        );
    }

    private Message buildMessageFromPair(IdPair p, List<UserDO> users) {
            return new Message(String.format(pattern,
                    users.get(p.rid).getUsername(),
                    users.get(p.aid).getUsername()),
                    users.get(p.rid).getId());
//        return new ArrayList<Message>(2){{
//            add(new Message(String.format(pattern,
//                    users.get(p.rid).getUsername(),
//                    users.get(p.aid).getUsername()),
//                    users.get(p.rid).getId()));
//
//            add(new Message(String.format(pattern,
//                    users.get(p.aid).getUsername(),
//                    users.get(p.rid).getUsername()),
//                    users.get(p.aid).getId()));
//        }};
    }

    class IdPair{
        int rid;
        int aid;

        IdPair(int r, int a) {
            rid = r;
            aid = a;
        }

        public IdPair() {}

        List<IdPair> getPairs(int r, int a){
            return new LinkedList<IdPair>(){{
                push(new IdPair(r, a));
                push(new IdPair(a, r));
            }};
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IdPair idPair = (IdPair) o;
            return rid == idPair.rid &&
                    aid == idPair.aid ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rid, aid);
        }
    }
}