package com.xhan.catshare.entity;

import com.xhan.catshare.entity.dto.RegisterDTO;
import com.xhan.catshare.entity.dto.UserInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

@Data
@Entity(name = "user")
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserDO extends UserInfoDTO {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, name = "user_id")
    Integer id;

    @Temporal(value = DATE)
    @Column(name = "register_date", nullable = false)
    Date registerDate;

    public UserDO() {
        super();
    }

    public UserDO(String account, String username, String password, String email) {
        super(account, username, password, email);
        this.setRegisterDate(new Date());
    }
    public UserDO(RegisterDTO dto){
        super(dto.getAccount(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail());
        this.setRegisterDate(new Date());
    }
    public static UserDO build(RegisterDTO dto) {
        return new UserDO(
                dto.getAccount(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getEmail());
    }
}
