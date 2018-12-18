package com.xhan.catshare.entity.dao.record;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "delete_record")
@EqualsAndHashCode(callSuper = false, of = "id")
public class DeleteRecord extends Pair{

    @Column(name = "relation_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "raise_time", nullable = false)
    private Date raiseTime;

    public DeleteRecord(Integer acceptorId, Integer raiserId) {
        super(raiserId, acceptorId);
        setRaiseTime(new Date());
    }
}
