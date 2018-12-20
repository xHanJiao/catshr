package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.TextMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Objects;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TextMessageRepositoryTest {

    @Autowired private TextMessageRepository tmRepo;

    @Test
    public void save(){
        assert Objects.nonNull(tmRepo);

        TextMessage mess = new TextMessage();
        mess.setDescription("what a fine day with you around.");
        mess.setOwnerId(0);
        mess.setSendTime(new Date());
        tmRepo.save(mess);
        System.out.println(mess.getId());
    }

}