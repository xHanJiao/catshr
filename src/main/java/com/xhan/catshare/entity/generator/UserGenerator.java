package com.xhan.catshare.entity.generator;

import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.entity.dto.RegisterDTO;

import java.util.Arrays;
import java.util.stream.Stream;

public class UserGenerator {
    public static final String[] names = {
            "xhan", "xlee", "xchang", "xzhang", "xyan", "xhuu", "xmma",
            "xwang", "xyuu", "xshi", "xxxu", "xjin", "xlii", "xkang"
    };

    private static String emailSuffix = "@123.com";

    public static Stream<UserDO> generator(){
        return Arrays.stream(names)
                .map(UserGenerator::geneUserFromString);
    }

    public static Stream<UserDO> generator(String[] names){
        return Arrays.stream(names)
                .map(UserGenerator::geneUserFromString);
    }

    public static UserDO geneUserFromString(String one){
        String two = one+one;
        int twolen = two.length();
        int onelen = one.length();
        if(onelen<2 || onelen>7)
            throw new IllegalArgumentException("name");
        if(twolen<7 || twolen>14)
            throw new IllegalArgumentException("email");

        UserDO user = new UserDO(
                one,
                two,
                one+ emailSuffix
        );
        user.setChecked(true);
        return user;
    }

    public static RegisterDTO geneRDTOFromString(String one){
        String two = one+one;

        return new RegisterDTO(
                one,
                two,
                two,
                one+emailSuffix
        );
    }

    public static LoginDTO geneLDTO(String one){
        String two = one+one;
        return new LoginDTO(two, two);
    }
}
