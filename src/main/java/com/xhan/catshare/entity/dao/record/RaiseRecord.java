package com.xhan.catshare.entity.dao.record;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "raise_record")
@EqualsAndHashCode(callSuper = true, exclude = {"raiseTime", "currentState"})
public class RaiseRecord extends DeleteRecord{

    @Temporal(TIMESTAMP)
    @Column(name = "raise_time", nullable = false)
    private Date raiseTime;

    @Temporal(TIMESTAMP)
    @Column(name = "accept_time", nullable = false)
    private Date acceptTime;

    @Column(name = "current_state", nullable = false)
    private String currentState;

}
