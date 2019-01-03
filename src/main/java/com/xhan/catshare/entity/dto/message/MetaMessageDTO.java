package com.xhan.catshare.entity.dto.message;

import lombok.Data;

import java.util.Date;

@Data
public class MetaMessageDTO {
    private String content;
    //fixme 这里怎么有username
    private String username;
    private Integer ownerId;
    private Integer id;
    private Date sendTime;
}
