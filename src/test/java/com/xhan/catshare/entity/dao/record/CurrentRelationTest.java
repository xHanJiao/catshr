package com.xhan.catshare.entity.dao.record;

import com.xhan.catshare.repository.record.CurrentRecordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.xhan.catshare.entity.dao.record.CurrentRelation.buildPair;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CurrentRelationTest {

    @Autowired private CurrentRecordRepository crRepo;

    @Test
    public void testPair(){
        List<CurrentRelation> crs = buildPair(1,  2, "xhanxhan",
                "xhan", "xchaxcha", "xcha");
        crs.forEach(
                cr -> crRepo.save(cr)
        );
        crRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void testSuperEquals(){
        CurrentRelation current1 = new CurrentRelation();
        current1.setId(1);
        CurrentRelation current2 = new CurrentRelation();
        current2.setId(1);
        assertEquals(current1, current2);

        RaiseRecord r1 = new RaiseRecord();
        r1.setId(1);
        RaiseRecord r2 = new RaiseRecord();
        r2.setId(1);
        assertEquals(r1, r2);
        assertNotEquals(r1, current1);

        DeleteRecord d1 = new DeleteRecord();
        DeleteRecord d2 = new DeleteRecord();
        d1.setId(1);
        d2.setId(1);
        assertEquals(d1, d2);
        assertNotEquals(d1, r1);
        assertNotEquals(d1, current1);

    }

    @Test
    public void testChildEquals(){
    }

}