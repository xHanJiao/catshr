package com.xhan.catshare.entity.dao.message.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("AC")
public class Comment extends MetaComment{
    public Comment(Integer ownerId, Integer messageId, String content) {
        super(ownerId, messageId, content);
    }

    @Override
    public String toString() {
        return "Comment{} " + super.toString();
    }
}
