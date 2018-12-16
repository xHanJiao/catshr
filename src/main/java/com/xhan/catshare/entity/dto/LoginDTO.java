package com.xhan.catshare.entity.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String account;
    private String password;

    public LoginDTO(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public LoginDTO() {

    }
}
