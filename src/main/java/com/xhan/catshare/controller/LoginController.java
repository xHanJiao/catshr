package com.xhan.catshare.controller;

import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.exception.LoginException;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.xhan.catshare.controller.ControllerConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class LoginController {

    private final UserManagerHelper helper;

    @Autowired
    public LoginController(UserManagerHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(value = {"/", loginURL}, method = GET)
    public String login(){
        return loginPage;
    }

    @RequestMapping(value = {"/", loginURL}, method = POST)
    public String login(@Valid LoginDTO dto,
                        BindingResult result,
                        HttpSession session){
        if(result.hasErrors() || !helper.checkLoginDTO(dto)){
            throw new LoginException("error input");
        }
        else session.setAttribute("userId", helper.getUserDOId(dto.getAccount()));
        return centerPage;
    }
}
