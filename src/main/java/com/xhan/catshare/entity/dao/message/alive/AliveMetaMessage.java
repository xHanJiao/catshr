package com.xhan.catshare.entity.dao.message.alive;

import com.xhan.catshare.entity.dao.message.MetaMessage;

public abstract class AliveMetaMessage extends MetaMessage {
    public AliveMetaMessage() {
    }

    public AliveMetaMessage(String content, Integer ownerId) {
        super(content, ownerId);
    }
}
