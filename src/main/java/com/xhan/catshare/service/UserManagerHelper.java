package com.xhan.catshare.service;

import com.xhan.catshare.entity.UserDO;
import com.xhan.catshare.entity.dto.UserInfoDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserManagerHelper {

    String generateCheckURL(UserInfoDTO info);

    UserDO saveUser(UserInfoDTO infoDTO);
}
