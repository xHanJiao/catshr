package com.xhan.catshare.controller;


import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.exception.loregi.RegisterException;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.xhan.catshare.controller.ControllerConstant.*;
import static com.xhan.catshare.exception.loregi.RegisterException.ERRORINPUT;

@Controller
public class RegisterController {

    private final UserManagerHelper helper;

    @Autowired
    public RegisterController(UserManagerHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(value = registerURL, method = RequestMethod.GET)
    public String register(){
        return registerPage;
    }

    @RequestMapping(value = registerURL, method = RequestMethod.POST)
    public String register(@Valid RegisterDTO dto,
                           BindingResult result){
        if(result.hasErrors())
            throw new RegisterException(ERRORINPUT);

        UserDO user = helper.saveUser(dto);

        helper.sendEmail(user);

        return checkPage;
    }

    @RequestMapping(value = registerURL, method = RequestMethod.GET, params = {checkPage, "account"})
    public String checkRegister(@RequestParam String check,
                                @RequestParam String account,
                                RedirectAttributes model){
        UserDO user = helper.findUserByAccount(account);
        model.addFlashAttribute("checkResult",
                user.getIdentifier().equals(check));
        return redirectPrefix+checkURL;
    }

}
