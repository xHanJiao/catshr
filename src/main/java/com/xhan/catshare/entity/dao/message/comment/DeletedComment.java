package com.xhan.catshare.entity.dao.message.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("DC")
public class DeletedComment extends MetaComment{

}
