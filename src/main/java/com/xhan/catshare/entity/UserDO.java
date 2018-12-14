package com.xhan.catshare.entity;

import com.xhan.catshare.entity.dto.UserInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity(name = "user")
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserDO extends UserInfoDTO {
    @Id @GeneratedValue(strategy = IDENTITY)
    Integer id;
}
