package com.xhan.catshare.entity;

import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.entity.generator.UserGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static com.xhan.catshare.entity.generator.UserGenerator.geneRDTOFromString;
import static org.junit.Assert.*;

public class UncheckedUserDOTest {

    private UncheckedUserDO userDO;

    @Before
    public void init(){
        RegisterDTO dto = geneRDTOFromString("xhan");
        userDO = UncheckedUserDO.build(dto);
    }

    @Test
    public void buildURL() throws UnsupportedEncodingException {
        System.out.println(userDO.buildURL());
    }

    @Test
    public void build() {
    }
}