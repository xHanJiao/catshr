package com.xhan.catshare.controller;

import com.xhan.catshare.entity.dto.LoginDTO;
import com.xhan.catshare.exception.loregi.LoginException;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.xhan.catshare.controller.ControllerConstant.*;
import static com.xhan.catshare.exception.loregi.LoginException.ERRORINPUT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 这个类主要处理和登录有关的事务
 */
@Controller
public class LoginController {

    private final UserManagerHelper helper;

    @Autowired
    public LoginController(UserManagerHelper helper) {
        this.helper = helper;
    }

    /**
     * 在"/"或者"/login"收到GET请求时，会返回登录页面的模板
     * @return 登录页面模板名，在ControllerConstant里写好
     */
    @RequestMapping(value = {"/", loginURL}, method = GET)
    public String login(){
        return loginPage;
    }

    /**
     * 在"/"或者"/login"收到POST 请求，并且
     * 请求里携带有账号和密码时，会进行验证，
     * 如果验证通过，会将登录用户id放入到
     * session中。
     */
    @RequestMapping(value = {"/", loginURL}, method = POST)
    public String login(@Valid LoginDTO dto,
                        BindingResult result,
                        HttpSession session){
        if(result.hasErrors() || !helper.checkLoginDTO(dto)){
            throw new LoginException(ERRORINPUT);
        }
        else session.setAttribute("userId", helper.getUserDOId(dto.getAccount()));
        return centerPage;
    }
}
