package com.xhan.catshare.repository;

import com.xhan.catshare.entity.generator.UserGenerator;
import com.xhan.catshare.repository.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired private UserRepository repo;

    @Before
    public void init(){
        UserGenerator.generator()
                .forEach(u -> repo.save(u));
    }

    @After
    public void clear(){
        repo.deleteAll();
    }
    @Test
    public void getByAccount() {
        Arrays.stream(UserGenerator.names)
                .forEach(
                        name -> assertTrue(
                            repo.findByAccount(name+name).isPresent()
                        )
                );
    }
}