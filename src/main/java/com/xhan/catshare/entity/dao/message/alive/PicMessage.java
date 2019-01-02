package com.xhan.catshare.entity.dao.message.alive;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AP")
@Data @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PicMessage extends AliveMetaMessage {
    @Column(columnDefinition = "VARCHAR(255)", name = "file_location")
    private String fileLocation;

    public PicMessage(String content, Integer ownerId, String fileLocation) {
        super(content, ownerId);
        this.fileLocation = fileLocation;
    }

    @Override
    public String toString() {
        return super.toString()+" "+
                "the file location is:"+
                this.fileLocation;
    }
}
