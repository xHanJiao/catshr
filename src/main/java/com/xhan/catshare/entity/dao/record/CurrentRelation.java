package com.xhan.catshare.entity.dao.record;

import com.xhan.catshare.entity.dto.AccountNamePair;
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

    @Column(name = "friend_account", nullable = false)
    String account;

    @Column(name = "friend_username", nullable = false)
    String username;

    public CurrentRelation(Integer raiserId,
                           Integer acceptorId,
                           String account,
                           String username) {
        this.setAcceptorId(acceptorId);
        this.setRaiserId(raiserId);
        this.setAccount(account);
        this.setUsername(username);
    }

    public static List<CurrentRelation> buildPair(
            Integer aid, Integer bid, String aAcc,
            String aName, String bAcc, String bName){
        CurrentRelation cr1 = new CurrentRelation(aid, bid, bAcc, bName);
        CurrentRelation cr2 = new CurrentRelation(bid, aid, aAcc, aName);
        return Arrays.asList(cr1, cr2);
    }

    public static AccountNamePair buildPair(CurrentRelation cr){
        return new AccountNamePair(cr.getAccount(), cr.getUsername());
    }

    @Override
    public String toString() {
        return "CurrentRelation{ raiserId:"+
                getRaiserId()+" "+
                " acceptorId:"+
                getAcceptorId() +" "+
                "acceptorAccount:" +
                getAccount()+ " "+
                "acceptorName:" +
                getUsername() +" "+
                "}";
    }
}
