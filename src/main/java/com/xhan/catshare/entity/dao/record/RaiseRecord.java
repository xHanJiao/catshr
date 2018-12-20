package com.xhan.catshare.entity.dao.record;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@Entity(name = "raise_record")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
public class RaiseRecord extends Pair{

    public static final String ACCEPT = "acce";
    public static final String ABORT = "abor";
    public static final String WAIT = "wait";

    @Column(name = "relation_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TIMESTAMP)
    @Column(name = "accept_time")
    private Date acceptTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "raise_time", nullable = false)
    private Date raiseTime;

    @Column(name = "current_state", nullable = false, columnDefinition = "CHAR(4)")
    private String currentState;

    @Override
    public String toString() {
        return "RaiseRecord{" +
                "id=" + id +
                ", raiserId=" + getRaiserId() +
                ", acceptorId=" + getAcceptorId() +
                ", acceptTime=" + acceptTime +
                ", raiseTime=" + raiseTime +
                ", currentState='" + currentState + '\'' +
                '}';
    }

    public RaiseRecord(Integer raiserId, Integer acceptorId) {
        super(raiserId, acceptorId);
        setRaiseTime(new Date());
        setCurrentState(WAIT);
    }
}
