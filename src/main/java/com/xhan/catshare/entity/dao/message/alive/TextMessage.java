package com.xhan.catshare.entity.dao.message.alive;

import com.xhan.catshare.entity.dao.message.MetaMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AT")
@Data @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends AliveMetaMessage {
    public TextMessage(String content, Integer id) {
        super(content, id);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
