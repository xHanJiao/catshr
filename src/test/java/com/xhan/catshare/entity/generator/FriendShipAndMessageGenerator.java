package com.xhan.catshare.entity.generator;

import com.xhan.catshare.entity.dao.message.Message;
import com.xhan.catshare.entity.dao.record.CurrentRelation;
import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.repository.message.MessageRepository;
import com.xhan.catshare.repository.record.CurrentRecordRepository;
import com.xhan.catshare.repository.record.DeleteRecordRepository;
import com.xhan.catshare.repository.record.RaiseRecordRepository;
import com.xhan.catshare.repository.user.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
public class FriendShipAndMessageGenerator {

    @Autowired protected RaiseRecordRepository rrRepo;
    @Autowired protected CurrentRecordRepository crRepo;
    @Autowired protected DeleteRecordRepository drRepo;
    @Autowired protected UserRepository repo;
    @Autowired protected MessageRepository mRepo;

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
    protected void messageGenerator(int times) {
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

    protected Message buildMessageFromPair(IdPair p, List<UserDO> users) {
        String pattern = "I'm %s and my friend is %s";
        return new Message(String.format(pattern,
                users.get(p.rid).getUsername(),
                users.get(p.aid).getUsername()),
                users.get(p.rid).getId());
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
