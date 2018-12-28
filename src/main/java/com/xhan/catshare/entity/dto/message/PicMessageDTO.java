package com.xhan.catshare.entity.dto.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true, of = "fileLocation")
public class PicMessageDTO extends MessageDTO {
    private String fileLocation;
}
