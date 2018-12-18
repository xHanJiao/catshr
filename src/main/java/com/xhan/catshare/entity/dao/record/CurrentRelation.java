package com.xhan.catshare.entity.dao.record;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "current_relation")
@EqualsAndHashCode(of = "id", callSuper = false)
public class CurrentRelation extends Pair {

    @Column(name = "relation_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public CurrentRelation(Integer raiserId, Integer acceptorId) {
        this.setAcceptorId(acceptorId);
        this.setRaiserId(raiserId);
    }
}
