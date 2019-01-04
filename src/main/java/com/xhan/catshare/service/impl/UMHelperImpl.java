package com.xhan.catshare.service.impl;

import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.entity.projection.CredentialPair;
import com.xhan.catshare.exception.loregi.LoginException;
import com.xhan.catshare.exception.loregi.RegisterException;
import com.xhan.catshare.repository.user.UserRepository;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static com.xhan.catshare.controller.ControllerConstant.*;
import static com.xhan.catshare.entity.dao.user.UserDO.buildUncheckedUser;
import static com.xhan.catshare.exception.loregi.LoginException.NOEMAIL;
import static com.xhan.catshare.exception.loregi.RegisterException.*;


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
        Optional<CredentialPair> pair = repo.getPairByEmail(dto.getEmail());
        return pair.map(p -> p.getPassword()
                .equals(dto.getPassword()))
                .orElse(false);
    }

    @Override
    public void checkRegisterDTO(RegisterDTO dto) {
        if (dto.pwdNotEqual())
            throw new RegisterException(ERRORINPUT);
        if (repo.findIdByEmail(dto.getEmail()).isPresent())
            throw new RegisterException(SAMEACCOUNT);
    }

    @Override
    public Integer getUserDOId(String email) {
        return repo.findIdByEmail(email)
                .orElseThrow(() -> new LoginException(NOEMAIL))
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
    public UserDO findUserByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RegisterException(NOEMAIL));
    }

    @Override
    public UserDO saveUser(UserDO user) {
        return repo.save(user);
    }

}
