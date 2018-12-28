package com.xhan.catshare.entity.dao.message.deleted;

import com.xhan.catshare.entity.dao.message.alive.TextMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DT")
@Data @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeletedTextMessage extends DeletedMetaMessage {

    public DeletedTextMessage(TextMessage te){
        super(te);
    }
}
