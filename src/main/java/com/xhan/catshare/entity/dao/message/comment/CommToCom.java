package com.xhan.catshare.entity.dao.message.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("C")
public class CommToCom extends MetaComment {

    @Column(name = "acceptor_name")
    private String acceptorName;

    @Column(name = "acceptor_id")
    private Integer acceptorId;
}
