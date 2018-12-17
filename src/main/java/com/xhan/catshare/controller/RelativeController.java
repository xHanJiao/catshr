package com.xhan.catshare.controller;

import com.xhan.catshare.entity.dao.user.UserDO;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.xhan.catshare.controller.ControllerConstant.*;

@Controller
public class RelativeController {

    private final UserManagerHelper helper;

    @Autowired
    public RelativeController(UserManagerHelper helper) {
        this.helper = helper;
    }

    @RequestMapping(value = friendURL, method = RequestMethod.POST, params = "add")
    public String addFriend(@RequestParam String add,
                            @SessionAttribute Integer userId){
        UserDO toAdd = helper.findUserByAccount(add);
        UserDO host = helper.findUserById(userId);

        return null;
    }

}
