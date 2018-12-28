package com.xhan.catshare.entity.generator;

import com.xhan.catshare.entity.dao.message.alive.PicMessage;
import com.xhan.catshare.entity.dao.message.alive.TextMessage;
import com.xhan.catshare.entity.dao.record.CurrentRelation;
import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.repository.message.MetaMessageRepository;
import com.xhan.catshare.repository.message.PicMessageRepository;
import com.xhan.catshare.repository.message.TextMessageRepository;
import com.xhan.catshare.repository.record.CurrentRecordRepository;
import com.xhan.catshare.repository.record.DeleteRecordRepository;
import com.xhan.catshare.repository.record.RaiseRecordRepository;
import com.xhan.catshare.repository.user.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static java.lang.String.format;

@RunWith(SpringRunner.class)
public class FriendShipAndMetaMessageGenerator {

    @Autowired protected RaiseRecordRepository rrRepo;
    @Autowired protected CurrentRecordRepository crRepo;
    @Autowired protected DeleteRecordRepository drRepo;
    @Autowired protected UserRepository repo;
    @Autowired protected MetaMessageRepository mmRepo;
    @Autowired protected TextMessageRepository tmRepo;
    @Autowired protected PicMessageRepository pmRepo;

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
    protected void TextMessageGenerator(int times) {
        List<UserDO> users = repo.findAll();
        getIdPairSets(times).forEach(p -> {
                    crRepo.save(new CurrentRelation(
                            users.get(p.rid).getId(),
                            users.get(p.aid).getId(),
                            users.get(p.aid).getAccount(),
                            users.get(p.aid).getUsername()));
                    mmRepo.save(buildTextMessageFromPair(p, users));
                }
        );
    }

    protected void PicMessageGenerator(int times) {
        List<UserDO> users = repo.findAll();
        getIdPairSets(times).forEach(p -> {
                    crRepo.save(new CurrentRelation(
                            users.get(p.rid).getId(),
                            users.get(p.aid).getId(),
                            users.get(p.aid).getAccount(),
                            users.get(p.aid).getUsername()));
                    mmRepo.save(buildPicMessageFromPair(p, users));
                }
        );
    }

    /**
     * 生成times条信息，其中可能有文字类也可能有有图片类的
     * @param times 生成信息的数目
     */
    protected void HybridMessageGenerator(int times) {
        List<UserDO> users = repo.findAll();
        Random ra = new Random(new Date().getTime());
        getIdPairSets(times).forEach(p -> {
                    crRepo.save(new CurrentRelation(
                            users.get(p.rid).getId(),
                            users.get(p.aid).getId(),
                            users.get(p.aid).getAccount(),
                            users.get(p.aid).getUsername()));
                    if(ra.nextInt(10) % 2==1) {
                        tmRepo.save(buildTextMessageFromPair(p, users));
                    } else
                        pmRepo.save(buildPicMessageFromPair(p, users));
                }
        );
    }

    protected void RepeatHybridMessageGenerator(int times) {
        List<UserDO> users = repo.findAll();
        Random ra = new Random(new Date().getTime());
        getIdPairSets(times).forEach(p -> {
                    crRepo.save(new CurrentRelation(
                            users.get(p.rid).getId(),
                            users.get(p.aid).getId(),
                            users.get(p.aid).getAccount(),
                            users.get(p.aid).getUsername()));
                    int messageNum = 3;
                    if(ra.nextInt(10) % 2==1) {
                        while (messageNum>0){
                            tmRepo.save(buildTextMessageFromPair(p, users));
                            messageNum--;
                        }
                    } else
                        while (messageNum>0){
                            pmRepo.save(buildPicMessageFromPair(p, users));
                            messageNum--;
                        }
                }
        );
    }
    /**
     * 随机产生times个IdPair并将它们加入到集合中返回
     * @param times 生成IdPair的数目，最大值也要小于
     *              ((userNum-1) * userNum / 2)
     * @return 返回一个IdPair组成的Set
     */
    private Set<IdPair> getIdPairSets(int times) {
        int userNum = repo.findAll().size();
        assert times < ((userNum-1) * userNum / 2);
        Set<IdPair> idSets = new HashSet<>(16);
        Random random = new Random(new Date().getTime());
        while (times > 0){
            int index1 = random.nextInt(repo.findAll().size());
            int index2 = random.nextInt(repo.findAll().size());
            if(index1 != index2){
                List<IdPair> pairs = new IdPair().getPairs(index1, index2);
                if(idSets.addAll(pairs)){
                    times--;
                }
            }
        }
        return idSets;
    }

    private TextMessage buildTextMessageFromPair(IdPair p, List<UserDO> users) {
        String pattern = "this is a text message I'm %s and my friend is %s";
        return new TextMessage(format(pattern,
                users.get(p.rid).getUsername(),
                users.get(p.aid).getUsername()),
                users.get(p.rid).getId());
    }

    private PicMessage buildPicMessageFromPair(IdPair p, List<UserDO> users) {
        String contentPattern = "this is a pic message I'm %s and my friend is %s";
        String fileLocationPattern = "file://%s %s";

        return new PicMessage(format(contentPattern,
                users.get(p.rid).getUsername(),
                users.get(p.aid).getUsername()),
                users.get(p.rid).getId(),
                format(fileLocationPattern, users.get(p.rid).getUsername(), LocalDate.now().toString())
                );
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
