package com.xhan.catshare.entity.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "raise_record")
@EqualsAndHashCode(of = "id")
public class RaiseRecord {

    @Column(name = "raise_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ask_id", nullable = false)
    private Integer raiser;

    @Column(name = "answer_id", nullable = false)
    private Integer acceptor;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "raise_date", nullable = false)
    private Date raiseDate;
}
