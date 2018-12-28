package com.xhan.catshare.entity.dao.message.comment;

import com.xhan.catshare.entity.dao.message.comment.MetaComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("A")
public class Comment extends MetaComment{

}
