package com.xhan.catshare.repository.message;

import com.xhan.catshare.entity.dao.message.PicMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Temporal;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PicMessageRepositoryTest {

    @Autowired private PicMessageRepository pmRepo;

    @Test
    public void insert(){
        PicMessage pm = new PicMessage();
        pm.setFileLocation("classpath:/pic/pic001.png");
        pm.setDescription("blades helmets, pretty many things to suit your needs");
        pm.setOwnerId(1);
        pm.setSendTime(new Date());

        pm = pmRepo.save(pm);
        System.out.println(pm);
    }

}