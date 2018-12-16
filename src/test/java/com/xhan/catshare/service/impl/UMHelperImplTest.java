package com.xhan.catshare.service.impl;

import com.xhan.catshare.entity.UserDO;
import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.entity.generator.UserGenerator;
import com.xhan.catshare.exception.RegisterException;
import com.xhan.catshare.repository.UserRepository;
import com.xhan.catshare.service.UserManagerHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.xhan.catshare.entity.generator.UserGenerator.geneLDTO;
import static com.xhan.catshare.entity.generator.UserGenerator.geneRDTOFromString;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UMHelperImplTest {

    @Autowired private UserRepository repo;

    @Autowired private UserManagerHelper helper;

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
    public void generateCheckURL() {
    }

    @Test
    public void saveUser() {
        int count = 0;
        RegisterDTO normal = geneRDTOFromString("test");
        UserDO testDO = helper.saveUser(normal);
        assertNotNull(testDO);
        count += 1;
        RegisterDTO same = geneRDTOFromString("xhan");
        try {
            helper.saveUser(same);
        }catch (RegisterException e){
            assertEquals(e.getMessage(), "same account");
            count += 2;
        }
        RegisterDTO disPwd = geneRDTOFromString("pass");
        disPwd.setConfirmPassword("asdfgggj");
        try {
            helper.saveUser(disPwd);
        }catch (RegisterException e){
            assertEquals(e.getMessage(), "error input");
            count += 4;
        }
        assertEquals(count, 7);

    }

    @Test
    public void checkLoginDTO() {
        LoginDTO xhan = geneLDTO(UserGenerator.names[0]);
        helper.checkLoginDTO(xhan);

        xhan.setAccount("error");
        assertFalse(helper.checkLoginDTO(xhan));

        xhan = geneLDTO(UserGenerator.names[0]);
        xhan.setPassword("weeoewewd");
        assertFalse(helper.checkLoginDTO(xhan));
    }

    @Test
    public void checkRegisterDTO() {
        int count = 0;
        RegisterDTO normal = geneRDTOFromString("test");
        helper.checkRegisterDTO(normal);
        normal.setConfirmPassword("dsfaafas");
        count += 1;

        try {
            helper.checkRegisterDTO(normal);
        }catch (RegisterException e){
            assertEquals(e.getMessage(), "error input");
            count += 2;
        }
        RegisterDTO same = geneRDTOFromString(UserGenerator.names[0]);
        try {
            helper.checkRegisterDTO(same);
        }catch (RegisterException ex){
            assertEquals(ex.getMessage(), "same account");
            count += 4;
        }
        assertEquals(count, 7);
    }

    @Test
    public void getUserDOId() {
        String name = UserGenerator.names[0] + UserGenerator.names[0];
        Integer i = helper.getUserDOId(name);
        assertNotNull(i);
        System.out.println(i);
    }
}