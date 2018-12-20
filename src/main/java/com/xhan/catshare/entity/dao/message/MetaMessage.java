package com.xhan.catshare.entity.dao.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Data @NoArgsConstructor
@Entity @Table(name = "message")
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "MESSAGE_TYPE", columnDefinition = "CHAR(1)")
public abstract class MetaMessage {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "message_id", nullable = false)
    protected Integer id;

    @Column(nullable = false, name = "description")
    protected String description;

    @Column(nullable = false, name = "owner_id")
    protected Integer ownerId;

    @Column(nullable = false, name = "send_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date sendTime;
}
