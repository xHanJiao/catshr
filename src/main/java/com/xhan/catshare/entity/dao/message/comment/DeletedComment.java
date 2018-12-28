package com.xhan.catshare.entity.dao.message.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("D")
public class DeletedComment extends MetaComment{

}
