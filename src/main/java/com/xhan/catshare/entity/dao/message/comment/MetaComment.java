package com.xhan.catshare.entity.dao.message.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data @NoArgsConstructor
@Entity @Table(name = "comment")
@EqualsAndHashCode(of = "id")
@DiscriminatorColumn(columnDefinition = "CHAR(1)")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MetaComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Integer id;

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @Column(name = "message_id", nullable = false)
    private Integer messageId;

    @Column(name = "content", nullable = false, columnDefinition = "VARCHAR(140)")
    private String content;

    @Lob @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "comment_time")
    private Date commentTime;

}
