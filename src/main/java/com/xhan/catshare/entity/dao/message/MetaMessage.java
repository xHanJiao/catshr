package com.xhan.catshare.entity.dao.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import static javax.persistence.GenerationType.IDENTITY;

@Data @NoArgsConstructor
@Entity @Table(name = "message")
@EqualsAndHashCode(of = "id")
@DiscriminatorColumn(columnDefinition = "CHAR(2)", length = 2)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MetaMessage {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Integer id;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "owner_id")
    private Integer ownerId;

    @Column(nullable = false, name = "send_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date sendTime;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "display_comments", columnDefinition = "TEXT", length = 65535)
    private String comments;

    public MetaMessage(String content, Integer ownerId) {
        this.content = content;
        this.ownerId = ownerId;
        sendTime = new Date();
        comments = "";
    }
}
