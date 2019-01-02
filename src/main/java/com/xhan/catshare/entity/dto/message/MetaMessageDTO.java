package com.xhan.catshare.entity.dto.message;

import lombok.Data;

@Data
public class MetaMessageDTO {
    private String content;
    //fixme 这里怎么有username
    private String username;
    private Integer id;
}
