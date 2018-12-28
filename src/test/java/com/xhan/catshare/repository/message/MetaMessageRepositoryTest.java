package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.MetaMessage;
import com.xhan.catshare.entity.dao.record.CurrentRelation;
import com.xhan.catshare.entity.dao.record.Pair;
import com.xhan.catshare.entity.generator.FriendShipAndMetaMessageGenerator;
import com.xhan.catshare.entity.generator.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MetaMessageRepositoryTest extends FriendShipAndMetaMessageGenerator {

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
        RepeatHybridMessageGenerator(times.nextInt(10)+1);
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
        mmRepo.findAll().forEach(System.out::println);
    }

    /**
     * 要验证的效果：可以通过一个userId得到他所有
     * 好友发的message，不管是什么类型的都可以
     * 验证的思路：从所有好友关系中随机选出一个好
     * 友关系，然后得到host的所有好友，并且得到他们
     * 发送的message集合，再通过这个方法得到另一个
     * 集合，判断这两个集合相等。
     */
    @Test
    public void findMessagesByFriendShip(){
        Random random = new Random(new Date().getTime());
        CurrentRelation cr = crRepo.findAll()
                .get(random.nextInt(crRepo.findAll().size()));

        List<MetaMessage> checks = new ArrayList<>();
        crRepo.findByRaiserId(cr.getRaiserId()).stream()
                .map(Pair::getAcceptorId)
                .map(mmRepo::findByOwnerId)
                .forEach(checks::addAll);

        List<MetaMessage> metaMessages = mmRepo
                .findMessagesByFriendShip(cr.getRaiserId());
        assert checks.equals(metaMessages);
        mmRepo.findMessagesByFriendShip(
                cr.getRaiserId(),
                PageRequest.of(0, 10, Sort.by("id"))
        ).forEach(System.out::println);
    }

}