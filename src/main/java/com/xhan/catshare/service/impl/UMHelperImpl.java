package com.xhan.catshare.service.impl;

import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.entity.projection.CredentialPair;
import com.xhan.catshare.exception.LoginException;
import com.xhan.catshare.exception.RegisterException;
import com.xhan.catshare.repository.UserRepository;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static com.xhan.catshare.controller.ControllerConstant.checkSubject;
import static com.xhan.catshare.controller.ControllerConstant.checkText;
import static com.xhan.catshare.controller.ControllerConstant.mailHost;
import static com.xhan.catshare.entity.dao.user.UserDO.buildUncheckedUser;
import static com.xhan.catshare.exception.LoginException.NOACCOUNT;
import static com.xhan.catshare.exception.RegisterException.*;


@Service
public class UMHelperImpl implements UserManagerHelper{

    private final UserRepository repo;
    private final JavaMailSender sender;

    @Autowired
    public UMHelperImpl(UserRepository userRepository, JavaMailSender sender) {
        this.repo = userRepository;
        this.sender = sender;
    }

    @Override
    @Transactional
    public UserDO saveUser(RegisterDTO dto) {
        checkRegisterDTO(dto);
        return repo.save(buildUncheckedUser(dto));
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
    }

    @Override
    public Integer getUserDOId(String account) {
        return repo.findIdByAccount(account)
                .orElseThrow(()-> new LoginException(NOACCOUNT))
                .getId();
    }

    @Override
    public void sendEmail(UserDO user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailHost);
        message.setTo(user.getEmail());
        message.setSubject(checkSubject);
        try {
            message.setText(checkText + user.getURL());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RegisterException(CANNOTSENDMAIL);
        }
        sender.send(message);
    }

    @Override
    public UserDO findUserByAccount(String account) {
        return repo.findByAccount(account)
                .orElseThrow(()->new RegisterException(NOACCOUNT));
    }

    @Override
    public UserDO findUserById(Integer userId) {
        return repo.findById(userId)
                .orElseThrow(() -> new RegisterException(NOID));
    }

}
