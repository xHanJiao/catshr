package com.xhan.catshare.entity.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Data
@MappedSuperclass
public class UserInfoDTO {

    public UserInfoDTO() {
    }

    public UserInfoDTO(@Size(min = 7, max = 14) String account,
                       @Size(min = 2, max = 7) String username,
                       @Size(min = 7, max = 14) String password,
                       @Email String email) {
        setAccount(account);
        setPassword(password);
        setEmail(email);
        setUsername(username);
    }

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

}
