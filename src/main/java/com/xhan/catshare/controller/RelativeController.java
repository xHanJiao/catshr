package com.xhan.catshare.controller;

import com.xhan.catshare.exception.records.RecordException;
import com.xhan.catshare.service.RelativeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.xhan.catshare.controller.ControllerConstant.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RelativeController {

    private final RelativeHelper relativeHelper;

    @Autowired
    public RelativeController(RelativeHelper relativeHelper) {
        this.relativeHelper = relativeHelper;
    }

    @PostMapping(value = friendURL, params = "acceptorAccount")
    public ResponseEntity<String> addFriend(
            @RequestParam String acceptorAccount,
            @SessionAttribute Integer userId
    ){
        relativeHelper.checkAndSaveRaiseRecord(acceptorAccount,userId);
        return ok("record Created");
    }

    @PostMapping(value = friendURL, params = "accept")
    public ResponseEntity<String> confirm(
            @RequestParam String accept,
            @SessionAttribute Integer userId
    ){
        relativeHelper.confirmRaiseRecord(accept, userId);
        return ok("confirming Successfully");
    }

    @PostMapping(value = friendURL, params = "delete")
    public ResponseEntity<String> deleteFriend(
            @RequestParam String delete,
            @SessionAttribute Integer userId
    ){
        relativeHelper.deleteFriend(delete, userId);
        return ok("delete Successfully");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(RecordException cre){
        return new ResponseEntity<>(cre.getMessage(), BAD_REQUEST);
    }

}
