package com.xhan.catshare.controller;


import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.exception.loregi.RegisterException;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.xhan.catshare.controller.ControllerConstant.*;
import static com.xhan.catshare.exception.loregi.RegisterException.ERRORINPUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

    @RequestMapping(value = registerURL, method = RequestMethod.GET, params = {checkPage, "email"})
    public String checkRegister(@RequestParam String check,
                                @RequestParam String email,
                                RedirectAttributes model){
        UserDO user = helper.findUserByEmail(email);
        if (user.getChecked())
            throw new RegisterException("should not check twice");

        if(user.getIdentifier().equals(check)){
            user.setChecked(true);
            helper.saveUser(user);
        }

        model.addFlashAttribute("checkResult",
                user.getIdentifier().equals(check));
        return redirectPrefix+checkURL;
    }

    @ExceptionHandler(value = RegisterException.class)
    public ResponseEntity handleException(RegisterException re){
        return new ResponseEntity<>(re.getMessage(), BAD_REQUEST);
    }

}
