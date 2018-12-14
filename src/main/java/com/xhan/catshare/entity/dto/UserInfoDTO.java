package com.xhan.catshare.entity.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@MappedSuperclass
public class UserInfoDTO {
    @Size(min = 7, max = 14)
    @Column(name = "account", nullable = false)
    String account;

    @Size(min = 2, max = 7)
    @Column(name = "username", nullable = false)
    String username;

    @Size(min = 7, max = 14)
    @Column(name = "password", nullable = false)
    String password;

    @Email
    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "registerDate", nullable = false)
    Date registerDate;
}
