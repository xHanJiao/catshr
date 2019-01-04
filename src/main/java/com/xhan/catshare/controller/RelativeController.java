package com.xhan.catshare.controller;

import com.xhan.catshare.entity.dto.IdNamePair;
import com.xhan.catshare.exception.records.IdNotFoundException;
import com.xhan.catshare.exception.records.RecordException;
import com.xhan.catshare.service.RelativeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xhan.catshare.controller.ControllerConstant.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.valueOf;

@RestController
public class RelativeController {

    private final RelativeHelper relativeHelper;

    @Autowired
    public RelativeController(RelativeHelper relativeHelper) {
        this.relativeHelper = relativeHelper;
    }

    @PostMapping(value = friendURL, params = "acceptorId")
    public ResponseEntity addFriend(
            @RequestParam Integer acceptorId,
            @SessionAttribute Integer userId
    ){
        if (acceptorId == null) throw new IdNotFoundException();

        relativeHelper.checkAndSaveRaiseRecord(acceptorId, userId);
        return new ResponseEntity(valueOf(200));
    }

    @PostMapping(value = friendURL, params = "confirm")
    public ResponseEntity confirm(
            @RequestParam Integer confirm,
            @SessionAttribute Integer userId
    ){
        relativeHelper.confirmRaiseRecord(confirm, userId);
        return new ResponseEntity(valueOf(200));
    }

    @PostMapping(value = friendURL, params = "delete")
    public ResponseEntity deleteFriend(
            @RequestParam Integer delete,
            @SessionAttribute Integer userId
    ){
        relativeHelper.deleteFriend(delete, userId);
        return new ResponseEntity(valueOf(204));
    }

    @ResponseBody
    @GetMapping(value = waitingRaiseURL)
    public List<IdNamePair> getRaiseRecord(@SessionAttribute Integer userId) {
        return relativeHelper.fromIdGetWaitingRecords(userId);
    }

    @ResponseBody
    @GetMapping(value = currentRecordURL)
    public List<IdNamePair> getCurrentRelations(@SessionAttribute Integer userId) {
        return relativeHelper.fromIdGetCurrentFriend(userId);
    }

    @ExceptionHandler(value = RecordException.class)
    public ResponseEntity handleExceptions(RecordException cre){
        return new ResponseEntity<>(cre.getMessage(), BAD_REQUEST);
    }

}
