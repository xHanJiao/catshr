package com.xhan.catshare.entity.dao.record;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "delete_record")
@EqualsAndHashCode(callSuper = true, exclude = "raiseTime")
public class DeleteRecord extends CurrentRelation{

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "raise_time", nullable = false)
    private Date raiseTime;

}
