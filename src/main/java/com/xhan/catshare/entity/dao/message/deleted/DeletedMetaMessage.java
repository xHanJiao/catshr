package com.xhan.catshare.entity.dao.message.deleted;

import com.xhan.catshare.entity.dao.message.MetaMessage;
import com.xhan.catshare.entity.dao.message.alive.AliveMetaMessage;

public abstract class DeletedMetaMessage extends MetaMessage {
    public DeletedMetaMessage() {
    }

    public DeletedMetaMessage(String content, Integer ownerId) {
        super(content, ownerId);
    }

    public DeletedMetaMessage(AliveMetaMessage aliveMetaMessage){
        setContent(aliveMetaMessage.getContent());
        setOwnerId(aliveMetaMessage.getOwnerId());
        setSendTime(aliveMetaMessage.getSendTime());
    }
}
