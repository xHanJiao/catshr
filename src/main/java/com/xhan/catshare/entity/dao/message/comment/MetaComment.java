package com.xhan.catshare.entity.dao.message.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity @Table(name = "comment")
@Data
@NoArgsConstructor
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

    @NotBlank
    @Column(name = "content", nullable = false, columnDefinition = "VARCHAR(140)")
    private String content;

    @NotBlank
    @Column(name = "owner_name", nullable = false, columnDefinition = "VARCHAR(7)")
    private String ownerName;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "comment_time", nullable = false)
    private Date commentTime;
}
