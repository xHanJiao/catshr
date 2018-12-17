package com.xhan.catshare.entity.dao.record;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@MappedSuperclass
@Entity(name = "current_relation")
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CurrentRelation {
    @Column(name = "relation_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "raiser_id", nullable = false)
    private Integer raiserId;

    @Column(name = "acceptor_id", nullable = false)
    private Integer acceptorId;
}
