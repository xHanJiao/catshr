package com.xhan.catshare.service;

import com.xhan.catshare.entity.dao.UserDO;
import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.entity.dto.RegisterDTO;

public interface UserManagerHelper {

    UserDO saveUser(RegisterDTO dto);

    boolean checkLoginDTO(LoginDTO dto);

    void checkRegisterDTO(RegisterDTO dto);

    Integer getUserDOId(String account);

    void sendEmail(UserDO userDO);

    UserDO findUserByAccount(String account);

    UserDO findUserById(Integer userId);
}
