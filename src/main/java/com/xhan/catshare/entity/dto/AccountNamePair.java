package com.xhan.catshare.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AccountNamePair {
    private String account;
    private String username;

    public AccountNamePair(@Size(min = 7, max = 14) String account, @Size(min = 2, max = 7) String username) {
        this.account = account;
        this.username = username;
    }
}
