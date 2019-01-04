package com.xhan.catshare.service;

import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.entity.dto.RegisterDTO;

public interface UserManagerHelper {

    UserDO saveUser(RegisterDTO dto);

    boolean checkLoginDTO(LoginDTO dto);

    void checkRegisterDTO(RegisterDTO dto);

    Integer getUserDOId(String email);

    void sendEmail(UserDO userDO);

    UserDO findUserByEmail(String email);

    UserDO saveUser(UserDO user);
}
