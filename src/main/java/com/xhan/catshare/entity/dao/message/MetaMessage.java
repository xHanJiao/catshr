package com.xhan.catshare.entity.dao.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * 这个类是所有动态类的虚基类，在这个类里规定了动态类的存储策略；
 * 将整个类结构存储在同一个表中。在虚基类中规定了所有子类公有的
 * 属性：id标识符，content文字描述，ownerId所有者的Id，
 * sendTime发送的时间戳
 *
 * 它的类继承结构如下：
 * 该基类有两个直接子类（虚）
 * 1、DeletedMetaMessage
 * 2、AliveMetaMessage
 * 这两个类分别代表有效动态和已删除动态（设计上在删除动态时不会
 * 从表中将数据删除，而是通过修改标识位来将其设置为已删除）
 * 这两个直接子类又分别派生出代表图片的PicMessage和代表文字的
 * TextMessage，其中代表文字的TextMessage没有自己特殊的属性，
 * 代表图片的PicMessage有一个文件地址属性
 */
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

//    @Lob @Basic(fetch = FetchType.LAZY)
//    @Column(name = "display_comments", columnDefinition = "TEXT", length = 65535)
//    private String comments;

    public MetaMessage(String content, Integer ownerId) {
        this.content = content;
        this.ownerId = ownerId;
        sendTime = new Date();
//        comments = "";
    }
}
