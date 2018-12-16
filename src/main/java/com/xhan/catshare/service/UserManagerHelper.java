package com.xhan.catshare.service;

import com.xhan.catshare.entity.UncheckedUserDO;
import com.xhan.catshare.entity.UserDO;
import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.entity.dto.UserInfoDTO;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

public interface UserManagerHelper {

    UserDO saveUser(RegisterDTO dto);

    boolean checkLoginDTO(LoginDTO dto);

    void checkRegisterDTO(RegisterDTO dto);

    Integer getUserDOId(String account);

    UncheckedUserDO saveUncheckedUser(UncheckedUserDO userDO);

    void sendEmail(UncheckedUserDO user);

    UncheckedUserDO findUnUserDO(String account);
}
