package com.xhan.catshare.entity.dao.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Data @Entity
@Table(name = "message")
@DiscriminatorValue("T")
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends MetaMessage{

}
