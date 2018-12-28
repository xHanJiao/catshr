package com.xhan.catshare.entity.dao.message.deleted;

import com.xhan.catshare.entity.dao.message.alive.PicMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DP")
@Data @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeletedPicMessage extends DeletedMetaMessage {

    @Column(columnDefinition = "VARCHAR(255)", name = "file_location")
    private String fileLocation;

    public DeletedPicMessage(PicMessage picMessage){
        super(picMessage);
        this.fileLocation = picMessage.getFileLocation();
    }
}
