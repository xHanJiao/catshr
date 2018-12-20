package com.xhan.catshare.entity.dao.message;

import com.xhan.catshare.repository.message.MetaMessageRepository;
import com.xhan.catshare.repository.message.PicMessageRepository;
import com.xhan.catshare.repository.message.TextMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Temporal;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MetaMessageTest {

    @Autowired private PicMessageRepository pmRepo;
    @Autowired private TextMessageRepository tmRepo;
    @Autowired private MetaMessageRepository mmRepo;

    @Test
    public void multiGet(){
        TextMessage tm = new TextMessage();
        tm.setOwnerId(0);
        tm.setDescription("no dis");
        tm.setSendTime(new Date());
        PicMessage pm = new PicMessage();
        pm.setSendTime(new Date());
        pm.setOwnerId(0);
        pm.setDescription("yes medem");
        pm.setFileLocation("no content");
        pm.setSendTime(new Date());

        pm = pmRepo.save(pm);
        System.out.println(pm.getId());
        tm = tmRepo.save(tm);
        System.out.println(tm.getId());

        List<MetaMessage> mms = mmRepo.findByOwnerId(0);
        System.out.println("the size is: "+mms.size());
        mms.forEach(m -> System.out.println(m.getDescription()));
    }
}