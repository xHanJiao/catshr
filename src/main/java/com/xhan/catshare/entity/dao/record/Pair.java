package com.xhan.catshare.entity.dao.record;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@NoArgsConstructor
public class Pair {
    @Column(name = "raiser_id", nullable = false)
    private Integer raiserId;

    @Column(name = "acceptor_id", nullable = false)
    private Integer acceptorId;

    public Pair(Integer raiserId, Integer acceptorId) {
        this.raiserId = raiserId;
        this.acceptorId = acceptorId;
    }
}
