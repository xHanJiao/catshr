package com.xhan.catshare.service.impl;

import com.xhan.catshare.entity.UncheckedUserDO;
import com.xhan.catshare.entity.UserDO;
import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.entity.dto.UserInfoDTO;
import com.xhan.catshare.entity.projection.CredentialPair;
import com.xhan.catshare.exception.LoginException;
import com.xhan.catshare.exception.RegisterException;
import com.xhan.catshare.repository.UnUserRepository;
import com.xhan.catshare.repository.UserRepository;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.xhan.catshare.exception.RegisterException.ERRORINPUT;
import static com.xhan.catshare.exception.RegisterException.SAMEACCOUNT;


@Service
public class UMHelperImpl implements UserManagerHelper{

    private final UserRepository repo;
    private final UnUserRepository urepo;

    @Autowired
    public UMHelperImpl(UserRepository userRepository, UnUserRepository urepo) {
        this.repo = userRepository;
        this.urepo = urepo;
    }

    @Override
    @Transactional
    public UserDO saveUser(RegisterDTO dto) {
        checkRegisterDTO(dto);
        //fixme still don't know what happened when there is already a user
        return repo.save(UserDO.build(dto));
    }

    @Override
    public boolean checkLoginDTO(LoginDTO dto) {
        Optional<CredentialPair> pair = repo.getPairByAccount(dto.getAccount());
        return pair.map(p -> p.getPassword()
                .equals(dto.getPassword()))
                .orElse(false);
    }

    @Override
    public void checkRegisterDTO(RegisterDTO dto) {
        if (!dto.checkPwd())
            throw new RegisterException(ERRORINPUT);
        if (repo.findIdByAccount(dto.getAccount()).isPresent())
            throw new RegisterException(SAMEACCOUNT);
        //fixme 把错误信息弄成常量 
		//fixme 还有关于email和username的unique约束
    }

    @Override
    public Integer getUserDOId(String account) {
        return repo.findIdByAccount(account)
                .orElseThrow(()-> new LoginException("no account"))
                .getId();
    }

    @Override
    @Transactional
    public UncheckedUserDO saveUncheckedUser(UncheckedUserDO userDO) {
        if(repo.findIdByAccount(userDO.getAccount()).isPresent() ||
                urepo.findByAccount(userDO.getAccount()).isPresent())
            throw new RegisterException(SAMEACCOUNT);

        return urepo.save(userDO);
    }

    @Override
    public void sendEmail(UncheckedUserDO user) {

    }

    @Override
    public UncheckedUserDO findUnUserDO(String account) {
        return urepo.findByAccount(account)
                .orElseThrow(() -> new RegisterException(ERRORINPUT));
    }

}
