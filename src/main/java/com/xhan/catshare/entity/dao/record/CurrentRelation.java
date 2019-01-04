package com.xhan.catshare.entity.dao.record;

import com.xhan.catshare.entity.dto.IdNamePair;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "current_relation")
@AttributeOverrides(value = {
        @AttributeOverride(name = "raiserId", column = @Column(name="host_id", nullable = false)),
        @AttributeOverride(name = "acceptorId", column = @Column(name="friend_id", nullable = false))
})
@EqualsAndHashCode(of = "id", callSuper = false)
public class CurrentRelation extends Pair {

    @Column(name = "relation_id", nullable = false)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "friend_username", nullable = false)
    private String username;

    CurrentRelation(Integer raiserId,
                           Integer acceptorId,
                           String username) {
        this.setAcceptorId(acceptorId);
        this.setRaiserId(raiserId);
        this.setUsername(username);
    }

    /**
     * 这个函数是在添加好友的时候构造一对cr来表征关系
     * 为了查询方便，在这里引入了反范式，在存储一对好
     * 友关系时，正反存储了两遍
     *
     * @param aid   发起者的id
     * @param bid   接受者的id
     * @param aName 发起者的用户名
     * @param bName 接受者的用户名
     * @return 一对cr
     */
    public static List<CurrentRelation> buildCrPairForFriends(
            Integer aid, Integer bid,
            String aName, String bName) {
        CurrentRelation cr1 = new CurrentRelation(aid, bid, bName);
        CurrentRelation cr2 = new CurrentRelation(bid, aid, aName);
        return Arrays.asList(cr1, cr2);
    }

    /**
     * 这个函数是构建Acceptor一方的pair
     *
     * @param cr 从这个参数中构建
     * @return 接收方的id和username
     */
    public static IdNamePair buildPairForAcceptor(CurrentRelation cr) {
        return new IdNamePair(cr.getAcceptorId(), cr.getUsername());
    }

    @Override
    public String toString() {
        return "CurrentRelation{ raiserId:"+
                getRaiserId()+" "+
                " acceptorId:"+
                getAcceptorId() +" "+
                "acceptorName:" +
                getUsername() +" "+
                "}";
    }
}
