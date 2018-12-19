package com.xhan.catshare.controller;

import com.xhan.catshare.entity.dto.AccountNamePair;
import com.xhan.catshare.exception.records.RecordException;
import com.xhan.catshare.service.RelativeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xhan.catshare.controller.ControllerConstant.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.valueOf;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class RelativeController {

    private final RelativeHelper relativeHelper;

    @Autowired
    public RelativeController(RelativeHelper relativeHelper) {
        this.relativeHelper = relativeHelper;
    }

    @PostMapping(value = friendURL, params = "acceptorAccount")
    public ResponseEntity addFriend(
            @RequestParam String acceptorAccount,
            @SessionAttribute Integer userId
    ){
        relativeHelper.checkAndSaveRaiseRecord(acceptorAccount,userId);
        return new ResponseEntity(valueOf(200));
    }

    @PostMapping(value = friendURL, params = "accept")
    public ResponseEntity confirm(
            @RequestParam String accept,
            @SessionAttribute Integer userId
    ){
        relativeHelper.confirmRaiseRecord(accept, userId);
        return new ResponseEntity(valueOf(200));
    }

    @PostMapping(value = friendURL, params = "delete")
    public ResponseEntity deleteFriend(
            @RequestParam String delete,
            @SessionAttribute Integer userId
    ){
        relativeHelper.deleteFriend(delete, userId);
        return new ResponseEntity(valueOf(204));
    }

    @ResponseBody
    @GetMapping(value = waitingRaiseURL)
    public List<AccountNamePair> getRaiseRecord(@SessionAttribute Integer userId){
        return relativeHelper.fromIdGetWaitingRecords(userId);
    }

    @ResponseBody
    @GetMapping(value = currentRecordURL)
    public List<AccountNamePair> getCurrentRelations(@SessionAttribute Integer userId){
        return relativeHelper.fromIdGetCurrentFriend(userId);
    }

    @ExceptionHandler(value = RecordException.class)
    public ResponseEntity handleExceptions(RecordException cre){
        return new ResponseEntity<>(cre.getMessage(), BAD_REQUEST);
    }

}
