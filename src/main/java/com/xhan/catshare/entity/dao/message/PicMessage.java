package com.xhan.catshare.entity.dao.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data @Entity
@Table(name = "message")
@DiscriminatorValue("P")
@EqualsAndHashCode(callSuper = true)
public class PicMessage extends TextMessage {

    @Column(name = "T_FILE_LOCATION")
    private String fileLocation;

}
