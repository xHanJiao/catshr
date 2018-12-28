package com.xhan.catshare.controller;

import com.xhan.catshare.entity.dto.message.MessageDTO;
import com.xhan.catshare.service.MessageHelper;
import com.xhan.catshare.service.RelativeHelper;
import com.xhan.catshare.service.UserManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Controller
public class MessageController {

    private final MessageHelper mHelper;
    private final RelativeHelper rHelper;
    private final UserManagerHelper umHelper;

    @Autowired
    public MessageController(MessageHelper mHelper, RelativeHelper rHelper, UserManagerHelper umHelper) {
        this.mHelper = mHelper;
        this.rHelper = rHelper;
        this.umHelper = umHelper;
    }

    @PostMapping(value = "/publishMessage", params = "content")
    public ResponseEntity publishTextMessage(
            @SessionAttribute Integer userId,
            @RequestParam String content)
    {
        mHelper.saveTextMessage(userId, content);
        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    @PostMapping(value = "/publishMessage", params = {"content", "pics"})
    public ResponseEntity publishPicMessage(
            @SessionAttribute Integer userId,
            @RequestParam String content,
            @RequestParam CommonsMultipartFile pics
    ){
        mHelper.savePicMessage(userId, pics, content);
        return new ResponseEntity(HttpStatus.valueOf(204));
    }

    @DeleteMapping(value = "/deleteMessage", params = {"deleteId"})
    public ResponseEntity deleteMessage(
            @SessionAttribute Integer userId,
            @RequestParam Integer deleteId
    ){
      mHelper.deleteMessage(deleteId, userId);
      return new ResponseEntity(HttpStatus.valueOf(204));
    }

    @ResponseBody
    @GetMapping(value = "/getFriendMessages", params = "page")
    public List<MessageDTO> getFriendMessages(
            @SessionAttribute Integer userId,
            @RequestParam Integer page
    ){
        return mHelper.findFriendMessagesById(userId, page);
    }

}
