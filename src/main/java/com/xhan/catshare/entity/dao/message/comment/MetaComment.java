package com.xhan.catshare.entity.dao.message.comment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 这个类是针对动态的评论，在这里将针对动态的评论设计成一个实体
 * 而将针对评论的评论设置为它的一个属性。对于删除评论，采取和动
 * 态表一样的方法，将它换一个标识位并放在同一个表中。
 * 针对动态的评论的属性有：
 * 1、代理键
 * 2、所有者的id
 * 3、所针对的动态的id
 * 4、文字描述content（String）
 * 5、针对它的评论（TEXT）
 * 6、评论时间
 *
 * 在实现中，将针对它的评论们做成一个json
 */
@Data @NoArgsConstructor
@Entity @Table(name = "comment")
@EqualsAndHashCode(of = "id")
@DiscriminatorColumn(name = "ALIVESIG", columnDefinition = "CHAR(2) NOT NULL")
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

    /**
     * 在这里要在这个字符串属性中保存发起评论的用户account和评论内容
     */
    @Lob @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "comment_time")
    private Date commentTime;

    MetaComment(Integer ownerId, Integer messageId, String content) {
        this.ownerId = ownerId;
        this.messageId = messageId;
        this.content = content;
        commentTime = new Date();
        comments = "";
    }

    @Override
    public String toString() {
        return "MetaComment{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", messageId=" + messageId +
                ", content='" + content + '\'' +
                ", comments='" + comments + '\'' +
                ", commentTime=" + commentTime +
                '}';
    }
}
